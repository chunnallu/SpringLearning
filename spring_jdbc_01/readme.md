#简介
主要测试spring 4的 `JdbcTemplate` 的使用，可以作为第一个使用spring操作数据库的例子。数据库的操作在一个系统里往往属于关键性的操作，因此，熟练使用spring的数据库组件很重要。

项目使用maven管理依赖。
#创建数据库表结构
两个表都只有一个id列。

    create table A(
	`id` INT (255) not null,
	primary key (`id`)
	);

	create table B(
	`id` INT (255) not null,
	primary key (`id`)
	);

#maven相关
### 创建一个maven项目
### 新建一个pom.xml文件

一共需要加入四个依赖：`spring-context`、`spring-jdbc`、`commons-dbcp`、`mysql-connector`等

	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!--自动生成-->
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.lcl</groupId>
	<artifactId>springlearning</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>springlearning</name>

	<url>http://maven.apache.org</url>

	<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>
 	<!--自动生成:结束-->
	
	<dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>4.2.3.RELEASE</version>
    </dependency>
    
     
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>4.2.3.RELEASE</version>
    </dependency>
    
    <dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>5.1.18</version>
    </dependency>
    
    <dependency>
		<groupId>commons-dbcp</groupId>
		<artifactId>commons-dbcp</artifactId>
		<version>1.4</version>
	</dependency> 
	</dependencies>
	</project>

# spring相关
### bean.xml文件
	<?xml version="1.0" encoding="UTF-8"?>
	<beans xmlns="http://www.springframework.org/schema/beans"  
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
  		xmlns:p="http://www.springframework.org/schema/p"  
        xmlns:context="http://www.springframework.org/schema/context"  
        xsi:schemaLocation="http://www.springframework.org/schema/beans   
           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd  
           http://www.springframework.org/schema/context   
           http://www.springframework.org/schema/context/spring-context-3.0.xsd">
      <context:component-scan base-package="com.lcl"></context:component-scan>
      
	  <!--数据源-->
      <bean id="dataSource" 
      		class="org.apache.commons.dbcp.BasicDataSource" >
      		<property name="driverClassName"
      				  value="com.mysql.jdbc.Driver"></property>
    		<property name="url" 
    				  value="jdbc:mysql://localhost:3306/common"></property>
    	    <property name="username"
    	    		  value="root"></property>
    	    <property name="password" 
    	    		  value="ro!admin#ot"></property>
    	  
      </bean>
      
	  <!--jdbc模板类-->
      <bean id="jdbcTemplate"
      		class="org.springframework.jdbc.core.JdbcTemplate">
      		<constructor-arg ref="dataSource" ></constructor-arg>
      </bean>
      
	</beans>

### App.class
主文件，实现使用 `JdbcTempalte` 进行批量插入操作。
	
	@Component("app")
	public class App 
	{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    public static void main( String[] args )
    {
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");
    	App app = (App) ctx.getBean("app");
    	JdbcTemplate template = app.getJdbcTemplate();
		
		//构建要插入的数据集
    	final List<Long> ids = new ArrayList<Long>();
    	for(long i=25879 ;i< 80000 ; i=i+1){
    		ids.add(i);
    	}
    
		//使用jdbcTemplate类的批量插入方法进行插入
    	template.batchUpdate("insert into b(id) values(?)", new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, ids.get(i));
			}
			
			@Override
			public int getBatchSize() {
				return ids.size();
			}
		});
    }

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

    
	}

# 其它注意问题
1. 一些注解比如 `@Resource` 需要jdk-1.6以上才能支持
2. spring 4已经没有 `SimpleJdbcTempalte` 这个类了，它的功能被合并到了 `JdbcTemplate`   中