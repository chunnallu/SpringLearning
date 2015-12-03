package com.lcl.springlearning;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AImpl
{
    public void doing() {
        System.out.println("hello");
    }

    public static void main( String[] args )
    {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("bean.xml");
        AImpl a=ctx.getBean(AImpl.class);  //错误示例
        //A a=ctx.getBean(A.class);//改成这样就正确了
        a.doing();
    }


}
