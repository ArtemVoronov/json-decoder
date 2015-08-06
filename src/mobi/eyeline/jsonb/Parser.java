package mobi.eyeline.jsonb;

import java.util.Iterator;
import java.util.List;

/**
 * Created by voronov on 03.08.2015.
 */
public class Parser {
    private Token lastToken;
    private Token currentToken;
    private TokenType lastTokenType;
    private TokenType currentTokenType;
    //all tokens
    List<Token> tokens;
    Iterator<Token> iterator;
    private Node tree;

    //then make lexical analysis -> now we have stream of tokens
    public void init(String input) {
        try {
            tokens = Lexer.lex(input);
            iterator = tokens.iterator();
            setLastToken(new Token(TokenType.EMPTY, ""));
            setLastTokenType(TokenType.EMPTY);
            setCurrentToken(new Token(TokenType.EMPTY, ""));
            setCurrentTokenType(TokenType.EMPTY);
            setTree(new Node(":root", ""));
            getTree().setType(NodeType.ROOT);

        } catch (UnmarshallerException e) {
            e.printStackTrace();
        }
    }

    public void next() throws UnmarshallerException {
        if (getCurrentTokenType().equals(TokenType.EOF)) {
            throw new UnmarshallerException("Calling next() for EOF token");
        }

        lastToken = getCurrentToken();
        setLastTokenType(getCurrentToken().getType());
        if (iterator.hasNext()) {
            setCurrentToken(iterator.next());
            setCurrentTokenType(getCurrentToken().getType());
        }

        //TODO: comment debug info later
        System.out.println("last: " + lastToken.toString());
        System.out.println("current: " + getCurrentToken().toString());
        System.out.println("----------");
    }

    //then start looking these tokens and using json rules for creating tree structure
    public void parse(Node parent) throws UnmarshallerException {
        //  end of recursion
        if (getCurrentTokenType().equals(TokenType.EOF)) {
            return;
        } else {
            //first of all we should have {...} or [...]

            // if {...} then parse Object
            if (getCurrentTokenType().equals(TokenType.LBRACE)) {
                //check for double braces
                if (getLastTokenType().equals(TokenType.LBRACE)) {
                    throw new UnmarshallerException("Wrong format of input string: " +
                            "double LBRACE");
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
                arrayNode.setName("");
                arrayNode.setValue("");
                parseArray(arrayNode);
            }
        }
    }

    public void parseObject(Node parent) throws UnmarshallerException {
        //object is a collection of PAIRs delimited by COMMAs
        while (!getCurrentTokenType().equals(TokenType.RBRACE)) {
            next();
            parsePair(parent);
        }
    }

    public void checkEOF() throws UnmarshallerException {
        next();
        if (!getCurrentTokenType().equals(TokenType.EOF)) {
            throw new UnmarshallerException("Wrong format of input string: " +
                    "expected EOF");
        }
    }

    public void parsePair(Node parent) throws UnmarshallerException {
        if (getCurrentTokenType().equals(TokenType.EOF)) {
            return;
        }
        Node pairNode = new Node();
        pairNode.setType(NodeType.PAIR);

        //current token should be a STRING
        if (getCurrentTokenType().equals(TokenType.STRING)) {
            //remove double quotes
            String name =  getCurrentToken().getData().substring(1, getCurrentToken().getData().length() - 1);
            pairNode.setName(name);
        } else {
            throw new UnmarshallerException("Wrong format of input string: " +
                    "pair name should be a STRING");
        }

        next();
        //current token should be a COLON, preceding token should be a STRING
        if (getCurrentTokenType().equals(TokenType.COLON) && getLastTokenType().equals(TokenType.STRING)) {
            next();
            //current token should be a VALUE:  STRING, NUMBER, TRUE, FALSE, NULL, {...}, [...]
            parseObjectValue(pairNode);
            parent.put(pairNode);
            parent.setName("");
            parent.setValue("");
            next();
            //next should be COMMA or RBRACE
            if (!getCurrentTokenType().equals(TokenType.COMMA) && !getCurrentTokenType().equals(TokenType.RBRACE)) {
                throw new UnmarshallerException("Wrong format of input string: " +
                        "PAIRs should be delimited by commas");
            }
        } else {
            throw new UnmarshallerException("Wrong format of input string: " +
                    "preceding token for COLON is not a STRING");
        }
    }

    public void parseObjectValue(Node parent) throws UnmarshallerException {
        //current token should be a VALUE:  STRING, NUMBER, TRUE, FALSE, NULL, {...}, [...]
        if (getCurrentTokenType().equals(TokenType.STRING)) {
            //remove double quotes
            String value =  getCurrentToken().getData().substring(1, getCurrentToken().getData().length() - 1);
            parent.setValue(value);
        } else if (getCurrentTokenType().equals(TokenType.NUMBER)) {
            parent.setValue(getCurrentToken().getData());
        } else if (getCurrentTokenType().equals(TokenType.TRUE)) {
            parent.setValue(getCurrentToken().getData());
        }  else if (getCurrentTokenType().equals(TokenType.FALSE)) {
            parent.setValue(getCurrentToken().getData());
        }  else if (getCurrentTokenType().equals(TokenType.NULL)) {
            parent.setValue(getCurrentToken().getData());
        } else if (getCurrentTokenType().equals(TokenType.LBRACE)) {
            parse(parent);
        } else if (getCurrentTokenType().equals(TokenType.LBRACKET)) {
            parse(parent);
        }
    }

    public void parseArray(Node parent) throws UnmarshallerException {
        //array is an ordered list of values delimited by COMMAs
        while (!getCurrentTokenType().equals(TokenType.RBRACKET)) {
            next();
            parseArrayValue(parent);
        }
    }

    public void parseArrayValue(Node parent) throws UnmarshallerException {
        Node arrayElement = new Node();

        //current token should be a VALUE:  STRING, NUMBER, TRUE, FALSE, NULL, {...}, [...]
        if (getCurrentTokenType().equals(TokenType.STRING)) {

            arrayElement.setValue(getCurrentToken().getData());
        } else if (getCurrentTokenType().equals(TokenType.NUMBER)) {
            arrayElement.setValue(getCurrentToken().getData());
        } else if (getCurrentTokenType().equals(TokenType.TRUE)) {
            arrayElement.setValue(getCurrentToken().getData());
        }  else if (getCurrentTokenType().equals(TokenType.FALSE)) {
            arrayElement.setValue(getCurrentToken().getData());
        }  else if (getCurrentTokenType().equals(TokenType.NULL)) {
            arrayElement.setValue(getCurrentToken().getData());
        } else if (getCurrentTokenType().equals(TokenType.LBRACE)) {
            parse(arrayElement);
        } else if (getCurrentTokenType().equals(TokenType.LBRACKET)) {
            parse(arrayElement);
        }
        arrayElement.setType(NodeType.VALUE);
        parent.put(arrayElement);

        next();
        //next should be COMMA or RBRACE
        if (!getCurrentTokenType().equals(TokenType.COMMA) && !getCurrentTokenType().equals(TokenType.RBRACKET)) {
            throw new UnmarshallerException("Wrong format of input string: " +
                    "array VALUES should be delimited by commas");
        }
    }

    public static void main(String[] args) {
        String json="{" +
                "\"stringProp\":\"stringValue\"," +
                "\"intProp\":123," +
                "\"floatProp\":55.5," +
                "\"unknownProp\":null,"+
                "\"booleanProp_1\":true,"+
                "\"booleanProp_2\":false"+
                "}";
//        String json2="[" +
//                "\"stringValue\"," +
//                "123," +
//                "55.5," +
//                "null,"+
//                "true,"+
//                "false"+
//                "]";
//        String json3="{" +
//                "\"obj_1\":" +
//                    "{" +
//                        "\"inner_obj_1\":" +
//                            "{" +
//                                "\"innerStringProp\":\"innerStringVal\"" +
//                            "}" +
//                    "}," +
//                "\"obj_2\":" +
//                    "{" +
//                        "\"stringProp\":\"stringValue\"," +
//                        "\"intProp\":123," +
//                        "\"floatProp\":55.5," +
//                        "\"unknownProp\":null,"+
//                        "\"booleanProp_1\":true,"+
//                        "\"booleanProp_2\":false"+
//                    "}" +
//                "}";
        String json4="{" +
                "\"arr_1\":[1,2]," +
                "\"arr_2\":[3,4]" +
                "}";

//        String json_inner="{" +
//                "\"innerObject1\" : { " +
//                "\"stringProp\":\"stringValue\","+
//                "\"intProp\":123,"+
//                "\"booleanProp\":true"+
//                "}," +
//                "\"innerObject2\" : {" +
//                "\"stringProp\":\"stringValue2\","+
//                "\"intProp\":1232,"+
//                "\"booleanProp\":false"+
//                "},"+
//                "\"stringProp\":\"stringVal2\","+
//                "\"unknownProp\": {\"key\":false}"+
//                "}";
//        String json_array = "{"+
//                "\"intArray\" : [55e-2,2],"+
//                "\"stringArray\" : [\"3\",\"4\",  null],"+
//                "\"booleanArray\" : [true,false,true],"+
//                "\"objectArray\" : [{\"intProp\":1}, {\"intProp\" : 2}, null]"+
//                "}";
        Parser parser = new Parser();
        parser.init(json);
        try {
            parser.next();
            Node tree = parser.getTree();
            parser.parse(tree);
            parser.checkEOF();
            System.out.println(tree.getNodeInfo(""));
        } catch (UnmarshallerException e) {
            e.printStackTrace();
        }

    }

    public Token getLastToken() {
        return lastToken;
    }

    public void setLastToken(Token lastToken) {
        this.lastToken = lastToken;
    }

    public Node getTree() {
        return tree;
    }

    public void setTree(Node tree) {
        this.tree = tree;
    }

    public TokenType getCurrentTokenType() {
        return currentTokenType;
    }

    public void setCurrentTokenType(TokenType currentTokenType) {
        this.currentTokenType = currentTokenType;
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
}
