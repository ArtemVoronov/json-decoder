package mobi.eyeline.jsonb;


/**
 * General class for unmarshalling exceptions
 */
public class UnmarshallerException extends Exception {

    public UnmarshallerException() {
        super();
    }

    public UnmarshallerException(String message) {
        super(message);
    }

    public UnmarshallerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnmarshallerException(Throwable cause) {
        super(cause);
    }
}
