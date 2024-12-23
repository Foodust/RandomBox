package org.foodust.randomBox.data.box;

import lombok.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxInventory {

    private Inventory inventory;

    @Builder.Default
    private HashMap<ItemStack, Double> itemChance = new HashMap<>();

    public ItemStack getRandomItem() {
        if (itemChance.isEmpty()) {
            return null;
        }

        // 1. 전체 확률의 합 계산
        double totalProbability = itemChance.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        // 2. 0부터 전체 확률 사이의 랜덤 값 생성
        double randomValue = Math.random() * totalProbability;

        // 3. 누적 확률을 계산하며 해당하는 아이템 선택
        double cumulativeProbability = 0.0;
        for (Map.Entry<ItemStack, Double> entry : itemChance.entrySet()) {
            cumulativeProbability += entry.getValue();
            if (randomValue <= cumulativeProbability) {
                return entry.getKey().clone(); // 원본 아이템 보존을 위해 clone 사용
            }
        }

        // 혹시 모를 예외 상황을 위한 처리
        return itemChance.keySet().iterator().next().clone();
    }
}
