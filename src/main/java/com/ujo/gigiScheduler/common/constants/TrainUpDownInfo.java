package com.ujo.gigiScheduler.common.constants;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TrainUpDownInfo {
    UP_LINE(0, "상행"),
    DOWN_LINE(1, "하행");

    private final int code;
    private final String message;
    TrainUpDownInfo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    private static final Map<Integer, TrainUpDownInfo> BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(TrainUpDownInfo::code, Function.identity()));

    private static final Map<String, TrainUpDownInfo> BY_MESSAGE =
            Stream.of(values()).collect(Collectors.toMap(TrainUpDownInfo::message, Function.identity()));

    public static TrainUpDownInfo valueOfCode(int code) {
        return BY_CODE.get(code);
    }
    public static TrainUpDownInfo valueOfMessage(String message) {
        return BY_MESSAGE.get(message);
    }
}
