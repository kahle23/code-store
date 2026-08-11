//
// 迁移注记（2026-08-12）：本测试引用的主类仍是整类注释空壳（旧版 artoria API）或
// 依赖在新版 artoria 中不存在，暂无法编译。整类注释保留源码。
//
// ----- 以下为原始测试代码（整类注释）-----
// package store.code.demo.jdbc.way3;
// 
// import java.util.List;
// 
// import org.junit.Test;
// 
// 
// public class DBUtil1Demo {
// 	
// 	@Test
// 	public void test1(){
// 		DbConf conf = new C3p0Conf(
// 				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
// 				"root", "root"); conf.init(); DBUtil1.init(conf.getDataSource());
// 		
// 		User user = DBUtil1.queryBean(User.class, "select * from tb_user");
// 		System.out.println(user);
// 	}
// 	
// 	@Test
// 	public void test2(){
// 		DbConf conf = new C3p0Conf(
// 				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
// 				"root", "root"); conf.init(); DBUtil1.init(conf.getDataSource());
// 		
// 		List<User> list = DBUtil1.queryListBean(User.class, "select * from tb_user");
// 		for (User user : list) System.out.println(user);
// 	}
// 	
// 	@Test
// 	public void test3(){
// 		DbConf conf = new C3p0Conf(
// 				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
// 				"root", "root"); conf.init(); DBUtil1.init(conf.getDataSource());
// 				
// 		System.out.println(DBUtil1.update("insert into tb_user(user_name, password, name) values(?, ?, ?)", "张三", "123", "zhangsan"));
// 		System.out.println(DBUtil1.queryArray("SELECT LAST_INSERT_ID();")[0]);
// 		List<User> list = DBUtil1.queryListBean(User.class, "select * from tb_user");
// 		for (User user : list) System.out.println(user);
// 	}
// 	
// 	@Test
// 	public void test4(){
// 		DbConf conf = new C3p0Conf(
// 				"jdbc:mysql://127.0.0.1/demo4j?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
// 				"root", "root");
// 		conf.init();
// 		DBUtil1.init(conf.getDataSource());
// 		DBUtil1.update("CREATE TABLE `tb_role` (`id` bigint(20) NOT NULL AUTO_INCREMENT, `name` varchar(32) NOT NULL, `pid` bigint(20) NOT NULL, PRIMARY KEY (`id`));");
// 	}
// }
// 
