package mobi.eyeline.jsonb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lexer transforms json-string to token stream
 * Created by Artem Voronov on 02.08.2015.
 */
public class Lexer {

    /**
     * return list of tokens
     * @param input json-string
     * @return list of tokens
     * @throws LexerException for unknown tokens (see json.org for details)
     */
    public static List<Token> lex(String input) throws LexerException {

        // the tokens to return
        List<Token> tokens = new ArrayList<Token>();

        // lexer logic begins here
        StringBuffer tokenPatternsBuffer = new StringBuffer();

        for (TokenType tokenType : TokenType.values()) {
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        }

        //error token
        tokenPatternsBuffer.append("|.+");

        // exclude '|' symbol
        Pattern tokenPatterns = Pattern.compile(tokenPatternsBuffer.substring(1));

        // begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            boolean found = false;
            for (TokenType tokenType : TokenType.values()) {
                if (matcher.group(tokenType.name()) != null) {
                    found = true;

                    if (!tokenType.equals(TokenType.WHITESPACE)) {
                        tokens.add(new Token(tokenType, matcher.group(tokenType.name())));
                    }
                    break;
                }
            }
            if (!found) {
                throw new LexerException("Wrong format of input string: unknown token");
            }
        }

        return tokens;
    }
}