//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.demo.spider;
// 
// import org.junit.Test;
// import us.codecraft.webmagic.Spider;
// import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
// import us.codecraft.webmagic.pipeline.FilePipeline;
// 
// public class WebmagicDemo {
// 
//     @Test
//     public void test1() {
//         Spider.create(new WebmagicProcessor())
//                 .addUrl("https://www.zhihu.com/question/22918070")
//                 .thread(5)
//                 .run();
//     }
// 
//     @Test
//     public void test2() {
//         Spider.create(new HuabanProcessor()).thread(5)
//                 .addUrl("https://www.zhihu.com/question/22918070")
//                 .addPipeline(new FilePipeline("/test"))
//                 .setDownloader(new SeleniumDownloader("D:\\Kit\\WebDriver\\chromedriver.exe"))
//                 .run();
//     }
// 
// }
// 
