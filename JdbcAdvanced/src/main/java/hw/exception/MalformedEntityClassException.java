package hw.exception;

public class MalformedEntityClassException extends RuntimeException {
    public MalformedEntityClassException(String message) {
        super(message);
    }

    public MalformedEntityClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
