package com.example.delivery.demo_deliverys.service;

import com.example.delivery.demo_deliverys.entities.Vehicle;
import com.example.delivery.demo_deliverys.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(Vehicle vehicle) {
        Vehicle existingVehicle = vehicleRepository.findById(vehicle.getId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        existingVehicle.setName(vehicle.getName());
        existingVehicle.setPlateNumber(vehicle.getPlateNumber());
        existingVehicle.setStatus(vehicle.getStatus());
        existingVehicle.setType(vehicle.getType());
        existingVehicle.setFuelCapacity(vehicle.getFuelCapacity());
        existingVehicle.setCarryingWeight(vehicle.getCarryingWeight());
        existingVehicle.setItems(vehicle.getItems());

        return vehicleRepository.save(existingVehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    @Override
    public Vehicle getByPlateNumber(String plateNumber) {
        return vehicleRepository.findByPlateNumber(plateNumber);
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new RuntimeException("Vehicle not found with ID: " + id);
        }
        vehicleRepository.deleteById(id);
    }
    // VehicleServiceImpl.java
    @Override
    public Vehicle findByIdWithItems(Long id) {
        return vehicleRepository.findByIdWithItems(id);
    }

}