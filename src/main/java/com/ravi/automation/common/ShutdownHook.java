package com.ravi.automation.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author rsing34
 * 
 */
public class ShutdownHook extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

	/**
	 * Loop and shutdown all the daemon threads using the hooks
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		logger.info("ShutdownHook Invoked ..");
		GPIOUtils.shutdown();
		LcdUtils.clearScreen();
	}
}