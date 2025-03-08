package com.example.delivery.demo_deliverys.repository;

import com.example.delivery.demo_deliverys.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
