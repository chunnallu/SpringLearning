package com.lcl.springlearning;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 */
@Component("app")
public class App 
{
	@Autowired
	private JdbcDao jdbcDao;
	
    public static void main( String[] args )
    {
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");
    	App app = (App) ctx.getBean("app");
    	JdbcTemplate template = app.getJdbcDao().getJdbcTemplate();
    	final List<Long> ids = new ArrayList<Long>();
    	ids.add((long) 8001);
    	//template.execute("insert into a(id) values(1)");
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

	public JdbcDao getJdbcDao() {
		return jdbcDao;
	}

	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

    
    
}
