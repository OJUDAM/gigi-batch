package com.ujo.gigiScheduler.common.constants;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TrainDirectInfo {
    NORMAL(0, "일반"),
    EXPRESS(1, "급행");

    private final int code;
    private final String message;
    TrainDirectInfo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    private static final Map<Integer, TrainDirectInfo> BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(TrainDirectInfo::code, Function.identity()));

    private static final Map<String, TrainDirectInfo> BY_MESSAGE =
            Stream.of(values()).collect(Collectors.toMap(TrainDirectInfo::message, Function.identity()));

    public static TrainDirectInfo valueOfCode(int code) {
        return BY_CODE.get(code);
    }
    public static TrainDirectInfo valueOfMessage(String message) {
        return BY_MESSAGE.get(message);
    }
}
