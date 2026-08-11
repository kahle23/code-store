//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.message.way1;
// 
// import artoria.util.ThreadUtils;
// import com.alibaba.fastjson.JSON;
// import org.junit.Ignore;
// import org.junit.Test;
// 
// @Ignore
// public class MessageUtilsTest {
//     private static MessageListener messageListener1;
//     private static MessageListener messageListener2;
//     private static AsyncCallback<Object> callback;
//     private static String destination = "message.test";
// 
//     static {
// //        messageProvider.createQueue(destination);
// //        MessageUtils.getMessageProvider().createTopic(destination);
//         messageListener1 = new MessageListener() {
//             @Override
//             public void onMessage(Message message) {
//                 System.out.println(">>listener 1 receive: " + JSON.toJSONString(message));
//             }
//         };
//         messageListener2 = new MessageListener() {
//             @Override
//             public void onMessage(Message message) {
//                 System.out.println(">>listener 2 receive: " + JSON.toJSONString(message));
//             }
//         };
//         callback = new AsyncCallback<Object>() {
//             @Override
//             public void onSuccess(Object result) {
//                 System.out.println("onSuccess");
//             }
//             @Override
//             public void onFailure(Throwable th) {
//                 System.out.println("onFailure");
//             }
//         };
//     }
// 
//     @Test
//     public void test1() {
//         MessageUtils.listening(destination, null, messageListener1);
//         MessageUtils.listening(destination, null, messageListener2);
//         for (int i = 0; i < 10; i++) {
//             Message message = new SimpleMessage();
//             message.setDestination(destination);
//             message.setBody("test1 >> " + i);
//             MessageUtils.send(message);
//         }
//         ThreadUtils.sleepQuietly(9000L);
//     }
// 
//     @Test
//     public void test2() {
//         MessageUtils.listening(destination, null, messageListener1);
//         MessageUtils.listening(destination, null, messageListener2);
//         Message message = new SimpleMessage();
//         message.setDestination(destination);
//         message.setBody("test2 >> sendAsync");
//         MessageUtils.sendAsync(message, callback);
//         System.out.println("sendAsync end");
//         ThreadUtils.sleepQuietly(9000L);
//     }
// 
// }
// 
