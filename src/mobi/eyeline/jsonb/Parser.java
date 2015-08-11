package mobi.eyeline.jsonb;

import sun.security.util.PendingException;

import java.util.Iterator;
import java.util.List;

/**
 * Parser class creates tree structure according JSON rules. Input is a stream of tokens from Lexer class.
 * Base idea is iterating through tokens and checking JSON rules.
 * Created by Artem Voronov on 03.08.2015.
 */
public class Parser {
    private Token lastToken;
    private Token currentToken;
    private TokenType lastTokenType;
    private TokenType currentTokenType;
    //all tokens
    private List<Token> tokens;
    private Iterator<Token> iterator;
    private Node tree;

    /**
     * initiate token stream, iterator for them, and root of the tree
     * @param input JSON-string
     * @throws LexerException for unknown tokens
     */
    //lexical analysis -> stream of tokens
    public void init(String input) throws LexerException {
        setTokens(Lexer.lex(input));
        iterator = getTokens().iterator();
        //EMPTY - special status for not initialized token (just for starting of iteration)
        setLastToken(new Token(TokenType.EMPTY, ""));
        setLastTokenType(TokenType.EMPTY);
        setCurrentToken(new Token(TokenType.EMPTY, ""));
        setCurrentTokenType(TokenType.EMPTY);
        setTree(new Node(":root", ""));
        getTree().setType(NodeType.ROOT);
    }

    /**
     * iteration step, it keeps current and last token.
     * @throws ParserException if current token is EOF
     */
    public void next() throws ParserException {
        if (getCurrentTokenType().equals(TokenType.EOF)) {
            throw new ParserException("Calling next() for EOF token", getCurrentToken(), getLastToken());
        }

        setLastToken(getCurrentToken());
        setLastTokenType(getCurrentToken().getType());
        if (iterator.hasNext()) {
            setCurrentToken(iterator.next());
            setCurrentTokenType(getCurrentToken().getType());
        }

        //TODO: comment debug info later
        System.out.println("last: " + getLastToken().toString());
        System.out.println("current: " + getCurrentToken().toString());
        System.out.println("----------");
    }

    /**
     * start looking these tokens and using json rules for creating tree structure
     * @param parent Node for parsing
     * @throws ParserException if input has double LBRACE "{{....", should be "{string...."
     */
    public void parse(Node parent) throws ParserException {
        //  end of recursion
        if (getCurrentTokenType().equals(TokenType.EOF)) {
            return;
        } else {
            //first of all we should have {...} or [...]

            // if {...} then parse Object
            if (getCurrentTokenType().equals(TokenType.LBRACE)) {
                //check for double braces
                if (getLastTokenType().equals(TokenType.LBRACE)) {
                    throw new ParserException("Wrong format of input string: " +
                            "double LBRACE", getCurrentToken(), getLastToken());
                }
                //create object Node, put it in the tree
                Node objectNode = new Node();
                objectNode.setType(NodeType.OBJECT);
                parent.put(objectNode);
                parseObject(objectNode);
            } else if (getCurrentTokenType().equals(TokenType.LBRACKET)) {
                //create array Node, put it in the tree
                Node arrayNode = new Node();
                arrayNode.setType(NodeType.ARRAY);
                parent.put(arrayNode);
                parseArray(arrayNode);
            }
        }
    }


    /**
     * parse OBJECT '{...}'
     * @param parent parent node
     * @throws ParserException parsing errors of forgotten RBRACE, also parsing errors of PAIR and VALUE
     */
    public void parseObject(Node parent) throws ParserException {
        //object is a collection of PAIRs delimited by COMMAs
        while (!getCurrentTokenType().equals(TokenType.RBRACE)) {
            next();
            parsePair(parent);
        }
    }

    /**
     * invokes at the end of parser work
     * @throws ParserException if last processed token is not EOF
     */
    public void checkEOF() throws ParserException {
        next();
        if (!getCurrentTokenType().equals(TokenType.EOF)) {
            throw new ParserException("Wrong format of input string: " +
                    "expected EOF", getCurrentToken(), getLastToken());
        }
    }

    /**
     * parse PAIR 'string : value'
     * @param parent parent node
     * @throws ParserException parsing errors of PAIR (see JSON grammar rules)
     */
    public void parsePair(Node parent) throws ParserException {
        //if it is empty object or end
        if (getCurrentTokenType().equals(TokenType.RBRACE)) {
            if (lastTokenType.equals(TokenType.COMMA)) {
                throw new ParserException("Wrong format of input string: " +
                        "unexpected comma before RBRACE", getCurrentToken(), getLastToken());
            }
            return;
        }
        Node pairNode = new Node();
        pairNode.setType(NodeType.PAIR);
        try {
            //current token should be a STRING
            if (getCurrentTokenType().equals(TokenType.STRING)) {
                //remove double quotes
                String name = getCurrentToken().getData().substring(1, getCurrentToken().getData().length() - 1);
                pairNode.setName(name);
            } else {
                throw new ParserException("Wrong format of input string: " +
                        "pair name should be a STRING", getCurrentToken(), getLastToken());
            }
        } catch (StringIndexOutOfBoundsException ex) {
            throw new ParserException("Wrong format of input string: " +
                    "current token is no a STRING", getCurrentToken(), getLastToken());
        }

        next();
        //current token should be a COLON, preceding token should be a STRING
        if (getCurrentTokenType().equals(TokenType.COLON) && getLastTokenType().equals(TokenType.STRING)) {
            next();
            //current token should be a VALUE:  STRING, NUMBER, TRUE, FALSE, NULL, {...}, [...]
            parseObjectValue(pairNode);
            parent.put(pairNode);
            next();
            //next should be COMMA or RBRACE
            if (!getCurrentTokenType().equals(TokenType.COMMA) && !getCurrentTokenType().equals(TokenType.RBRACE)) {
                throw new ParserException("Wrong format of input string: " +
                        "PAIRs should be delimited by commas", getCurrentToken(), getLastToken());
            }
        } else {
            throw new ParserException("Wrong format of input string: " +
                    "preceding token for COLON is not a STRING", getCurrentToken(), getLastToken());
        }
    }

    /**
     * parse VALUE: STRING, NUMBER, TRUE, FALSE, NULL, {...}, [...]
     * @param parent parent node
     * @throws ParserException parsing errors of VALUE (see JSON grammar rules)
     */
    public void parseObjectValue(Node parent) throws ParserException {
        //current token should be a VALUE: STRING, NUMBER, TRUE, FALSE, NULL, {...}, [...]
        if (getCurrentTokenType().equals(TokenType.STRING)) {
            try {
                //remove double quotes
                String value = getCurrentToken().getData().substring(1, getCurrentToken().getData().length() - 1);
                parent.setValue(value);
            } catch (StringIndexOutOfBoundsException ex) {
                throw new ParserException("Wrong format of input string: " +
                        "current token is no a STRING VALUE", getCurrentToken(), getLastToken());
            }
        } else if (getCurrentTokenType().equals(TokenType.NUMBER)) {
            parent.setValue(getCurrentToken().getData());
        } else if (getCurrentTokenType().equals(TokenType.TRUE)) {
            parent.setValue(getCurrentToken().getData());
        }  else if (getCurrentTokenType().equals(TokenType.FALSE)) {
            parent.setValue(getCurrentToken().getData());
        }  else if (getCurrentTokenType().equals(TokenType.NULL)) {
            parent.setIsNull(true);
        } else if (getCurrentTokenType().equals(TokenType.LBRACE)) {
            parse(parent);
        } else if (getCurrentTokenType().equals(TokenType.LBRACKET)) {
            parse(parent);
        } else {
            throw new ParserException("Wrong format of input string: " +
                    "unknown token for OBJECT VALUE", getCurrentToken(), getLastToken());
        }
    }

    /**
     * parse ARRAY '[...]'
     * @param parent parent node
     * @throws ParserException parsing errors of forgotten RBRACKET, also parsing errors of VALUE
     */
    public void parseArray(Node parent) throws ParserException {
        //array is an ordered list of values delimited by COMMAs
        while (!getCurrentTokenType().equals(TokenType.RBRACKET)) {
            next();
            parseArrayValue(parent);
        }
    }

    /**
     * parse VALUE: STRING, NUMBER, TRUE, FALSE, NULL, {...}, [...]
     * @param parent parent node
     * @throws ParserException parsing errors of VALUE (see JSON grammar rules)
     */
    public void parseArrayValue(Node parent) throws ParserException {
        Node arrayElement = new Node();

        //current token should be a VALUE:  STRING, NUMBER, TRUE, FALSE, NULL, {...}, [...]
        //or RBRACKET if it is empty array
        if (getCurrentTokenType().equals(TokenType.RBRACKET)) {
            if (lastTokenType.equals(TokenType.COMMA)) {
                throw new ParserException("Wrong format of input string: " +
                        "unexpected comma before RBRACKET", getCurrentToken(), getLastToken());
            }
            return;
        }

        if (getCurrentTokenType().equals(TokenType.STRING)) {
            arrayElement.setValue(getCurrentToken().getData());
        } else if (getCurrentTokenType().equals(TokenType.NUMBER)) {
            arrayElement.setValue(getCurrentToken().getData());
        } else if (getCurrentTokenType().equals(TokenType.TRUE)) {
            arrayElement.setValue(getCurrentToken().getData());
        }  else if (getCurrentTokenType().equals(TokenType.FALSE)) {
            arrayElement.setValue(getCurrentToken().getData());
        }  else if (getCurrentTokenType().equals(TokenType.NULL)) {
            arrayElement.setIsNull(true);
        } else if (getCurrentTokenType().equals(TokenType.LBRACE)) {
            parse(arrayElement);
        } else if (getCurrentTokenType().equals(TokenType.LBRACKET)) {
            parse(arrayElement);
        } else {
            throw new ParserException("Wrong format of input string: " +
                    "unknown token for ARRAY VALUE", getCurrentToken(), getLastToken());
        }
        arrayElement.setType(NodeType.VALUE);
        parent.put(arrayElement);

        next();
        //next should be COMMA or RBRACE
        if (!getCurrentTokenType().equals(TokenType.COMMA) && !getCurrentTokenType().equals(TokenType.RBRACKET)) {
            throw new ParserException("Wrong format of input string: " +
                    "array VALUES should be delimited by commas", getCurrentToken(), getLastToken());
        }
    }

    public Token getLastToken() {
        return lastToken;
    }

    public void setLastToken(Token lastToken) {
        this.lastToken = lastToken;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(Token currentToken) {
        this.currentToken = currentToken;
    }

    public TokenType getLastTokenType() {
        return lastTokenType;
    }

    public void setLastTokenType(TokenType lastTokenType) {
        this.lastTokenType = lastTokenType;
    }

    public TokenType getCurrentTokenType() {
        return currentTokenType;
    }

    public void setCurrentTokenType(TokenType currentTokenType) {
        this.currentTokenType = currentTokenType;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Node getTree() {
        return tree;
    }

    public void setTree(Node tree) {
        this.tree = tree;
    }
}
