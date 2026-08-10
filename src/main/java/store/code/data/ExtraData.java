package store.code.data;

public interface ExtraData {

    Object getExtra(String key);

    void setExtra(String key, Object value);

}
