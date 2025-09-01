package org.foodust.randomBox.source;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.foodust.mailBoxPlugin.api.MailBoxAPI;
import org.foodust.randomBox.BaseMessage;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.data.InventoryData;
import org.foodust.randomBox.data.ItemData;
import org.foodust.randomBox.data.box.BoxInventory;

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
        if (data.length < 2) {
            messageModule.sendPlayerC(player, BaseMessage.ERROR_ADD_NUMBER.getMessage());
            return;
        }
        String index = data[1];
        BoxInventory boxInventory = InventoryData.randomBoxInventory.get(index);
        if (boxInventory == null) {
            messageModule.sendPlayerC(player, BaseMessage.INFO_NO_INVENTORY_MAKE_INVENTORY.getMessage());
            player.openInventory(configModule.makeInventory(54, index));
        } else {
            player.openInventory(boxInventory.getInventory());
        }
    }

    public void commandGive(CommandSender sender, String[] data) {
        if (!(sender instanceof Player player)) return;
        if (data.length < 3) {
            messageModule.sendPlayerC(player, BaseMessage.ERROR_ADD_NUMBER.getMessage());
            return;
        }
        String index = data[1];
        String count = data[2];
        ItemStack itemStack = ItemData.randomBox.get(index);
        if (itemStack == null) {
            messageModule.sendPlayerC(player, BaseMessage.ERROR_WRONG_NUMBER.getMessage());
            return;
        }
        for (int i = 0; i < Integer.parseInt(count); i++) {
            MailBoxAPI.getInstance().sendItem(player, itemStack);
//            player.getInventory().addItem(itemStack);
        }
    }

    public void commandSet(CommandSender sender, String[] data) {
        if (!(sender instanceof Player player)) return;
        if (data.length < 2) {
            messageModule.sendPlayerC(player, BaseMessage.ERROR_ADD_NUMBER.getMessage());
            return;
        }
        String index = data[1];
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR) {
            messageModule.sendPlayerC(player, BaseMessage.ERROR_ADD_NUMBER.getMessage());
            return;
        }
        configModule.setRandomBox(index, itemInMainHand);
        messageModule.sendPlayerC(player, index + " " + BaseMessage.INFO_SET_RANDOM_BOX.getMessage());
    }

    public void commandReload(CommandSender sender, String[] data) {
        configModule.initialize();
        messageModule.sendPlayerC(sender, BaseMessage.INFO_RELOAD.getMessage());
    }

    public void commandRemove(CommandSender sender, String[] data) {
        if (!(sender instanceof Player player)) return;
        if (data.length < 2) {
            messageModule.sendPlayerC(player, BaseMessage.ERROR_ADD_NUMBER.getMessage());
            return;
        }
        configModule.removeRandomBox(player, data[1]);
        configModule.initialize();
    }
}
