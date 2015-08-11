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
     * @throws UnmarshallerException for unknown tokens (see json.org for details)
     */
    public static List<Token> lex(String input) throws UnmarshallerException {

        // the tokens to return
        List<Token> tokens = new ArrayList<Token>();

        // lexer logic begins here
        StringBuffer tokenPatternsBuffer = new StringBuffer();

        for (TokenType tokenType : TokenType.values()) {
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        }

        // exclude '|' symbol
        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

        // begin matching tokens
        Matcher matcher = tokenPatterns.matcher(input);
        while (matcher.find()) {
            for (TokenType tokenType : TokenType.values()) {
                if (matcher.group(tokenType.name()) != null) {

                    //if we have wrong input (e.g. forgotten double quotes for string value)
                    if (tokenType.equals(TokenType.ERROR)) {
                        String token = matcher.group(tokenType.name());
                        throw new UnmarshallerException("Wrong format of input string: " +
                                "unknown token '" + token + "'");
                    }
                    if (!tokenType.equals(TokenType.WHITESPACE)) {
                        tokens.add(new Token(tokenType, matcher.group(tokenType.name())));
                    }
                    break;
                }
            }
        }

        return tokens;
    }
}