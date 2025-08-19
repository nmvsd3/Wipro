package com.gl.ui;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.gl.beans.Todos;
import com.gl.config.JdbcConfig;
import com.gl.dao.TodoDao;
import com.gl.dao.TodoDaoImpl;

public class TodoMain 
{


	public static void main(String[] args)
	{
		ApplicationContext context= new AnnotationConfigApplicationContext(JdbcConfig.class);
		TodoDao dao=context.getBean(TodoDaoImpl.class);

		Todos todo1 = new Todos(1,"task3","task3 so and so");
		Todos todo2 = new Todos(2,"task3","task3 so and so");

		dao.saveTodo(todo1);
		dao.saveTodo(todo2);

		//		Todo todo1 = new Todo(2,"task2","coding");
		//		dao.updateTodo(todo1);

		//	 dao.deleteTodo(2);

		System.out.println(dao.getTodo(1));
		List<Todos> todos= dao.getAllTodos();
		for(Todos t:todos)
		{
			System.out.println(t);
		}





	}

}
