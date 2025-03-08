package com.example.delivery.demo_deliverys.controller;

import com.example.delivery.demo_deliverys.entities.Item;
import com.example.delivery.demo_deliverys.entities.Vehicle;
import com.example.delivery.demo_deliverys.service.ItemService;
import com.example.delivery.demo_deliverys.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;
    private final ItemService itemService;

    public VehicleController(VehicleService vehicleService, ItemService itemService) {
        this.vehicleService = vehicleService;
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/get-vehicle/{plateNumber}")
    public ResponseEntity<Vehicle> getVehicleByPlateNumber(@PathVariable String plateNumber) {
        Vehicle vehicle = vehicleService.getByPlateNumber(plateNumber);
        if (vehicle != null) {
            return ResponseEntity.ok(vehicle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.findById(id);
        if (vehicle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        try {
            vehicleService.deleteVehicle(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // New endpoint to add an item to a vehicle
    // VehicleController.java
    @PostMapping("/{vehicleId}/add-item/{itemId}")
    public ResponseEntity<?> addItemToVehicle(
            @PathVariable Long vehicleId,
            @PathVariable Long itemId) {
        try {
            Vehicle vehicle = vehicleService.findById(vehicleId);
            Item item = itemService.getItemById(itemId);

            if (vehicle == null || item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Vehicle or Item not found!"));
            }

            // Calculate current load
            float currentLoad = vehicle.getItems().stream()
                    .map(Item::getWeight)
                    .reduce(0f, Float::sum);

            // Capacity check
            if (currentLoad + item.getWeight() > vehicle.getCarryingWeight()) {
                float remaining = vehicle.getCarryingWeight() - currentLoad;
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(
                                "error", "Cannot add item - capacity exceeded",
                                "remainingCapacity", remaining
                        ));
            }

            // Add item
            vehicle.getItems().add(item);
            item.setVehicle(vehicle);
            Vehicle updatedVehicle = vehicleService.updateVehicle(vehicle);

            return ResponseEntity.ok(updatedVehicle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error: " + e.getMessage()));
        }
    }
    @PostMapping("/create_vehicle")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        try {
            Vehicle createdVehicle = vehicleService.createVehicle(vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body((Vehicle) Map.of("error", "Error creating vehicle: " + e.getMessage()));
        }
    }
}