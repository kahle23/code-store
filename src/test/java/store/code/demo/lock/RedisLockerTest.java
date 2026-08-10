/*
 * 迁移注记（2026-08-09）：本文件源自 demo4j，依赖 artoria.template.Renderer / artoria.collection.ReferenceMap，这两个 API 在 code-store 当前锁定的 artoria 版本（com.github.kahlkn:artoria:1.0.0.20210917f.beta）中不存在，无法解析，故整类注释。如需启用，需升级 artoria 到包含对应 API 的版本，或改为依赖 code-store 已有的 store.code.renderer / store.code.lock 体系。
 *
 * ----- 以下为原始迁移代码（整类注释）-----
package store.code.demo.lock;

import artoria.util.ThreadUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class RedisLockerTest {
    private static Logger log = LoggerFactory.getLogger(RedissonLockDemo.class);
    private static RedisLocker redisLocker;
    private ExecutorService pool;
    private Integer num = 100;

    static {
        Config config = new Config();
//        config.setTransportMode(TransportMode.EPOLL);
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379").setPassword("123456");
        RedissonClient redisson = Redisson.create(config);
        redisLocker = new RedisLocker(redisson);
    }

    @Before
    public void init() {
        // Executors.newFixedThreadPool(10);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        pool = new ThreadPoolExecutor(10, 10, 0L
                , TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
    }

    @After
    public void destroy() {

        pool.shutdown();
    }

    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            pool.submit(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000000; j++) {
                        if (num >= 0) {
                            redisLocker.lock("redis-locker-test1");
                            try {
                                if (num >= 0) {
                                    log.info("{}", num--);
                                }
                            }
                            finally {
                                redisLocker.unlock("redis-locker-test1");
                            }
                        }
                    }
                }
            });
        }
        ThreadUtils.sleepQuietly(10000);
    }

}
 */
