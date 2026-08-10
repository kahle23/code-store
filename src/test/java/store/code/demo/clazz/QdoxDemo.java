// 迁移注记（2026-08-09）：依赖 com.thoughtworks.qdox.JavaProjectBuilder，但迁移所用 qdox 1.12.1 无此类（仅 2.0.x 提供），demo 测试，故整类注释。
// package store.code.demo.clazz;
//
// import artoria.logging.Logger;
// import artoria.logging.LoggerFactory;
// import com.thoughtworks.qdox.JavaProjectBuilder;
// import com.thoughtworks.qdox.model.JavaClass;
// import org.apache.commons.lang3.StringUtils;
// import org.junit.Test;
//
// import java.io.File;
// import java.util.Collection;
//
// public class QdoxDemo {
//     private static Logger log = LoggerFactory.getLogger(QdoxDemo.class);
//
//     @Test
//     public void test1() {
//         String path = "src/main/java";
//         path = StringUtils.replace(path, "\\", "/");
//         JavaProjectBuilder builder = new JavaProjectBuilder();
//         builder.addSourceTree(new File(path));
//         Collection<JavaClass> classes = builder.getClasses();
//         for (JavaClass javaClass : classes) {
//             String fullyQualifiedName = javaClass.getFullyQualifiedName();
//             log.info("{}", fullyQualifiedName);
//         }
//     }
//
// }