package org.foodust.randomBox.source;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.data.BoxData;
import org.foodust.randomBox.data.DisplayData;
import org.foodust.randomBox.data.TaskData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConfigModule {

    @Getter
    private final RandomBox plugin;

    public ConfigModule(RandomBox plugin) {
        this.plugin = plugin;
        this.release();
        this.initialize();
    }

    public FileConfiguration getConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            // 파일이 존재하지 않으면 기본 설정을 생성
            plugin.saveResource(fileName, false);
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig(File configFile) {
        if (!configFile.exists()) {
            // 파일이 존재하지 않으면 기본 설정을 생성
            plugin.saveResource(configFile.getName(), false);
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig(FileConfiguration config, String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    public void initialize() {
        getBoxConfig("badBox");
        getBoxConfig("goodBox");
        getBoxConfig("bigBadBox");
        getBoxConfig("bigGoodBox");
    }

    public void getBoxConfig(String fileName) {
        FileConfiguration config = getConfig(fileName + ".yml");
        List<BoxData.BoxClass> boxClasses = new ArrayList<>();
        for (String key : Objects.requireNonNull(config.getConfigurationSection("")).getKeys(false)) {
            String name = config.getString(key + ".name", "name");
            int chance = config.getInt(key + ".chance", 0);
            String command = config.getString(key + ".command", null);
            String function = config.getString(key + ".function", null);
            boxClasses.add(BoxData.BoxClass.builder()
                    .name(name)
                    .chance(chance)
                    .command(command)
                    .function(function).build());
        }
        BoxData.boxClass.put(fileName, boxClasses);
    }

    public void release() {
        BoxData.release();
        DisplayData.release();
        TaskData.release();
    }
}
