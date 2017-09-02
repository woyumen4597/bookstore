备份数据库
1.创建数据库 create database bookstore;
2.使用数据库 use bookstore;
3.导入备份 source bs.sql(sql文件的绝对路径 e.g E:\\bs.sql)

修改src目录下的c3p0-config.xml，填入自己的用户名和密码
<property name="driverClass">com.mysql.jdbc.Driver</property>
		<property name="jdbcUrl">jdbc:mysql://localhost:3306/bookstore</property>
		<property name="user">root</property>
		<property name="password">woyumen4597;</property>

