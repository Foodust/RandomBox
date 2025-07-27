package org.foodust.randomBox.data;


import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

@Getter
@Builder
public class BoxData {

    public static HashMap<String, List<BoxClass>> boxClass = new HashMap<>();
    public static HashMap<UUID, Boolean> isBoxOpen = new HashMap<>();

    public static void release(){
        boxClass.clear();
    }

    @Getter
    @Builder
    public static class BoxClass {
        private String name;
        private Integer chance;
        private String command;
        private String function;
    }

}
