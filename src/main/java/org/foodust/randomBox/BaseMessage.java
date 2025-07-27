package org.foodust.randomBox;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

// 기본 메세지
@Getter
public enum BaseMessage {

    PREFIX("[RandomBox]"),
    PREFIX_C("<gradient:yellow:blue><bold>[RandomBox]</bold></gradient> "),

    BOX("상자"),

    // Command
    COMMAND_RANDOM_BOX("랜덤박스"),
    COMMAND_GIVE("받기"),
    COMMAND_SET("등록"),
    COMMAND_REMOVE("삭제"),
    COMMAND_OPEN("열기"),

    COMMAND_BAD("badBox"),
    COMMAND_GOOD("goodBox"),
    COMMAND_BIG_GOOD("bigGoodBox"),
    COMMAND_BIG_BAD("bigBadBox"),

    COMMAND_RELOAD("리로드"),

    INFO_RELOAD("리로드 되었습니다."),

    // error message
    ERROR("에러"),
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
