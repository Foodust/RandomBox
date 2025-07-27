package org.foodust.randomBox.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.foodust.randomBox.BaseMessage;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.source.CommandModule;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

// 커맨드 를 할 수 있게 해줍니다!
public class CommandManager implements CommandExecutor {
    private final CommandModule commandModule;

    public CommandManager(RandomBox plugin) {
        this.commandModule = new CommandModule(plugin);
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_RANDOM_BOX.getMessage())).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_RANDOM_BOX.getMessage())).setTabCompleter(new CommandSub());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] data) {
        if (!label.equalsIgnoreCase(BaseMessage.COMMAND_RANDOM_BOX.getMessage()))
            return false;
        BaseMessage byBaseMessage = BaseMessage.getByMessage(data[0]);
        switch (byBaseMessage) {
            case COMMAND_OPEN -> commandModule.commandOpen(sender, data);
            case COMMAND_RELOAD -> commandModule.commandReload(sender, data);
        }
        return true;
    }
}
