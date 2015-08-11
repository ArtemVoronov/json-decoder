package mobi.eyeline.jsonb;

/**
 * Parsing exceptions
 * Created by Artem Voronov on 11.08.2015.
 */
public class ParserException extends UnmarshallerException {

    private Token currentToken;
    private Token lastToken;

    public ParserException() {
        super();
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }

    public ParserException(String message, Token currentToken, Token lastToken) {
        super(message);
        this.currentToken = currentToken;
        this.lastToken = lastToken;
    }
}
