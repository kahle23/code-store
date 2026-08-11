//
// 迁移注记（2026-08-12）：本测试引用的依赖在新版 artoria 中不存在，或引用的主类仍是
// 整类注释空壳（旧版 artoria API），暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.fake.way2.extend;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import artoria.test.pojo.entity.other.Book;
// import artoria.test.pojo.entity.system.User;
// import com.alibaba.fastjson.JSON;
// import org.junit.Test;
// import store.code.fake.way2.FakeUtils;
// 
// import java.util.List;
// 
// import static java.lang.Boolean.TRUE;
// 
// public class FakeUtilsTest1 {
//     private static Logger log = LoggerFactory.getLogger(FakeUtilsTest1.class);
// 
//     static {
//         new JavaFakerAutoConfiguration();
//     }
// 
//     @Test
//     public void testFake1() {
//         String expression = "name=name|gender=dog.gender|nickname=name.firstName|" +
//                 "phoneNumber=phone_number|introduce=lorem.paragraph";
//         User user = FakeUtils.fake(expression, User.class);
//         log.info("Fake user: {}", JSON.toJSONString(user, TRUE));
//         expression = "name=book.title|author=book.author|publisher=book.publisher";
//         Book book = FakeUtils.fake(expression, Book.class);
//         log.info("Fake book: {}", JSON.toJSONString(book, TRUE));
//     }
// 
//     @Test
//     public void testFakeList1() {
//         String expression = "name=name|gender=dog.gender|nickname=name.firstName|" +
//                 "phoneNumber=phone_number|introduce=lorem.paragraph";
//         List<User> userList = FakeUtils.fakeList(expression, User.class);
//         log.info("Fake user list: {}", JSON.toJSONString(userList, TRUE));
//     }
// 
//     @Test
//     public void testFakeList2() {
//         String expression = "name=book.title|author=book.author|publisher=book.publisher";
//         List<Book> bookList = FakeUtils.fakeList(expression, Book.class);
//         log.info("Fake book list: {}", JSON.toJSONString(bookList, TRUE));
//     }
// 
// }
// 
