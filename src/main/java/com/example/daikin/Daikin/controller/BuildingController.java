package com.example.daikin.Daikin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.daikin.Daikin.model.Apartment;
import com.example.daikin.Daikin.model.Building;
import com.example.daikin.Daikin.model.CommonRoom;
import com.example.daikin.Daikin.model.Room;
import com.example.daikin.Daikin.service.BuildingService;

@RestController
@RequestMapping("/api/building")
public class BuildingController {
	private final BuildingService buildingService;

	public BuildingController(BuildingService buildingService) {
		this.buildingService = buildingService;
	}

	@GetMapping
	public Building getBuilding() {
		return buildingService.getBuilding();
	}

	@GetMapping("/rooms")
	public List<Room> getRooms() {
		return buildingService.getAllRooms();
	}

	@GetMapping("/rooms/{id}")
	public ResponseEntity<Room> getRoom(@PathVariable String id) {
		Optional<Room> room = buildingService.findRoomById(id);
		return room.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/temperature")
	public Building setRequestedTemperature(@RequestBody SetTemperatureRequest request) {
		buildingService.setRequestedTemperature(request.requestedTemperature);
		return buildingService.getBuilding();
	}

	@PostMapping("/apartments")
	public Building upsertApartment(@RequestBody Apartment apartment) {
		buildingService.upsertApartment(apartment);
		return buildingService.getBuilding();
	}

	@PostMapping("/common-rooms")
	public Building upsertCommonRoom(@RequestBody CommonRoom commonRoom) {
		buildingService.upsertCommonRoom(commonRoom);
		return buildingService.getBuilding();
	}

	@DeleteMapping("/rooms/{id}")
	public ResponseEntity<Void> deleteRoom(@PathVariable String id) {
		boolean removed = buildingService.deleteRoom(id);
		return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
	}

	public static class SetTemperatureRequest {
		public double requestedTemperature;
	}
}


