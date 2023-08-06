package com.ujo.gigiScheduler.common.utils.apiUtils;

import com.ujo.gigiScheduler.entity.RequestStatEntity;
import com.ujo.gigiScheduler.common.exception.BusinessException;
import com.ujo.gigiScheduler.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PuzzleApi {

    @Value("${puzzle.apiKey}")
    private final String puzzleApiKey;
    @Value("${puzzle.statisticsUrl}")
    private final String puzzleStatisticsUrl;
    @Value("${puzzle.exitUrl}")
    private final String puzzleExitUrl;
    @Value("${puzzle.meta}")
    private final String puzzleMetaUrl;

    private String requestPuzzleApi(String url,String apiName){

        return new CommonApi.ApiBuilder(url, new BusinessException("퍼즐 "+apiName+ " API 호출 중 오류 발생했습니다.", ErrorCode.PZ01))
                .setRequestMethod("GET")
                .setRequestProperty("Accept", "application/json")
                .setRequestProperty("appKey", puzzleApiKey)
                .build()
                .callApi();
    }

    public String callStaticsApi(String station, String hour , String dow){
        CommonUrl url = new CommonUrl.UrlBuilder(puzzleStatisticsUrl)
                .setPathParam(station)
                .startQueryStringParam("hh", hour)
                .setQueryStringParam("dow", dow)
                .build();

        return requestPuzzleApi(url.getUrl(), "지하철 혼잡도 통계");
    }

    public String callStaticsApi(RequestStatEntity requestStat){
        CommonUrl url = new CommonUrl.UrlBuilder(puzzleStatisticsUrl)
                .setPathParam(requestStat.getStationCode())
                .startQueryStringParam("hh", requestStat.getRequestHour())
                .build();

        return requestPuzzleApi(url.getUrl(), "지하철 혼잡도 통계");
    }

    public List<String> callStaticsApi(List<RequestStatEntity> requestStats){

        List<String> statList = new ArrayList<>();
        for (RequestStatEntity requestStat : requestStats) {
            statList.add(this.callStaticsApi(requestStat));
        }

        return statList;
    }

    public String callExitApi(String station, String date){
        CommonUrl url = new CommonUrl.UrlBuilder(puzzleExitUrl)
                .setPathParam(station)
                .startQueryStringParam("date", date)
                .build();

        return requestPuzzleApi(url.getUrl(), "지하철 출구 이용 통계");
    }

    public String callExitApi(RequestStatEntity requestStat){
        CommonUrl url = new CommonUrl.UrlBuilder(puzzleExitUrl)
                .setPathParam(requestStat.getStationCode())
                .startQueryStringParam("date", requestStat.getRequestDate())
                .build();

        return requestPuzzleApi(url.getUrl(), "지하철 출구 이용 통계");
    }

    public List<String> callExitApi(List<RequestStatEntity> requestStats){

        List<String> exitList = new ArrayList<>();
        for (RequestStatEntity requestStat : requestStats) {
            exitList.add(this.callExitApi(requestStat));
        }

        return exitList;
    }

    public String callMetaInfoApi(){
        CommonUrl url = new CommonUrl.UrlBuilder(puzzleMetaUrl)
                .startQueryStringParam("type", "exit")
                .build();

        return requestPuzzleApi(url.getUrl(), "지하철 역 정보");
    }
}
