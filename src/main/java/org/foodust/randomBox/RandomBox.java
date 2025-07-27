package org.foodust.randomBox;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.foodust.randomBox.Command.CommandManager;
import org.foodust.randomBox.Event.EventManager;
import org.foodust.randomBox.module.BoxManager;
import org.foodust.randomBox.module.FunctionModule;
import org.foodust.randomBox.source.ConfigModule;

import java.util.logging.Logger;

@Getter
public final class RandomBox extends JavaPlugin {
    private Plugin plugin;
    private Logger log;

    private ConfigModule configModule;
    private BoxManager boxManager;
    private FunctionModule functionModule;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.log = getLogger();
        this.plugin = this;

        this.functionModule = new FunctionModule(this);
        this.boxManager = new BoxManager(this);
        this.configModule = new ConfigModule(this);

        CommandManager commandManager = new CommandManager(this);
        // event init
        EventManager eventManager = new EventManager(getServer(), this);
        new ConfigModule(this).initialize();
    }

    @Override
    public void onDisable() {
   }
}
