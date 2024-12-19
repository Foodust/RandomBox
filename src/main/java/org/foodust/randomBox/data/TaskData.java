package org.foodust.randomBox.data;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class TaskData {
    public static Boolean IS_RELEASING = false;
    public static List<BukkitTask> TASKS = new ArrayList<>();

    public static void release(){
        TASKS.forEach(bukkitTask -> {
            Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
        });
    }
}
