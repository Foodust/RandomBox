package org.foodust.randomBox.source;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.foodust.randomBox.data.SoundData;

public class SoundModule {

    public void playSound(Player player) {
        SoundData soundConfig = SoundData.getSoundConfig();
        if (soundConfig == null) {
            return;
        }

        player.playSound(player.getLocation(), soundConfig.getName(), soundConfig.getVolume(), soundConfig.getPitch());
    }

    public void playSound(Player player, Location location) {
        SoundData soundConfig = SoundData.getSoundConfig();
        if (soundConfig == null) {
            return;
        }

        player.playSound(player.getLocation(), soundConfig.getName(), soundConfig.getVolume(), soundConfig.getPitch());
    }

    public void playCustomSound(Player player, String soundName, float volume, float pitch) {
        player.playSound(player.getLocation(), soundName, volume, pitch);
    }
}