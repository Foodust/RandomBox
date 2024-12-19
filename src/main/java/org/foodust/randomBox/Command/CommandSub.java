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
    Set<String> mainSub = EnumSet.range(BaseMessage.COMMAND_SET, BaseMessage.COMMAND_RELOAD).stream().map(BaseMessage::getMessage).collect(Collectors.toSet());

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], mainSub, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
