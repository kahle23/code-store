package store.code.codec;

public interface BinaryEncoder {

    Object encode(Object source) throws EncodeException;

    byte[] encode(byte[] source) throws EncodeException;

}
