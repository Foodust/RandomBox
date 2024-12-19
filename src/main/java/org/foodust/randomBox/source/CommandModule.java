package org.foodust.randomBox.source;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.foodust.randomBox.BaseMessage;
import org.foodust.randomBox.RandomBox;

public class CommandModule {

    private final RandomBox randomBox;
    private final MessageModule messageModule;

    public CommandModule(RandomBox plugin) {
        this.randomBox = plugin;
        this.messageModule = new MessageModule(plugin);
    }

    public void commandOpen(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;
        if (args.length == 0) {
            messageModule.sendPlayer(player, BaseMessage.ERROR_ADD_NUMBER.getMessage());
            return;
        }


    }

    public void commandSet(CommandSender sender, String[] args) {

    }

    public void commandReload(CommandSender sender, String[] args) {

    }
}
