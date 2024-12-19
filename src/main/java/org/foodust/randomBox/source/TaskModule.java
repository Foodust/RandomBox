package org.foodust.randomBox.source;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.data.TaskData;

public class TaskModule {
    private final RandomBox plugin;

    public TaskModule(RandomBox plugin) {
        this.plugin = plugin;
    }

    public BukkitTask runBukkitTask(Runnable task) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTask(plugin, task);
        TaskData.TASKS.add(bukkitTask);
        return bukkitTask;
    }

    public BukkitTask runBukkitTaskLater(Runnable task, Long delay) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater(plugin, task, delay);
        TaskData.TASKS.add(bukkitTask);
        return bukkitTask;
    }

    public BukkitTask runBukkitTaskLater(Runnable task, double delay) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater(plugin, task, (long) delay);
        TaskData.TASKS.add(bukkitTask);
        return bukkitTask;
    }

    public BukkitTask runBukkitTaskLater(Runnable task, float delay) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater(plugin, task, (long) delay);
        TaskData.TASKS.add(bukkitTask);
        return bukkitTask;
    }

    public BukkitTask runBukkitTaskTimer(Runnable task, Long delay, Long tick) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, task, delay, tick);
        TaskData.TASKS.add(bukkitTask);
        return bukkitTask;
    }

    public void cancelBukkitTask(BukkitTask bukkitTask) {
        if (bukkitTask != null)
            Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
    }
}
