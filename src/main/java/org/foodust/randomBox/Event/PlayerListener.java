package org.foodust.randomBox.Event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.foodust.randomBox.RandomBox;

public class PlayerListener implements Listener {

    private final RandomBox plugin;

    public PlayerListener(RandomBox plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        plugin.getBoxManager().clearPlayerQueue(event.getPlayer());
    }
}
