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

    BOX("상자"),

    // Command
    COMMAND_RANDOM_BOX("랜덤박스"),
    COMMAND_GIVE("받기"),
    COMMAND_SET("등록"),
    COMMAND_REMOVE("삭제"),
    COMMAND_OPEN("열기"),
    COMMAND_RELOAD("리로드"),

    INFO_RELOAD("리로드 되었습니다."),
    INFO_SET_RANDOM_BOX("랜덤 박스가 지정되었습니다."),

    INFO_COMMAND_DEFAULT(
            """
                    =========랜덤박스=======
                    /랜덤박스 등록  [번호]            들고 있는 아이템을 뽑기 상자로 지정
                    /랜덤박스 받기  [번호]            랜덤박스 받기
                    /랜덤박스 열기  [번호]            랜덤박스 설정용 인벤토리
                    /랜덤박스 삭제  [번호]            랜덤박스 삭제 
                    /랜덤박스 리로드                  랜덤박스 리로드
                    =========================
                    """),


    // error message
    ERROR("<dark_red>에러</dark_red>"),
    ERROR_WRONG_COMMAND("잘못 된 명령입니다."),
    ERROR_ADD_NUMBER("번호를 입력해주세요."),
    ERROR_ITEM_PLEASE("아이템을 들어주세요."),
    ERROR_WRONG_NUMBER("<red>번호가 존재하지 않습니다.</red>"),

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
