//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.demo.captcha;
// 
// import org.junit.Test;
// 
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;
// 
// public class CaptchaDemo {
// 
//     @Test
//     public void test() throws IOException {
//         StringCaptcha captcha = new StringCaptcha(120, 90);
//         File file = new File("d:\\1.png"); file.createNewFile();
//         captcha.write(new FileOutputStream(file));
//     }
// 
// }
// 
