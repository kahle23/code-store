//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.fake.way1;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import artoria.test.pojo.entity.system.Menu;
// import artoria.test.pojo.entity.system.User;
// import artoria.time.DateUtils;
// import com.alibaba.fastjson.JSON;
// import org.junit.Test;
// 
// import java.util.Date;
// 
// public class FakeUtilsTest {
//     private static Logger log = LoggerFactory.getLogger(FakeUtilsTest.class);
// 
//     @Test
//     public void testFake() {
//         User user = FakeUtils.fake(User.class);
//         log.info(JSON.toJSONString(user, true));
// 
//         Menu menu = FakeUtils.fake(Menu.class);
//         log.info(JSON.toJSONString(menu, true));
// 
//         for (int i = 0; i < 10; i++) {
//             Date date = FakeUtils.fake(Date.class);
//             log.info(DateUtils.format(date));
//         }
//     }
// 
// }
// 
