package store.code.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractExtraData implements ExtraData {

    private final Map<String, Object> extras = new ConcurrentHashMap<>();

    @Override
    public Object getExtra(String key) {
        return extras.get(key);
    }

    @Override
    public void setExtra(String key, Object value) {
        extras.put(key, value);
    }

}
