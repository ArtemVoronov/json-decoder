package mobi.eyeline.jsonb;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by voronov on 02.08.2015.
 */
public class Lexer {

    public static ArrayList<Token> lex(String input) throws UnmarshallerException {

        // the tokens to return
        ArrayList<Token> tokens = new ArrayList<Token>();

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

    public static void main(String[] args) {
        String input = "";
        String json1="{" +
                "\"stringProp\":\"stringValue\"," +
                "\"intProp\":123," +
                "\"unknownProp\":false,"+
                "\"booleanProp\":true"+
                "}";
        String json2="{" +
                "\"innerObject1\" : { " +
                "\"stringProp\":\"stringValue\","+
                "\"intProp\":123,"+
                "\"booleanProp\":true"+
                "}," +
                "\"innerObject2\" : {" +
                "\"stringProp\":\"stringValue2\","+
                "\"intProp\":1232,"+
                "\"booleanProp\":false"+
                "},"+
                "\"stringProp\":\"stringVal2\","+
                "\"unknownProp\": {\"key\":false}"+
                "}";
        String json3 = "{"+
                "\"intArray\" : [55e-2,2],"+
                "\"stringArray\" : [\"3\",\"4\",  null],"+
                "\"booleanArray\" : [true,false,true],"+
                "\"objectArray\" : [{\"intProp\":1}, {\"intProp\" : 2}, null]"+
                "}";

        System.out.println("json1: " + json1);
//        System.out.println("json2: " + json2);
//        System.out.println("json3: " + json3);

        // Create tokens and print them
        ArrayList<Token> tokens;
        try {
            tokens = lex(input);
            for (Token token : tokens) {
                System.out.println(token);
            }
        } catch (UnmarshallerException e) {
            e.printStackTrace();
        }

    }
}
