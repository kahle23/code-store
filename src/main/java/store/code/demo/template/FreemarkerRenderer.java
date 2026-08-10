// 迁移注记（2026-08-09）：本文件源自 demo4j，依赖 artoria.template.Renderer，该 API 在 code-store 当前锁定的 artoria 版本（com.github.kahlkn:artoria:1.0.0.20210917f.beta）中不存在，无法解析，故整类注释。如需启用，需升级 artoria 到包含 artoria.template.Renderer 的版本，或改为依赖 code-store 已有的 store.code.renderer 体系。
// package store.code.demo.template;
//
// import artoria.exception.ExceptionUtils;
// import artoria.template.Renderer;
// import artoria.util.Assert;
// import artoria.util.StringUtils;
// import freemarker.cache.ClassTemplateLoader;
// import freemarker.cache.FileTemplateLoader;
// import freemarker.cache.MultiTemplateLoader;
// import freemarker.cache.TemplateLoader;
// import freemarker.template.Configuration;
// import freemarker.template.Template;
//
// import java.io.File;
// import java.io.Reader;
// import java.io.StringReader;
// import java.io.Writer;
//
// import static artoria.common.Constants.*;
//
// /**
//  * Freemarker template renderer.
//  * @author Kahle
//  */
// public class FreemarkerRenderer implements Renderer {
//     private Configuration configuration;
//
//     public FreemarkerRenderer() {
//         try {
//             Configuration configuration = new Configuration();
//             TemplateLoader[] loaders = new TemplateLoader[2];
//             loaders[0] = new FileTemplateLoader(new File(DOT));
//             loaders[1] = new ClassTemplateLoader(artoria.template.FreemarkerRenderer.class, SLASH);
//             MultiTemplateLoader loader = new MultiTemplateLoader(loaders);
//             configuration.setTemplateLoader(loader);
//             this.configuration = configuration;
//         }
//         catch (Exception e) {
//             throw ExceptionUtils.wrap(e);
//         }
//     }
//
//     public FreemarkerRenderer(Configuration configuration) {
//
//         this.configuration = configuration;
//     }
//
//     @Override
//     public void render(Object data, Object output, String name, Object input, String charsetName) {
//         try {
//             Assert.notBlank(name, "Parameter \"name\" must not blank. ");
//             Assert.state((output instanceof Writer)
//                     , "Parameter \"output\" must instance of \"Writer\". ");
//             if ((input instanceof Reader) || (input instanceof String)) {
//                 Reader reader = input instanceof Reader
//                         ? (Reader) input : new StringReader((String) input);
//                 Template template = new Template(name, reader, this.configuration);
//                 template.process(data, (Writer) output);
//             }
//             else {
//                 charsetName = StringUtils.isNotBlank(charsetName)
//                         ? charsetName : DEFAULT_CHARSET_NAME;
//                 Template template = this.configuration.getTemplate(name, charsetName);
//                 template.process(data, (Writer) output);
//             }
//         }
//         catch (Exception e) {
//             throw ExceptionUtils.wrap(e);
//         }
//     }
//
// }