package org.foodust.randomBox.module;

import org.bukkit.entity.Player;
import org.foodust.randomBox.RandomBox;

public class FunctionModule {

    private final RandomBox plugin;

    public FunctionModule(RandomBox plugin) {
        this.plugin = plugin;
    }

    public void executeFunction(Player player, String number) {

        switch (number) {
            case "1" -> {
                
            }
            case "2" -> {

            }
        }

    }
}
