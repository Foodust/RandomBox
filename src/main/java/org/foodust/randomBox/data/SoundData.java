package org.foodust.randomBox.data;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SoundData {
    private String name;
    private float volume;
    private float pitch;
    
    @Setter
    @Getter
    private static SoundData soundConfig;

    public static void release() {
        soundConfig = null;
    }
}