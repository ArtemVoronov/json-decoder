package mobi.eyeline.jsonb;

import org.junit.Test;


/**
 * Тесты для парсера
 * Created by Artem Voronov on 11.08.2015.
 */
public class ParserTest {


    /**
     * проверяем корректность работы парсера: двойные скобки
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
     * проверяем корректность работы парсера: двойные скобки
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
     * проверяем корректность работы парсера: пропущенная запятая в объекте
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
     * проверяем корректность работы парсера: пропущенная запятая в массиве
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
     * проверяем корректность работы парсера: пропущенное двоеточие
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
     * проверяем корректность работы парсера: лишнее двоеточие
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
     * проверяем корректность работы парсера: лишняя запятая
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
     * проверяем корректность работы парсера: запятая после двоеточия
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
     * проверяем корректность работы парсера: лишняя запятая в объекте (после последней пары)
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
     * проверяем корректность работы парсера: лишняя запятая в массиве (после последнего элемента)
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
     * проверяем корректность работы парсера: пустая строка
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
     * проверяем корректность работы парсера: на входе число, а не {...} или [...]. Лексер пропустит данную ошибку,
     * т.к. с его точки зрения её здесь нет, лексема типа (NUMBER 99) будет найдена и помещена в поток токенов
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
     * проверяем корректность работы парсера: выход за границу потока токенов, пытаемся двигается по нему после EOF
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
     * проверяем корректность работы парсера: непредвиденная скобка '}',
     * EOF ещё не достигнут а процесс парсинга прерывается
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
