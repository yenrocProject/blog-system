# springbootProject
springboot项目的模板
模板项目：
	+ TK Mybatis 
	+ 统一相应，统一异常处理
	+ JWT + oauth2
	+ 多数据源
	+ redis，mongoDB
	+ 分布式锁注解
	
	+  分支dev_websocket websocket及时消息广播
	+  分支dev_quartz 定时器
	+  分支dev_rabbitmq 消息队列mq
	+  分支dev_kafka kafka
	+  分支dev_file 文件
	+ 

sys_dictionary (系统字典表配置)
sys_table_config (表配置)
sys_del_data (已删除数据记录表)

实现高度自由的增删改查
	controller 提供变量型url地址
	service 从spring容器获取dao层操作对象
	dao层基于数据库加载
		获取对象配置的数据信息，动态生成Entity.class，Dao.class，将dao创建为bean对象，交由Spring管理
		