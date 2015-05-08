package com.ravi.automation.models;

import org.springframework.util.StringUtils;

/**
 * 
 * @author rsing34
 * 
 */
public enum Status {

	ON, OFF;

	public static Status fromStatusName(String stat) {
		if (!StringUtils.hasText(stat)) {
			return null;
		}
		if (stat.toLowerCase().equalsIgnoreCase("off"))
			return Status.OFF;

		if (stat.toLowerCase().equalsIgnoreCase("on"))
			return Status.ON;

		try {
			int intValue = Integer.parseInt(stat);

			if (intValue <= 0) {
				return Status.OFF;
			}
			if (intValue > 0) {
				return Status.ON;
			}
		} catch (NumberFormatException e) {
			return null;
		}

		return null;
	}

}
