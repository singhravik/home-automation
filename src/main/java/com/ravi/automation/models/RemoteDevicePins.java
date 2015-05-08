package com.ravi.automation.models;

/**
 * 
 * @author rsing34
 * 
 */
public enum RemoteDevicePins {

	DEVICE1(1, 29, 28), DEVICE2(1, 27, 26), DEVICE3(1, 25, 24),
	// DEVICE4(1, 24, 28),
	DEVICE5(1, 23, 22);

	private int deviceNumber = 0;
	private int onPin = 0;
	private int offPin = 0;

	RemoteDevicePins(int device, int on, int off) {
		this.deviceNumber = device;
		this.onPin = on;
		this.offPin = off;
	}

	public static RemoteDevicePins fromDeviceId(int deviceNum) {
		for (RemoteDevicePins rDevice : RemoteDevicePins.values()) {
			if (rDevice.getDeviceNumber() == deviceNum)
				return rDevice;
		}
		return null;
	}

	public int getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(int deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public int getOnPin() {
		return onPin;
	}

	public void setOnPin(int onPin) {
		this.onPin = onPin;
	}

	public int getOffPin() {
		return offPin;
	}

	public void setOffPin(int offPin) {
		this.offPin = offPin;
	}

}
