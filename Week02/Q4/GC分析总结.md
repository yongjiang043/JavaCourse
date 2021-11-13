# GC分析总结

运行 GCLogAnalysis 程序，根据不同的gc参数进行演练分析：

## 并行gc

不加任意参数:

```sh
yongs-mac:src Yong$ java -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 33170K->5116K(38400K)] 33170K->13950K(125952K), 0.0081799 secs] [Times: user=0.01 sys=0.01, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 38396K->5116K(71680K)] 47230K->22560K(159232K), 0.0105215 secs] [Times: user=0.01 sys=0.01, real=0.02 secs] 
[GC (Allocation Failure) [PSYoungGen: 71676K->5117K(71680K)] 89120K->40417K(159232K), 0.0157017 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 71677K->5112K(138240K)] 106977K->65725K(225792K), 0.0213083 secs] [Times: user=0.03 sys=0.03, real=0.03 secs] 
[Full GC (Ergonomics) [PSYoungGen: 5112K->0K(138240K)] [ParOldGen: 60612K->62966K(129024K)] 65725K->62966K(267264K), [Metaspace: 2804K->2804K(1056768K)], 0.0151006 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 132964K->5117K(138240K)] 195931K->102553K(267264K), 0.0297208 secs] [Times: user=0.02 sys=0.03, real=0.03 secs] 
[Full GC (Ergonomics) [PSYoungGen: 5117K->0K(138240K)] [ParOldGen: 97435K->96491K(183808K)] 102553K->96491K(322048K), [Metaspace: 2804K->2804K(1056768K)], 0.0211282 secs] [Times: user=0.05 sys=0.01, real=0.02 secs] 
[GC (Allocation Failure) [PSYoungGen: 133120K->43457K(276480K)] 229611K->140720K(460288K), 0.0409465 secs] [Times: user=0.02 sys=0.03, real=0.04 secs] 
[GC (Allocation Failure) [PSYoungGen: 276417K->51192K(284160K)] 373680K->213830K(467968K), 0.0800593 secs] [Times: user=0.05 sys=0.10, real=0.08 secs] 
[Full GC (Ergonomics) [PSYoungGen: 51192K->7661K(284160K)] [ParOldGen: 162638K->183647K(302080K)] 213830K->191308K(586240K), [Metaspace: 2804K->2804K(1056768K)], 0.0597966 secs] [Times: user=0.10 sys=0.02, real=0.06 secs] 
[GC (Allocation Failure) [PSYoungGen: 240364K->83791K(491008K)] 424011K->267438K(793088K), 0.0858805 secs] [Times: user=0.04 sys=0.07, real=0.08 secs] 
[GC (Allocation Failure) [PSYoungGen: 482127K->105469K(503808K)] 665774K->350175K(805888K), 0.1419446 secs] [Times: user=0.08 sys=0.12, real=0.14 secs] 
[Full GC (Ergonomics) [PSYoungGen: 105469K->0K(503808K)] [ParOldGen: 244706K->269641K(406528K)] 350175K->269641K(910336K), [Metaspace: 2804K->2804K(1056768K)], 0.0662750 secs] [Times: user=0.12 sys=0.02, real=0.07 secs] 
执行结束!共生成对象次数:5016
Heap
 PSYoungGen      total 503808K, used 16054K [0x0000000795580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 398336K, 4% used [0x0000000795580000,0x000000079652dbc0,0x00000007ada80000)
  from space 105472K, 0% used [0x00000007ada80000,0x00000007ada80000,0x00000007b4180000)
  to   space 147968K, 0% used [0x00000007b6f80000,0x00000007b6f80000,0x00000007c0000000)
 ParOldGen       total 406528K, used 269641K [0x0000000740000000, 0x0000000758d00000, 0x0000000795580000)
  object space 406528K, 66% used [0x0000000740000000,0x0000000750752418,0x0000000758d00000)
 Metaspace       used 2810K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 307K, capacity 386K, committed 512K, reserved 1048576K
```

- 机器内存总共为4g，可以看到在不配置任何Xmx/Xms参数下，jvm按照自己的一套逻辑（总内存>1G时堆最大为1/4）分配内存，初始堆内存约125MB，在最后一次gc后堆内存已接近1G。

指定堆内存1G：

```shell
yongs-mac:src Yong$ java -Xms1g -Xmx1g -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 262144K->43516K(305664K)] 262144K->81178K(1005056K), 0.0922941 secs] [Times: user=0.04 sys=0.06, real=0.09 secs] 
[GC (Allocation Failure) [PSYoungGen: 305660K->43518K(305664K)] 343322K->160085K(1005056K), 0.0872117 secs] [Times: user=0.06 sys=0.10, real=0.08 secs] 
[GC (Allocation Failure) [PSYoungGen: 305662K->43517K(305664K)] 422229K->240553K(1005056K), 0.0543067 secs] [Times: user=0.07 sys=0.08, real=0.05 secs] 
[GC (Allocation Failure) [PSYoungGen: 305661K->43516K(305664K)] 502697K->311601K(1005056K), 0.1109709 secs] [Times: user=0.06 sys=0.07, real=0.11 secs] 
[GC (Allocation Failure) [PSYoungGen: 305660K->43518K(305664K)] 573745K->382427K(1005056K), 0.1524296 secs] [Times: user=0.06 sys=0.05, real=0.15 secs] 
[GC (Allocation Failure) [PSYoungGen: 305662K->43502K(160256K)] 644571K->461068K(859648K), 0.0530799 secs] [Times: user=0.06 sys=0.08, real=0.05 secs] 
执行结束!共生成对象次数:5783
Heap
 PSYoungGen      total 160256K, used 48209K [0x00000007aab00000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 116736K, 4% used [0x00000007aab00000,0x00000007aaf98e68,0x00000007b1d00000)
  from space 43520K, 99% used [0x00000007bd580000,0x00000007bfffb970,0x00000007c0000000)
  to   space 116224K, 0% used [0x00000007b1d00000,0x00000007b1d00000,0x00000007b8e80000)
 ParOldGen       total 699392K, used 417565K [0x0000000780000000, 0x00000007aab00000, 0x00000007aab00000)
  object space 699392K, 59% used [0x0000000780000000,0x00000007997c77e0,0x00000007aab00000)
 Metaspace       used 2810K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 307K, capacity 386K, committed 512K, reserved 1048576K
```

- 指定堆内存1G后，未产生full gc，是因为蓄水池大的缘故。

指定堆内存512MB：

```shell
yongs-mac:src Yong$ java -Xms512m -Xmx512m -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 131584K->21489K(153088K)] 131584K->42302K(502784K), 0.0187975 secs] [Times: user=0.02 sys=0.04, real=0.02 secs] 
[GC (Allocation Failure) [PSYoungGen: 153048K->21500K(153088K)] 173861K->80863K(502784K), 0.0276352 secs] [Times: user=0.03 sys=0.06, real=0.02 secs] 
[GC (Allocation Failure) [PSYoungGen: 153084K->21499K(153088K)] 212447K->116758K(502784K), 0.0203927 secs] [Times: user=0.03 sys=0.04, real=0.02 secs] 
[GC (Allocation Failure) [PSYoungGen: 152949K->21500K(153088K)] 248208K->161174K(502784K), 0.0288442 secs] [Times: user=0.04 sys=0.05, real=0.03 secs] 
[GC (Allocation Failure) [PSYoungGen: 153084K->21503K(153088K)] 292758K->204818K(502784K), 0.0503073 secs] [Times: user=0.04 sys=0.04, real=0.05 secs] 
[GC (Allocation Failure) [PSYoungGen: 153087K->21496K(80384K)] 336402K->248297K(430080K), 0.0477938 secs] [Times: user=0.03 sys=0.03, real=0.05 secs] 
[GC (Allocation Failure) [PSYoungGen: 80376K->33502K(116736K)] 307177K->264556K(466432K), 0.0078774 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 92177K->46107K(116736K)] 323231K->281676K(466432K), 0.0130007 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 104866K->53295K(116736K)] 340436K->297793K(466432K), 0.0135970 secs] [Times: user=0.03 sys=0.01, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 112116K->39815K(116736K)] 356614K->316695K(466432K), 0.0303516 secs] [Times: user=0.03 sys=0.02, real=0.03 secs] 
[GC (Allocation Failure) [PSYoungGen: 98650K->21949K(116736K)] 375530K->336462K(466432K), 0.0274708 secs] [Times: user=0.03 sys=0.03, real=0.02 secs] 
[Full GC (Ergonomics) [PSYoungGen: 21949K->0K(116736K)] [ParOldGen: 314512K->239323K(349696K)] 336462K->239323K(466432K), [Metaspace: 2804K->2804K(1056768K)], 0.0558808 secs] [Times: user=0.13 sys=0.01, real=0.05 secs] 
[GC (Allocation Failure) [PSYoungGen: 58880K->21411K(116736K)] 298203K->260735K(466432K), 0.0066408 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 80121K->17959K(116736K)] 319445K->277482K(466432K), 0.0105656 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 76839K->23068K(116736K)] 336362K->299608K(466432K), 0.0118958 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 81948K->18364K(116736K)] 358488K->316484K(466432K), 0.0110609 secs] [Times: user=0.03 sys=0.00, real=0.02 secs] 
[Full GC (Ergonomics) [PSYoungGen: 18364K->0K(116736K)] [ParOldGen: 298119K->265026K(349696K)] 316484K->265026K(466432K), [Metaspace: 2804K->2804K(1056768K)], 0.0524825 secs] [Times: user=0.14 sys=0.00, real=0.05 secs] 
[GC (Allocation Failure) [PSYoungGen: 58880K->20104K(116736K)] 323906K->285131K(466432K), 0.0062080 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 78951K->18442K(116736K)] 343977K->302675K(466432K), 0.0064583 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 77322K->19373K(116736K)] 361555K->321069K(466432K), 0.0069108 secs] [Times: user=0.02 sys=0.01, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 78253K->19030K(116736K)] 379949K->338833K(466432K), 0.0080843 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 19030K->0K(116736K)] [ParOldGen: 319802K->282055K(349696K)] 338833K->282055K(466432K), [Metaspace: 2804K->2804K(1056768K)], 0.0617544 secs] [Times: user=0.14 sys=0.01, real=0.06 secs] 
[GC (Allocation Failure) [PSYoungGen: 58880K->21650K(116736K)] 340935K->303705K(466432K), 0.0042084 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
执行结束!共生成对象次数:6117
Heap
 PSYoungGen      total 116736K, used 31688K [0x00000007b5580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 58880K, 17% used [0x00000007b5580000,0x00000007b5f4d8c0,0x00000007b8f00000)
  from space 57856K, 37% used [0x00000007bc780000,0x00000007bdca49c0,0x00000007c0000000)
  to   space 57856K, 0% used [0x00000007b8f00000,0x00000007b8f00000,0x00000007bc780000)
 ParOldGen       total 349696K, used 282055K [0x00000007a0000000, 0x00000007b5580000, 0x00000007b5580000)
  object space 349696K, 80% used [0x00000007a0000000,0x00000007b1371c50,0x00000007b5580000)
 Metaspace       used 2810K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 307K, capacity 386K, committed 512K, reserved 1048576K
```

指定堆内存128MB：

```
yongs-mac:src Yong$ java -Xms128m -Xmx128m -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 33280K->5117K(38400K)] 33280K->9476K(125952K), 0.0051513 secs] [Times: user=0.00 sys=0.01, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 38032K->5115K(38400K)] 42391K->20474K(125952K), 0.0074229 secs] [Times: user=0.00 sys=0.02, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 38355K->5100K(38400K)] 53714K->31332K(125952K), 0.0074691 secs] [Times: user=0.01 sys=0.01, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 38380K->5102K(38400K)] 64612K->41736K(125952K), 0.0057199 secs] [Times: user=0.01 sys=0.01, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 38055K->5110K(38400K)] 74688K->54190K(125952K), 0.0065441 secs] [Times: user=0.01 sys=0.01, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 38201K->5119K(19968K)] 87282K->65676K(107520K), 0.0076844 secs] [Times: user=0.01 sys=0.01, real=0.01 secs] 
[GC (Allocation Failure) [PSYoungGen: 19946K->6557K(29184K)] 80503K->69651K(116736K), 0.0027983 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 21170K->10023K(29184K)] 84264K->74409K(116736K), 0.0022546 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 24603K->13828K(29184K)] 88990K->79533K(116736K), 0.0029919 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [PSYoungGen: 28495K->10727K(29184K)] 94200K->85114K(116736K), 0.0076954 secs] [Times: user=0.01 sys=0.02, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 10727K->0K(29184K)] [ParOldGen: 74386K->80405K(87552K)] 85114K->80405K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0198740 secs] [Times: user=0.05 sys=0.01, real=0.02 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14822K->0K(29184K)] [ParOldGen: 80405K->86707K(87552K)] 95228K->86707K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0138855 secs] [Times: user=0.03 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14657K->4582K(29184K)] [ParOldGen: 86707K->87223K(87552K)] 101365K->91805K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0178735 secs] [Times: user=0.05 sys=0.01, real=0.02 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14524K->6163K(29184K)] [ParOldGen: 87223K->87212K(87552K)] 101747K->93376K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0157430 secs] [Times: user=0.04 sys=0.00, real=0.02 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14629K->8605K(29184K)] [ParOldGen: 87212K->87142K(87552K)] 101841K->95748K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0053398 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14732K->10783K(29184K)] [ParOldGen: 87142K->87549K(87552K)] 101875K->98332K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0181792 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14824K->12182K(29184K)] [ParOldGen: 87549K->87337K(87552K)] 102373K->99520K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0191909 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14799K->12292K(29184K)] [ParOldGen: 87337K->87385K(87552K)] 102137K->99678K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0080049 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14815K->13087K(29184K)] [ParOldGen: 87385K->87385K(87552K)] 102201K->100472K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0025924 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14820K->13407K(29184K)] [ParOldGen: 87385K->87385K(87552K)] 102206K->100793K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0022995 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14496K->13704K(29184K)] [ParOldGen: 87385K->87385K(87552K)] 101882K->101090K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0025403 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14710K->14354K(29184K)] [ParOldGen: 87385K->87385K(87552K)] 102095K->101739K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0021091 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14809K->14282K(29184K)] [ParOldGen: 87385K->87385K(87552K)] 102194K->101667K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0024274 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14844K->14282K(29184K)] [ParOldGen: 87385K->87385K(87552K)] 102230K->101667K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0023335 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14687K->14591K(29184K)] [ParOldGen: 87385K->87155K(87552K)] 102073K->101747K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0163903 secs] [Times: user=0.04 sys=0.01, real=0.02 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14824K->14607K(29184K)] [ParOldGen: 87500K->87421K(87552K)] 102324K->102029K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0136747 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
[Full GC (Ergonomics) [PSYoungGen: 14751K->14751K(29184K)] [ParOldGen: 87421K->87349K(87552K)] 102173K->102101K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0022279 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [PSYoungGen: 14751K->14751K(29184K)] [ParOldGen: 87349K->87330K(87552K)] 102101K->102082K(116736K), [Metaspace: 2801K->2801K(1056768K)], 0.0133875 secs] [Times: user=0.05 sys=0.00, real=0.02 secs] 
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 	at java.util.Arrays.copyOf(Arrays.java:3332)
 	at java.lang.AbstractStringBuilder.expandCapacity(AbstractStringBuilder.java:137)
 	at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:121)
 	at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:647)
 	at java.lang.StringBuilder.append(StringBuilder.java:208)
 	at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:53)
 	at GCLogAnalysis.main(GCLogAnalysis.java:22)
Heap
 PSYoungGen      total 29184K, used 14848K [0x00000007bd580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 14848K, 100% used [0x00000007bd580000,0x00000007be400000,0x00000007be400000)
  from space 14336K, 0% used [0x00000007bf200000,0x00000007bf200000,0x00000007c0000000)
  to   space 14336K, 0% used [0x00000007be400000,0x00000007be400000,0x00000007bf200000)
 ParOldGen       total 87552K, used 87330K [0x00000007b8000000, 0x00000007bd580000, 0x00000007bd580000)
  object space 87552K, 99% used [0x00000007b8000000,0x00000007bd548a20,0x00000007bd580000)
 Metaspace       used 2832K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 310K, capacity 386K, committed 512K, reserved 1048576K
```

- 因为分配的堆内存太小，很快就不够了，所以最后 OOM 了

## 串行gc

指定堆内存1G:

```shell
yongs-mac:src Yong$ java -Xms1g -Xmx1g -XX:+PrintGCDetails -XX:+UseSerialGC GCLogAnalysis
正在执行...
[GC (Allocation Failure) [DefNew: 279616K->34943K(314560K), 0.0672105 secs] 279616K->85921K(1013632K), 0.0672516 secs] [Times: user=0.03 sys=0.02, real=0.07 secs] 
[GC (Allocation Failure) [DefNew: 314559K->34943K(314560K), 0.1910625 secs] 365537K->160671K(1013632K), 0.1910965 secs] [Times: user=0.05 sys=0.04, real=0.19 secs] 
[GC (Allocation Failure) [DefNew: 314119K->34943K(314560K), 0.1449493 secs] 439847K->240337K(1013632K), 0.1449958 secs] [Times: user=0.04 sys=0.03, real=0.15 secs] 
[GC (Allocation Failure) [DefNew: 314559K->34944K(314560K), 0.0700374 secs] 519953K->317630K(1013632K), 0.0700701 secs] [Times: user=0.03 sys=0.03, real=0.07 secs] 
[GC (Allocation Failure) [DefNew: 314170K->34942K(314560K), 0.0725310 secs] 596856K->401990K(1013632K), 0.0725672 secs] [Times: user=0.03 sys=0.03, real=0.08 secs] 
执行结束!共生成对象次数:6101
Heap
 def new generation   total 314560K, used 267949K [0x0000000780000000, 0x0000000795550000, 0x0000000795550000)
  eden space 279616K,  83% used [0x0000000780000000, 0x000000078e38bc98, 0x0000000791110000)
  from space 34944K,  99% used [0x0000000793330000, 0x000000079554fa98, 0x0000000795550000)
  to   space 34944K,   0% used [0x0000000791110000, 0x0000000791110000, 0x0000000793330000)
 tenured generation   total 699072K, used 367048K [0x0000000795550000, 0x00000007c0000000, 0x00000007c0000000)
   the space 699072K,  52% used [0x0000000795550000, 0x00000007abbc20f0, 0x00000007abbc2200, 0x00000007c0000000)
 Metaspace       used 2810K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 307K, capacity 386K, committed 512K, reserved 1048576K
```

指定堆内存512MB：

```shell
yongs-mac:src Yong$ java -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+UseSerialGC GCLogAnalysis
正在执行...
[GC (Allocation Failure) [DefNew: 139776K->17472K(157248K), 0.0268019 secs] 139776K->41216K(506816K), 0.0268369 secs] [Times: user=0.03 sys=0.02, real=0.03 secs] 
[GC (Allocation Failure) [DefNew: 157248K->17471K(157248K), 0.0450339 secs] 180992K->87139K(506816K), 0.0450726 secs] [Times: user=0.02 sys=0.02, real=0.04 secs] 
[GC (Allocation Failure) [DefNew: 157247K->17471K(157248K), 0.0393649 secs] 226915K->135818K(506816K), 0.0393975 secs] [Times: user=0.03 sys=0.02, real=0.04 secs] 
[GC (Allocation Failure) [DefNew: 156900K->17471K(157248K), 0.0390379 secs] 275247K->184443K(506816K), 0.0390763 secs] [Times: user=0.02 sys=0.01, real=0.03 secs] 
[GC (Allocation Failure) [DefNew: 156949K->17471K(157248K), 0.0349215 secs] 323921K->226940K(506816K), 0.0349520 secs] [Times: user=0.02 sys=0.02, real=0.04 secs] 
[GC (Allocation Failure) [DefNew: 157247K->17471K(157248K), 0.0403144 secs] 366716K->269963K(506816K), 0.0403464 secs] [Times: user=0.02 sys=0.02, real=0.04 secs] 
[GC (Allocation Failure) [DefNew: 157247K->17471K(157248K), 0.0315508 secs] 409739K->311566K(506816K), 0.0316158 secs] [Times: user=0.01 sys=0.01, real=0.03 secs] 
[GC (Allocation Failure) [DefNew: 157247K->17471K(157248K), 0.0436016 secs] 451342K->358679K(506816K), 0.0436363 secs] [Times: user=0.02 sys=0.02, real=0.04 secs] 
[GC (Allocation Failure) [DefNew: 157247K->157247K(157248K), 0.0000124 secs][Tenured: 341207K->278429K(349568K), 0.0679707 secs] 498455K->278429K(506816K), [Metaspace: 2803K->2803K(1056768K)], 0.0680245 secs] [Times: user=0.06 sys=0.00, real=0.07 secs] 
[GC (Allocation Failure) [DefNew: 139764K->17471K(157248K), 0.0111396 secs] 418194K->325960K(506816K), 0.0111763 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
[GC (Allocation Failure) [DefNew: 157247K->157247K(157248K), 0.0000134 secs][Tenured: 308488K->302580K(349568K), 0.0592706 secs] 465736K->302580K(506816K), [Metaspace: 2804K->2804K(1056768K)], 0.0593255 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
[GC (Allocation Failure) [DefNew: 139776K->139776K(157248K), 0.0000135 secs][Tenured: 302580K->306254K(349568K), 0.0600546 secs] 442356K->306254K(506816K), [Metaspace: 2804K->2804K(1056768K)], 0.0601096 secs] [Times: user=0.05 sys=0.00, real=0.06 secs] 
[GC (Allocation Failure) [DefNew: 139776K->139776K(157248K), 0.0000125 secs][Tenured: 306254K->300055K(349568K), 0.0710870 secs] 446030K->300055K(506816K), [Metaspace: 2804K->2804K(1056768K)], 0.0711420 secs] [Times: user=0.06 sys=0.01, real=0.07 secs] 
执行结束!共生成对象次数:7038
Heap
 def new generation   total 157248K, used 6146K [0x00000007a0000000, 0x00000007aaaa0000, 0x00000007aaaa0000)
  eden space 139776K,   4% used [0x00000007a0000000, 0x00000007a0600a60, 0x00000007a8880000)
  from space 17472K,   0% used [0x00000007a9990000, 0x00000007a9990000, 0x00000007aaaa0000)
  to   space 17472K,   0% used [0x00000007a8880000, 0x00000007a8880000, 0x00000007a9990000)
 tenured generation   total 349568K, used 300055K [0x00000007aaaa0000, 0x00000007c0000000, 0x00000007c0000000)
   the space 349568K,  85% used [0x00000007aaaa0000, 0x00000007bcfa5c30, 0x00000007bcfa5e00, 0x00000007c0000000)
 Metaspace       used 2810K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 307K, capacity 386K, committed 512K, reserved 1048576K
```

## CMS GC

指定堆内存1G：

```
yongs-mac:src Yong$ java -Xms1g -Xmx1g -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC GCLogAnalysis
正在执行...
[GC (Allocation Failure) [ParNew: 272526K->34047K(306688K), 0.0328310 secs] 272526K->75832K(1014528K), 0.0328835 secs] [Times: user=0.04 sys=0.08, real=0.03 secs] 
[GC (Allocation Failure) [ParNew: 306687K->34048K(306688K), 0.0491343 secs] 348472K->155801K(1014528K), 0.0491727 secs] [Times: user=0.06 sys=0.10, real=0.05 secs] 
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0747812 secs] 428441K->236761K(1014528K), 0.0748146 secs] [Times: user=0.20 sys=0.04, real=0.08 secs] 
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0739081 secs] 509401K->319386K(1014528K), 0.0740208 secs] [Times: user=0.19 sys=0.05, real=0.08 secs] 
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0863713 secs] 592026K->409170K(1014528K), 0.0864182 secs] [Times: user=0.18 sys=0.05, real=0.09 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 375122K(707840K)] 414949K(1014528K), 0.0008772 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.012/0.012 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
[CMS-concurrent-abortable-preclean-start]
[GC (Allocation Failure) [ParNew[CMS-concurrent-abortable-preclean: 0.001/0.103 secs] [Times: user=0.17 sys=0.04, real=0.10 secs] 
: 306688K->34048K(306688K), 0.0761167 secs] 681810K->485311K(1014528K), 0.0761566 secs] [Times: user=0.16 sys=0.04, real=0.08 secs] 
[GC (CMS Final Remark) [YG occupancy: 34196 K (306688 K)][Rescan (parallel) , 0.0015103 secs][weak refs processing, 0.0000177 secs][class unloading, 0.0003655 secs][scrub symbol table, 0.0004037 secs][scrub string table, 0.0001718 secs][1 CMS-remark: 451263K(707840K)] 485459K(1014528K), 0.0026215 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[CMS-concurrent-sweep-start]
[CMS-concurrent-sweep: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-reset-start]
[CMS-concurrent-reset: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0253975 secs] 623293K->423425K(1014528K), 0.0254305 secs] [Times: user=0.09 sys=0.01, real=0.02 secs] 
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0434727 secs] 696065K->510570K(1014528K), 0.0435174 secs] [Times: user=0.11 sys=0.01, real=0.04 secs] 
执行结束!共生成对象次数:8173[GC (CMS Initial Mark) [1 CMS-initial-mark: 476522K(707840K)] 516099K(1014528K), 0.0003881 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
Heap
 par new generation   total 306688K, used 45030K [0x0000000780000000, 0x0000000794cc0000, 0x0000000794cc0000)
  eden space 272640K,   4% used [0x0000000780000000, 0x0000000780ab9800, 0x0000000790a40000)
  from space 34048K, 100% used [0x0000000790a40000, 0x0000000792b80000, 0x0000000792b80000)
  to   space 34048K,   0% used [0x0000000792b80000, 0x0000000792b80000, 0x0000000794cc0000)
 concurrent mark-sweep generation total 707840K, used 476522K [0x0000000794cc0000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 2810K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 307K, capacity 386K, committed 512K, reserved 1048576K
```

指定堆内存2G:

```shell
yongs-mac:src Yong$ java -Xms2g -Xmx2g -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC GCLogAnalysis
正在执行...
[GC (Allocation Failure) [ParNew: 272640K->34047K(306688K), 0.0379616 secs] 272640K->80225K(2063104K), 0.0380104 secs] [Times: user=0.06 sys=0.08, real=0.04 secs] 
[GC (Allocation Failure) [ParNew: 306668K->34048K(306688K), 0.0484154 secs] 352846K->154768K(2063104K), 0.0484493 secs] [Times: user=0.07 sys=0.09, real=0.05 secs] 
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0693692 secs] 427408K->226252K(2063104K), 0.0694015 secs] [Times: user=0.21 sys=0.04, real=0.07 secs] 
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0634306 secs] 498892K->302385K(2063104K), 0.0634617 secs] [Times: user=0.18 sys=0.04, real=0.06 secs] 
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0654410 secs] 575025K->379895K(2063104K), 0.0654904 secs] [Times: user=0.18 sys=0.04, real=0.06 secs] 
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.0897654 secs] 652535K->458028K(2063104K), 0.0898092 secs] [Times: user=0.18 sys=0.04, real=0.09 secs] 
[GC (Allocation Failure) [ParNew: 306688K->34048K(306688K), 0.1044911 secs] 730668K->537002K(2063104K), 0.1045280 secs] [Times: user=0.21 sys=0.04, real=0.11 secs] 
执行结束!共生成对象次数:7201
Heap
 par new generation   total 306688K, used 45142K [0x0000000740000000, 0x0000000754cc0000, 0x0000000754cc0000)
  eden space 272640K,   4% used [0x0000000740000000, 0x0000000740ad5ba8, 0x0000000750a40000)
  from space 34048K, 100% used [0x0000000752b80000, 0x0000000754cc0000, 0x0000000754cc0000)
  to   space 34048K,   0% used [0x0000000750a40000, 0x0000000750a40000, 0x0000000752b80000)
 concurrent mark-sweep generation total 1756416K, used 502954K [0x0000000754cc0000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 2808K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 307K, capacity 386K, committed 512K, reserved 1048576K
```

指定堆内存512MB:

```shell
yongs-mac:src Yong$ java -Xms512m -Xmx512m -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC GCLogAnalysis
正在执行...
[GC (Allocation Failure) [ParNew: 139776K->17471K(157248K), 0.0204184 secs] 139776K->45390K(506816K), 0.0204612 secs] [Times: user=0.03 sys=0.05, real=0.02 secs] 
[GC (Allocation Failure) [ParNew: 157247K->17470K(157248K), 0.0288451 secs] 185166K->90121K(506816K), 0.0288794 secs] [Times: user=0.05 sys=0.05, real=0.03 secs] 
[GC (Allocation Failure) [ParNew: 157246K->17472K(157248K), 0.0359882 secs] 229897K->132404K(506816K), 0.0360433 secs] [Times: user=0.11 sys=0.03, real=0.04 secs] 
[GC (Allocation Failure) [ParNew: 157248K->17472K(157248K), 0.0360623 secs] 272180K->174473K(506816K), 0.0360947 secs] [Times: user=0.10 sys=0.02, real=0.04 secs] 
[GC (Allocation Failure) [ParNew: 157248K->17470K(157248K), 0.0427383 secs] 314249K->225274K(506816K), 0.0427690 secs] [Times: user=0.11 sys=0.03, real=0.05 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 207803K(349568K)] 225418K(506816K), 0.0001953 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.006/0.006 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-abortable-preclean-start]
[GC (Allocation Failure) [ParNew: 157061K->17472K(157248K), 0.0358484 secs] 364865K->265649K(506816K), 0.0358837 secs] [Times: user=0.10 sys=0.02, real=0.04 secs] 
[GC (Allocation Failure) [ParNew: 157248K->17470K(157248K), 0.0325846 secs] 405425K->308162K(506816K), 0.0326216 secs] [Times: user=0.09 sys=0.03, real=0.04 secs] 
[GC (Allocation Failure) [ParNew: 157246K->17471K(157248K), 0.0342679 secs] 447938K->345828K(506816K), 0.0342983 secs] [Times: user=0.09 sys=0.02, real=0.03 secs] 
[CMS-concurrent-abortable-preclean: 0.006/0.177 secs] [Times: user=0.35 sys=0.07, real=0.18 secs] 
[GC (CMS Final Remark) [YG occupancy: 23949 K (157248 K)][Rescan (parallel) , 0.0004485 secs][weak refs processing, 0.0000151 secs][class unloading, 0.0002593 secs][scrub symbol table, 0.0003380 secs][scrub string table, 0.0001363 secs][1 CMS-remark: 328356K(349568K)] 352306K(506816K), 0.0013330 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[CMS-concurrent-sweep-start]
[CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-reset-start]
[CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [ParNew: 157247K->17471K(157248K), 0.0152998 secs] 447571K->348686K(506816K), 0.0153382 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 331214K(349568K)] 348988K(506816K), 0.0001750 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.004/0.004 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-abortable-preclean-start]
[CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (CMS Final Remark) [YG occupancy: 34670 K (157248 K)][Rescan (parallel) , 0.0004018 secs][weak refs processing, 0.0000076 secs][class unloading, 0.0002750 secs][scrub symbol table, 0.0003921 secs][scrub string table, 0.0001307 secs][1 CMS-remark: 331214K(349568K)] 365885K(506816K), 0.0013009 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[CMS-concurrent-sweep-start]
[CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-reset-start]
[CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [ParNew: 157247K->17470K(157248K), 0.0145557 secs] 388913K->294524K(506816K), 0.0145938 secs] [Times: user=0.04 sys=0.00, real=0.02 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 277054K(349568K)] 294823K(506816K), 0.0001478 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.004/0.004 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-abortable-preclean-start]
[GC (Allocation Failure) [ParNew: 157246K->17471K(157248K), 0.0156611 secs] 434300K->342821K(506816K), 0.0156970 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
[GC (Allocation Failure) [ParNew: 157247K->157247K(157248K), 0.0000158 secs][CMS[CMS-concurrent-abortable-preclean: 0.003/0.069 secs] [Times: user=0.10 sys=0.01, real=0.07 secs] 
 (concurrent mode failure): 325349K->295536K(349568K), 0.0620652 secs] 482597K->295536K(506816K), [Metaspace: 2799K->2799K(1056768K)], 0.0621414 secs] [Times: user=0.06 sys=0.00, real=0.06 secs] 
[GC (Allocation Failure) [ParNew: 139776K->17468K(157248K), 0.0091078 secs] 435312K->342869K(506816K), 0.0091520 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 325400K(349568K)] 343032K(506816K), 0.0002456 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.005/0.005 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-abortable-preclean-start]
[CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (CMS Final Remark) [YG occupancy: 34174 K (157248 K)][Rescan (parallel) , 0.0008593 secs][weak refs processing, 0.0000079 secs][class unloading, 0.0003289 secs][scrub symbol table, 0.0004468 secs][scrub string table, 0.0001475 secs][1 CMS-remark: 325400K(349568K)] 359574K(506816K), 0.0018838 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[CMS-concurrent-sweep-start]
[CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-reset-start]
[CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [ParNew: 157244K->17470K(157248K), 0.0128653 secs] 446601K->348324K(506816K), 0.0129235 secs] [Times: user=0.04 sys=0.00, real=0.01 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 330853K(349568K)] 348632K(506816K), 0.0001682 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.004/0.004 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-abortable-preclean-start]
[CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (CMS Final Remark) [YG occupancy: 35285 K (157248 K)][Rescan (parallel) , 0.0010893 secs][weak refs processing, 0.0000081 secs][class unloading, 0.0002761 secs][scrub symbol table, 0.0003757 secs][scrub string table, 0.0001454 secs][1 CMS-remark: 330853K(349568K)] 366139K(506816K), 0.0019901 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-sweep-start]
[CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-reset-start]
[CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [ParNew: 156972K->17467K(157248K), 0.0192938 secs] 455116K->358417K(506816K), 0.0193252 secs] [Times: user=0.05 sys=0.01, real=0.02 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 340949K(349568K)] 358926K(506816K), 0.0001660 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.003/0.003 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-abortable-preclean-start]
[CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC (CMS Final Remark) [YG occupancy: 40690 K (157248 K)][Rescan (parallel) , 0.0003857 secs][weak refs processing, 0.0000062 secs][class unloading, 0.0002224 secs][scrub symbol table, 0.0003113 secs][scrub string table, 0.0001553 secs][1 CMS-remark: 340949K(349568K)] 381639K(506816K), 0.0011582 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-sweep-start]
[CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-reset-start]
[CMS-concurrent-reset: 0.001/0.001 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[GC (Allocation Failure) [ParNew: 157243K->157243K(157248K), 0.0000174 secs][CMS: 307438K->338420K(349568K), 0.0680773 secs] 464681K->338420K(506816K), [Metaspace: 2799K->2799K(1056768K)], 0.0681592 secs] [Times: user=0.06 sys=0.00, real=0.06 secs] 
[GC (CMS Initial Mark) [1 CMS-initial-mark: 338420K(349568K)] 338492K(506816K), 0.0002178 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark-start]
执行结束!共生成对象次数:8271
Heap
 par new generation   total 157248K, used 5783K [0x00000007a0000000, 0x00000007aaaa0000, 0x00000007aaaa0000)
  eden space 139776K,   4% used [0x00000007a0000000, 0x00000007a05a5eb8, 0x00000007a8880000)
  from space 17472K,   0% used [0x00000007a8880000, 0x00000007a8880000, 0x00000007a9990000)
  to   space 17472K,   0% used [0x00000007a9990000, 0x00000007a9990000, 0x00000007aaaa0000)
 concurrent mark-sweep generation total 349568K, used 338420K [0x00000007aaaa0000, 0x00000007c0000000, 0x00000007c0000000)
 Metaspace       used 2805K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 307K, capacity 386K, committed 512K, reserved 1048576K
```

## G1 GC

指定堆内存1G：

```shell
yongs-mac:src Yong$ java -Xms1g -Xmx1g -XX:+PrintGC -XX:+UseG1GC GCLogAnalysis
正在执行...
[GC pause (G1 Evacuation Pause) (young) 59M->20M(1024M), 0.0076220 secs]
[GC pause (G1 Evacuation Pause) (young) 77M->40M(1024M), 0.0086369 secs]
[GC pause (G1 Evacuation Pause) (young) 93M->56M(1024M), 0.0066414 secs]
[GC pause (G1 Evacuation Pause) (young) 112M->74M(1024M), 0.0078195 secs]
[GC pause (G1 Evacuation Pause) (young) 149M->98M(1024M), 0.0103229 secs]
[GC pause (G1 Evacuation Pause) (young) 239M->150M(1024M), 0.0191941 secs]
[GC pause (G1 Evacuation Pause) (young) 246M->184M(1024M), 0.0152483 secs]
[GC pause (G1 Evacuation Pause) (young) 319M->229M(1024M), 0.0159777 secs]
[GC pause (G1 Evacuation Pause) (young) 412M->288M(1024M), 0.0214672 secs]
[GC pause (G1 Evacuation Pause) (young) 487M->348M(1024M), 0.0218936 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 585M->415M(1024M), 0.0265324 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0003048 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0052343 secs]
[GC remark, 0.0022335 secs]
[GC cleanup 444M->398M(1024M), 0.0018637 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0001667 secs]
[GC pause (G1 Evacuation Pause) (young)-- 840M->661M(1024M), 0.0193631 secs]
[GC pause (G1 Evacuation Pause) (mixed) 667M->602M(1024M), 0.0138334 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 604M->601M(1024M), 0.0034991 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001318 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0046589 secs]
[GC remark, 0.0032599 secs]
[GC cleanup 615M->582M(1024M), 0.0016486 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000556 secs]
[GC pause (G1 Evacuation Pause) (young) 783M->641M(1024M), 0.0115161 secs]
[GC pause (G1 Evacuation Pause) (mixed) 676M->553M(1024M), 0.0065353 secs]
[GC pause (G1 Evacuation Pause) (mixed) 606M->483M(1024M), 0.0099779 secs]
执行结束!共生成对象次数:7614
```

指定堆内存512MB:

```shell
yongs-mac:src Yong$ java -Xms512m -Xmx512m -XX:+PrintGC -XX:+UseG1GC GCLogAnalysis
正在执行...
[GC pause (G1 Evacuation Pause) (young) 29M->7383K(512M), 0.0048868 secs]
[GC pause (G1 Evacuation Pause) (young) 33M->15M(512M), 0.0049610 secs]
[GC pause (G1 Evacuation Pause) (young) 49M->25M(512M), 0.0050125 secs]
[GC pause (G1 Evacuation Pause) (young) 79M->49M(512M), 0.0091254 secs]
[GC pause (G1 Evacuation Pause) (young) 103M->71M(512M), 0.0086402 secs]
[GC pause (G1 Evacuation Pause) (young) 139M->96M(512M), 0.0100807 secs]
[GC pause (G1 Evacuation Pause) (young) 194M->127M(512M), 0.0157947 secs]
[GC pause (G1 Evacuation Pause) (young) 241M->165M(512M), 0.0287919 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 365M->228M(512M), 0.0510486 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0003716 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0081484 secs]
[GC remark, 0.0019691 secs]
[GC cleanup 241M->230M(512M), 0.0005919 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000215 secs]
[GC pause (G1 Evacuation Pause) (young) 355M->266M(512M), 0.0256059 secs]
[GC pause (G1 Evacuation Pause) (mixed) 277M->253M(512M), 0.0086492 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 258M->254M(512M), 0.0058380 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001329 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0022678 secs]
[GC remark, 0.0025179 secs]
[GC cleanup 261M->253M(512M), 0.0008121 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000188 secs]
[GC pause (G1 Evacuation Pause) (young) 409M->301M(512M), 0.0087343 secs]
[GC pause (G1 Evacuation Pause) (mixed) 311M->284M(512M), 0.0058513 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 285M->284M(512M), 0.0013977 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001245 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0020319 secs]
[GC remark, 0.0019412 secs]
[GC cleanup 293M->286M(512M), 0.0005760 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000151 secs]
[GC pause (G1 Evacuation Pause) (young) 412M->324M(512M), 0.0104218 secs]
[GC pause (G1 Evacuation Pause) (mixed) 338M->306M(512M), 0.0071409 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 311M->307M(512M), 0.0014273 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001322 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0023322 secs]
[GC remark, 0.0018903 secs]
[GC cleanup 318M->308M(512M), 0.0005259 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000188 secs]
[GC pause (G1 Evacuation Pause) (young) 402M->335M(512M), 0.0063597 secs]
[GC pause (G1 Evacuation Pause) (mixed) 353M->318M(512M), 0.0078259 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 321M->319M(512M), 0.0013490 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001384 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0023323 secs]
[GC remark, 0.0018409 secs]
[GC cleanup 331M->328M(512M), 0.0005691 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000109 secs]
[GC pause (G1 Evacuation Pause) (young) 405M->352M(512M), 0.0095684 secs]
[GC pause (G1 Evacuation Pause) (mixed) 373M->336M(512M), 0.0093041 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 337M->336M(512M), 0.0014662 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0000298 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0021871 secs]
[GC remark, 0.0018880 secs]
[GC cleanup 349M->337M(512M), 0.0005873 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000180 secs]
[GC pause (G1 Evacuation Pause) (young) 387M->348M(512M), 0.0040312 secs]
[GC pause (G1 Evacuation Pause) (mixed) 370M->337M(512M), 0.0095395 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 346M->339M(512M), 0.0016819 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001048 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0019922 secs]
[GC remark, 0.0022453 secs]
[GC cleanup 350M->341M(512M), 0.0005771 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000185 secs]
[GC pause (G1 Evacuation Pause) (young) 397M->355M(512M), 0.0049453 secs]
[GC pause (G1 Evacuation Pause) (mixed) 375M->344M(512M), 0.0064960 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 346M->344M(512M), 0.0036182 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001455 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0023384 secs]
[GC remark, 0.0019717 secs]
[GC cleanup 357M->352M(512M), 0.0005476 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000110 secs]
[GC pause (G1 Evacuation Pause) (young) 401M->366M(512M), 0.0050598 secs]
[GC pause (G1 Evacuation Pause) (mixed) 387M->359M(512M), 0.0067064 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 361M->360M(512M), 0.0014530 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001684 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0021353 secs]
[GC remark, 0.0020902 secs]
[GC cleanup 373M->363M(512M), 0.0006350 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000210 secs]
[GC pause (G1 Evacuation Pause) (young) 386M->364M(512M), 0.0029395 secs]
[GC pause (G1 Evacuation Pause) (mixed) 389M->354M(512M), 0.0092655 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 354M->354M(512M), 0.0012709 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0000278 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0024303 secs]
[GC remark, 0.0020606 secs]
[GC cleanup 366M->358M(512M), 0.0005878 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000147 secs]
[GC pause (G1 Evacuation Pause) (young) 392M->363M(512M), 0.0032011 secs]
[GC pause (G1 Evacuation Pause) (mixed) 389M->355M(512M), 0.0063856 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 363M->358M(512M), 0.0027156 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001877 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0029763 secs]
[GC remark, 0.0028244 secs]
[GC cleanup 365M->356M(512M), 0.0005748 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0000177 secs]
执行结束!共生成对象次数:6645
```

