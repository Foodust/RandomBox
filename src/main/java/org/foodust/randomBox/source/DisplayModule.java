package org.foodust.randomBox.source;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.foodust.randomBox.data.ItemData;
import org.joml.Vector3d;

public class DisplayModule {

    public ItemDisplay makeItemDisplay(Player player, Location location, Material material, Double size) {
        ItemDisplay itemDisplay = player.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setItemStack(new ItemStack(material));
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(size);
        itemDisplay.setTransformation(transformation);
        ItemData.ENTITIES.add(itemDisplay);
        return itemDisplay;
    }

    public ItemDisplay makeItemDisplay(Player player, Location location, ItemStack itemStack, Double size, Display.Billboard billboard) {
        ItemDisplay itemDisplay = player.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setItemStack(itemStack);
        itemDisplay.setBillboard(billboard);
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(size);
        itemDisplay.setTransformation(transformation);
        ItemData.ENTITIES.add(itemDisplay);
        return itemDisplay;
    }

    public ItemDisplay makeItemDisplay(Player player, Location location, ItemStack itemStack, Double size) {
        ItemDisplay itemDisplay = player.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setItemStack(itemStack);
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(size);
        itemDisplay.setTransformation(transformation);
        ItemData.ENTITIES.add(itemDisplay);
        return itemDisplay;
    }

    public ItemDisplay makeItemDisplay(Player player, Location location, ItemStack itemStack, float x, float y, float z) {
        ItemDisplay itemDisplay = player.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setItemStack(itemStack);
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(x, y, z);
        itemDisplay.setTransformation(transformation);
        ItemData.ENTITIES.add(itemDisplay);
        return itemDisplay;
    }

    public ItemDisplay makeItemDisplay(Player player, Location location, ItemStack itemStack, Vector3d size) {
        ItemDisplay itemDisplay = player.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setItemStack(itemStack);
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(size);
        itemDisplay.setTransformation(transformation);
        ItemData.ENTITIES.add(itemDisplay);
        return itemDisplay;
    }

    public ItemDisplay makeItemDisplay(Player player, Location location, ItemStack itemStack, Display.Billboard billboard, Vector3d size) {
        ItemDisplay itemDisplay = player.getWorld().spawn(location, ItemDisplay.class);
        itemDisplay.setItemStack(itemStack);
        itemDisplay.setBillboard(billboard);
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(size);
        itemDisplay.setTransformation(transformation);
        ItemData.ENTITIES.add(itemDisplay);
        return itemDisplay;
    }

    public BlockDisplay makeBlockDisplay(Player player, Location location, Material material, Double size) {
        BlockDisplay blockDisplay = player.getWorld().spawn(location, BlockDisplay.class);
        blockDisplay.setBlock(material.createBlockData());
        Transformation transformation = blockDisplay.getTransformation();
        transformation.getScale().set(size);
        blockDisplay.setTransformation(transformation);
        ItemData.ENTITIES.add(blockDisplay);
        return blockDisplay;
    }

    public TextDisplay makeTextDisplay(Player player, Location location, String text, Double size) {
        TextDisplay textDisplay = player.getWorld().spawn(location, TextDisplay.class);
        textDisplay.setText(text);
        Transformation transformation = textDisplay.getTransformation();
        transformation.getScale().set(size);
        textDisplay.setTransformation(transformation);
        ItemData.ENTITIES.add(textDisplay);
        return textDisplay;
    }
}
