package mobi.eyeline.jsonb;


/**
 * Unmarshaller used for deserialization of JSON strings to java objects
 */
public class Unmarshaller {

  /**
   * deserialize json string to java object
   * @param json input string
   * @param clazz class of the object
   * @param <T>
   * @return
   * @throws UnmarshallerException
   */
  public static <T> T unmarshal(String json, Class<T> clazz) throws UnmarshallerException {
    Object obj = null;
    TreeVisitor visitor = new TreeVisitor();
    Parser parser = new Parser();
    parser.init(json);

    try {
      parser.next();
      Node tree = parser.getTree();
      parser.parse(tree);
      parser.checkEOF();
      System.out.println(tree.getNodeInfo(""));

      try {
        //create object
        obj = clazz.newInstance();
        //walk through the tree and init object's fields
        visitor.visitTree(tree, obj);

      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    } catch (UnmarshallerException e) {
      e.printStackTrace();
    }

    return (T)obj;
  }
}
