package org.foodust.randomBox.source;


import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.foodust.randomBox.BaseMessage;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.data.ItemData;

public class CommandModule {

    private final RandomBox randomBox;
    private final MessageModule messageModule;
    private final ConfigModule configModule;
    ;

    public CommandModule(RandomBox plugin) {
        this.randomBox = plugin;
        this.messageModule = new MessageModule(plugin);
        this.configModule = new ConfigModule(plugin);
    }

    public void commandOpen(CommandSender sender, String[] data) {
        if (!(sender instanceof Player player)) return;
        if (data.length <= 1) {
            messageModule.sendPlayer(player, BaseMessage.ERROR_ADD_NUMBER.getMessage());
            return;
        }
    }

    public void commandGive(CommandSender sender, String[] data) {
        if (!(sender instanceof Player player)) return;

        String index = data[1];
        ItemStack itemStack = ItemData.randomBox.get(index);
        if (itemStack == null) {
            messageModule.sendPlayer(player, BaseMessage.ERROR_WRONG_NUMBER.getMessage());
            return;
        }
        player.getInventory().addItem(itemStack);
    }

    public void commandSet(CommandSender sender, String[] data) {
        if (!(sender instanceof Player player)) return;
        if (data.length <= 1) {
            messageModule.sendPlayer(player, BaseMessage.ERROR_ADD_NUMBER.getMessage());
            return;
        }
        String index = data[1];

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR) {
            messageModule.sendPlayer(player, BaseMessage.ERROR_ADD_NUMBER.getMessage());
            return;
        }
        configModule.setRandomBox(index, itemInMainHand);
        messageModule.sendPlayer(player, index + " " + BaseMessage.INFO_SET_RANDOM_BOX.getMessage());
    }

    public void commandReload(CommandSender sender, String[] data) {
        configModule.initialize();
        messageModule.sendPlayer(sender, BaseMessage.INFO_RELOAD.getMessage());
    }
}
