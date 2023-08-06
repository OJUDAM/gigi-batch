package com.ujo.gigiScheduler.common.utils.apiUtils;

import com.ujo.gigiScheduler.common.exception.BusinessException;
import com.ujo.gigiScheduler.common.exception.ErrorCode;
import com.ujo.gigiScheduler.common.utils.DateUtils;
import com.ujo.gigiScheduler.common.utils.jsonUtils.CommonJSON;
import com.ujo.gigiScheduler.common.utils.jsonUtils.CustomJSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

public class SeoulApiTest {


    @Test
    void seoulApiTest() throws ParseException {
        String seoulMetroUrl = "http://swopenAPI.seoul.go.kr/api/subway/";
        String appKey = "5157746d71646877313231714c435359";
        String responseType = "json";
        String serviceName = "realtimeStationArrival";
        String startPage ="0" ;
        String endPage = "5";
        String stationName = "망포";


        CommonUrl url = new CommonUrl.UrlBuilder(seoulMetroUrl)
                .setPathParam(appKey)
                .setPathParam(responseType)
                .setPathParam(serviceName)
                .setPathParam(startPage)
                .setPathParam(endPage)
                .setPathParam(stationName)
                .build();

         String responseJson = requestSeoulApi(url.getUrl(), "실시간 지하철 도착 시간");

        CustomJSONParser jsonParser = new CustomJSONParser();
        JSONObject jsonObject = jsonParser.parse(responseJson);

        JSONArray realtimeArrivalList = new CommonJSON.CommonJSONBuilder(jsonObject)
                .parseArray("realtimeArrivalList")
                .build().getJsonArray();
        for (Object arrivalTime : realtimeArrivalList) {
            JSONObject arrivalInfo = (JSONObject) arrivalTime;

            String updnLine = arrivalInfo.get("updnLine").toString();
            if (updnLine.equals("상행")) {
                continue;
            }


            String trainName = arrivalInfo.get("bstatnNm").toString();
            String trainNo = arrivalInfo.get("btrainNo").toString();
            String direct = arrivalInfo.get("btrainSttus").toString();
            String arrivalStation = arrivalInfo.get("arvlMsg3").toString();
            String arrivalMessage = arrivalInfo.get("arvlMsg2").toString();
            String arrivalCode = arrivalInfo.get("arvlCd").toString();
            String createTime = arrivalInfo.get("recptnDt").toString();
            String targetStation = arrivalInfo.get("statnNm").toString();

            System.out.println(trainName);
            System.out.println(trainNo);
            System.out.println(direct);
            System.out.println(arrivalStation);
            System.out.println(arrivalMessage);
            System.out.println(arrivalCode);
            System.out.println(createTime);
            System.out.println(targetStation);
            System.out.println("----------------------------");
        }
    }

    @Test
    void seoulRealTimePositionApiTest() throws ParseException {
        String seoulMetroUrl = "http://swopenAPI.seoul.go.kr/api/subway/";
        String appKey = "437a706f7364687739334145577046";
        String responseType = "json";
        String serviceName = "realtimePosition";
        String startPage ="0" ;
        String endPage = "100";
        String stationName = "수인분당선";


        CommonUrl url = new CommonUrl.UrlBuilder(seoulMetroUrl)
                .setPathParam(appKey)
                .setPathParam(responseType)
                .setPathParam(serviceName)
                .setPathParam(startPage)
                .setPathParam(endPage)
                .setPathParam(stationName)
                .build();

        String responseJson = requestSeoulApi(url.getUrl(), "실시간 지하철 위치");

        CustomJSONParser jsonParser = new CustomJSONParser();
        JSONObject jsonObject = jsonParser.parse(responseJson);

        JSONArray realtimeArrivalList = new CommonJSON.CommonJSONBuilder(jsonObject)
                .parseArray("realtimePositionList")
                .build().getJsonArray();
        for (Object arrivalTime : realtimeArrivalList) {
            JSONObject arrivalInfo = (JSONObject) arrivalTime;


            String finalStation = arrivalInfo.get("statnTnm").toString();

            String trainNo = arrivalInfo.get("trainNo").toString();
            String trainName = arrivalInfo.get("statnNm").toString();
            String arrivalCode = arrivalInfo.get("trainSttus").toString();

            String direct = arrivalInfo.get("directAt").toString();
            String updnLine = arrivalInfo.get("updnLine").toString();
            String arrivalDate = DateUtils.addDate("yyyyMMdd", 0);
            String createTime = arrivalInfo.get("recptnDt").toString();

            System.out.println(updnLine);
            System.out.println(finalStation);
            System.out.println(trainName);
            System.out.println(trainNo);
            System.out.println(direct);
            System.out.println(arrivalCode);
            System.out.println(arrivalDate);
            System.out.println(createTime);
            System.out.println("----------------------------");
        }
    }

    String requestSeoulApi(String url,String apiName){

        return new CommonApi.ApiBuilder(url, new BusinessException("서울 "+apiName+ " API 호출 중 오류 발생했습니다.", ErrorCode.PZ01))
                .setRequestMethod("GET")
                .setRequestProperty("Content-type","application/json")
                .build()
                .callApi();
    }
}
