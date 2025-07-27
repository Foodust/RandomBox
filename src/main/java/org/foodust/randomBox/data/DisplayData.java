package org.foodust.randomBox.data;

import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class DisplayData {
    public static List<Entity> entities = new ArrayList<>();

    public static void release() {
        entities.forEach(Entity::remove);
    }
}
