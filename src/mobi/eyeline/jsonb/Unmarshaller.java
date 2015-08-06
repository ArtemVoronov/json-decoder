package mobi.eyeline.jsonb;


import mobi.eyeline.jsonb.annotations.JSONProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
//        visitTree((Node) tree.getNodes().get(0),/* set, clazz, */obj);

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


  public static void main(String[] args) {
    String json_simple="{" +
            "\"stringProp\":\"stringValue\"," +
            "\"intProp\":123," +
            "\"unknownProp\":null,"+
            "\"booleanProp\":true"+
            "}";
    String json_complex="{" +
            "\"innerObject1\" : { " +
            "\"stringProp\":\"stringValue\","+
            "\"intProp\":123,"+
            "\"booleanProp\":true"+
            "}," +
            "\"innerObject2\" : { " +
            "\"stringProp\":\"stringValue_2\","+
            "\"intProp\":456,"+
            "\"booleanProp\":false"+
            "}," +
            "\"stringProp\":\"stringValue_outer\""+
            "}";
      String json_array = "{"+
              "\"intArray\" : [1,2],"+
              "\"stringArray\" : [\"3\",\"4\",  null],"+
              "\"booleanArray\" : [true,false,true],"+
              "\"objectArray\" : [{\"intProp\":1}, {\"intProp\" : 2}, null]"+
              "}";
    try {
//      SimpleObject sob = Unmarshaller.unmarshal(json_simple, SimpleObject.class);
//      System.out.println(sob.toString());
      ComplexObject cob = Unmarshaller.unmarshal(json_array, ComplexObject.class);
      System.out.println(cob.toString());
    } catch (UnmarshallerException e) {
      e.printStackTrace();
    }

  }
}
