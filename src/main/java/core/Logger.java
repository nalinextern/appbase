package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logger {

	public static  boolean isDebugEnable =true;
	public static  boolean isWriteToFile =false;    
	private static boolean isFilterApply =false;
	public static String filterString = null;
	
	
	public static boolean isFilterApply() {
		return isFilterApply;
	}

	public static void setFilterApply(boolean isFilterApply) {
		Logger.isFilterApply = isFilterApply;

		if (!Logger.isFilterApply)
			filterString = null;
	}



	public static void handleException(Exception ex) {
		try {
			Throwable t = new Throwable();
			StackTraceElement[] elements = t.getStackTrace();

			String callerMethodName = elements[1].getMethodName();
			String callerClassName = elements[1].getClassName();
			String formatMessage = null;
			if (ex != null) {

				formatMessage = String.format("%s - [%s] - [%s]",
						ex.getMessage(), callerClassName, callerMethodName);
				logMessage(formatMessage, LogType.Error, null);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void handleException(Exception ex, String message) {
		try {
			Throwable t = new Throwable();
			StackTraceElement[] elements = t.getStackTrace();

			String callerMethodName = elements[1].getMethodName();
			String callerClassName = elements[1].getClassName();

			String formatMessage = null;

			if (ex != null)
				formatMessage = String.format("%s - [%s] -[%s] -[%s]", message,
						ex.getMessage(), callerClassName, callerMethodName);
			else
				formatMessage = String.format("%s ", message);

			logMessage(formatMessage, LogType.Error, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void logMessage(String message) {
		logMessage(message, LogType.Information, null);
	}
	
	public static void logMessage(String tag, String message) {
		logMessage(message, LogType.DFNTAB, tag);	
	}

	public static void logMessage(String message, LogType logType, String tag) {
		String formatMessage = String.format("[%s] - %s", logType.toString(), message);

		if(tag != null){
			formatMessage = String.format("[%s] <%s> - %s", logType.toString(), tag, message);
		}
		
		if (isDebugEnable) {
			if (isFilterApply && Validator.isValidString(filterString)) {

				if (Validator.isValidString(formatMessage)
						&& formatMessage.matches(filterString)) {
					System.out.println(formatMessage);
				}

			} else {
				System.out.println(formatMessage);
			}
		if(isWriteToFile){
			printLog(formatMessage);
		}}

	}

	public static void logMessage(String message, LogType logType) {
		String formatMessage = String.format("[%s] - %s", logType.toString(), message);

		if (isDebugEnable) {
			if (isFilterApply && Validator.isValidString(filterString)) {

				if (Validator.isValidString(formatMessage)
						&& formatMessage.matches(filterString)) {
					System.out.println(formatMessage);
				}

			} else {
				System.out.println(formatMessage);
			}
		if(isWriteToFile){
			printLog(formatMessage);
		}}

	}
	
	public static void logInfoMessage(String msg){
		logMessage(msg);
	}
	
	public static void logDebugMessage(String msg){
		logMessage(msg);
	}
	
	public static void setFilterStartsWith(String filterText) {

		if (Validator.isValidString(filterText)) {
			filterString = "(?i)" + filterText + ".*";
		}

	}

	public static void setFilterContains(String filterText) {

		if (Validator.isValidString(filterText)) {
			filterString = "(?i).*" + filterText + ".*";
		}
	}
	
	public static void printLog(String formatMessage) {
		try {

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			File file = new File("CMLogger.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(dateFormat.format(date));
			bufferWritter.write("\r\n");
			bufferWritter.write(formatMessage);
			bufferWritter.write("\r\n");
			bufferWritter.write("\r\n");
			bufferWritter.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}}

	public enum LogType {
		
		Error,
		Warning,
		Information, 
		DFNTAB
		
	}
}
