package com.ujo.gigiScheduler.batch.mapper;

import com.ujo.gigiScheduler.common.exception.BusinessException;
import com.ujo.gigiScheduler.common.exception.ErrorCode;
import com.ujo.gigiScheduler.common.utils.jsonUtils.CommonJSON;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BaseMapper {

    /**
     * 상속받은 클래스에서 오버라이드 하여 사용
     * 응답 내용(json) 을 Map으로 파싱
     * */
    public Map<String, Object> jsonToMap(String jsonString) {
        return new HashMap<>();
    }

    /**
     * 응답 체크 후 실패하면 예외 던짐
     * */
    public void checkStatus(JSONObject jsonObject){
        //응답 상태 파싱
        CommonJSON.CommonJSONBuilder commonJSONBuilder = new CommonJSON.CommonJSONBuilder(jsonObject)
                .parseObject("status");

        //jsonObject로 변환
        JSONObject status = commonJSONBuilder
                .build()
                .getJsonObject();

        //응답 상태 매칭
        String responseCode = status.get("code").toString();
        String responseMessage = status.get("message").toString();

        //api 요청 실패 시 예외 던짐
        if (!responseCode.equals("00")) {
            log.error(responseMessage);
            throw new BusinessException(responseMessage, ErrorCode.PZ01);
        }
    }
}
