package mobi.eyeline.jsonb;

import org.junit.Test;


/**
 * ����� ��� �������
 * Created by Artem Voronov on 11.08.2015.
 */
public class ParserTest {


    /**
     * ��������� ������������ ������ �������: ������� ������
     * @throws ParserException
     * @throws LexerException
     */
    @Test(expected=ParserException.class)
    public void testParser_Exception_DoubleBrace1() throws ParserException, LexerException {
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

    /**
     * ��������� ������������ ������ �������: ������� ������
     * @throws ParserException
     * @throws LexerException
     */
    @Test(expected=ParserException.class)
    public void testParser_Exception_DoubleBrace2() throws ParserException, LexerException {
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

    /**
     * ��������� ������������ ������ �������: ����������� ������� � �������
     * @throws ParserException
     * @throws LexerException
     */
    @Test(expected=ParserException.class)
    public void testParser_Exception_MissedCommaObject() throws ParserException, LexerException {
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

    /**
     * ��������� ������������ ������ �������: ����������� ������� � �������
     * @throws ParserException
     * @throws LexerException
     */
    @Test(expected=ParserException.class)
    public void testParser_Exception_MissedCommaArray() throws ParserException, LexerException {
        String json = "[1 , 2  3]";

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }


    /**
     * ��������� ������������ ������ �������: ����������� ���������
     * @throws ParserException
     * @throws LexerException
     */
    @Test (expected=ParserException.class)
    public void testParser_Exception_MissedColon() throws ParserException, LexerException {
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

    /**
     * ��������� ������������ ������ �������: ������ ���������
     * @throws ParserException
     * @throws LexerException
     */
    @Test (expected=ParserException.class)
    public void testParser_Exception_DoubleColon() throws ParserException, LexerException {
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

    /**
     * ��������� ������������ ������ �������: ������ �������
     * @throws ParserException
     * @throws LexerException
     */
    @Test (expected=ParserException.class)
    public void testParser_Exception_DoubleComma() throws ParserException, LexerException {
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

    /**
     * ��������� ������������ ������ �������: ������� ����� ���������
     * @throws ParserException
     * @throws LexerException
     */
    @Test (expected=ParserException.class)
    public void testParser_Exception_UnexpectedComma() throws ParserException, LexerException {
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

    /**
     * ��������� ������������ ������ �������: ������ ������� � ������� (����� ��������� ����)
     * @throws ParserException
     * @throws LexerException
     */
    @Test (expected=ParserException.class)
    public void testParser_Exception_UnexpectedCommaObjectEnd() throws ParserException, LexerException {
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

    /**
     * ��������� ������������ ������ �������: ������ ������� � ������� (����� ���������� ��������)
     * @throws ParserException
     * @throws LexerException
     */
    @Test (expected=ParserException.class)
    public void testParser_Exception_UnexpectedCommaArrayEnd() throws ParserException, LexerException {
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

    /**
     * ��������� ������������ ������ �������: ������ ������
     * @throws ParserException
     * @throws LexerException
     */
    @Test (expected=ParserException.class)
    public void testParser_Exception_Empty() throws ParserException, LexerException {
        String json = "" ;

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    /**
     * ��������� ������������ ������ �������: �� ����� �����, � �� {...} ��� [...]. ������ ��������� ������ ������,
     * �.�. � ��� ����� ������ � ����� ���, ������� ���� (NUMBER 99) ����� ������� � �������� � ����� �������
     * @throws ParserException
     * @throws LexerException
     */
    @Test (expected=ParserException.class)
    public void testParser_Exception_NotJSON() throws ParserException, LexerException {
        String json = "99" ;

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();
    }

    /**
     * ��������� ������������ ������ �������: ����� �� ������� ������ �������, �������� ��������� �� ���� ����� EOF
     * @throws ParserException
     * @throws LexerException
     */
    @Test (expected=ParserException.class)
    public void testParser_Exception_EOFBOrder() throws ParserException, LexerException {
        String json = "{}" ;

        Parser parser = new Parser();
        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();

        parser.checkEOF(); //<--
    }

    /**
     * ��������� ������������ ������ �������: �������������� ������ '}',
     * EOF ��� �� ��������� � ������� �������� �����������
     * @throws ParserException
     * @throws LexerException
     */
    @Test (expected=ParserException.class)
    public void testParser_Exception_UnexpectedEOF() throws ParserException, LexerException {
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
