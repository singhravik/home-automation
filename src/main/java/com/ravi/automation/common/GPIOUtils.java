package com.ravi.automation.common;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.PinState;

/**
 * 
 * @author rsing34
 * 
 */
public class GPIOUtils {

	private final static Logger logger = LoggerFactory.getLogger(GPIOUtils.class);

	// create gpio controller
	final static GpioController gpio = GpioFactory.getInstance();

	/**
	 * 
	 */
	public static void pulse(int pinAddress, int time) {
		logger.info("PULSE on : " + getPinName(pinAddress));
		// setPinState(pinAddress,PinState.LOW);
		setPinState(pinAddress, PinState.HIGH);
		try {
			Thread.currentThread().sleep(time);
		} catch (InterruptedException e) {
		}
		setPinState(pinAddress, PinState.LOW);
	}

	/**
	 * 
	 */
	public static void setHigh(int pinAddress) {
		logger.info("HIGH on : " + getPinName(pinAddress));
		setPinState(pinAddress, PinState.HIGH);
	}

	/**
	 * 
	 */
	public static void setLow(int pinAddress) {
		logger.info("LOW on : " + getPinName(pinAddress));
		setPinState(pinAddress, PinState.LOW);
	}

	public static void shutdown() {
		for (int index = 0; index < 30; index++) {
			setPinState(index, PinState.LOW);
		}
		gpio.shutdown();
	}

	private static String getPinName(int pinAddress) {
		if (pinAddress < 10)
			return "GPIO 0" + pinAddress;
		else
			return "GPIO " + pinAddress;
	}

	private static void setPinState(int pin, PinState pinState) {
		String cmdOption = ";";
		switch (pinState) {
		case LOW:
			cmdOption = "in";
			break;
		case HIGH:
			cmdOption = "out";
			break;

		}
		try {
			Runtime.getRuntime().exec("/usr/local/bin/gpio mode " + pin + " " + cmdOption);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
