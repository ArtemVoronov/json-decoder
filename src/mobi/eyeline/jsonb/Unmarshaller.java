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
            "\"intProp_primitive\":1," +
            "\"intProp\":2," +
            "\"doubleProp_primitive\":3.5," +
            "\"doubleProp\":4.5," +
            "\"floatProp_primitive\":5.5," +
            "\"floatProp\":6.6," +
//            "\"unknownProp\":null,"+
            "\"booleanProp_primitive\":true,"+
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
              "\"intArray_primitive\" : [1,2],"+
              "\"integerArray\" : [3,4],"+
              "\"stringArray\" : [\"3\", \"4\",  null],"+
              "\"booleanArray_primitive\" : [true, false, true],"+
              "\"booleanArray\" : [true, false, true],"+
              "\"floatArray\" : [1.5,2.5],"+
              "\"floatArray_primitive\" : [3.5,4.5],"+
              "\"doubleArray_primitive\" : [5.5,6.5],"+
              "\"doubleArray\" : [7.5,8.5]"+
              "}";
      String json_array2 = "{"+
              "\"objectArray\" :" +
                "[" +
                    "{\"stringProp\" : \"someText1\", \"intProp\" : 1, \"booleanProp\" : true}, " +
                    "{\"stringProp\" : \"someText2\", \"intProp\" : 2, \"booleanProp\" : false}, " +
                    "{\"stringProp\" : \"someText3\"}, " +
                    "{\"intProp\" : 3}, " +
                    "{\"booleanProp\" : true}, " +
                    "{\"booleanProp\" : null} " +
                "]"+
              "}";
      String json_array_null = "{"+
              "\"objectArray\" : [{\"intProp\":1}, null, {\"intProp\" : 2}]"+
              "}";
      String json_null_simple = "{" +
              "\"stringProp\":null," +
              "\"intProp_primitive\":null," +
              "\"intProp\":null," +
              "\"doubleProp_primitive\":null," +
              "\"doubleProp\":null," +
              "\"floatProp_primitive\":null," +
              "\"floatProp\":null," +
              "\"booleanProp_primitive\":null,"+
              "\"booleanProp\":null"+
              "}";
      String json_array_null2 =  "{"+
              "\"intArray_primitive\" : [1,2],"+
              "\"stringArray\" : [\"3\",\"4\", null],"+
              "\"booleanArray_primitive\" : [true, false ,true],"+
              "\"objectArray\" : [{\"intProp\":1}, {\"intProp\" : 2}, null]"+
              "}";
    try {
//      SimpleObject sob = Unmarshaller.unmarshal(json_null_simple, SimpleObject.class);
//      System.out.println(sob.toString());
      ComplexObject cob = Unmarshaller.unmarshal(json_array_null2, ComplexObject.class);
      System.out.println(cob.toString());
    } catch (UnmarshallerException e) {
      e.printStackTrace();
    }

  }
}
