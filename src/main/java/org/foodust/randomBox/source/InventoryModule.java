package org.foodust.randomBox.source;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.foodust.randomBox.RandomBox;

public class InventoryModule {

    private final RandomBox plugin;

    public InventoryModule(RandomBox plugin) {
        this.plugin = plugin;
    }


    public void openMakeBoxInventory(Player player, Integer number) {

    }

    public void clickMakeBoxInventory(InventoryClickEvent event) {

    }

    public void interactRandomBox(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
    }

}
