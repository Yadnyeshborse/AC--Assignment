package com.example.daikin.Daikin.model;

import java.util.UUID;

public class Room {
	private String id;
	private double currentTemperature;
	private boolean heatingEnabled;
	private boolean coolingEnabled;

	public Room() {
		this.id = UUID.randomUUID().toString();
	}

	public Room(String id, double currentTemperature) {
		this.id = id;
		this.currentTemperature = currentTemperature;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(double currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public boolean isHeatingEnabled() {
		return heatingEnabled;
	}

	public void setHeatingEnabled(boolean heatingEnabled) {
		this.heatingEnabled = heatingEnabled;
	}

	public boolean isCoolingEnabled() {
		return coolingEnabled;
	}

	public void setCoolingEnabled(boolean coolingEnabled) {
		this.coolingEnabled = coolingEnabled;
	}
}


