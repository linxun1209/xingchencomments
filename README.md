项目介绍
在线店铺点评 APP（H5 网页），有点类似美团点评。
特色：
1项目整体比较精简，适合初学练手
2项目中大量运用到了 Redis，极其适合学习 Redis 的同学来实战（事实上这个项目都是为了 Redis 课而生的）
3项目的代码细节很多，能学到不少优化技巧

![](https://cdn.nlark.com/yuque/0/2022/png/398476/1664281117452-4fa1ed35-8c19-4c7a-a36b-a41545e4706c.png?x-oss-process=image%2Fresize%2Cw_802%2Climit_0#averageHue=%23877457&from=url&id=OzGtI&originHeight=1444&originWidth=802&originalType=binary&ratio=1&rotation=0&showTitle=false&status=done&style=none&title=)

项目功能
查看点评（热评）、发布点评、点赞
关注、查询关注的人发的帖子、查看好友共同关注
分类浏览店铺、查看附近的店铺
个人信息查看和管理

话说项目太仿真了，在用户编辑资料页竟然还能开 VIP？！（狗头）

技术栈
后端
Spring 相关：
●Spring Boot 2.x
●Spring MVC

数据存储层：
●MySQL：存储数据
●MyBatis Plus：数据访问框架

Redis 相关：
●spring-data-redis：操作 Redis
●Lettuce：操作 Redis 的高级客户端
●Apache Commons Pool：用于实现 Redis 连接池
●Redisson：基于 Redis 的分布式数据网格

工具库：
●HuTool：工具库合集
●Lombok：注解式代码生成工具

前端
前端不是本项目的重点，了解即可

●原生 HTML、CSS、JS 三件套
●Vue 2（渐进式使用）
●Element UI 组件库
●axios 请求库

技术架构图
这个项目是单体项目，架构比较简单，下图依然是理想架构，实际上只用单台 Tomcat、MySQL、Redis 即可：

学习重点
本项目可以说是为了帮大家学习 Redis 而定制的，因此学习重点在后端 Redis 上。
这个项目几乎用到了 Redis 的所有主流特性，都值得重点学习，如官方提供的项目介绍图：

启动项目
启动依赖服务
主要包括 MySQL 和 Redis。

MySQL
本地自行安装 MySQL，然后创建一个名为 hmdp 的数据库，并执行 SQL 脚本导入初始数据。
SQL 脚本位置：Redis-笔记资料\02-实战篇\代码\完整实现版代码\hm-dianping\src\main\resources\db\hmdp.sql

如果 MySQL 版本高于 5.x，可能出现报错：Invalid default value for 'begin_time'，导致部分表创建失败。
修改方法：
把 begin_time 的默认值 DEFAULT '0000-00-00 00:00:00' 去掉，大概在 SQL 脚本 90 行左右的位置，如下：

然后修改 application.yaml 的数据库配置为自己的，如图：

Redis
如果是 MAC 或 Linux 系统，可以本地安装 Redis。如果是 Windows 系统，建议使用 Docker 或者在 WSL 子系统的 Linux 里安装（参考：[https://redis.io/docs/getting-started/installation/install-redis-on-windows/](https://redis.io/docs/getting-started/installation/install-redis-on-windows/)）。
注意 Redis 的版本必须要大于 6.2.0！否则不支持 GEOSEARCH 命令，会报如下错误：
Error in execution; nested exception is io.lettuce.core.RedisCommandExecutionException: ERR unknown command `GEOSEARCH`

使用 Docker 安装方法：
1找到目标 Redis 版本的镜像：[https://hub.docker.com/_/redis](https://hub.docker.com/_/redis)
2在命令行中执行命令拉取镜像，如：docker pull redis:latest（安装最新版本的 Redis）
3启动容器（用 docker run 命令或 Docker Desktop 操作），记得开启端口映射，如下图：

修改 application.yaml:11 和 com/hmdp/config/RedissonConfig.java:16 中的 Redis 地址为自己的，比如 127.0.0.1，如图：

如果本地 Redis 没设置密码的话，记得要把 application.yaml 的 password 和 RedissonConfig 的 setPassword 删掉。

后端
代码路径：Redis-笔记资料\02-实战篇\代码\完整实现版代码\hm-dianping
后端是一个单体的 Spring Boot 项目，直接运行主类即可。

但是在运行前，要先做几件事：
1）把下面这行代码（com/hmdp/service/impl/VoucherOrderServiceImpl.java:65）注释掉，否则会无限报错：

因为这个类是常驻消费者线程，持续监听 Redis Stream 消息队列，而如果我们没有创建队列的话，就会一直抛异常。

2）把 com/hmdp/utils/SystemConstants.java:4 里的文件上传路径改为自己本地项目 Nginx 目录的地址（这样才能利用 Nginx 提供的静态资源访问服务），比如我这里是 E:\code\nginx-1.18.0\html\hmdp\imgs，如图：

3）执行 com.hmdp.HmDianPingApplicationTests#loadShopData 方法，加载初始化店铺数据到 Redis 中，这样才能查看到店铺数据。

前端
代码路径：Redis-笔记资料\02-实战篇\资料\nginx-1.18.0\nginx-1.18.0\html\hmdp（小样，藏在 Nginx 里我就找不到你了？）

有两种启动方式：Nginx 或 Serve 工具启动。

Nginx 启动：
将 nginx-1.18.0 复制到一个纯英文的路径下，然后在该目录中运行 cmd，输入 nginx 命令即可，如图：

访问地址：[http://localhost:8080/](http://localhost:8080/)
本项目推荐使用这种方式，因为 Nginx 还负责了后端接口的反向代理、图片等静态资源的访问。
实际线上部署前端也是用这种方式较多。

Serve 工具启动：
serve 是一个非常简单易用的小工具，可以快速在本地启动 web 服务器。
先全局安装 serve：
然后在代码目录 nginx-1.18.0\html\hmdp 中直接输入 serve 命令即可，如图：

访问地址：[http://localhost:3000/](http://localhost:8080/)
这种方式非常方便，适合本地开发调试，但是一般不会应用于线上。
注意，该项目的 Nginx 还承担了反向代理后端的工作，如果使用 serve 方式，需要手动修改前端 axios 全局请求 url 地址为后端接口地址 http://127.0.0.1:8081（前端代码位置：js/common.js:2）。

问题及解决
1）后端报错：Failed to obtain JDBC Connection; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: Public Key Retrieval is not allowed
原因：MySQL 版本过高
解决：修改 application 配置文件的 mysql url 配置，添加 allowPublicKeyRetrieval=true
如图：

代码学习
config 目录：存放项目依赖相关配置
●MvcConfig：配置了登录、自动刷新登录 Token 的拦截器
●MybatisConfig：配置 MyBatis Plus 分页插件
●RedissonConfig：创建单例 Redisson 客户端
●WebExceptionAdvice：全局响应拦截器

controller 目录：存放 Restful 风格的 API 接口
dto 目录：存放业务封装类，如 Result 通用响应封装（不推荐学习它的写法）
entity 目录：存放和数据库对应的 Java POJO，一般是用 MyBatisX 等插件自动生成
mapper 目录：存放操作数据库的代码，基本没有自定义 SQL，都是复用了 MyBatis Plus 的方法，不做重点学习。
service 目录：存放业务逻辑处理代码，需要重点学习
●BlogServiceImpl：基于 Redis 实现点赞、按时间排序的点赞排行榜；基于 Redis 实现拉模式的 Feed 流，推荐学习
●FollowServiceImpl：基于 Redis 集合实现关注、共同关注，推荐学习
●ShopServiceImpl：基于 Redis 缓存优化店铺查询性能；基于 Redis GEO 实现附近店铺按距离排序，推荐学习
●UserServiceImpl：基于 Redis 实现短信登录（分布式 Session），推荐学习（虽然没有真的实现短信登录，而是通过日志打印验证码代替）
●VoucherOrderServiceImpl：基于 Redis 分布式锁、Redis + Lua 两种方式，结合消息队列，共同实现了秒杀和一人一单功能，非常值得学习
●VoucherServiceImpl：添加优惠券，并将库存保存在 Redis 中，为秒杀做准备。

utils 目录：存放项目内通用的工具类，需要重点学习
●CacheClient：封装了通用的缓存工具类，涉及泛型、函数式编程等知识点，值得学习
●RedisConstants：保存项目中用到的 Redis 键、过期时间等常量，值得学习
●RedisIdWorker：基于 Redis 的全局唯一自增 id 生成器，值得学习
●RedisTokenInterceptor：自动刷新 Redis 登录 Token 有效期，值得学习
●SimpleRedisLock：简单的 Redis 锁实现，了解即可，一般用 Redisson
●UserHolder：线程内缓存用户信息，可以学习

项目点评及学习建议
1这个项目几乎用到了 Redis 的所有主流特性，非常适合已经学完 Spring Boot、还未系统学习过 Redis 的后端同学，其中秒杀功能、一人一单的实现需要格外重点学习。
2课程质量极高，一句不少、一句不多、幽默风趣、百万 PPT，还能学到库表设计、代码抽象复用、JMeter 压力测试工具等实用知识。后端的初学者可以只看 p1 - p95、以及 p134 - p139 的最佳实践部分，高级篇和原理篇等把项目做完、且其他主流技术（如消息队列、微服务）学的差不多了，等面试前再看就行。
3虽然项目的实现完整度一般，但是有难度的功能基本都实现了，建议有能力的同学把它完整实现，完全可以写在简历上。
4在学习的过程中，可以多使用 Redis 可视化管理工具来观察数据的变化。比如 Quick Redis（[https://quick123.net/](https://quick123.net/)）、RESP（Redis-笔记资料\01-入门篇\资料\resp-2022.2.zip）
5项目缺乏管理端，可以自行实现如店铺管理、点评审核等功能。

**可以写到简历上的点
项目介绍：
基于 Spring Boot + Redis 的店铺点评 APP，实现了找店铺 => 写点评 => 看热评 => 点赞关注 => 关注 Feed 流的完整业务流程。

主要工作：
1短信登录：使用 Redis 实现分布式 Session，解决集群间登录态同步问题；使用 Hash 代替 String 来存储用户信息，节约了 xx% 的内存并便于单字段的修改。（需要自己实际测试对比数据，节省内存的原因是不用保存序列化对象信息或者 JSON 的一些额外字符串）**

**
2店铺查询：使用 Redis 对高频访问店铺进行缓存，降低 DB 压力同时提升 90% 的数据查询性能。**

**
3为方便其他业务后续使用缓存，使用泛型 + 函数式编程实现了通用缓存访问静态方法，并解决了缓存雪崩、缓存穿透等问题。**

**
4使用常量类全局管理 Redis Key 前缀、TTL 等，保证了键空间的业务隔离，减少冲突。**

**
5使用 Redis 的 Geo + Hash 数据结构分类存储附近商户，并使用 Geo Search 命令实现高性能商户查询及按距离排序。**

**
6使用 Redis List 数据结构存储用户点赞信息，并基于 ZSet 实现 TopN 点赞排行，实测相对于 DB 查询性能提升 xx%。（需要自己实际测试对比数据）**

**
7使用 Redis Set 数据结构实现用户关注、共同关注功能（交集），实测相对于 DB 查询性能提升 xx%。（需要自己实际测试对比数据）**

**
8使用 Redis BitMap 实现用户连续签到统计功能，相对于传统关系库存储，节约 xx% 的内存并提升 xx% 的查询性能。（需要自己实际测试对比数据）**

**
9在系统用户量不大的前提下，基于推模式实现关注 Feed 流，保证了新点评消息的及时可达，并减少用户访问的等待时间。**

**
10优惠券秒杀：使用 Redis + Lua 脚本实现库存预检，并通过 Stream 队列实现订单的异步创建，解决了超卖问题、实现一人一单。实现相比传统数据库，秒杀性能提高了 xx%。（需要自己实际测试对比数据）

再列举一些该项目可以扩展的点，有能力的同学可以自己尝试实现（注意，没有自己实现过千万别写到简历上！！！做没做过一问便知）：
1使用 Redis + Token 机制实现单点登录（补充到上述第 1 点中）**
**
2对 Redis 的所有 key 设置 N + n 的过期时间，从而合理使用内存并防止缓存雪崩；针对热点店铺缓存，使用逻辑过期（或自动续期）机制解决缓存击穿问题，防止数据库宕机。**
**
3使用 Redis 的 Geo + Hash 数据结构分类存储附近商户，并使用 Geo Search 命令实现高性能商户查询及按距离排序，实测相对于传统 DB 查询 + 业务层计算的方式，性能提升 xx%。**
**
4使用 Redis Set 数据结构实现用户关注、共同关注功能（交集），实测相对于 DB 查询性能提升 xx%，并使用 Redis AOF + 业务层日志防止关注数据丢失。（理解 AOF 和 RDB 持久化机制后再写这点）**
**
5基于 Spring Scheduler 实现对热点数据的定期检测和缓存预加载，提升用户的访问体验，并通过 Redisson 分布式锁保证集群中同一时刻的定时任务只执行一次。**
**
6关注 Feed 流可以改为推拉结合模式（活跃用户用推、普通用户用拉）**
**
7使用哨兵集群来提升 Redis 的读并发量、可用性和稳定性；或者使用 Redis 分片集群来提升 Redis 读写并发量、总存储容量，保障可用性和稳定性。**
**
8随着系统用户增多，使用 Redis HyperLogLog 代替 DB 来实现店铺和点评的 UV 统计，提高 xx% 的查询分析性能并解决 xx% 的内存空间。

**
**

