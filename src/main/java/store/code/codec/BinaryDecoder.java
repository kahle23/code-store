package store.code.codec;

public interface BinaryDecoder {

    Object decode(Object source) throws DecodeException;

    byte[] decode(byte[] source) throws DecodeException;

}
