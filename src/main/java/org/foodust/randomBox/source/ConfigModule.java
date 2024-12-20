package org.foodust.randomBox.source;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.foodust.randomBox.BaseMessage;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.data.InventoryData;
import org.foodust.randomBox.data.ItemData;
import org.foodust.randomBox.data.TaskData;
import org.foodust.randomBox.data.box.BoxInventory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class ConfigModule {

    private final MessageModule messageModule;
    private final ItemModule itemModule;
    private final ItemSerialize itemSerialize;
    private final RandomBox plugin;

    public ConfigModule(RandomBox plugin) {
        this.messageModule = new MessageModule(plugin);
        this.itemModule = new ItemModule();
        this.itemSerialize = new ItemSerialize();
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
        release();

        File boxDirectory = new File(plugin.getDataFolder(), "box");
        // box 디렉토리가 없다면 생성
        if (!boxDirectory.exists()) {
            boolean mkdirs = boxDirectory.mkdirs();
        }
        File[] files = boxDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".yml"));

        if (files == null) {
            messageModule.logInfoNoPrefix("파일이 없음");
            return;
        }
        getRandomBox(files);
    }

    public void release() {
        TaskData.release();
        ItemData.release();
    }

    public void getRandomBox(File[] files) {
        int size = 54;

        for (File file : files) {
            FileConfiguration boxConfig = getConfig(file);

            String index = boxConfig.getName().replace(".yml", "");

            Inventory inventory = makeInventory(size, index);

            HashMap<ItemStack, Double> chances = new HashMap<>();

            for (String items : Objects.requireNonNull(boxConfig.getConfigurationSection("items")).getKeys(false)) {
                String path = "items." + items + ".";
                String base64 = boxConfig.getString(path + "base64");
                double chance = boxConfig.getDouble(path + "change");

                ItemStack itemStack = itemSerialize.deserializeItem(base64);
                chances.put(itemStack, chance);
            }
            BoxInventory boxInventory = BoxInventory.builder()
                    .inventory(inventory)
                    .itemChance(chances)
                    .build();
            InventoryData.randomBoxInventory.put(index, boxInventory);

            String boxBase64 = boxConfig.getString("box.base64");
            ItemStack randomBoxItem = itemSerialize.deserializeItem(boxBase64);
            ItemData.randomBox.put(index, randomBoxItem);
        }
    }

    public Inventory makeInventory(int size, String name) {
        Inventory inventory = Bukkit.createInventory(null, size, name +"/" + BaseMessage.BOX.getMessage());
        inventory.setItem(size, itemModule.setCustomItem(Material.GREEN_STAINED_GLASS, "저장", 1, 1));
        inventory.setItem(size - 9, itemModule.setCustomItem(Material.RED_STAINED_GLASS, "취소", 1, 1));
        return inventory;
    }

    public void setRandomBox(String index, ItemStack itemStack) {
        String serialized = itemSerialize.serializeItem(itemStack);
        FileConfiguration config = getConfig("box/" + index + ".yml");
        config.set("box.base64." + serialized, true);
        saveConfig(config, "box/" + index + ".yml");

        initialize();
    }
}
