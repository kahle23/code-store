package store.code.context.way1;

import artoria.cache.Cache;
import artoria.cache.SimpleCache;
import artoria.data.ReferenceType;
import artoria.identifier.IdentifierUtils;
import artoria.util.Assert;
import artoria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static artoria.common.Constants.ZERO;
import static artoria.util.ObjectUtils.cast;

public class SimpleTokenManager implements TokenManager {
    private static Logger log = LoggerFactory.getLogger(SimpleTokenManager.class);
    private final Long tokenExpirationTime;
    private final Cache cache;

    public SimpleTokenManager(Long tokenExpirationTime) {
        // todo Need to optimize.
        Assert.notNull(tokenExpirationTime, "Parameter \"tokenExpirationTime\" must not null. ");
        if (tokenExpirationTime < ZERO) { tokenExpirationTime = 0L; }
        this.tokenExpirationTime = tokenExpirationTime;
        // todo cache
        this.cache = new SimpleCache(
                getClass().getName(), ReferenceType.SOFT
        );
    }

    @Override
    public void save(Token token) {
        Assert.notNull(token, "Parameter \"token\" must not null. ");
        String userId = token.getUserId();
        Assert.notBlank(userId, "Parameter \"userId\" must not blank. ");
        String tokenId = token.getId();
        if (StringUtils.isBlank(tokenId)) {
            token.setId(tokenId = generateId());
        }
        cache.put(tokenId, token);
        cache.expire(tokenId, tokenExpirationTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void refresh(String tokenId) {
        Assert.notBlank(tokenId, "Parameter \"tokenId\" must not blank. ");
        if (tokenExpirationTime <= ZERO) { return; }
        cache.expire(tokenId, tokenExpirationTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void remove(String tokenId) {
        Assert.notBlank(tokenId, "Parameter \"tokenId\" must not blank. ");
        cache.remove(tokenId);
    }

    @Override
    public void clear() {

        cache.clear();
    }

    @Override
    public void removeByUserId(String userId) {
        Assert.notBlank(userId, "Parameter \"userId\" must not blank. ");
        List<Object> delete = new ArrayList<Object>();
        Collection<Object> keys = cache.keys();
        for (Object key : keys) {
            if (key == null) { continue; }
            Token token = cache.get(key, Token.class);
            if (token == null) { continue; }
            if (userId.equals(token.getUserId())) {
                delete.add(key);
            }
        }
        cache.removeAll(delete);
    }

    @Override
    public String generateId() {

        return IdentifierUtils.nextStringIdentifier();
    }

    @Override
    public Token findById(String tokenId) {
        Assert.notBlank(tokenId, "Parameter \"tokenId\" must not blank. ");
        Object object = cache.get(tokenId);
        return cast(object);
    }

    @Override
    public List<Token> findByUserId(String userId) {
        Assert.notBlank(userId, "Parameter \"userId\" must not blank. ");
        throw new UnsupportedOperationException();
    }

}
