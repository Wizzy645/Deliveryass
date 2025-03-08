package com.example.delivery.demo_deliverys.service;

import com.example.delivery.demo_deliverys.entities.Vehicle;

import java.util.List;

public interface VehicleService  {
    Vehicle createVehicle(Vehicle vehicle);

    // Remove this conflicting declaration
    // Vehicle updateVehicle(Long id, Vehicle vehicle);

    Vehicle getByPlateNumber(String plateNumber);
    void deleteVehicle(Long id);

    // Keep this one
    Vehicle updateVehicle(Vehicle vehicle);

    List<Vehicle> getAllVehicles();
    Vehicle findById(Long id);

    // VehicleServiceImpl.java
    Vehicle findByIdWithItems(Long id);
}