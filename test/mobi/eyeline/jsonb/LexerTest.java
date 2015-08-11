package mobi.eyeline.jsonb;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Artem Voronov on 11.08.2015.
 */
public class LexerTest {

    @Test
    public void testLexer_SimpleObject() {
        String json = "{" +
                "\"stringProp\":\"stringValue\"," +
                "\"nullString\":null," +
                "\"numberProp_integer\":123," +
                "\"numberProp_fractional\":6.5," +
                "\"booleanProp\":true" +
                "}";

        List<Token> tokens;
        try {
            tokens = Lexer.lex(json);
            assertNotNull(tokens);
            assertEquals(22, tokens.size());
        } catch (LexerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLexer_ComplexObject() {
        String json = "{" +
                "\"object\" : {\"nullString\" : null}," +
                "\"array\" : [123, 6.5]," +
                "\"booleanProp\" : true" +
                "}";

        List<Token> tokens;
        try {
            tokens = Lexer.lex(json);
            assertNotNull(tokens);
            assertEquals(22, tokens.size());
        } catch (LexerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLexer_SimpleArray() {
        String json = "[\"stringValue\", null, 123, 6.5, true]";

        List<Token> tokens;
        try {
            tokens = Lexer.lex(json);
            assertNotNull(tokens);
            assertEquals(12, tokens.size());
        } catch (LexerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLexer_ComplexArray() {
        String json = "[ {\"stringValue\":null}, [123, 6.5], true]";

        List<Token> tokens;
        try {
            tokens = Lexer.lex(json);
            assertNotNull(tokens);
            assertEquals(16, tokens.size());
        } catch (LexerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLexer_SimpleObject_Ugly() {
        String json = "    {         " +
                "       \"stringProp\"  :      \"stringValue\"  ,  " +
                "   \"nullString\"   :       null," +
                "         \"numberProp_integer\"   :   123," +
                "    \"numberProp_fractional\"  :         6.5     ,  " +
                "\"booleanProp\"        :    true" +
                " }     ";

        List<Token> tokens;
        try {
            tokens = Lexer.lex(json);
            assertNotNull(tokens);
            assertEquals(22, tokens.size());
        } catch (LexerException e) {
            e.printStackTrace();
        }
    }

    @Test (expected=LexerException.class)
    public void testLexer_Exception_MissedQuotes() throws LexerException {
        String json = "{" +
                "stringProp:\"stringValue\"," + //<--
                "}";

        List<Token> tokens = Lexer.lex(json);
    }

    @Test (expected=LexerException.class)
    public void testLexer_Exception_WrongDataEnds() throws LexerException {
        String json = "{" +
                "\"stringProp\":\"stringValue\"," +
                "\"nullString\":null," +
                "\"numberProp_integer\":123," +
                "\"numberProp_fractional\":6.5," +
                "\"booleanProp\":true" +
                "} WRONG DATA"; //<--

        List<Token> tokens = Lexer.lex(json);
    }

    @Test (expected=LexerException.class)
    public void testLexer_Exception_WrongDataStarts() throws LexerException {
        String json = "WRONG DATA {" +  //<--
                "\"stringProp\":\"stringValue\"," +
                "\"nullString\":null," +
                "\"numberProp_integer\":123," +
                "\"numberProp_fractional\":6.5," +
                "\"booleanProp\":true" +
                "}";

        List<Token> tokens = Lexer.lex(json);
    }

    @Test (expected=LexerException.class)
    public void testLexer_Exception_WrongDataSomewhere() throws LexerException {
        String json = "{" +
                "\"stringProp\":\"stringValue\"," +
                "\"nullString\":null, WRONG DATA" + //<--
                "\"numberProp_integer\":123," +
                "\"numberProp_fractional\":6.5," +
                "\"booleanProp\":true" +
                "}";

        List<Token> tokens = Lexer.lex(json);
    }
}
