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

    @Test
    public void testLexer_Empty() throws LexerException {
        String json1 = "";
        String json2 = "{}";
        String json3 = "[]";

        List<Token> tokens1 = Lexer.lex(json1);
        List<Token> tokens2 = Lexer.lex(json2);
        List<Token> tokens3 = Lexer.lex(json3);

        assertNotNull(tokens1);
        assertNotNull(tokens2);
        assertNotNull(tokens3);

        assertEquals(1, tokens1.size());
        assertEquals(3, tokens2.size());
        assertEquals(3, tokens3.size());

        assertEquals(TokenType.EMPTY, tokens1.get(0).getType());

        assertEquals(TokenType.LBRACE, tokens2.get(0).getType());
        assertEquals(TokenType.RBRACE, tokens2.get(1).getType());
        assertEquals(TokenType.EOF, tokens2.get(2).getType());

        assertEquals(TokenType.LBRACKET, tokens3.get(0).getType());
        assertEquals(TokenType.RBRACKET, tokens3.get(1).getType());
        assertEquals(TokenType.EOF, tokens3.get(2).getType());
    }
}
