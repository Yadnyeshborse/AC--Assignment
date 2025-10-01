package com.example.daikin.Daikin.model;

import java.util.ArrayList;
import java.util.List;

public class Building {
	private double requestedTemperature = 20.0;
	private final List<Apartment> apartments = new ArrayList<>();
	private final List<CommonRoom> commonRooms = new ArrayList<>();

	public double getRequestedTemperature() {
		return requestedTemperature;
	}

	public void setRequestedTemperature(double requestedTemperature) {
		this.requestedTemperature = requestedTemperature;
	}

	public List<Apartment> getApartments() {
		return apartments;
	}

	public List<CommonRoom> getCommonRooms() {
		return commonRooms;
	}
}


