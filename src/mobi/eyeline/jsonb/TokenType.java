package mobi.eyeline.jsonb;

/**
 * Token types according JSON rules. Regexp for each from http://json.org/
 * Created by Artem Voronov on 04.08.2015.
 */
public enum TokenType {
    //EMPTY - special status for not initialized token (just for starting of iteration)
    EMPTY("\\A\\z"),
    WHITESPACE("\\s+"),
    LBRACE("\\{"),
    RBRACE("\\}"),
    LBRACKET("\\["),
    RBRACKET("\\]"),
    COMMA(","),
    COLON(":"),
    STRING("\"(([^\"\\\\]|\\\\[\\\\\"/bfnrt]|\\\\u\\d{4})*)\""),
    NUMBER("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?"),
    TRUE("true"),
    FALSE("false"),
    NULL("null"),
    EOF("\\z"),
    ERROR(".+");

    public final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }
}
