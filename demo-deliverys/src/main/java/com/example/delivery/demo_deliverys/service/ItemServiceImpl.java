package com.example.delivery.demo_deliverys.service;

import com.example.delivery.demo_deliverys.entities.Item;
import com.example.delivery.demo_deliverys.entities.Vehicle;
import com.example.delivery.demo_deliverys.repository.ItemRepository;
import com.example.delivery.demo_deliverys.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item findItemById(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.orElse(null); // Handle not found case appropriately
    }

    @Override
    public List<Item> findAllItem() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll(); // Return the list of items
    }

    @Override
    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item not found with ID: " + id);
        }
        try {
            itemRepository.deleteById(id);
        } catch (Exception e) {
            // Log the actual error
            e.printStackTrace();
            throw new RuntimeException("Failed to delete item: " + e.getMessage());
        }
    }

    @Override
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }
}