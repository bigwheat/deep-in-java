~~~~
java
-Denv=PRO   
--程序环境设置

-server     
//模式设置,默认服务器模式, 服务器模式-server / 客户端模式-client

-Xms4g      
//堆区初始值,-XX:InitialHeapSize=4096m也可

-Xmx4g      
//堆区最大值,-XX:MaxHeapSize=4096m也可

-Xmn2g      
//新生代最大值,-XX:MaxNewSize=2048m也可

-XX:MaxDirectMemorySize=512m 
// 最大直接内存

-XX:MetaspaceSize=128m       
//共享内存的非堆区域    触发FullGC阈值,默认21807104B,约20.8M,Metaspace内存容量到达该阈值后,每次扩容都有可能会触发FullGC

-XX:MaxMetaspaceSize=512m    
//共享内存的非堆区域    最大元空间大小,默认无限制

-XX:-UseBiasedLocking  
//关闭 锁机制的性能改善

-XX:-UseCounterDecay  
//关闭热度衰减

-XX:AutoBoxCacheMax=10240 
// IntegerCache静态代码块大小JVM在加载Integer这个类时,会优先加载静态的代码,当JVM进程启动完毕后, -128 ~ +127 范围的数字会被缓存起来,调用valueOf方法的时候,如果是这个范围内的数字,则直接从缓存取出,超过这个范围的,只能构造新的Integer对象

-XX:CMSInitiatingOccupancyFraction=75 
//GC参数
//垃圾收集线程会跟应用的线程一起并行的工作,当垃圾收集线程在工作的时候,老年代内存可能存在不足会导致promontion faild从而导致full gc,参数意思是：当老年代堆空间的使用率达到75%的时候就开始执行垃圾回收,CMSInitiatingOccupancyFraction默认值是92%
//CMSInitiatingOccupancyFraction 必须和UseConcMarkSweepGC/UseCMSInitiatingOccupancyOnly一起使用

-XX:+UseConcMarkSweepGC   
// GC参数  	使用CMS内存收集,默认新生代收集器

-XX:+UseCMSInitiatingOccupancyOnly 
//GC参数 	使用手动定义初始化定义开始CMS收集,禁止hostspot自行触发CMS GC

-XX:MaxTenuringThreshold=6   
//年轻代对象转换为老年代对象最大年龄值,默认值15,对象在坚持过一次Minor GC之后,年龄就加1

-XX:+ExplicitGCInvokesConcurrent 
//CMS配置,改变System.gc()的行为,让其从full gc 改为 CMS GC,解决full gc,整个应用停顿,设置了ExplicitGCInvokesConcurrent,那就不要设置DisableExplicitGC参数来禁掉System.gc()

-XX:+ParallelRefProcEnabled 
//默认为false,并行的处理Reference对象,如WeakReference,除非在GC log里出现Reference处理时间较长的日志，否则效果不会很明显。

-XX:+PerfDisableSharedMem 
//启用标准内存使用。JVM控制分为标准或共享内存，区别在于一个是在JVM内存中，一个是生成/tmp/hsperfdata_{userid}/{pid}文件，存储统计数据，通过mmap映射到内存中，别的进程可以通过文件访问内容。通过这个参数，可以禁止JVM写在文件中写统计数据，代价就是jps、jstat这些命令用不了了，只能通过jmx获取数据。但是在问题排查是，jps、jstat这些小工具是很好用的，比jmx这种很重的东西好用很多，需要取舍

-XX:+AlwaysPreTouch 
// 在启动时把所有参数定义的内存全部捋一遍。使用这个参数可能会使启动变慢，但是在后面内存使用过程中会更快。可以保证内存页面连续分配，新生代晋升时不会因为申请内存页面使GC停顿加长。通常只有在内存大于32G的时候才会有感觉。

-XX:-OmitStackTraceInFastThrow 
//不忽略重复异常的栈，JDK的优化，大量重复的JDK异常不再打印其StackTrace。但是如果系统是长时间不重启的系统，在同一个地方跑了N多次异常，结果就被JDK忽略了，那岂不是查看日志的时候就看不到具体的StackTrace，那还怎么调试，所以还是关了的好

-XX:+HeapDumpOnOutOfMemoryError  
// 发生内存溢出时进行heap-dump

-XX:HeapDumpPath=/home/devjava/logs/  
// 与-XX:+HeapDumpOnOutOfMemoryError共同作用，设置heap-dump时内容输出文件

-Xloggc:/home/devjava/logs/lifecircle-tradecore-gc.log   
// 指定gc日志位置

-XX:+PrintGCApplicationStoppedTime 
// 打印所有引起JVM停顿时间，如果发现了一些不知什么的停顿，再临时加上-XX:+PrintSafepointStatistics -XX: PrintSafepointStatisticsCount=1找原因

-XX:+PrintGCDateStamps 
// 打印可读的日期而不是时间戳

-XX:+PrintGCDetails  
// 启用gc日志打印功能

-javaagent:/home/devjava/ArmsAgent/arms-bootstrap-1.7.0-SNAPSHOT.jar   
// 使用-javaagent 参数可以在执行main函数前执行一些其他逻辑（类似aop），甚至可以动态的修改替换类中代码。代理 (agent) 是在main方法前的一个拦截器 (interceptor)，也就是在main方法执行之前，执行agent的代码。agent的代码与main方法在同一个JVM中运行，并被同一个system classloader装载，被同一的安全策略 (security policy) 和上下文 (context) 所管理

-jar /home/devjava/lifecircle-tradecore/app/lifecircle-tradecore.jar   
// 运行jar包，会自动到 jar 包中查询mainfest中定义的启动类并运行













2020-10-29T21:19:19.488+0800: 114.015: [GC (CMS Initial Mark) [1 CMS-initial-mark: 106000K(2097152K)] 1084619K(3984640K), 0.2824583 secs] [Times: user=0.86 sys=0.00, real=0.28 secs]
-- 第一阶段初始标记，开始使用CMS回收器进行老年代回收。初始标记(CMS-initial-mark)阶段,这个阶段标记由根可以直接到达的对象，标记期间整个应用线程会暂停。
-- 老年代容量为2097152K,CMS 回收器在空间占用达到 106000K 时被触发

2020-10-29T21:19:19.771+0800: 114.298: [CMS-concurrent-mark-start]
//第二阶段开始并发标记(concurrent-mark-start) ，在第一个阶段被暂停的线程重新开始运行，由前阶段标记过的对象出发，所有可到达的对象都在本阶段中标记。

2020-10-29T21:19:19.931+0800: 114.458: [CMS-concurrent-mark: 0.160/0.160 secs] [Times: user=0.32 sys=0.03, real=0.16 secs]
//并发标记阶段结束，占用 0.160秒CPU时间, 0.160秒墙钟时间(也包含线程让出CPU给其他线程执行的时间)

2020-10-29T21:19:19.931+0800: 114.459: [CMS-concurrent-preclean-start]
//第三阶段开始预清理阶段
//预清理也是一个并发执行的阶段。在本阶段，会查找前一阶段执行过程中,从新生代晋升或新分配或被更新的对象。通过并发地重新扫描这些对象，预清理阶段可以减少下一个stop-the-world 重新标记阶段的工作量。

2020-10-29T21:19:19.998+0800: 114.525: [CMS-concurrent-preclean: 0.065/0.066 secs] [Times: user=0.05 sys=0.01, real=0.06 secs]
//预清理阶段费时 0.065秒CPU时间，0.066秒墙钟时间

2020-10-29T21:19:19.998+0800: 114.525: [CMS-concurrent-abortable-preclean-start]CMS: abort preclean due to time
2020-10-29T21:19:25.072+0800: 119.599: [CMS-concurrent-abortable-preclean: 5.038/5.073 secs] [Times: user=7.72 sys=0.50, real=5.08 secs]
//并发可中止的预清理阶段，不会影响用户线程，该阶段是为了尽量承担 STW（stop-the-world）中最终标记阶段的工作，减少下一个STW重新标记阶段的工作量

2020-10-29T21:19:25.076+0800: 119.603: [GC (CMS Final Remark) [YG occupancy: 1279357 K (1887488 K)]
//第四阶段最终标记(CMS Final Remark)，暂停所有用户线程，从GC Root开始重新扫描整堆，标记存活的对象
//1279357 K (1887488 K) 年轻代当前占用情况和容量

2020-10-29T21:19:25.076+0800: 119.603: [Rescan (parallel) , 0.3120602 secs]
//应用停止的阶段完成存活对象的标记工作以及耗时,多线程处理young区和多线程扫描old+perm的卡表的总时间， parallel表示多GC线程并行

2020-10-29T21:19:25.388+0800: 119.915: [weak refs processing, 0.0015920 secs]
//第一个子阶段，随着这个阶段的进行处理弱引用以及耗时

2020-10-29T21:19:25.390+0800: 119.917: [class unloading, 0.0517863 secs]
//第二个子阶段，类写卸载，清理未使用的类信息以及耗时

2020-10-29T21:19:25.441+0800: 119.969: [scrub symbol table, 0.0212825 secs]
//第三个自阶段，清理持有class级别元数据的symbol和string表以及耗时

2020-10-29T21:19:25.463+0800: 119.990: [scrub string table, 0.0022435 secs][1 CMS-remark: 106000K(2097152K)] 1385358K(3984640K), 0.3959182 secs] [Times: user=1.33 sys=0.00, real=0.40 secs]
//106000K(2097152K) 老年代内存使用量和总量      1385358K(3984640K)堆内存使用量和总量  以及本阶段耗时0.3959182     本阶段的耗时，由user，sys和real三部分组成。

2020-10-29T21:19:25.473+0800: 120.000: [CMS-concurrent-sweep-start]
//第五阶段标记并发清除(CMS-concurrent-sweep-start)

2020-10-29T21:19:25.540+0800: 120.067: [CMS-concurrent-sweep: 0.067/0.067 secs] [Times: user=0.18 sys=0.02, real=0.06 secs]
//清除不再使用的对象，回收这些对象占用的空间,这一阶段的耗时

2020-10-29T21:19:25.540+0800: 120.068: [CMS-concurrent-reset-start]
//第六阶段并发重置(CMS-concurrent-reset-start),并发执行阶段，重置CMS算法内部使用的数据结构，为下一次垃圾回收作准备

2020-10-29T21:19:25.544+0800: 120.071: [CMS-concurrent-reset: 0.003/0.003 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
//标识并发重置阶段耗时~~
