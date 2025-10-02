package com.example.daikin.Daikin.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.daikin.Daikin.model.Apartment;
import com.example.daikin.Daikin.model.Building;
import com.example.daikin.Daikin.model.CommonRoom;
import com.example.daikin.Daikin.model.CommonRoom.CommonRoomType;
import com.example.daikin.Daikin.model.Room;

@Service
public class BuildingService {
	private final Building building;
	private final Random random = new Random();

	public BuildingService() {
		this.building = new Building();
		this.building.setRequestedTemperature(25.0);

		Apartment a101 = new Apartment();
		a101.setId("101");
		a101.setOwnerName("Owner 101");
		a101.setCurrentTemperature(randomTemperature());

		Apartment a102 = new Apartment();
		a102.setId("102");
		a102.setOwnerName("Owner 102");
		a102.setCurrentTemperature(randomTemperature());

		CommonRoom gym = new CommonRoom();
		gym.setId("gym");
		gym.setType(CommonRoomType.GYM);
		gym.setCurrentTemperature(randomTemperature());

		CommonRoom library = new CommonRoom();
		library.setId("library");
		library.setType(CommonRoomType.LIBRARY);
		library.setCurrentTemperature(randomTemperature());

		this.building.getApartments().add(a101);
		this.building.getApartments().add(a102);
		this.building.getCommonRooms().add(gym);
		this.building.getCommonRooms().add(library);

		recalculateStatuses();
	}

	private double randomTemperature() {
		return 10.0 + (40.0 - 10.0) * random.nextDouble();
	}

	public Building getBuilding() {
		return building;
	}

	public void setRequestedTemperature(double requestedTemperature) {
		building.setRequestedTemperature(requestedTemperature);
		recalculateStatuses();
	}

	public void recalculateStatuses() {
		double target = building.getRequestedTemperature();
		List<Room> rooms = getAllRooms();
		for (Room room : rooms) {
			boolean heat = room.getCurrentTemperature() < target;
			boolean cool = room.getCurrentTemperature() > target;
			room.setHeatingEnabled(heat);
			room.setCoolingEnabled(cool);
		}
	}

	public List<Room> getAllRooms() {
		java.util.ArrayList<Room> all = new java.util.ArrayList<>();
		all.addAll(building.getApartments());
		all.addAll(building.getCommonRooms());
		return all;
	}

	public Optional<Room> findRoomById(String id) {
		for (Apartment a : building.getApartments()) {
			if (a.getId().equals(id)) return Optional.of(a);
		}
		for (CommonRoom c : building.getCommonRooms()) {
			if (c.getId().equals(id)) return Optional.of(c);
		}
		return Optional.empty();
	}

	public void upsertApartment(Apartment apartment) {
		Optional<Apartment> existing = building.getApartments().stream().filter(a -> a.getId().equals(apartment.getId())).findFirst();
		if (existing.isPresent()) {
			Apartment e = existing.get();
			e.setOwnerName(apartment.getOwnerName());
			e.setCurrentTemperature(apartment.getCurrentTemperature());
		} else {
			if (apartment.getId() == null || apartment.getId().isBlank()) {
				apartment.setId(String.valueOf(100 + building.getApartments().size() + 1));
			}
			if (apartment.getCurrentTemperature() == 0) {
				apartment.setCurrentTemperature(randomTemperature());
			}
			building.getApartments().add(apartment);
		}
		recalculateStatuses();
	}

	public void upsertCommonRoom(CommonRoom commonRoom) {
		Optional<CommonRoom> existing = building.getCommonRooms().stream().filter(c -> c.getId().equals(commonRoom.getId())).findFirst();
		if (existing.isPresent()) {
			CommonRoom e = existing.get();
			e.setType(commonRoom.getType());
			e.setCurrentTemperature(commonRoom.getCurrentTemperature());
		} else {
			if (commonRoom.getId() == null || commonRoom.getId().isBlank()) {
				commonRoom.setId(commonRoom.getType() != null ? commonRoom.getType().name().toLowerCase() : "common-" + (building.getCommonRooms().size() + 1));
			}
			if (commonRoom.getCurrentTemperature() == 0) {
				commonRoom.setCurrentTemperature(randomTemperature());
			}
			building.getCommonRooms().add(commonRoom);
		}
		recalculateStatuses();
	}

	public boolean deleteRoom(String id) {
		System.out.println("Attempting to delete room with ID: " + id);
		System.out.println("Available apartments: " + building.getApartments().stream().map(Room::getId).toList());
		System.out.println("Available common rooms: " + building.getCommonRooms().stream().map(Room::getId).toList());
		
		Iterator<Apartment> aIt = building.getApartments().iterator();
		while (aIt.hasNext()) {
			if (aIt.next().getId().equals(id)) {
				aIt.remove();
				System.out.println("Removed apartment with ID: " + id);
				return true;
			}
		}
		Iterator<CommonRoom> cIt = building.getCommonRooms().iterator();
		while (cIt.hasNext()) {
			if (cIt.next().getId().equals(id)) {
				cIt.remove();
				System.out.println("Removed common room with ID: " + id);
				return true;
			}
		}
		System.out.println("Room with ID " + id + " not found");
		return false;
	}
}


