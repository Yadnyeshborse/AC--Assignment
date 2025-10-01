package com.example.daikin.Daikin.model;

public class Apartment extends Room {
	private String ownerName;

	public Apartment() {
		super();
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}


