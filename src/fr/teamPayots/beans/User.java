package fr.teamPayots.beans;

public class User {
	
	private String username, email, password;
	
	public User(){
		
		this.username = null;
		this.email = null;
		this.password = null;
	}
	
	public User(String un, String em, String pa){
		
		this.username = un;
		this.email = em;
		this.password = pa;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setUsername(String s){
		this.username = s;
	}
	
	public void setEmail(String s){
		this.email = s;
	}
	
	public void setPassword(String s){
		this.password = s;
	}

}
