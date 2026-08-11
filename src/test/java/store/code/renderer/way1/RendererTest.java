//
// 迁移注记（2026-08-12）：本测试引用的依赖在新版 artoria 中不存在，或引用的主类仍是
// 整类注释空壳（旧版 artoria API），暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.renderer.way1;
// 
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import org.junit.Before;
// import org.junit.Test;
// import store.code.renderer.way1.RenderUtils;
// 
// import java.io.StringReader;
// import java.util.HashMap;
// import java.util.Map;
// 
// import static artoria.common.Constants.DEFAULT;
// 
// public class RendererTest {
//     private static Logger log = LoggerFactory.getLogger(RendererTest.class);
//     private Map<String, Object> data = new HashMap<String, Object>();
// 
//     @Before
//     public void init() {
//         data.put("hello", "world");
//         data.put("hello1", new Object());
//     }
// 
//     @Test
//     public void test1() {
//         String tmp = "hello, ${hello}! \n" +
//                 "hello, ${hello1}! \n" +
//                 "hello, ${hello2}! \n" +
//                 "${hello}${hello}.";
//         log.info(RenderUtils.renderToString(data, DEFAULT, new StringReader(tmp)));
//     }
// 
//     @Test
//     public void test2() {
//         log.info(RenderUtils.renderToString(data, DEFAULT, new StringReader("\\${hello}abc")));
//         log.info(RenderUtils.renderToString(data, DEFAULT, new StringReader("abc\\${hello}abc${hello}abc")));
//         log.info(RenderUtils.renderToString(data, DEFAULT, new StringReader("\\\\${hello}abc")));
//         log.info(RenderUtils.renderToString(data, DEFAULT, new StringReader("\\\\\\${hello}abc")));
//         log.info(RenderUtils.renderToString(data, DEFAULT, new StringReader("abc\\\\${hello}abc${hello}abc")));
//     }
// 
// }
// 
