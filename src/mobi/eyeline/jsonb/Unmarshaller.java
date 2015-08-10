package mobi.eyeline.jsonb;


public class Unmarshaller {

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
        String name = clazz.getName();
        //created object
        obj = clazz.newInstance();
        //find these fields in the tree and init object's fields
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
