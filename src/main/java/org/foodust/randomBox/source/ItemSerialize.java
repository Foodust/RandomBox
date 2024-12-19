package org.foodust.randomBox.source;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ItemSerialize {

    /**
     * ItemStack을 Base64 문자열로 변환
     */
    public String serializeItem(ItemStack item) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // ItemStack 직렬화
            dataOutput.writeObject(item);

            // 스트림 닫기
            dataOutput.close();

            // Base64로 인코딩
            return Base64Coder.encodeLines(outputStream.toByteArray());

        } catch (Exception e) {
            throw new IllegalStateException("아이템을 직렬화하는 중 오류가 발생했습니다", e);
        }
    }

    /**
     * Base64 문자열을 ItemStack으로 변환
     */
    public ItemStack deserializeItem(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            // ItemStack 역직렬화
            ItemStack item = (ItemStack) dataInput.readObject();

            // 스트림 닫기
            dataInput.close();

            return item;

        } catch (ClassNotFoundException | IOException e) {
            throw new IllegalStateException("아이템을 역직렬화하는 중 오류가 발생했습니다", e);
        }
    }

    /**
     * 여러 ItemStack을 Base64 문자열로 변환
     */
    public String serializeItems(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // 배열 길이 저장
            dataOutput.writeInt(items.length);

            // 각 아이템 직렬화
            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            // 스트림 닫기
            dataOutput.close();

            // Base64로 인코딩
            return Base64Coder.encodeLines(outputStream.toByteArray());

        } catch (Exception e) {
            throw new IllegalStateException("아이템 배열을 직렬화하는 중 오류가 발생했습니다", e);
        }
    }

    /**
     * Base64 문자열을 ItemStack 배열로 변환
     */
    public ItemStack[] deserializeItems(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            // 배열 길이 읽기
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // 각 아이템 역직렬화
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            // 스트림 닫기
            dataInput.close();

            return items;

        } catch (ClassNotFoundException | IOException e) {
            throw new IllegalStateException("아이템 배열을 역직렬화하는 중 오류가 발생했습니다", e);
        }
    }
}
