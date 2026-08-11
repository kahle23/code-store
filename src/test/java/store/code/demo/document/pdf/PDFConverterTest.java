//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.demo.document.pdf;
// 
// import artoria.io.IOUtils;
// import store.code.demo.document.common.AbstractConverter;
// import org.junit.Ignore;
// import org.junit.Test;
// 
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// 
// @Ignore
// public class PDFConverterTest {
//     private File file = new File("E:\\Temp\\Test\\Test.pdf");
//     private AbstractConverter converter = new PDFConverter();
// 
//     @Test
//     public void test1() throws Exception {
//         FileInputStream in = new FileInputStream(file);
//         FileOutputStream out = new FileOutputStream("E:\\Temp\\Test\\pdf_test.html");
//         converter.convertToHtml(in, out, "utf-8"
//                 , "./", new File("E:\\Temp\\Test"));
//         IOUtils.closeQuietly(out);
//         IOUtils.closeQuietly(in);
//     }
// 
// }
