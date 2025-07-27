package org.foodust.randomBox.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.foodust.randomBox.BaseMessage;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class CommandSub implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add(BaseMessage.COMMAND_OPEN.getMessage());
            completions.add(BaseMessage.COMMAND_RELOAD.getMessage());
        }
        if (args.length == 2) {
            completions.add(BaseMessage.COMMAND_GOOD.getMessage());
            completions.add(BaseMessage.COMMAND_BAD.getMessage());
            completions.add(BaseMessage.COMMAND_BIG_BAD.getMessage());
            completions.add(BaseMessage.COMMAND_BIG_GOOD.getMessage());
        }
        Collections.sort(completions);
        return completions;
    }
}
