package mobi.eyeline.jsonb;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Unmarshaller {

  public static <T> T unmarshal(String json, Class<T> clazz) throws UnmarshallerException {
    // json string -> lexer
    // lexer matches tokens
    // ...
    // tokens -> parser
    // parser creates tree
    // ...
    // then iterating through the tree and creating Java objects
    //
    // profit!


    /*
      for(Field field : ObjectWithSimpleProps.class.getDeclaredFields()){
      Class type = field.getType();
      String name = field.getName();
      Annotation[] annotations = field.getDeclaredAnnotations();
      String vall = annotations.toString();
    }
    */
    return null; // todo реализовать
  }

}
