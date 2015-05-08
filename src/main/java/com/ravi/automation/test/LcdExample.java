package com.ravi.automation.test;

import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.io.gpio.RaspiPin;

/**
 * 
 * @author rsing34
 * 
 */
public class LcdExample {

	public final static int LCD_ROWS = 4;
	public final static int LCD_ROW_1 = 0;
	public final static int LCD_ROW_2 = 1;
	public final static int LCD_ROW_3 = 2;
	public final static int LCD_ROW_4 = 3;
	public final static int LCD_COLUMNS = 20;
	public final static int LCD_BITS = 4;

	/**
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String args[]) throws InterruptedException {

		System.out.println("LcdExample: Raspberry Runing ..");

		// initialize LCD
		final GpioLcdDisplay lcd = new GpioLcdDisplay(LCD_ROWS, LCD_COLUMNS, //
				RaspiPin.GPIO_07, // LCD RS pin
				RaspiPin.GPIO_08, // LCD strobe pin
				RaspiPin.GPIO_00, // LCD data bit 1
				RaspiPin.GPIO_01, // LCD data bit 2
				RaspiPin.GPIO_02, // LCD data bit 3
				RaspiPin.GPIO_03); // LCD data bit 4

		// clear LCD
		lcd.clear();
		Thread.sleep(1000);

		// write line 1 to LCD
		lcd.write(LCD_ROW_1, "Raspberry Runing ..");

		// write line 2 to LCD
		lcd.write(LCD_ROW_2, "--------------------");
	}
}
