package com.ravi.automation.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.pi4j.component.lcd.LCDTextAlignment;
import com.pi4j.component.lcd.impl.GpioLcdDisplay;
import com.pi4j.io.gpio.RaspiPin;

/**
 * 
 * @author rsing34
 * 
 */
public class LcdUtils {

	public final static int LCD_ROWS = 4;
	public final static int LCD_ROW_1 = 0;
	public final static int LCD_ROW_2 = 1;
	public final static int LCD_ROW_3 = 2;
	public final static int LCD_ROW_4 = 3;
	public final static int LCD_COLUMNS = 20;
	public final static int LCD_BITS = 4;

	// initialize LCD
	final static GpioLcdDisplay lcd = new GpioLcdDisplay(LCD_ROWS, LCD_COLUMNS, //
			RaspiPin.GPIO_07, // LCD RS pin
			RaspiPin.GPIO_08, // LCD strobe pin
			RaspiPin.GPIO_06, // LCD data bit 1
			RaspiPin.GPIO_05, // LCD data bit 2
			RaspiPin.GPIO_04, // LCD data bit 3
			RaspiPin.GPIO_01); // LCD data bit 4

	public static void main(String... args) {
		if (args == null || args.length < 1) {
			showHome();
			System.exit(0);
		}

		if (args[0].equalsIgnoreCase("clear")) {
			clearScreen();
			System.exit(0);
		}

		try {
			String data = "<No Data>";
			int lineIndex = Integer.parseInt(args[0]);
			if (args.length >= 2 && StringUtils.hasText(args[1])) {
				data = args[1];
			}
			lcd.write(lineIndex, data, LCDTextAlignment.ALIGN_CENTER);
		} catch (NumberFormatException e) {
			System.err.println("BAD Arguments: <Line#> <Data>");
		}
	}

	/**
	 * 
	 */
	public static void showHome() {
		// write line 1 to LCD
		lcd.write(LCD_ROW_1, "Raspberry Automation");

		// write line 2 to LCD
		lcd.write(LCD_ROW_2, "--------------------");
		DateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss");
		// write line 3 to LCD
		lcd.write(LCD_ROW_3, df.format(new Date()), LCDTextAlignment.ALIGN_CENTER);
		// write line 4 to LCD
		lcd.write(LCD_ROW_4, NetworkUtils.getHostAddress(), LCDTextAlignment.ALIGN_CENTER);
	}

	/**
	 * 
	 */
	public static void showData(String data, int line) {
		lcd.write(line, "                   ");
		lcd.write(line, data, LCDTextAlignment.ALIGN_CENTER);
	}

	/**
	 * 
	 */
	public static void clearScreen() {
		try {
			Thread.currentThread().sleep(1000);
			lcd.clear();
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
