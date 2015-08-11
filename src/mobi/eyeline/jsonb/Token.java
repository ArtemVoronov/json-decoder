package mobi.eyeline.jsonb;

/**
 * Token is a part of JSON notation (http://json.org/).
 * Created by Artem Voronov on 04.08.2015.
 */
public class Token {
    private TokenType type;
    private String data;

    public Token(TokenType type, String data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", getType().name(), getData());
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
