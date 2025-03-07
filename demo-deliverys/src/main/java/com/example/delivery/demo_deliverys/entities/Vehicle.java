package com.example.delivery.demo_deliverys.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private String plateNumber;
    private String status;
    private String type;
    private float fuelCapacity;
    private float carryingWeight;
    // Vehicle.java
    public float getRemainingCapacity() {
        return carryingWeight - items.stream()
                .map(Item::getWeight)
                .reduce(0f, Float::sum);
    }
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Item> items = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(float fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public float getCarryingWeight() {
        return carryingWeight;
    }

    public void setCarryingWeight(float carryingWeight) {
        this.carryingWeight = carryingWeight;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


}
