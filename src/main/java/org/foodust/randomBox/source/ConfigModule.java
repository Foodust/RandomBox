package org.foodust.randomBox.source;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
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

public class ConfigModule {

    private final MessageModule messageModule;
    private final ItemModule itemModule;
    @Getter
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
            try {
                FileConfiguration boxConfig = getConfig(file);
                if (boxConfig == null) {
                    messageModule.logInfo("Failed to load config for file: " + file.getName());
                    continue;
                }
                String index = file.getName().replace(".yml", "");
                Inventory inventory = makeInventory(size, index);
                HashMap<ItemStack, Double> chances = new HashMap<>();
                int itemIndex = 0;
                ConfigurationSection configurationSection = boxConfig.getConfigurationSection("items");
                if (configurationSection != null) {
                    for (String number : configurationSection.getKeys(false)) {
                        try {
                            String path = "items." + number + ".";
                            String base64 = boxConfig.getString(path + "base64");
                            if (base64 == null) {
                                messageModule.logInfo("Missing base64 for item: " + number);
                                continue;
                            }

                            double chance = boxConfig.getDouble(path + "chance");
                            ItemStack itemStack = itemSerialize.deserializeItem(base64);
                            if (itemStack != null) {
                                chances.put(itemStack, chance);
                                inventory.setItem(itemIndex, itemStack);
                                itemIndex++;
                            }
                        } catch (Exception e) {
                            messageModule.logInfo("Error processing item: " + number);
                        }
                    }
                }

                BoxInventory boxInventory = BoxInventory.builder()
                        .inventory(inventory)
                        .itemChance(chances)
                        .build();
                InventoryData.randomBoxInventory.put(index, boxInventory);

                String boxBase64 = boxConfig.getString("box.base64");
                ItemStack randomBoxItem = itemSerialize.deserializeItem(boxBase64);
                if (randomBoxItem != null) {
                    ItemData.randomBox.put(index, randomBoxItem);
                } else {
                    messageModule.logInfo("can't serialize random box item");
                }
            } catch (Exception e) {
                messageModule.logInfo("Error processing file: " + file.getName());
            }
        }
    }

    public Inventory makeInventory(int size, String name) {
        Inventory inventory = Bukkit.createInventory(null, size, name + "/" + BaseMessage.BOX.getMessage());
        size -= 1;

        inventory.setItem(size, itemModule.setCustomItem(Material.GREEN_STAINED_GLASS, "저장", 1, 1));
        inventory.setItem(size - 8, itemModule.setCustomItem(Material.RED_STAINED_GLASS, "취소", 1, 1));
        return inventory;
    }

    public void removeRandomBoxItem(String index) {
        FileConfiguration config = getConfig("box/" + index + ".yml");
        config.set("items", null);
        saveConfig(config, "box/" + index + ".yml");
    }

    public void saveRandomBoxItem(String index, String itemIndex, ItemStack itemStack) {
        String serialized = itemSerialize.serializeItem(itemStack);
        FileConfiguration config = getConfig("box/" + index + ".yml");
        config.set("items." + itemIndex + ".base64", serialized);
        config.set("items." + itemIndex + ".chance", 0.0);
        saveConfig(config, "box/" + index + ".yml");
    }

    public void setRandomBox(String index, ItemStack itemStack) {
        String serialized = itemSerialize.serializeItem(itemStack);
        FileConfiguration config = getConfig("box/" + index + ".yml");
        config.set("box.base64", serialized);
        saveConfig(config, "box/" + index + ".yml");
        initialize();
    }

    public void removeRandomBox(Player player, String index) {
        try {
            File file = new File(plugin.getDataFolder() + "/box", index + ".yml");
            if (file.exists()) {
                if (file.delete()) {
                    InventoryData.randomBoxInventory.remove(index);
                    ItemData.randomBox.remove(index);
                    messageModule.sendPlayerC(player, "랜덤박스 " + index + "가 성공적으로 삭제되었습니다.");
                } else {
                    messageModule.sendPlayerC(player, "랜덤박스 " + index + " 삭제 실패");
                }
            } else {
                messageModule.sendPlayerC(player, "해당 랜덤박스 파일이 존재하지 않습니다: " + index);
            }
        } catch (Exception ignore) {
        }
    }
}
