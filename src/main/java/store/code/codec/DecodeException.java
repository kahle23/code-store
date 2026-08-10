package store.code.codec;

public class DecodeException extends RuntimeException {

    public DecodeException() { super(); }

    public DecodeException(String message) { super(message); }

    public DecodeException(String message, Throwable cause) { super(message, cause); }

}
