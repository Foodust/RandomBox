package org.foodust.randomBox.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.foodust.randomBox.BaseMessage;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.source.MessageModule;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

// 커맨드 를 할 수 있게 해줍니다!
public class CommandManager implements CommandExecutor {
    private final MessageModule messageModule;


    public CommandManager(RandomBox plugin) {
        this.messageModule = new MessageModule(plugin);
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_RANDOM_BOX.getMessage())).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_RANDOM_BOX.getMessage())).setTabCompleter(new CommandSub());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] data) {
        if (data.length == 0) {
            sender.sendMessage(BaseMessage.INFO_COMMAND_DEFAULT.getMessage());
        } else {
            BaseMessage byBaseMessage = BaseMessage.getByMessage(data[0]);
            switch (byBaseMessage) {
                default -> messageModule.sendPlayer(sender, BaseMessage.ERROR_WRONG_COMMAND.getMessage());
            }
        }
        return true;
    }
}
