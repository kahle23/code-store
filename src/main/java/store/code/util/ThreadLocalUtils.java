package store.code.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocalUtils {

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public static Object getValue(String key) {
        return THREAD_LOCAL.get().get(key);
    }

    public static void setValue(String key, Object value) {
        THREAD_LOCAL.get().put(key, value);
    }

    public static void remove(String key) {
        THREAD_LOCAL.get().remove(key);
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }

}
