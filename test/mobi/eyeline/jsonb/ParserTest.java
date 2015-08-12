package mobi.eyeline.jsonb;

import org.junit.Test;


/**
 * Created by Artem Voronov on 11.08.2015.
 */
public class ParserTest {


    @Test(expected=ParserException.class)
    public void testParser_Exception_DoubleBrace1() throws ParserException {
        String json = "{" +
                "\"object\": {{\"stringProp\":\"stringValue\"}}" + //<--
                "}";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test(expected=ParserException.class)
    public void testParser_Exception_DoubleBrace2() throws ParserException {
        String json = "{{" +  //<--
                "\"stringProp\": \"stringValue\"," +
                "\"nullString\":null," +
                "\"numberProp_integer\":123," +
                "\"numberProp_fractional\":6.5," +
                "\"booleanProp\":true" +
                "}";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test(expected=ParserException.class)
    public void testParser_Exception_MissedCommaObject() throws ParserException {
        String json = "{" +
                "\"stringProp\":\"stringValue\"" + //<--
                "\"nullString\":null," +
                "\"numberProp_integer\":123," +
                "\"numberProp_fractional\":6.5," +
                "\"booleanProp\":true" +
                "}";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test(expected=ParserException.class)
    public void testParser_Exception_MissedCommaArray() throws ParserException {
        String json = "[1 , 2  3]";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }


    @Test (expected=ParserException.class)
    public void testParser_Exception_MissedColon() throws ParserException {
        String json = "{" +
                "\"stringProp\"\"stringValue\"," +  //<--
                "\"nullString\":null," +
                "\"numberProp_integer\":123," +
                "\"numberProp_fractional\":6.5," +
                "\"booleanProp\":true" +
                "}";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test (expected=ParserException.class)
    public void testParser_Exception_DoubleColon() throws ParserException {
        String json = "{" +
                "\"stringProp\"::\"stringValue\"," +  //<--
                "\"nullString\":null," +
                "\"numberProp_integer\":123," +
                "\"numberProp_fractional\":6.5," +
                "\"booleanProp\":true" +
                "}";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test (expected=ParserException.class)
    public void testParser_Exception_DoubleComma() throws ParserException {
        String json = "{" +
                "\"stringProp\":\"stringValue\",," +  //<--
                "\"nullString\":null," +
                "\"numberProp_integer\":123," +
                "\"numberProp_fractional\":6.5," +
                "\"booleanProp\":true" +
                "}";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test (expected=ParserException.class)
    public void testParser_Exception_UnexpectedComma() throws ParserException {
        String json = "{" +
                "\"stringProp\":,\"stringValue\"," +  //<--
                "\"nullString\":null," +
                "\"numberProp_integer\":123," +
                "\"numberProp_fractional\":6.5," +
                "\"booleanProp\":true" +
                "}";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test (expected=ParserException.class)
    public void testParser_Exception_UnexpectedCommaObjectEnd() throws ParserException {
        String json = "{" +
                "\"stringProp\":\"stringValue\"," + //<--
                "}";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test (expected=ParserException.class)
    public void testParser_Exception_UnexpectedCommaArrayEnd() throws ParserException {
        String json = "[" +
                "\"stringValue\"," + //<--
                "]";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test (expected=ParserException.class)
    public void testParser_Exception_Empty() throws ParserException {
        String json = "" ;

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test (expected=ParserException.class)
    public void testParser_Exception_NotJSON() throws ParserException {
        String json = "99" ;

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    @Test (expected=ParserException.class)
    public void testParser_Exception_EOFBOrder() throws ParserException {
        String json = "{}" ;

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();

        parser.checkEOF(); //<--
    }

    @Test (expected=ParserException.class)
    public void testParser_Exception_UnexpectedEOF() throws ParserException {
        String json = "{" +
                "\"object1\" : { \"stringProp\":\"stringValue\" }}," +
                "\"object2\" : { \"stringProp\":\"stringValue\" }" +
                "}";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }
}
