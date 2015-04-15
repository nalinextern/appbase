package core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {

	public static boolean  isValidString(String value)
	{	
		return value != null && !value.isEmpty() && !value.trim().isEmpty();		
	}
	
	public static boolean tryParseInt(String value)  
	{  
	     try  {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch(NumberFormatException e)
	      {  
	    	  Logger.handleException(e);
	          return false;  
	      }  
	}
	
	public static boolean tryParseDouble(String value)  
	{  
	     try  	     {  
	         Double.parseDouble(value);  
	         return true;  
	      } catch(NumberFormatException e)  
	      {  
	    	  Logger.handleException(e);
	          return false;  
	      }  
	}
	
	
	public static boolean tryParseLong(String value)  
	{  
	     try  	     {  
	         Long.parseLong(value);  
	         return true;  
	      } catch(NumberFormatException e)  
	      {  
	    	  Logger.handleException(e);
	          return false;  
	      }  
	}
	
	public static boolean passwordValidate(final String password){
		 
	    final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	
		Pattern pattern = null;
	    Matcher matcher = null;
	    pattern = Pattern.compile(PASSWORD_PATTERN);
		matcher = pattern.matcher(password);
		return matcher.matches();
	}
}