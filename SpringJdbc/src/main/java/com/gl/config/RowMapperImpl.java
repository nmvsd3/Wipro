package com.gl.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gl.beans.Todos;

public class RowMapperImpl implements RowMapper<Todos> {

	@Override
	public Todos mapRow(ResultSet rs, int rowNum) throws SQLException {
		Todos todo = new Todos();
		todo.setId(rs.getInt(1));
		todo.setTitle(rs.getString(2));
		todo.setDescription(rs.getString(3));
		return todo;
	}

}
