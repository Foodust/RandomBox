package org.foodust.randomBox.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.source.InventoryModule;

public class EventFromPlayer implements Listener {

    private final InventoryModule inventoryModule;

    public EventFromPlayer(RandomBox plugin) {
        this.inventoryModule = new InventoryModule(plugin);
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        inventoryModule.interactRandomBox(event);
    }
}
