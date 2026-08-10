package store.code.demo.common.sigar;

/*
 * 迁移注记：依赖 kamon.sigar / org.hyperic.sigar 无法从 central 解析，已整体注释
 */
// 
// import kamon.sigar.SigarProvisioner;
// import org.hyperic.sigar.*;
// import org.junit.Test;
// 
// import java.io.File;
// import java.net.InetAddress;
// import java.net.UnknownHostException;
// import java.util.Map;
// import java.util.Properties;
// 
// public class Demo {
// 
//     static {
//         try {
//             final File location = new File("target/native");
//             SigarProvisioner.provision(location);
//         }
//         catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// 
//     @Test
//     public void main() {
//         try {
//             property();
//             System.out.println("----------------------------------");
//             cpu();
//             System.out.println("----------------------------------");
//             memory();
//             System.out.println("----------------------------------");
//             os();
//             System.out.println("----------------------------------");
//             who();
//             System.out.println("----------------------------------");
//             file();
//             System.out.println("----------------------------------");
//             net();
//             System.out.println("----------------------------------");
//             System.out.println("----------------------------------");
//         }
//         catch (Exception e1) {
//             e1.printStackTrace();
//         }
//     }
// 
//     @Test
//     public void property() throws UnknownHostException {
//         Runtime r = Runtime.getRuntime();
//         Properties props = System.getProperties();
//         InetAddress addr;
//         addr = InetAddress.getLocalHost();
//         String ip = addr.getHostAddress();
//         Map<String, String> map = System.getenv();
//         String userName = map.get("USERNAME");// 鑾峰彇鐢ㄦ埛鍚?        String computerName = map.get("COMPUTERNAME");// 鑾峰彇璁＄畻鏈哄悕
//         String userDomain = map.get("USERDOMAIN");// 鑾峰彇璁＄畻鏈哄煙鍚?        System.out.println("鐢ㄦ埛鍚?    " + userName);
//         System.out.println("璁＄畻鏈哄悕:    " + computerName);
//         System.out.println("璁＄畻鏈哄煙鍚?    " + userDomain);
//         System.out.println("鏈湴ip鍦板潃:    " + ip);
//         System.out.println("鏈湴涓绘満鍚?    " + addr.getHostName());
//         System.out.println("JVM鍙互浣跨敤鐨勬€诲唴瀛?    " + r.totalMemory());
//         System.out.println("JVM鍙互浣跨敤鐨勫墿浣欏唴瀛?    " + r.freeMemory());
//         System.out.println("JVM鍙互浣跨敤鐨勫鐞嗗櫒涓暟:    " + r.availableProcessors());
//         System.out.println("Java鐨勮繍琛岀幆澧冪増鏈細    " + props.getProperty("java.version"));
//         System.out.println("Java鐨勮繍琛岀幆澧冧緵搴斿晢锛?   " + props.getProperty("java.vendor"));
//         System.out.println("Java渚涘簲鍟嗙殑URL锛?   " + props.getProperty("java.vendor.url"));
//         System.out.println("Java鐨勫畨瑁呰矾寰勶細    " + props.getProperty("java.home"));
//         System.out.println("Java鐨勮櫄鎷熸満瑙勮寖鐗堟湰锛?   " + props.getProperty("java.vm.specification.version"));
//         System.out.println("Java鐨勮櫄鎷熸満瑙勮寖渚涘簲鍟嗭細    " + props.getProperty("java.vm.specification.vendor"));
//         System.out.println("Java鐨勮櫄鎷熸満瑙勮寖鍚嶇О锛?   " + props.getProperty("java.vm.specification.name"));
//         System.out.println("Java鐨勮櫄鎷熸満瀹炵幇鐗堟湰锛?   " + props.getProperty("java.vm.version"));
//         System.out.println("Java鐨勮櫄鎷熸満瀹炵幇渚涘簲鍟嗭細    " + props.getProperty("java.vm.vendor"));
//         System.out.println("Java鐨勮櫄鎷熸満瀹炵幇鍚嶇О锛?   " + props.getProperty("java.vm.name"));
//         System.out.println("Java杩愯鏃剁幆澧冭鑼冪増鏈細    " + props.getProperty("java.specification.version"));
//         System.out.println("Java杩愯鏃剁幆澧冭鑼冧緵搴斿晢锛?   " + props.getProperty("java.specification.vender"));
//         System.out.println("Java杩愯鏃剁幆澧冭鑼冨悕绉帮細    " + props.getProperty("java.specification.name"));
//         System.out.println("Java鐨勭被鏍煎紡鐗堟湰鍙凤細    " + props.getProperty("java.class.version"));
//         System.out.println("Java鐨勭被璺緞锛?   " + props.getProperty("java.class.path"));
//         System.out.println("鍔犺浇搴撴椂鎼滅储鐨勮矾寰勫垪琛細    " + props.getProperty("java.library.path"));
//         System.out.println("榛樿鐨勪复鏃舵枃浠惰矾寰勶細    " + props.getProperty("java.io.tmpdir"));
//         System.out.println("涓€涓垨澶氫釜鎵╁睍鐩綍鐨勮矾寰勶細    " + props.getProperty("java.ext.dirs"));
//         System.out.println("鎿嶄綔绯荤粺鐨勫悕绉帮細    " + props.getProperty("os.name"));
//         System.out.println("鎿嶄綔绯荤粺鐨勬瀯鏋讹細    " + props.getProperty("os.arch"));
//         System.out.println("鎿嶄綔绯荤粺鐨勭増鏈細    " + props.getProperty("os.version"));
//         System.out.println("鏂囦欢鍒嗛殧绗︼細    " + props.getProperty("file.separator"));
//         System.out.println("璺緞鍒嗛殧绗︼細    " + props.getProperty("path.separator"));
//         System.out.println("琛屽垎闅旂锛?   " + props.getProperty("line.separator"));
//         System.out.println("鐢ㄦ埛鐨勮处鎴峰悕绉帮細    " + props.getProperty("user.name"));
//         System.out.println("鐢ㄦ埛鐨勪富鐩綍锛?   " + props.getProperty("user.home"));
//         System.out.println("鐢ㄦ埛鐨勫綋鍓嶅伐浣滅洰褰曪細    " + props.getProperty("user.dir"));
//     }
// 
//     @Test
//     public void memory() throws SigarException {
//         Sigar sigar = new Sigar();
//         Mem mem = sigar.getMem();
//         System.out.println("----------------");
//         System.out.println("鍐呭瓨鎬婚噺:    " + mem.getTotal() / 1024L / 1024L + "MB av");
// 
//         System.out.println("----------------");
//         System.out.println("浣跨敤鐜?    " + mem.getUsedPercent());
//         System.out.println("鍓╀綑鐜?    " + mem.getFreePercent());
// 
//         System.out.println("----------------");
//         Swap swap = sigar.getSwap();
//         System.out.println("浜ゆ崲鍖烘€婚噺:    " + swap.getTotal() / 1024L / 1024L  + "MB av");
//         System.out.println("褰撳墠浜ゆ崲鍖轰娇鐢ㄩ噺:    " + swap.getUsed() / 1024L / 1024L  + "MB used");
//         System.out.println("褰撳墠浜ゆ崲鍖哄墿浣欓噺:    " + swap.getFree() / 1024L / 1024L + "MB free");
//     }
// 
//     @Test
//     public void cpu() throws SigarException {
//         Sigar sigar = new Sigar();
//         CpuInfo[] infos = sigar.getCpuInfoList();
//         CpuPerc[] cpuList = sigar.getCpuPercList();
//         for (int i = 0; i < infos.length; i++) {
//             CpuInfo info = infos[i];
//             System.out.print("绗? + (i + 1) + "鍧桟PU淇℃伅[ ");
//             System.out.print("CPU鐨勬€婚噺MHz: " + info.getMhz());
//             System.out.print(", CPU鐢熶骇鍟? " + info.getVendor());
//             System.out.print(", CPU绫诲埆: " + info.getModel());
//             System.out.println(" ]");
//             printCpuPerc(cpuList[i]);
//             System.out.println("----------------");
//         }
//     }
// 
//     public void printCpuPerc(CpuPerc cpu) {
//         System.out.println("CPU褰撳墠绌洪棽鐜?    " + CpuPerc.format(cpu.getIdle()));// 褰撳墠绌洪棽鐜?        System.out.println("CPU鎬荤殑浣跨敤鐜?    " + CpuPerc.format(cpu.getCombined()));// 鎬荤殑浣跨敤鐜?    }
// 
//     @Test
//     public void os() {
//         OperatingSystem OS = OperatingSystem.getInstance();
//         System.out.println("鎿嶄綔绯荤粺:    " + OS.getArch());
//         System.out.println("鎿嶄綔绯荤粺CpuEndian():    " + OS.getCpuEndian());
//         System.out.println("鎿嶄綔绯荤粺DataModel():    " + OS.getDataModel());
//         System.out.println("鎿嶄綔绯荤粺鐨勬弿杩?    " + OS.getDescription());
//         System.out.println("OS.getName():    " + OS.getName());
//         System.out.println("OS.getPatchLevel():    " + OS.getPatchLevel());
//         System.out.println("鎿嶄綔绯荤粺鐨勫崠涓诲悕:    " + OS.getVendorCodeName());
//         System.out.println("鎿嶄綔绯荤粺鍚嶇О:    " + OS.getVendorName());
//         System.out.println("鎿嶄綔绯荤粺鍗栦富绫诲瀷:    " + OS.getVendorVersion());
//         System.out.println("鎿嶄綔绯荤粺鐨勭増鏈彿:    " + OS.getVersion());
//     }
// 
//     @Test
//     public void who() throws SigarException {
//         Sigar sigar = new Sigar();
//         Who who[] = sigar.getWhoList();
//         if (who != null && who.length > 0) {
//             for (int i = 0; i < who.length; i++) {
//                 Who _who = who[i];
//                 System.out.println("鐢ㄦ埛鎺у埗鍙?    " + _who.getDevice());
//                 System.out.println("鐢ㄦ埛host:    " + _who.getHost());
//                 System.out.println("褰撳墠绯荤粺杩涚▼琛ㄤ腑鐨勭敤鎴峰悕:    " + _who.getUser());
//             }
//         }
//     }
// 
//     @Test
//     public void file() throws Exception {
//         Sigar sigar = new Sigar();
//         FileSystem fslist[] = sigar.getFileSystemList();
//         try {
//             for (int i = 0; i < fslist.length; i++) {
//                 System.out.println("鍒嗗尯鐨勭洏绗﹀悕绉? + i);
//                 FileSystem fs = fslist[i];
//                 System.out.println("鐩樼绫诲瀷:    " + fs.getSysTypeName());
//                 System.out.println("鐩樼绫诲瀷鍚?    " + fs.getTypeName());
//                 System.out.println("鐩樼鏂囦欢绯荤粺绫诲瀷:    " + fs.getType());
//                 FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
//                 switch (fs.getType()) {
//                     case 0: // TYPE_UNKNOWN 锛氭湭鐭?                        break;
//                     case 1: // TYPE_NONE
//                         break;
//                     case 2: // TYPE_LOCAL_DISK : 鏈湴纭洏
//                         System.out.println(fs.getDevName() + "鍓╀綑澶у皬:    " + usage.getFree() / 1024 / 1024 + "GB");
//                         System.out.println(fs.getDevName() + "鍙敤澶у皬:    " + usage.getAvail() / 1024 / 1024 + "GB");
//                         double usePercent = usage.getUsePercent() * 100D;
//                         System.out.println(fs.getDevName() + "璧勬簮鐨勫埄鐢ㄧ巼:    " + usePercent + "%");
//                         break;
//                     case 3:// TYPE_NETWORK 锛氱綉缁?                        break;
//                     case 4:// TYPE_RAM_DISK 锛氶棯瀛?                        break;
//                     case 5:// TYPE_CDROM 锛氬厜椹?                        break;
//                     case 6:// TYPE_SWAP 锛氶〉闈氦鎹?                        break;
//                 }
//                 System.out.println(fs.getDevName() + "璇诲嚭锛?   " + usage.getDiskReads() + "kb/s");
//                 System.out.println(fs.getDevName() + "鍐欏叆锛?   " + usage.getDiskWrites() + "kb/s");
//                 System.out.println("----------------");
//             }
//         }
//         catch (Exception e) {
//             e.printStackTrace();
//         }
// 
//         return;
//     }
// 
//     @Test
//     public void net() throws Exception {
//         Sigar sigar = new Sigar();
//         String ifNames[] = sigar.getNetInterfaceList();
//         for (int i = 0; i < ifNames.length; i++) {
//             String name = ifNames[i];
//             NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
//             System.out.println("缃戠粶璁惧鍚?    " + name);// 缃戠粶璁惧鍚?            System.out.println("IP鍦板潃:    " + ifconfig.getAddress());// IP鍦板潃
//             System.out.println("瀛愮綉鎺╃爜:    " + ifconfig.getNetmask());// 瀛愮綉鎺╃爜
//             if ((ifconfig.getFlags() & 1L) <= 0L) {
//                 System.out.println("!IFF_UP...skipping getNetInterfaceStat");
//                 System.out.println();
//                 continue;
//             }
//             NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
//             System.out.println(name + "鎺ユ敹鐨勬€诲寘瑁规暟:" + ifstat.getRxPackets());// 鎺ユ敹鐨勬€诲寘瑁规暟
//             System.out.println(name + "鍙戦€佺殑鎬诲寘瑁规暟:" + ifstat.getTxPackets());// 鍙戦€佺殑鎬诲寘瑁规暟
//             System.out.println(name + "鎺ユ敹鍒扮殑鎬诲瓧鑺傛暟:" + ifstat.getRxBytes());// 鎺ユ敹鍒扮殑鎬诲瓧鑺傛暟
//             System.out.println(name + "鍙戦€佺殑鎬诲瓧鑺傛暟:" + ifstat.getTxBytes());// 鍙戦€佺殑鎬诲瓧鑺傛暟
//             System.out.println(name + "鎺ユ敹鍒扮殑閿欒鍖呮暟:" + ifstat.getRxErrors());// 鎺ユ敹鍒扮殑閿欒鍖呮暟
//             System.out.println(name + "鍙戦€佹暟鎹寘鏃剁殑閿欒鏁?" + ifstat.getTxErrors());// 鍙戦€佹暟鎹寘鏃剁殑閿欒鏁?            System.out.println(name + "鎺ユ敹鏃朵涪寮冪殑鍖呮暟:" + ifstat.getRxDropped());// 鎺ユ敹鏃朵涪寮冪殑鍖呮暟
//             System.out.println(name + "鍙戦€佹椂涓㈠純鐨勫寘鏁?" + ifstat.getTxDropped());// 鍙戦€佹椂涓㈠純鐨勫寘鏁?            System.out.println("----------------");
//             System.out.println();
//         }
//     }
// 
//     @Test
//     public void ethernet() throws SigarException {
//         Sigar sigar = new Sigar();
//         String[] ifaces = sigar.getNetInterfaceList();
//         for (int i = 0; i < ifaces.length; i++) {
//             NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
//             if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
//                     || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
//                 continue;
//             }
//             System.out.println(cfg.getName() + "IP鍦板潃:" + cfg.getAddress());// IP鍦板潃
//             System.out.println(cfg.getName() + "缃戝叧骞挎挱鍦板潃:" + cfg.getBroadcast());// 缃戝叧骞挎挱鍦板潃
//             System.out.println(cfg.getName() + "缃戝崱MAC鍦板潃:" + cfg.getHwaddr());// 缃戝崱MAC鍦板潃
//             System.out.println(cfg.getName() + "瀛愮綉鎺╃爜:" + cfg.getNetmask());// 瀛愮綉鎺╃爜
//             System.out.println(cfg.getName() + "缃戝崱鎻忚堪淇℃伅:" + cfg.getDescription());// 缃戝崱鎻忚堪淇℃伅
//             System.out.println(cfg.getName() + "缃戝崱绫诲瀷:" + cfg.getType());
//             System.out.println("----------------");
//             System.out.println();
//         }
//     }
// 
// }
