//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.fake.way2;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import artoria.test.pojo.entity.other.Book;
// import artoria.test.pojo.entity.system.User;
// import artoria.time.DateUtils;
// import com.alibaba.fastjson.JSON;
// import org.junit.Test;
// 
// import java.util.Date;
// import java.util.List;
// 
// import static java.lang.Boolean.TRUE;
// 
// public class FakeUtilsTest {
//     private static Logger log = LoggerFactory.getLogger(FakeUtilsTest.class);
// 
//     @Test
//     public void testFake1() {
//         log.info("{}", FakeUtils.fake("time", String.class));
//         log.info("{}", FakeUtils.fake("time.now", String.class));
//         log.info("{}", FakeUtils.fake("id", String.class));
//         log.info("{}", FakeUtils.fake("id.long", Long.class));
//         log.info("{}", FakeUtils.fake("id.string", String.class));
//         log.info("{}", FakeUtils.fake("name", String.class));
//         log.info("{}", FakeUtils.fake("name", String.class));
//         log.info("{}", FakeUtils.fake("name.full_name", String.class));
//         log.info("{}", FakeUtils.fake("name.first_name", String.class));
//         log.info("{}", FakeUtils.fake("name.first_name[len:10]", String.class));
//         for (int i = 0; i < 4; i++) {
//             Date date = FakeUtils.fake(Date.class);
//             log.info("{}", DateUtils.format(date));
//         }
//     }
// 
//     @Test
//     public void testFake2() {
//         User user = FakeUtils.fake(User.class);
//         log.info("{}", JSON.toJSONString(user, TRUE));
// 
//         user = FakeUtils.fake("uid=id|nickname=name|name=name", User.class);
//         log.info("{}", JSON.toJSONString(user, TRUE));
// 
// //        Menu menu = FakeUtils.fake(Menu.class);
// //        log.info(JSON.toJSONString(menu, true));
//     }
// 
//     @Test
//     public void testFakeList1() {
//         log.info("{}", FakeUtils.fakeList("time", String.class));
//         log.info("{}", FakeUtils.fakeList("time", Date.class));
//         log.info("{}", FakeUtils.fakeList("name", String.class));
//     }
// 
//     @Test
//     public void testFakeList2() {
//         List<User> userList = FakeUtils.fakeList("", User.class);
//         log.info("{}", JSON.toJSONString(userList, true));
//         List<Book> bookList = FakeUtils.fakeList("", Book.class);
//         log.info("{}", JSON.toJSONString(bookList, true));
//     }
// 
// }
// 
