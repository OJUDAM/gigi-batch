package com.ujo.gigiScheduler.common.utils.apiUtils;

import com.ujo.gigiScheduler.common.exception.BusinessException;
import com.ujo.gigiScheduler.common.exception.ErrorCode;
import com.ujo.gigiScheduler.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeoulApi {

    @Value("${seoul-data.realTime.apiKey}")
    private final String seoulDataRealTimeApiKey;
    @Value("${seoul-data.realTime.url}")
    private final String seoulDataRealTimesUrl;

    @Value("${seoul-data.realTimePosition.apiKeyList}")
    private final List<String> seoulDataRealTimePositionApiKeyList;
    @Value("${seoul-data.realTimePosition.url}")
    private final String seoulDataRealTimePositionUrl;

    private String seoulDataRealTimePositionApiKey;
    private String responseJsonResult;
    private static final int API_KEY_LIST_MAX_SIZE = 5;
    private int currentKeyIndex = 0;

    public SeoulApi(@Value("${seoul-data.realTime.apiKey}") String seoulDataRealTimeApiKey,@Value("${seoul-data.realTime.url}") String seoulDataRealTimesUrl
            ,@Value("${seoul-data.realTimePosition.apiKeyList}") List<String> seoulDataRealTimePositionApiKeyList,@Value("${seoul-data.realTimePosition.url}") String seoulDataRealTimePositionUrl) {
        this.seoulDataRealTimeApiKey = seoulDataRealTimeApiKey;
        this.seoulDataRealTimesUrl = seoulDataRealTimesUrl;
        this.seoulDataRealTimePositionApiKeyList = seoulDataRealTimePositionApiKeyList;
        this.seoulDataRealTimePositionUrl = seoulDataRealTimePositionUrl;
        this.seoulDataRealTimePositionApiKey = seoulDataRealTimePositionApiKeyList.get(0);
    }

    private String requestSeoulApi(String url,String apiName){

        responseJsonResult = new CommonApi.ApiBuilder(url, new BusinessException("서울 공공데이터"+apiName+ " API 호출 중 오류 발생했습니다.", ErrorCode.SD01))
                .setRequestMethod("GET")
                .setRequestProperty("Content-type","application/json")
                .build()
                .callApi();

        //프로퍼티 iterator 사용하여 api-key 순환
        //api 호출 시 exception 발생하면 다음 키로 교체
        if(!isExceed()){
            return responseJsonResult;
        }

        changeNextApiKey();

        return "next";
    }

    private boolean isExceed(){
        return responseJsonResult.contains("ERROR-337");
    }

    private void changeNextApiKey(){
        currentKeyIndex++;
        if(currentKeyIndex >= API_KEY_LIST_MAX_SIZE) {
            currentKeyIndex = 0;
        }

        seoulDataRealTimePositionApiKey = seoulDataRealTimePositionApiKeyList.get(currentKeyIndex);
    }

    public String callRealArrivalTimeApi(String stationName) {
        CommonUrl url = new CommonUrl.UrlBuilder(seoulDataRealTimesUrl)
                .setPathParam(seoulDataRealTimeApiKey)
                .setPathParam("json")
                .setPathParam("realtimeStationArrival")
                .setPathParam("0")
                .setPathParam("6")
                .setPathParam(stationName)
                .build();

        return requestSeoulApi(url.getUrl(), "실시간 지하철 도착 시간");
    }

    public String callRealTimePositionApi(String SubwayLine) {

        CommonUrl url = new CommonUrl.UrlBuilder(seoulDataRealTimePositionUrl)
                .setPathParam(seoulDataRealTimePositionApiKey)
                .setPathParam("json")
                .setPathParam("realtimePosition")
                .setPathParam("0")
                .setPathParam("50")
                .setPathParam(SubwayLine)
                .build();

        return requestSeoulApi(url.getUrl(), "실시간 지하철 위치");
    }
}
