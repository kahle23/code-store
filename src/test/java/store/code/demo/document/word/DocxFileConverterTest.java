//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.demo.document.word;
// 
// import artoria.io.IOUtils;
// import store.code.demo.document.common.AbstractConverter;
// import org.junit.Ignore;
// import org.junit.Test;
// 
// import java.io.*;
// 
// @Ignore
// public class DocxFileConverterTest {
//     private AbstractConverter converter = new DocxFileConverter();
// 
//     @Test
//     public void test1() throws IOException {
//         InputStream in = new FileInputStream("E:\\Temp\\Test\\Test.docx");
//         OutputStream out = new FileOutputStream("E:\\Temp\\Test\\docx_test.html");
//         converter.convertToHtml(in, out, "utf-8"
//                 , "./", new File("E:\\Temp\\Test"));
//         IOUtils.closeQuietly(out);
//         IOUtils.closeQuietly(in);
//     }
// 
// }
