package com.ujo.gigiScheduler.common.constants;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SeoulData {
    ENTER(0, "진입"),
    ARRIVE(1, "도착"),
    DEPART(2, "출발"),
    PREV_ENTER(4, "전역 진입"),
    PREV_ARRIVE(5, "전역 도착"),
    PREV_DEPART(3, "전역 출발"),
    RUNNING(99, "운행중");

    private final int code;
    private final String message;
    SeoulData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    private static final Map<Integer, SeoulData> BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(SeoulData::code, Function.identity()));

    private static final Map<String, SeoulData> BY_MESSAGE =
            Stream.of(values()).collect(Collectors.toMap(SeoulData::message, Function.identity()));

    public static SeoulData valueOfCode(int code) {
        return BY_CODE.get(code);
    }
    public static SeoulData valueOfMessage(String message) {
        return BY_MESSAGE.get(message);
    }

}
