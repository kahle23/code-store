package store.code.cache.way2;

import artoria.collect.ReferenceMap;
import artoria.lang.ReferenceType;
import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import artoria.util.Assert;
import artoria.util.MapUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static artoria.common.Constants.*;
import static artoria.lang.ReferenceType.SOFT;
import static artoria.lang.ReferenceType.WEAK;
import static java.util.Collections.emptyMap;

/**
 * Memory cache simple implement by jdk.
 * @author Kahle
 */
public class SimpleCache extends AbstractValueWrapperCache {
    /**
     * The log object.
     */
    private static Logger log = LoggerFactory.getLogger(SimpleCache.class);
    /**
     * The number of times the cache was missed.
     */
    private final AtomicLong missCount = new AtomicLong();
    /**
     * The number of times the cache was hit.
     */
    private final AtomicLong hitCount = new AtomicLong();
    /**
     * Cached storage object.
     */
    private final Map<Object, ValueWrapper> storage;
    /**
     * Cache capacity. 0 indicates unlimited.
     */
    private final Long capacity;
    /**
     * The amount of time for the element to idle, in millisecond. 0 indicates unlimited.
     */
    private final Long timeToIdle;
    /**
     * The amount of time for the element to live, in millisecond. 0 indicates unlimited.
     */
    private final Long timeToLive;

    public SimpleCache(String name) {

        this(name, ZERO, MINUS_ONE, MINUS_ONE, ReferenceType.WEAK);
    }

    public SimpleCache(String name, ReferenceType referenceType) {

        this(name, ZERO, MINUS_ONE, MINUS_ONE, referenceType);
    }

    public SimpleCache(String name, long capacity, ReferenceType referenceType) {

        this(name, capacity, MINUS_ONE, MINUS_ONE, referenceType);
    }

    public SimpleCache(String name, long capacity, long timeToLive, ReferenceType referenceType) {

        this(name, capacity, timeToLive, MINUS_ONE, referenceType);
    }

    public SimpleCache(String name, long capacity, long timeToLive, long timeToIdle, ReferenceType referenceType) {
        super(name);
        Assert.isFalse(timeToLive == ZERO, "Parameter \"timeToLive\" must not be equal to zero. ");
        Assert.isFalse(timeToIdle == ZERO, "Parameter \"timeToIdle\" must not be equal to zero. ");
        Assert.notNull(referenceType, "Parameter \"referenceType\" must not null. ");
        this.timeToLive = timeToLive < ZERO ? MINUS_ONE : timeToLive;
        this.timeToIdle = timeToIdle < ZERO ? MINUS_ONE : timeToIdle;
        this.capacity = capacity < ZERO ? ZERO : capacity;
        this.storage = buildStorage(referenceType);
    }

    protected Map<Object, ValueWrapper> getStorage() {

        return storage;
    }

    protected Map<Object, ValueWrapper> buildStorage(ReferenceType referenceType) {
        Assert.isTrue(SOFT.equals(referenceType) || WEAK.equals(referenceType),
                "Parameter \"referenceType\" must be soft reference or weak reference. ");
        return new ReferenceMap<Object, ValueWrapper>(referenceType
                , new ConcurrentHashMap<Object, ReferenceMap.ValueCell<Object, ValueWrapper>>(THIRTY));
    }

    @Override
    protected ValueWrapper getValueWrapper(Object key) {

        return storage.get(key);
    }

    @Override
    protected ValueWrapper putValueWrapper(Object key, ValueWrapper valueWrapper) {
        long timeToLive = calcTimeToLive(this.timeToLive, this.timeToIdle);
        // Since "put()" is called first, then "expire()" is called.
        // There is no need to compare the old expiration time.
        // And logically avoid setting the expiration time before saving the value.
        if (timeToLive >= ZERO) { valueWrapper.expire(timeToLive); }
        return storage.put(key, valueWrapper);
    }

    @Override
    protected ValueWrapper removeValueWrapper(Object key) {

        return storage.remove(key);
    }

    @Override
    protected boolean isFull() {

        return capacity > ZERO && (size() + TEN) >= capacity;
    }

    @Override
    protected void recordTouch(Object key, boolean touched) {
        (touched ? hitCount : missCount).incrementAndGet();
        if (getRecordLog()) {
            String content = NEWLINE +
                    "---- Begin Cache ----" + NEWLINE +
                    "Name:        " + getName() + NEWLINE +
                    "Key:         " + key + NEWLINE +
                    "Touched:     " + touched + NEWLINE +
                    "Hit Count:   " + hitCount + NEWLINE +
                    "Miss Count:  " + missCount + NEWLINE +
                    "Provider:    " + getClass().getName() + NEWLINE +
                    "---- End Cache ----" + NEWLINE;
            log.info(content);
        }
    }

    @Override
    public Object getNativeCache() {

        return storage;
    }

    @Override
    public Object get(Object key) {
        Object value = super.get(key);
        if (value != null && timeToIdle >= ZERO) {
            expire(key, timeToIdle, TimeUnit.MILLISECONDS);
        }
        return value;
    }

    @Override
    public boolean containsKey(Object key) {
        Assert.notNull(key, "Parameter \"key\" must not null. ");
        boolean containsKey = storage.containsKey(key);
        if (containsKey && timeToIdle >= ZERO) {
            expire(key, timeToIdle, TimeUnit.MILLISECONDS);
        }
        recordTouch(key, containsKey);
        return containsKey;
    }

    @Override
    public long size() {

        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
        hitCount.set(ZERO);
        missCount.set(ZERO);
    }

    @Override
    public long prune() {
        if (MapUtils.isEmpty(storage)) { return ZERO; }
        long count = ZERO;
        for (Map.Entry<Object, ValueWrapper> entry : storage.entrySet()) {
            ValueWrapper valueWrapper = entry.getValue();
            if (valueWrapper == null) { continue; }
            Object key = entry.getKey();
            if (valueWrapper.isExpired()) {
                storage.remove(key);
                recordEviction(key, TWO);
                count++;
            }
        }
        if (isFull()) {
            log.warn("The cache named \"{}\" is still full after pruning. ", getName());
        }
        return count;
    }

    @Override
    public Collection<Object> keys() {

        return storage.keySet();
    }

    @Override
    public Map<Object, Object> entries() {
        Map<Object, Object> result = new HashMap<Object, Object>(storage.size());
        if (MapUtils.isEmpty(storage)) { return emptyMap(); }
        for (Map.Entry<Object, ValueWrapper> entry : storage.entrySet()) {
            ValueWrapper val = entry.getValue();
            Object key = entry.getKey();
            if (key == null || val == null) { continue; }
            if (val.isExpired()) {
                removeValueWrapper(key);
                recordEviction(key, TWO);
                continue;
            }
            result.put(key, val.getValue());
        }
        return Collections.unmodifiableMap(result);
    }

}
