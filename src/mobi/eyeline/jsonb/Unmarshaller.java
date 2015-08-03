package mobi.eyeline.jsonb;


public class Unmarshaller {

  public static <T> T unmarshal(String json, Class<T> clazz) throws UnmarshallerException {
    // json string -> lexer
    // lexer matches tokens
    // ...
    // tokens -> parser
    // parser creates tree
    // ...
    // then iterate through the tree and creating Java objects
    //
    // profit!
    return null; // todo реализовать
  }

}
