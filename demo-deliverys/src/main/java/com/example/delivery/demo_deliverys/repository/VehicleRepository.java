package com.example.delivery.demo_deliverys.repository;

import com.example.delivery.demo_deliverys.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v WHERE v.status = 'maintenance'")
    List<Vehicle> getVehiclesStatus();

    List<Vehicle> findByCarryingWeightGreaterThan(float weight);
    Vehicle findByPlateNumber(String plateNumber);
    // VehicleRepository.java
    @Query("SELECT v FROM Vehicle v LEFT JOIN FETCH v.items WHERE v.id = :id")
    Vehicle findByIdWithItems(Long id);
}