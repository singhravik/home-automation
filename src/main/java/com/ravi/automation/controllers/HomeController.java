package com.ravi.automation.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ravi.automation.beans.RaspberryResource;
import com.ravi.automation.models.RemoteDevicePins;
import com.ravi.automation.models.Status;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private RaspberryResource rpi;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		rpi.displayHome();
		return "Home";
	}

	@RequestMapping(value = "/print/{row}/{data}", method = RequestMethod.GET)
	public @ResponseBody
	String home(@PathVariable("row") int row, @PathVariable("data") String data) {

		if (row > 3) {
			rpi.displayHome();
			return "Home";
		}
		rpi.displayData(data, row);
		return "Printed : " + data;
	}

	@RequestMapping(value = "/gpio/pulse/{pin}/{time}", method = RequestMethod.GET)
	public @ResponseBody
	String pulse(@PathVariable("pin") int pin, @PathVariable("time") int time) {
		rpi.setLow(pin);
		return pin + " Pulse for " + time + " ms";
	}

	@RequestMapping(value = "/gpio/high/{pin}", method = RequestMethod.GET)
	public @ResponseBody
	String high(@PathVariable("pin") int pin) {
		rpi.setHigh(pin);
		return pin + " High";
	}

	@RequestMapping(value = "/gpio/low/{pin}", method = RequestMethod.GET)
	public @ResponseBody
	String low(@PathVariable("pin") int pin) {
		rpi.setLow(pin);
		return pin + " Low";
	}

	@RequestMapping(value = "/remote/{switchN}/{status}", method = RequestMethod.GET)
	public @ResponseBody
	String low(@PathVariable("switchN") int switchN, @PathVariable("status") String status) {

		RemoteDevicePins remoteDevice = RemoteDevicePins.fromDeviceId(switchN);

		if (remoteDevice == null) {
			return switchN + " is not supported ";
		}
		// set low first
		rpi.setLow(remoteDevice.getOffPin());
		rpi.setLow(remoteDevice.getOnPin());

		// switch
		switch (Status.fromStatusName(status)) {
		case ON:
			rpi.pulse(remoteDevice.getOnPin(), 500);
			break;

		case OFF:
			rpi.pulse(remoteDevice.getOffPin(), 500);
			break;

		default:
			break;
		}
		rpi.displayData(switchN + " is now " + status, 1);
		return switchN + " is now " + status;
	}

	@RequestMapping(value = "/gpio/shutdown", method = RequestMethod.GET)
	public @ResponseBody
	String gpioShutdown() {
		rpi.shutdown();
		return "gpioShutdown";
	}
}
