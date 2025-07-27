package org.foodust.randomBox.source;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.foodust.randomBox.BaseMessage;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.module.BoxManager;

public class CommandModule {

    private final RandomBox randomBox;
    private final ConfigModule configModule;
    private final BoxManager boxManager;

    public CommandModule(RandomBox plugin) {
        this.randomBox = plugin;
        this.configModule = plugin.getConfigModule();
        this.boxManager = plugin.getBoxManager();
    }

    public void commandOpen(CommandSender sender, String[] data) {
        if (!(sender instanceof Player player)) return;
        if (data.length < 1) return;

        String command = data[1];
        BaseMessage byMessage = BaseMessage.getByMessage(command);
        switch (byMessage) {
            case COMMAND_GOOD -> boxManager.openBox(player,BaseMessage.COMMAND_GOOD);
            case COMMAND_BAD -> boxManager.openBox(player,BaseMessage.COMMAND_BAD);
            case COMMAND_BIG_GOOD -> boxManager.openBox(player,BaseMessage.COMMAND_BIG_GOOD);
            case COMMAND_BIG_BAD -> boxManager.openBox(player,BaseMessage.COMMAND_BIG_BAD);
        }
    }

    public void commandReload(CommandSender sender, String[] data) {
        configModule.initialize();
    }
}
