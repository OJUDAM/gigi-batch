package com.ujo.gigiScheduler.utils.apiUtils;


import com.ujo.gigiScheduler.common.exception.BusinessException;
import com.ujo.gigiScheduler.common.exception.ErrorCode;
import com.ujo.gigiScheduler.common.utils.apiUtils.CommonApi;
import org.junit.jupiter.api.Test;

class CommonApiTest {

    @Test
    public void callApi(){
        String url = "https://apis.openapi.sk.com/puzzle/subway/congestion/stat/train/stations";
        String apiKey = "Z1iuLatVLQ9FsfIlE7R0H1ti9esZmLkUa996f691";
        String station = "/D12";

        url += station;


        CommonApi commonApi = new CommonApi.ApiBuilder(url, new BusinessException("퍼즐 지하철 혼잡도 API 호출 중 오류 발생했습니다.", ErrorCode.PZ01))
                .setRequestMethod("GET")
                .setRequestProperty("Accept", "application/json")
                .setRequestProperty("appKey", apiKey)
                .build();

        System.out.println(commonApi.callApi());
        System.out.println("test");
    }
}