### 引言
刚刚开始学Spring AOP，按照教程配置好之后，可以像正常方法那样执行，却怎么也不会执行切面的方法。去群里问了一下，结果也没有解决。然后自己慢慢的试，终于发现了原因。

下面是一段有问题的代码，只有一处有问题，运行的时候，会报一个NoSuchBeanDefinitionException异常( 这里故意不给出详细异常信息 )，若能从下面代码中看出问题，那应该对Spring的AOP实现机制有一定了解。若不能，可以到 Spring_03_AOP_Wrong–github里下载源代码。另外，我将在下一篇博客里解析一下，毕竟新手的话，碰到这样的问题，也比较难处理


### 代码如下
类A,测试类，打算拦截A的doing方法

    @Component
    public class AImpl implements A
	{
	
		public void doing() {
			System.out.println("hello");
		}
	
    	public static void main( String[] args )
    	{
    		ApplicationContext ctx=new ClassPathXmlApplicationContext("bean.xml");
        	AImpl a=ctx.getBean(AImpl.class);
        	a.doing();
    	}

	
	}
类B,切面

	@Aspect
	public class B {
    
		@Pointcut("execution(* doing(..))")
		public void pointCutMethod(){
			System.out.println("进入切入点！");
		}
	
		@Before("pointCutMethod()")
		public void doBefore(){
			System.out.println("前置通知！");
		}
	
		@After("pointCutMethod()")
		public void doAfter(){
			System.out.println("后置通知！");
		}
	}
