//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.cache.way1;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import org.junit.Ignore;
// import org.junit.Test;
// 
// import static artoria.common.Constants.*;
// 
// public class CacheUtilsTest {
//     private static Logger log = LoggerFactory.getLogger(CacheUtilsTest.class);
//     private static String cacheName = "TEST";
//     private static String cacheName1 = "TEST1";
// 
//     static {
//         long timeToLive = FOUR * ONE_THOUSAND;
//         SimpleCache cache = new SimpleCache(cacheName, timeToLive, ZERO);
//         cache.setPrintLog(true);
//         CacheUtils.register(cache);
//         SimpleCache cache1 = new SimpleCache(cacheName1);
//         cache1.setPrintLog(true);
//         CacheUtils.register(cache1);
//     }
// 
//     @Test
//     public void testTimeToLive() {
//         for (int i = ZERO; i < TEN; i++) {
//             CacheUtils.put(cacheName, i, "test-data-" + i);
//         }
//         for (int i = ZERO; i < 11; i++) {
//             log.info("{}", CacheUtils.get(cacheName, i));
//         }
//     }
// 
//     @Ignore
//     @Test
//     public void testWeakReferenceCache() {
//         StringBuilder builder = new StringBuilder();
//         for (int i = ZERO; i < 1000; i++) {
//             builder.append("data-").append(i).append("-data-");
//         }
//         for (int i = ZERO; i <= 9999999; i++) {
//             CacheUtils.put(cacheName1, i, builder.toString());
//             CacheUtils.get(cacheName1, i);
//         }
//     }
// 
// }
// 
