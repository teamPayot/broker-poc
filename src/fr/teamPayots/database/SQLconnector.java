package fr.teamPayots.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import com.mysql.cj.jdbc.Driver;

public class SQLconnector {

	private String url, user, password;
	private HashMap<String, String> errors;
	private HashMap<Integer, String> unknown_errors;
	public static final String ERR_DUPLICATE_EMAIL = "dupliEmail";
	public static final String ERR_SQLDRIVER = "sqldriver";
	
	public SQLconnector(){
		
		this.url = null;
		this.user = null;
		this.password = null;
	}
	
	public SQLconnector(String ur, String us, String pa){
		
		this.url = ur;
		this.user = us;
		this.password = pa;
	}
	
	public void init(){
		
		errors = new HashMap<>();
		unknown_errors = new HashMap<>();
		Connection connection = null;
		
		try{
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");
			}catch(ClassNotFoundException e){
				setError(ERR_SQLDRIVER,  "Database cannot be loaded, please try again later");
			}
			connection = DriverManager.getConnection(this.url, this.user, this.password);
			
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("CREATE DATABASE IF NOT EXISTS broker_poc_db");
			statement.executeUpdate("USE broker_poc_db");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS User(id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,username VARCHAR(40) NOT NULL,password CHAR(40) NOT NULL,email VARCHAR(60) NOT NULL,subdate DATETIME NOT NULL,PRIMARY KEY (id),UNIQUE (email))ENGINE=INNODB");
			
		}catch(SQLException e){
			unknown_errors.put(e.getErrorCode(), e.getMessage());
		}finally{
			if (connection != null){
				try{
					connection.close();
				}catch(SQLException ignore){
					ignore.printStackTrace();
				}
			}
		}
	}
	
	public int insert(String table, ArrayList<String> columns, ArrayList<String> values){
		
		int r = 0;
		
		Connection connection = null;
		
		if (columns.size() != values.size())
			throw new InputMismatchException("There must be as many values as columns to insert in");
		
		String set_str = " SET ";
		int p=0;
		for (String i : columns){
			set_str += i+"='"+values.get(p)+"'";
			p++;
			if (p < columns.size())
				set_str += ", ";
		}

		try{
			try{
				Class.forName("com.mysql.cj.jdbc.Driver");
			}catch(ClassNotFoundException e){
				setError(ERR_SQLDRIVER,  "Database cannot be loaded, please try again later");
			}
			connection = DriverManager.getConnection(this.url, this.user, this.password);
			
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("USE broker_poc_db");
			r = statement.executeUpdate("INSERT INTO "+table+set_str);
			
		}catch(SQLException e){
			if (e.getErrorCode() == 1062)
				setError(ERR_DUPLICATE_EMAIL,  "This email address is already used");
			else
				unknown_errors.put(e.getErrorCode(), e.getMessage());
		}finally{
			if (connection != null){
				try{
					connection.close();
				}catch(SQLException ignore){
					ignore.printStackTrace();
				}
			}
		}
		
		return r;
		
	}
	
	public void setError(String type, String message){
		errors.put(type, message);
	}
	
}
