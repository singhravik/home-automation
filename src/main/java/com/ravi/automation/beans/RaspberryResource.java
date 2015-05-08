package com.ravi.automation.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ravi.automation.common.GPIOUtils;
import com.ravi.automation.common.LcdUtils;

@Component
public class RaspberryResource {

	private static final Logger logger = LoggerFactory.getLogger(RaspberryResource.class);

	public void displayHome() {
		LcdUtils.showHome();
	}

	public void displayData(String data, int line) {
		logger.info("Showing Data: " + data);
		LcdUtils.showData(data, line);
	}

	public void pulse(int pinAddress, int time) {
		GPIOUtils.pulse(pinAddress, time);
	}

	public void setHigh(int pinAddress) {
		GPIOUtils.setHigh(pinAddress);
	}

	public void setLow(int pinAddress) {
		GPIOUtils.setLow(pinAddress);
	}

	public void shutdown() {
		GPIOUtils.shutdown();
	}
}
