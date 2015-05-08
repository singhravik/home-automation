package com.ravi.automation.beans;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ravi.automation.common.LcdUtils;
import com.ravi.automation.common.ShutdownHook;

/**
 * 
 * @author rsing34
 * 
 */
@Component
public class AppInspector {

	private static final Logger logger = LoggerFactory.getLogger(AppInspector.class);

	@PostConstruct
	public void setup() {
		logger.info("Showing Home");
		LcdUtils.showHome();
		logger.info("Adding shutdown hook");
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
	}

}
