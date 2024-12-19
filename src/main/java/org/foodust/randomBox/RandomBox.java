package org.foodust.randomBox;

import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.foodust.randomBox.Command.CommandManager;
import org.foodust.randomBox.Event.EventManager;

import java.util.logging.Logger;

@Getter
public final class RandomBox extends JavaPlugin {
    private BukkitAudiences adventure;
    private Plugin plugin;
    private Logger log;

    public @NonNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.log = getLogger();
        this.plugin = this;
        this.adventure = BukkitAudiences.create(this);

        CommandManager commandManager = new CommandManager(this);
        // event init
        EventManager eventManager = new EventManager(getServer(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
