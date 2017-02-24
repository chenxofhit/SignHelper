# SignHelper

1, 签到系统能够实现自动签到, 在216内部已经稳定运行半年有余, 采用springmvc+quartz+mongodb实现的,包管理由maven驱动. 

2, 附件中提供的是源代码, 请首先用eclipse导入, 在adminController中将各个成员的姓名和蓝牙地址按照制定代码要求初始化(adminController的url为A). 也需要注意profile里的mongo.properties配置文件. 代码检查完后使用maven打包成war包.

3,安装tomcat,mongodb等;

4,上传war包, 运行http://192.168.1.113:8080/signhelper-1.0.0/A(A为上面的url) 初始化数据;

5,可以正常使用了,  url为: http://192.168.1.113:8080/signhelper-1.0.0/index ,下面是我们内部的一个截图.


部署请找有相关web部署经验的同学执行, 有问题的请与我联系
