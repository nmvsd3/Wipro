package com.gl.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.gl.dao.TodoDaoImpl;

@Configuration	
@ComponentScan("com.gl.dao")
public class JdbcConfig 
{
	@Bean("dataSource")
	public DataSource getDataSource()
	{
		DriverManagerDataSource dataSource= new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/springJdbc");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
	
	@Bean("jdbcTemplate")
	public JdbcTemplate getJdbcTemplate() 
	{
		JdbcTemplate jdbcTemplate= new JdbcTemplate();
		jdbcTemplate.setDataSource(getDataSource());//setter based di
		
	   return jdbcTemplate;
	}
	
	
}
