package mobi.eyeline.jsonb;


import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Unmarshaller used for deserialization of JSON strings to java objects
 */
public class Unmarshaller {

    /**
     * deserialize json string to java object
     * @param json input string
     * @param clazz class of the object
     * @param <T>
     * @return instance of clazz
     * @throws UnmarshallerException
     */
    public static <T> T unmarshal(String json, Class<T> clazz) throws UnmarshallerException,
            IllegalAccessException, InstantiationException, LexerException, ParserException {
        Object obj = null;
        TreeVisitor visitor = new TreeVisitor();
        Parser parser = new Parser();

        parser.init(json);
        parser.next();
        Node tree = parser.getTree();
        parser.parse(tree);
        parser.checkEOF();

        //debug info
        //System.out.println(tree.getNodeInfo(""));

        //create object
        obj = clazz.newInstance();
        //walk through the tree and init object's fields
        visitor.visitTree(tree, obj);

        return (T)obj;
    }
}
