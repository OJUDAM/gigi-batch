package com.ujo.gigiScheduler.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //퍼즐 관련 에러코드
    PZ01("퍼즐 조회 API 호출 중 오류"),

    //서울 공공 데이터 관련 에러코드
    SD01("서울 공공데이터 조회 API 호출 중 오류"),

    //공통 에러
    E001("예상치 못한 예외가 발생한 경우"),

    // DTO null 값
    V001("요청에 대한 DTO 필드값 일부가 null 인 경우");

    private final String description;

    ErrorCode(final String description) {
        this.description = description;
    }
}
