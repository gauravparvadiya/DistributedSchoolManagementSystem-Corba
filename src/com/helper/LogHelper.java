/**
 * 
 */
package com.helper;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * @author gauravparvadiya
 *
 */
public class LogHelper {

	private PatternLayout layout;
	private FileAppender fileAppender;
	private Logger rootLogger;

	/**
	 * default constructor to define layout, file appender and rootlogger
	 */
	public LogHelper() {
		super();
		layout = new PatternLayout();
		fileAppender = new FileAppender();
		rootLogger = Logger.getRootLogger();
	}

	/**
	 * Method to set log file
	 * 
	 * @param fileName
	 */
	public void setupLogFile(String fileName) {
		// creates pattern layout
		String conversionPattern = "%-7p %d [%t] %c %x - %m%n";
		layout.setConversionPattern(conversionPattern);

		// creates file appender
		fileAppender.setFile(fileName);
		fileAppender.setLayout(layout);
		fileAppender.activateOptions();

		// configures the root logger
		rootLogger.setLevel(Level.DEBUG);
		rootLogger.addAppender(fileAppender);

	}
}
