package mobi.eyeline.jsonb;

/**
 * Lexing exceptions
 * Created by Artem Voronov on 11.08.2015.
 */
public class LexerException extends ParserException {

    private String token;

    public LexerException() {
        super();
    }

    public LexerException(String message) {
        super(message);
    }

    public LexerException(String message, Throwable cause) {
        super(message, cause);
    }

    public LexerException(Throwable cause) {
        super(cause);
    }

    public LexerException(String message, String token) {
        super(message);
        this.token = token;
    }
}
