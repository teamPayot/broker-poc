package fr.teamPayots.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import fr.teamPayots.beans.User;

public class SigninController {
	private static final String INPUT_EMAIL = "email";
	private static final String INPUT_PASSW = "password";
	private String result;
	private HashMap<String, String> errors = new HashMap<>();
	
	public String getResult(){
		return this.result;
	}
	
	public HashMap<String, String> getErrors(){
		return this.errors;
	}
	
	public User connectUser(HttpServletRequest request){
		String email = getInputValue(request, INPUT_EMAIL);
		String passw = getInputValue(request, INPUT_PASSW);
		
		User user = new User();
		
		try{
			checkEmail(email);
		}catch(Exception e){
			setError(INPUT_EMAIL, e.getMessage());
		}
		user.setEmail(email);
		
		try {
			checkPassword(passw);
		} catch (Exception e) {
			String s = e.getMessage();
			setError(INPUT_PASSW, s);
		}
		user.setPassword(passw);
		
		if (errors.isEmpty()){
			result = "Successfully signed in !";
		}
		else
			result = "Failed to sign in...";
		
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
	
	private void checkPassword(String passw) throws Exception{
		if (passw != null && passw.trim().length() != 0){
			if (passw.contains(" "))
				throw new Exception("Spaces are not allowed in password");
			else if (passw.trim().length() < 6)
				throw new Exception("Password must contain at least 6 characters");
			else if (!passw.matches("(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+"))
				throw new Exception("Password must be composed of both letter(s) and number(s)");
		}else
			throw new Exception("Please provide a password");
	}
}
