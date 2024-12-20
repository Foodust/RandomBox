package org.foodust.randomBox.source;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.foodust.randomBox.BaseMessage;
import org.foodust.randomBox.RandomBox;

import java.util.Objects;

public class InventoryModule {

    private final RandomBox plugin;
    private final ConfigModule configModule;

    public InventoryModule(RandomBox plugin) {
        this.plugin = plugin;
        this.configModule = new ConfigModule(plugin);
    }


    public void openMakeBoxInventory(Player player, Integer number) {

    }

    public void clickMakeBoxInventory(InventoryClickEvent event) {
        String[] split = event.getView().getTitle().split("/");
        if (split.length <= 2) return;
        String index = split[0];
        if (!Objects.equals(split[1], BaseMessage.BOX.getMessage())) return;
        if (event.getSlot() == 45) {
            event.setCancelled(true);
            return;
        }
        if (event.getSlot() == 54) {
            event.setCancelled(true);
            Inventory clickedInventory = event.getClickedInventory();
            if (clickedInventory == null) return;
            for (int i = 0; i < clickedInventory.getSize() - 10; i++) {
                clickedInventory.getItem();
            }
        }

    }

    public void interactRandomBox(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
    }

}
