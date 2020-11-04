java
-Denv=PRO   //程序环境设置
-server     //模式设置,默认服务器模式, 服务器模式-server / 客户端模式-client
-Xms4g      //堆区初始值,-XX:InitialHeapSize=4096m也可
-Xmx4g      //堆区最大值,-XX:MaxHeapSize=4096m也可
-Xmn2g      //新生代最大值,-XX:MaxNewSize=2048m也可
-XX:MaxDirectMemorySize=512m // 最大直接内存
-XX:MetaspaceSize=128m       //共享内存的非堆区域    触发FullGC阈值,默认21807104B,约20.8M,Metaspace内存容量到达该阈值后,每次扩容都有可能会触发FullGC
-XX:MaxMetaspaceSize=512m    //共享内存的非堆区域    最大元空间大小,默认无限制
-XX:-UseBiasedLocking  //关闭 锁机制的性能改善
-XX:-UseCounterDecay  //关闭热度衰减
-XX:AutoBoxCacheMax=10240
//IntegerCache静态代码块大小JVM在加载Integer这个类时,会优先加载静态的代码,当JVM进程启动完毕后, -128 ~ +127 范围的数字会被缓存起来,调用valueOf方法的时候,如果是这个范围内的数字,则直接从缓存取出,超过这个范围的,只能构造新的Integer对象
-XX:CMSInitiatingOccupancyFraction=75 //GC参数
//垃圾收集线程会跟应用的线程一起并行的工作,当垃圾收集线程在工作的时候,老年代内存可能存在不足会导致promontion faild从而导致full gc,参数意思是：当老年代堆空间的使用率达到75%的时候就开始执行垃圾回收,CMSInitiatingOccupancyFraction默认值是92%
//CMSInitiatingOccupancyFraction 必须和UseConcMarkSweepGC/UseCMSInitiatingOccupancyOnly一起使用
-XX:+UseConcMarkSweepGC   // GC参数  	使用CMS内存收集,默认新生代收集器
-XX:+UseCMSInitiatingOccupancyOnly //GC参数 	使用手动定义初始化定义开始CMS收集,禁止hostspot自行触发CMS GC
-XX:MaxTenuringThreshold=6   //年轻代对象转换为老年代对象最大年龄值,默认值15,对象在坚持过一次Minor GC之后,年龄就加1
-XX:+ExplicitGCInvokesConcurrent //CMS配置,改变System.gc()的行为,让其从full gc 改为 CMS GC,解决full gc,整个应用停顿,设置了ExplicitGCInvokesConcurrent,那就不要设置DisableExplicitGC参数来禁掉System.gc()
-XX:+ParallelRefProcEnabled //默认为false,并行的处理Reference对象,如WeakReference,除非在GC log里出现Reference处理时间较长的日志，否则效果不会很明显。
-XX:+PerfDisableSharedMem //启用标准内存使用。JVM控制分为标准或共享内存，区别在于一个是在JVM内存中，一个是生成/tmp/hsperfdata_{userid}/{pid}文件，存储统计数据，通过mmap映射到内存中，别的进程可以通过文件访问内容。通过这个参数，可以禁止JVM写在文件中写统计数据，代价就是jps、jstat这些命令用不了了，只能通过jmx获取数据。但是在问题排查是，jps、jstat这些小工具是很好用的，比jmx这种很重的东西好用很多，需要取舍
-XX:+AlwaysPreTouch // 在启动时把所有参数定义的内存全部捋一遍。使用这个参数可能会使启动变慢，但是在后面内存使用过程中会更快。可以保证内存页面连续分配，新生代晋升时不会因为申请内存页面使GC停顿加长。通常只有在内存大于32G的时候才会有感觉。
-XX:-OmitStackTraceInFastThrow //不忽略重复异常的栈，JDK的优化，大量重复的JDK异常不再打印其StackTrace。但是如果系统是长时间不重启的系统，在同一个地方跑了N多次异常，结果就被JDK忽略了，那岂不是查看日志的时候就看不到具体的StackTrace，那还怎么调试，所以还是关了的好
-XX:+HeapDumpOnOutOfMemoryError  // 发生内存溢出时进行heap-dump
-XX:HeapDumpPath=/home/devjava/logs/  // 与-XX:+HeapDumpOnOutOfMemoryError共同作用，设置heap-dump时内容输出文件
-Xloggc:/home/devjava/logs/lifecircle-tradecore-gc.log   // 指定gc日志位置
-XX:+PrintGCApplicationStoppedTime // 打印所有引起JVM停顿时间，如果发现了一些不知什么的停顿，再临时加上-XX:+PrintSafepointStatistics -XX: PrintSafepointStatisticsCount=1找原因
-XX:+PrintGCDateStamps // 打印可读的日期而不是时间戳
-XX:+PrintGCDetails  // 启用gc日志打印功能
-javaagent:/home/devjava/ArmsAgent/arms-bootstrap-1.7.0-SNAPSHOT.jar   // 使用-javaagent 参数可以在执行main函数前执行一些其他逻辑（类似aop），甚至可以动态的修改替换类中代码。代理 (agent) 是在main方法前的一个拦截器 (interceptor)，也就是在main方法执行之前，执行agent的代码。agent的代码与main方法在同一个JVM中运行，并被同一个system classloader装载，被同一的安全策略 (security policy) 和上下文 (context) 所管理
-jar /home/devjava/lifecircle-tradecore/app/lifecircle-tradecore.jar   // 运行jar包，会自动到 jar 包中查询mainfest中定义的启动类并运行