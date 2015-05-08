package com.ravi.automation.test;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

/**
 * 
 * @author rsing34
 * 
 */
public class GpioExample {

	// create gpio controller
	final static GpioController gpio = GpioFactory.getInstance();

	/**
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String args[]) throws InterruptedException {

		System.out.println("GpioExample: Raspberry Runing ..");
		
	}
}
