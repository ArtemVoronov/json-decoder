package mobi.eyeline.jsonb;

/**
 * Parsing exceptions
 * Created by Artem Voronov on 11.08.2015.
 */
public class ParserException extends Exception {

    private Token currentToken;
    private Token lastToken;

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Token currentToken, Token lastToken) {
        super(message);
        this.currentToken = currentToken;
        this.lastToken = lastToken;
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public Token getLastToken() {
        return lastToken;
    }
}
