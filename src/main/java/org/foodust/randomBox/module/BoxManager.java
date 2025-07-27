package org.foodust.randomBox.module;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.foodust.randomBox.BaseMessage;
import org.foodust.randomBox.RandomBox;
import org.foodust.randomBox.data.BoxData;
import org.foodust.randomBox.data.TaskData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BoxManager {
    // 플레이어별 박스 오픈 진행 상태
    public static final Map<UUID, Boolean> isBoxOpen = new ConcurrentHashMap<>();

    // 플레이어별 대기 큐
    public static final Map<UUID, Queue<BoxOpenRequest>> boxQueue = new ConcurrentHashMap<>();

    private final RandomBox plugin;

    public BoxManager(RandomBox plugin) {
        this.plugin = plugin;
    }

    // 박스 오픈 요청 데이터 클래스
    @Getter
    @AllArgsConstructor
    private static class BoxOpenRequest {
        private Player player;
        private BaseMessage message;
    }

    // 박스 오픈 요청 처리
    public void openBox(Player player, BaseMessage message) {
        UUID playerId = player.getUniqueId();

        // 큐가 없으면 생성
        boxQueue.putIfAbsent(playerId, new ConcurrentLinkedQueue<>());

        // 현재 박스 오픈 중인지 확인
        if (isBoxOpen.getOrDefault(playerId, false)) {
            // 박스 오픈 중이면 큐에 추가
            boxQueue.get(playerId).offer(new BoxOpenRequest(player, message));
            return;
        }

        // 박스 오픈 중이 아니면 바로 실행
        processBoxOpen(player, message);
    }

    // 실제 박스 오픈 처리
    private void processBoxOpen(Player player, BaseMessage message) {
        UUID playerId = player.getUniqueId();

        // 박스 오픈 상태로 변경
        isBoxOpen.put(playerId, true);

        List<BoxData.BoxClass> boxClasses = BoxData.boxClass.get(message.getMessage());
        BoxData.BoxClass randomBoxClass = getRandomBoxClass(boxClasses);

        ActiveModel box = createBox(player);

        // 애니메이션 재생 (2초 회전 + 1초 정지 = 총 3초)
        box.getAnimationHandler().playAnimation("result1", 0, 0, 1, true);

        // 3초 후 박스 오픈 완료 처리
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (box.getModeledEntity().getBase().getOriginal() instanceof ItemDisplay item) {
                item.remove();
            }
            // 박스 오픈 완료 처리
            completeBoxOpen(player, randomBoxClass);
            player.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, player.getLocation(), 20, 1, 1, 1);
            player.getWorld().playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1f);

            // 다음 박스 처리
            processNextBox(playerId);
        }, 60L);// 3초 = 60 ticks
        TaskData.TASKS.add(bukkitTask);
    }

    // 박스 오픈 완료 처리
    private void completeBoxOpen(Player player, BoxData.BoxClass boxClass) {
        // 보상 지급 등의 처리
        if (boxClass.getCommand() != null && !boxClass.getCommand().isEmpty()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), boxClass.getCommand().replace("%playerName%", player.getName()));
        }
        if (boxClass.getFunction() != null) {
            plugin.getFunctionModule().executeFunction(player, boxClass.getFunction());
        }
        player.sendMessage(boxClass.getName() + "을(를) 획득했습니다!");
    }

    // 큐에서 다음 박스 처리
    private void processNextBox(UUID playerId) {
        // 박스 오픈 상태 해제
        isBoxOpen.put(playerId, false);

        // 큐에서 다음 요청 가져오기
        Queue<BoxOpenRequest> queue = boxQueue.get(playerId);
        if (queue != null && !queue.isEmpty()) {
            BoxOpenRequest nextRequest = queue.poll();

            // 플레이어가 온라인인지 확인
            if (nextRequest.getPlayer().isOnline()) {
                // 약간의 딜레이 후 다음 박스 오픈
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    processBoxOpen(nextRequest.getPlayer(), nextRequest.getMessage());
                }, 10L); // 0.5초 딜레이
            } else {
                // 플레이어가 오프라인이면 다음 박스 처리
                processNextBox(playerId);
            }
        }
    }

    public BoxData.BoxClass getRandomBoxClass(List<BoxData.BoxClass> boxClasses) {
        if (boxClasses == null || boxClasses.isEmpty()) {
            return null;
        }
        // 전체 chance 합계 계산
        int totalChance = boxClasses.stream().mapToInt(BoxData.BoxClass::getChance).sum();

        // 0부터 totalChance 사이의 랜덤 값 생성
        Random random = new Random();
        int randomValue = random.nextInt(totalChance);

        // 누적 chance로 선택할 BoxClass 찾기
        int currentSum = 0;
        for (BoxData.BoxClass boxClass : boxClasses) {
            currentSum += boxClass.getChance();
            if (randomValue < currentSum) {
                return boxClass;
            }
        }
        // 혹시 모를 경우를 대비해 마지막 요소 반환
        return boxClasses.getLast();
    }

    public ActiveModel createBox(Player player) {
        ItemDisplay animateDisplay = player.getWorld().spawn(player.getLocation(), ItemDisplay.class);
        ModeledEntity model = ModelEngineAPI.createModeledEntity(animateDisplay);
        ActiveModel activeModel = ModelEngineAPI.createActiveModel("random_box");
        model.addModel(activeModel, true);
        return activeModel;
    }

    // 플레이어 퇴장 시 큐 정리
    public void clearPlayerQueue(Player player) {
        UUID playerId = player.getUniqueId();
        isBoxOpen.remove(playerId);
        boxQueue.remove(playerId);
    }
}