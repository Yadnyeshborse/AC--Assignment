package com.example.daikin.Daikin.model;

public class CommonRoom extends Room {
	public enum CommonRoomType { GYM, LIBRARY, LAUNDRY }

	private CommonRoomType type;

	public CommonRoom() {
		super();
	}

	public CommonRoomType getType() {
		return type;
	}

	public void setType(CommonRoomType type) {
		this.type = type;
	}
}


