package com.example.delivery.demo_deliverys.service;

import com.example.delivery.demo_deliverys.entities.Item;


import java.util.List;
public interface ItemService {
    Item createItem (Item item);
    Item findItemById (Long id);
    List<Item> findAllItem();
    void deleteItem (Long id);

    List<Item> getAllItems();

    Item getItemById(Long itemId);
}
