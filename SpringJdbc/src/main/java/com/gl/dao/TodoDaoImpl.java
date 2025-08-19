package com.gl.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gl.beans.Todos;
import com.gl.config.RowMapperImpl;

@Repository
public class TodoDaoImpl implements TodoDao
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int saveTodo(Todos todo) {
	String insertQuery= "insert into Todos (id,title,description) values(?,?,?)";//prepared statemnts
	int updatedRows=jdbcTemplate.update(insertQuery,todo.getId(),todo.getTitle(),todo.getDescription());
		return updatedRows;
	}

	@Override
	public int updateTodo(Todos todo) {
		String updateQuery= "update Todos set title=? , description=? where id=?";
		int updatedRows=jdbcTemplate.update(updateQuery,todo.getTitle(),todo.getDescription(),todo.getId());
			return updatedRows;
	}

	@Override
	public int deleteTodo(int id) {
		String deleteQuery= "delete from Todos where id=?";
		int updatedRows=jdbcTemplate.update(deleteQuery,id);
		return updatedRows;
	}

	@Override
	public Todos getTodo(int id) {
		String selectQuery="select * from Todos where id=?";
		Todos todo=jdbcTemplate.queryForObject(selectQuery,new RowMapperImpl(),id);
		return todo;
	}

	@Override
	public List<Todos> getAllTodos() {
		String selectQuery="select * from Todos";
		List<Todos> todos=jdbcTemplate.query(selectQuery,new BeanPropertyRowMapper<>(Todos.class));
		return todos;
	}
	
	
	
	

}
