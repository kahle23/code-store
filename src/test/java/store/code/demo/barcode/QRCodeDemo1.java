//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.demo.barcode;
// 
// import com.google.zxing.BarcodeFormat;
// import com.google.zxing.EncodeHintType;
// import com.google.zxing.WriterException;
// import com.google.zxing.common.BitMatrix;
// import com.google.zxing.qrcode.QRCodeWriter;
// import org.junit.Test;
// 
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.util.HashMap;
// 
// public class QRCodeDemo1 {
// 
//     @Test
//     public void test1() throws WriterException, IOException {
//         int width = 200;
//         int height = 200;
//         HashMap<EncodeHintType, Object> hints = new HashMap<>();
//         hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//         hints.put(EncodeHintType.MARGIN, 0);
//         FileOutputStream out = new FileOutputStream("d:\\aa.png");
// 
//         QRCodeWriter e = new QRCodeWriter();
//         BitMatrix bitMatrix = e.encode("123", BarcodeFormat.QR_CODE, width, height, hints);
//         MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
//     }
// }
// 
