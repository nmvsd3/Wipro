package com.gl.dao;

import java.util.List;

import com.gl.beans.Todos;

public interface TodoDao
{
	public int saveTodo(Todos todo);
	public int updateTodo(Todos todo);
	public int deleteTodo(int id);
	public Todos getTodo(int id);
	public List<Todos> getAllTodos();
	

}
