package org.foodust.randomBox.data.box;

import lombok.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxInventory {



    private Inventory inventory;

    private HashMap<ItemStack, Double> itemChance = new HashMap<>();
}
