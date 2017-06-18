package fr.teamPayots.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import fr.teamPayots.beans.User;
import fr.teamPayots.database.SQLconnector;

public final class SignupController {

	public static final String INPUT_UNAME = "username";
	public static final String INPUT_EMAIL = "email";
	public static final String INPUT_PASSW = "password";
	public static final String INPUT_CONFPASSW = "confpassword";
	String result;
	HashMap<String, String> errors = new HashMap<>();
	
	public User signupUser(HttpServletRequest request){
		
		String uname = getInputValue(request, INPUT_UNAME);
		String email = getInputValue(request, INPUT_EMAIL);
		String passw = getInputValue(request, INPUT_PASSW);
		String confpassw = getInputValue(request, INPUT_CONFPASSW);
		
		User user = new User();
		
		try{
			checkEmail(email);
		}catch(Exception e){
			setError(INPUT_EMAIL, e.getMessage());
		}
		user.setEmail(email);
		
		try {
			checkUsername(uname);
		} catch (Exception e) {
			setError(INPUT_UNAME, e.getMessage());
		}
		user.setUsername(uname);
		
		try {
			checkPassword(passw, confpassw);
		} catch (Exception e) {
			String s = e.getMessage();
			if (s.contains("match"))
				setError(INPUT_CONFPASSW, s);
			else
				setError(INPUT_PASSW, s);
		}
		user.setPassword(passw);
		
		if (errors.isEmpty()){
			result = "Successfully signed up !";
			
			String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
			
			ArrayList<String> cols = new ArrayList<>();
			ArrayList<String> vals = new ArrayList<>();
			
			cols.add("username");vals.add(uname);
			cols.add("password");vals.add(passw);
			cols.add("email");vals.add(email);
			cols.add("subdate");vals.add(today);
			
			SQLconnector sql = new SQLconnector("jdbc:mysql://localhost:3306?useLegacyDatetimeCode=false&serverTimezone=Australia/Sydney&useSSL=false", "payot", "mix2pay");
			
			sql.init();
			sql.insert("User", cols, vals);
		}
		else
			result = "Failed to sign up...";
		
		return user;
		
	}
	
	public String getInputValue(HttpServletRequest request, String inputName){
		
		String value = request.getParameter(inputName);
		if (value == null || value.trim().length() == 0)
			return null;
		else
			return value.trim();
	}
	
	public void setError(String input, String message){
		errors.put(input,  message);
	}
	
	private void checkEmail(String email) throws Exception{
		if (email != null && email.trim().length() != 0){
			if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)"))
				throw new Exception("Please provide a valid email address");
		}else
			throw new Exception("Please provide an email address");
		
	}
	
	private void checkPassword(String passw, String confpassw) throws Exception{
		if (passw != null && passw.trim().length() != 0 && confpassw != null && confpassw.trim().length() != 0){
			if (!passw.equals(confpassw))
				throw new Exception("Password confirmation does not match with password");
			else if (passw.contains(" "))
				throw new Exception("Spaces are not allowed in password");
			else if (passw.trim().length() < 6)
				throw new Exception("Password must contain at least 6 characters");
			else if (!passw.matches("(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+"))
				throw new Exception("Password must be composed of both letter(s) and number(s)");
		}else
			throw new Exception("Please provide a password and confirmation");
	}
	
	private void checkUsername(String uname) throws Exception{
		if (uname != null && uname.trim().length() != 0)
			if (uname.contains(" "))
				throw new Exception("Spaces are not allowed in username");
			if (uname.trim().length() < 5)
				throw new Exception("Username must contain at least 5 characters");
	}
	
	public String getResult(){
		return this.result;
	}
	
	public HashMap<String, String> getErrors(){
		return this.errors;
	}
	
}
