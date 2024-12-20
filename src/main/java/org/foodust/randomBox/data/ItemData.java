package org.foodust.randomBox.data;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemData {
    public static HashMap<String, ItemStack> randomBox = new HashMap<>();
    public static List<Entity> ENTITIES = new ArrayList<>();

    public static void release() {
        randomBox.clear();
        ENTITIES.forEach(entity -> {
            if (entity != null) {
                entity.remove();
            }
        });
        ENTITIES.clear();
    }
}
