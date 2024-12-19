package org.foodust.randomBox.source;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.data.ItemData;
import org.foodust.randomBox.data.TaskData;

import java.io.File;
import java.io.IOException;

public class ConfigModule {
    public String errorName = "오류";
    public final String stageConfigName = "stage.yml";
    public final String playerConfig = "player.yml";
    public final String itemConfig = "item.yml";

    private final MessageModule messageModule;
    private final ItemModule itemModule = new ItemModule();
    private final RandomBox plugin;

    public ConfigModule(RandomBox plugin) {
        this.messageModule = new MessageModule(plugin);
        this.plugin = plugin;
    }

    public FileConfiguration getConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            // 파일이 존재하지 않으면 기본 설정을 생성
            plugin.saveResource(fileName, false);
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
        release();
        getConfigPlayer();
    }

    public void release() {
        TaskData.release();
        ItemData.release();
    }

    @SneakyThrows
    public void getConfigPlayer() {
    }

}
