package mobi.eyeline.jsonb;

/**
 * Lexing exceptions
 * Created by Artem Voronov on 11.08.2015.
 */
public class LexerException extends Exception {

    private String token;

    public LexerException(String message, String token) {
        super(message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
