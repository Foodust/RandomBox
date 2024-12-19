package org.foodust.randomBox;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

// 기본 메세지
@Getter
public enum BaseMessage {

    PREFIX("[RandomBox]"),
    PREFIX_C("<gradient:yellow:blue><bold>[RandomBox]</gradient></bold> "),

    // Command
    COMMAND_RANDOM_BOX("랜덤박스"),
    COMMAND_SET("등록"),
    COMMAND_OPEN("열기"),
    COMMAND_RELOAD("리로드"),

    INFO_COMMAND_DEFAULT(
            """
                    =========랜덤박스=======
                    /랜덤박스 등록                    들고 있는 아이템을 뽑기 상자로 지정
                    /랜덤박스 열기                    랜덤박스 설정용 인벤토리 
                    /랜덤박스 리로드                  랜덤박스 리로드
                    =========================
                    """),


    // error message
    ERROR("<dark_red>에러</dark_red>"),
    ERROR_WRONG_COMMAND("잘못 된 명령입니다."),

    ;
    private final String message;

    BaseMessage(String message) {
        this.message = message;
    }

    private static final Map<String, BaseMessage> commandInfo = new HashMap<>();

    static {
        for (BaseMessage baseMessage : EnumSet.range(COMMAND_RANDOM_BOX, COMMAND_RELOAD)) {
            commandInfo.put(baseMessage.message, baseMessage);
        }
    }

    public static BaseMessage getByMessage(String message) {
        return commandInfo.getOrDefault(message, ERROR);
    }
}
