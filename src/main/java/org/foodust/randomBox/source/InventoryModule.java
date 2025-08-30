package org.foodust.randomBox.source;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.foodust.randomBox.BaseMessage;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.data.InventoryData;
import org.foodust.randomBox.data.ItemData;
import org.foodust.randomBox.data.box.BoxInventory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InventoryModule {

    private final RandomBox plugin;
    private final MessageModule messageModule;
    private final ConfigModule configModule;

    public InventoryModule(RandomBox plugin) {
        this.plugin = plugin;
        this.configModule = new ConfigModule(plugin);
        this.messageModule = new MessageModule(plugin);
    }

    public void clickMakeBoxInventory(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        String[] split = event.getView().getTitle().split("/");
        if (split.length < 2) return;
        String index = split[0];
        if (!Objects.equals(split[1], BaseMessage.BOX.getMessage())) return;
        if (event.getSlot() == 45) {
            event.setCancelled(true);
            player.closeInventory();
            return;
        }
        if (event.getSlot() == 53) {
            event.setCancelled(true);
            Inventory clickedInventory = event.getClickedInventory();
            if (clickedInventory == null) return;
            configModule.removeRandomBoxItem(index);
            for (int i = 0; i < clickedInventory.getSize() - 9; i++) {
                ItemStack item = clickedInventory.getItem(i);
                if (item == null) continue;
                configModule.saveRandomBoxItem(index, String.valueOf(i), item);
            }
            player.closeInventory();
            messageModule.sendPlayerC(player, index + BaseMessage.INFO_SAVE_BOX.getMessage());
            messageModule.sendPlayerC(player, BaseMessage.INFO_CHANGE_YML.getMessage());
            configModule.initialize();
        }
    }

    public void interactRandomBox(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType().isAir()) return;
        
        for (Map.Entry<String, ItemStack> stringItemStackEntry : ItemData.randomBox.entrySet()) {
            ItemStack item = stringItemStackEntry.getValue();
            if (item == null) continue;
            
            // Check if both items are the same type and have matching meta
            if (item.isSimilar(itemInMainHand)) {
                BoxInventory boxInventory = InventoryData.randomBoxInventory.get(stringItemStackEntry.getKey());
                if (boxInventory == null) continue;
                
                Optional.ofNullable(boxInventory.getRandomItem()).ifPresent(randomItem -> {
                    if (itemInMainHand.getAmount() > 1) {
                        itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
                    } else {
                        player.getInventory().removeItem(itemInMainHand);
                    }
                    player.getInventory().addItem(randomItem);
                });
                break;
            }
        }
    }

}
