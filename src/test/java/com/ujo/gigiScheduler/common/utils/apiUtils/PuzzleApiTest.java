package com.ujo.gigiScheduler.common.utils.apiUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ujo.gigiScheduler.entity.ExitEntity;
import com.ujo.gigiScheduler.entity.StatEntity;
import com.ujo.gigiScheduler.batch.mapper.StatMapper;
import com.ujo.gigiScheduler.entity.StationEntity;
import com.ujo.gigiScheduler.common.utils.DateUtils;
import com.ujo.gigiScheduler.common.utils.jsonUtils.CommonJSON;
import com.ujo.gigiScheduler.common.utils.jsonUtils.CustomJSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ExtendWith(MockitoExtension.class)
class PuzzleApiTest {

    private PuzzleApi puzzleApi;
    private String puzzleApiKey;
    private String puzzleStatisticsUrl;
    private String puzzleExitUrl;
    private String puzzleMetaUrl;
    private StatMapper statMapper;

    @BeforeEach
    public void setUp() {
//        puzzle.apiKey=Z1iuLatVLQ9FsfIlE7R0H1ti9esZmLkUa996f691
//        puzzle.statisticsUrl=https://apis.openapi.sk.com/puzzle/subway/congestion/stat/train/stations/
//        puzzle.exitUrl=https://apis.openapi.sk.com/puzzle/subway/exit/raw/hourly/stations/
        puzzleApiKey = "Z1iuLatVLQ9FsfIlE7R0H1ti9esZmLkUa996f691";
        puzzleStatisticsUrl = "https://apis.openapi.sk.com/puzzle/subway/congestion/stat/train/stations/";
        puzzleExitUrl = "https://apis.openapi.sk.com/puzzle/subway/exit/raw/hourly/stations/";
        puzzleMetaUrl = "https://apis.openapi.sk.com/puzzle/subway/meta/stations/";

        puzzleApi = new PuzzleApi(puzzleApiKey, puzzleStatisticsUrl, puzzleExitUrl, puzzleMetaUrl);
        statMapper = new StatMapper();
    }

    @Test
    @DisplayName("STAT API JSON -> LIST 파싱")
    public void mapperListTest(){
        String response = "{\"status\":\n" +
                "{\"code\":\"00\",\"message\":\"success\",\"totalCount\":1},\"contents\":{\"subwayLine\":\"신분당선\",\"stationName\":\"정자역\",\"stationCode\":\"D12\",\"stat\":[{\"startStationCode\":\"D04\",\"startStationName\":\"신사역\",\"endStationCode\":\"D12\",\"endStationName\":\"정자역\",\"prevStationCode\":\"D11\",\"prevStationName\":\"판교역\",\"updnLine\":1,\"directAt\":0,\"data\":[{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"00\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"10\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"20\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"30\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"40\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"50\",\"congestionTrain\":0}]},{\"startStationCode\":\"D04\",\"startStationName\":\"신사역\",\"endStationCode\":\"D19\",\"endStationName\":\"광교역\",\"prevStationCode\":\"D11\",\"prevStationName\":\"판교역\",\"updnLine\":1,\"directAt\":0,\"data\":[{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"00\",\"congestionTrain\":104},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"10\",\"congestionTrain\":102},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"20\",\"congestionTrain\":126},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"30\",\"congestionTrain\":140},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"40\",\"congestionTrain\":147},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"50\",\"congestionTrain\":146}]},{\"startStationCode\":\"D19\",\"startStationName\":\"광교역\",\"endStationCode\":\"D04\",\"endStationName\":\"신사역\",\"prevStationCode\":\"D13\",\"prevStationName\":\"미금역\",\"updnLine\":0,\"directAt\":0,\"data\":[{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"00\",\"congestionTrain\":58},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"10\",\"congestionTrain\":67},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"20\",\"congestionTrain\":77},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"30\",\"congestionTrain\":71},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"40\",\"congestionTrain\":78},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"50\",\"congestionTrain\":62}]}],\"statStartDate\":\"20230329\",\"statEndDate\":\"20230628\"}}";

        List<Map<String, Object>> list = statMapper.jsonToList(response);

        for (Map<String, Object> objectMap : list) {
            StatEntity statEntity = StatEntity.from(objectMap);

            System.out.println(statEntity.toString());
        }

        Assertions.assertEquals(2, list.size());

    }

    @Test
    @DisplayName("STAT API JSON -> MAP 파싱")
    public void mapperTest(){
        String response = "{\"status\":\n" +
                "{\"code\":\"00\",\"message\":\"success\",\"totalCount\":1},\"contents\":{\"subwayLine\":\"신분당선\",\"stationName\":\"정자역\",\"stationCode\":\"D12\",\"stat\":[{\"startStationCode\":\"D04\",\"startStationName\":\"신사역\",\"endStationCode\":\"D12\",\"endStationName\":\"정자역\",\"prevStationCode\":\"D11\",\"prevStationName\":\"판교역\",\"updnLine\":1,\"directAt\":0,\"data\":[{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"00\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"10\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"20\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"30\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"40\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"50\",\"congestionTrain\":0}]},{\"startStationCode\":\"D04\",\"startStationName\":\"신사역\",\"endStationCode\":\"D19\",\"endStationName\":\"광교역\",\"prevStationCode\":\"D11\",\"prevStationName\":\"판교역\",\"updnLine\":1,\"directAt\":0,\"data\":[{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"00\",\"congestionTrain\":104},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"10\",\"congestionTrain\":102},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"20\",\"congestionTrain\":126},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"30\",\"congestionTrain\":140},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"40\",\"congestionTrain\":147},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"50\",\"congestionTrain\":146}]},{\"startStationCode\":\"D19\",\"startStationName\":\"광교역\",\"endStationCode\":\"D04\",\"endStationName\":\"신사역\",\"prevStationCode\":\"D13\",\"prevStationName\":\"미금역\",\"updnLine\":0,\"directAt\":0,\"data\":[{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"00\",\"congestionTrain\":58},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"10\",\"congestionTrain\":67},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"20\",\"congestionTrain\":77},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"30\",\"congestionTrain\":71},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"40\",\"congestionTrain\":78},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"50\",\"congestionTrain\":62}]}],\"statStartDate\":\"20230329\",\"statEndDate\":\"20230628\"}}";

        Map<String, Object> map = statMapper.jsonToMap(response);

        StatEntity statEntity = StatEntity.from(map);

        System.out.println(statEntity.toString());

    }

    @Test
    void requestExitTest() throws ParseException, JsonProcessingException {


        String dateBeforeWeek = DateUtils.addDate("yyyyMMdd",-1);

        CustomJSONParser jsonParser = new CustomJSONParser();
        JSONObject jsonObject = jsonParser.parse(puzzleApi.callExitApi("D12","latest"));

        CommonJSON.CommonJSONBuilder commonJSONBuilder =  new CommonJSON.CommonJSONBuilder(jsonObject)
                .parseObject("status");

        JSONObject status = commonJSONBuilder
                .build()
                .getJsonObject();

        String responseCode = status.get("code").toString();
        String responseMessage = status.get("message").toString();

        JSONObject contents = new CommonJSON.CommonJSONBuilder(jsonObject)
                .parseObject("contents")
                .build().getJsonObject();

        String stationCode = contents.get("stationCode").toString();

        JSONArray rawArray = (JSONArray) contents.get("raw");

        Map<String, Object> countMap = new HashMap<>();


        //00 10 20 30 40 50
        for (int i = 0; i < rawArray.size(); i++) {

            JSONObject raw = (JSONObject) rawArray.get(i);

            String datetime = raw.get("datetime").toString();

            if (raw.get("userCount") == null) {
                continue;
            }

            int userCount = Integer.parseInt(raw.get("userCount").toString());
            if (!datetime.equals(dateBeforeWeek + "170000") && !datetime.equals(dateBeforeWeek + "180000") && !datetime.equals(dateBeforeWeek + "190000")) {
                continue;
            }

            String userCountKey = "userCount" + datetime.substring(8, 10);

            if(countMap.containsKey(userCountKey)){
                countMap.put(userCountKey, (int)countMap.get(userCountKey) + userCount);
                continue;
            }

            countMap.put(userCountKey, userCount);
        }

        countMap.put("date", dateBeforeWeek);
        countMap.put("stationCode", stationCode);

        ExitEntity exit = ExitEntity.from(countMap);

        Assertions.assertEquals(5, countMap.size());
    }

    @Test
    void requestMetaTest() throws ParseException, JsonProcessingException {
        CustomJSONParser jsonParser = new CustomJSONParser();
        JSONObject jsonObject = jsonParser.parse(puzzleApi.callMetaInfoApi());

        CommonJSON.CommonJSONBuilder commonJSONBuilder =  new CommonJSON.CommonJSONBuilder(jsonObject)
                .parseObject("status");

        JSONObject status = commonJSONBuilder
                .build()
                .getJsonObject();

        String responseCode = status.get("code").toString();
        String responseMessage = status.get("message").toString();

        String contents =  new CommonJSON.CommonJSONBuilder(jsonObject)
                .parseArray("contents")
                .build().getJsonArray().toString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<StationEntity> stations = objectMapper.readValue(contents, new TypeReference<>() {});

        for (StationEntity station : stations) {
            System.out.println(station.toString());
        }

        System.out.println(contents);
    }
    @Test
    public void requestPuzzleApi() throws ParseException {

//        String response = puzzleApi.callStaticsApi("D12", "19", "MON");

        String response = "{\"status\":\n" +
                "{\"code\":\"00\",\"message\":\"success\",\"totalCount\":1},\"contents\":{\"subwayLine\":\"신분당선\",\"stationName\":\"정자역\",\"stationCode\":\"D12\",\"stat\":[{\"startStationCode\":\"D04\",\"startStationName\":\"신사역\",\"endStationCode\":\"D12\",\"endStationName\":\"정자역\",\"prevStationCode\":\"D11\",\"prevStationName\":\"판교역\",\"updnLine\":1,\"directAt\":0,\"data\":[{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"00\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"10\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"20\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"30\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"40\",\"congestionTrain\":0},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"50\",\"congestionTrain\":0}]},{\"startStationCode\":\"D04\",\"startStationName\":\"신사역\",\"endStationCode\":\"D19\",\"endStationName\":\"광교역\",\"prevStationCode\":\"D11\",\"prevStationName\":\"판교역\",\"updnLine\":1,\"directAt\":0,\"data\":[{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"00\",\"congestionTrain\":104},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"10\",\"congestionTrain\":102},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"20\",\"congestionTrain\":126},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"30\",\"congestionTrain\":140},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"40\",\"congestionTrain\":147},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"50\",\"congestionTrain\":146}]},{\"startStationCode\":\"D19\",\"startStationName\":\"광교역\",\"endStationCode\":\"D04\",\"endStationName\":\"신사역\",\"prevStationCode\":\"D13\",\"prevStationName\":\"미금역\",\"updnLine\":0,\"directAt\":0,\"data\":[{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"00\",\"congestionTrain\":58},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"10\",\"congestionTrain\":67},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"20\",\"congestionTrain\":77},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"30\",\"congestionTrain\":71},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"40\",\"congestionTrain\":78},{\"dow\":\"FRI\",\"hh\":\"17\",\"mm\":\"50\",\"congestionTrain\":62}]}],\"statStartDate\":\"20230329\",\"statEndDate\":\"20230628\"}}";
        CustomJSONParser jsonParser = new CustomJSONParser();
        JSONObject jsonObject = jsonParser.parse(response);



        //응답 상태 파싱
        CommonJSON.CommonJSONBuilder commonJSONBuilder =  new CommonJSON.CommonJSONBuilder(jsonObject)
                .parseObject("status");

        JSONObject status = commonJSONBuilder
                .build()
                .getJsonObject();

        String responseCode = status.get("code").toString();
        String responseMessage = status.get("message").toString();

        commonJSONBuilder =  new CommonJSON.CommonJSONBuilder(jsonObject)
                .parseObject("contents");

        //통계 날짜 파싱
        String startDate = commonJSONBuilder
                .build()
                .getJsonObject()
                .get("statStartDate")
                .toString();

        String endDate = commonJSONBuilder
                .build()
                .getJsonObject()
                .get("statEndDate")
                .toString();


        commonJSONBuilder = commonJSONBuilder.parseArray("stat")
                .parseArrayMember(1)
                .parseArray("data");

        JSONObject contents = null;
        //00 10 20 30 40 50
        for (int i = 0; i < 6; i++) {

            contents = commonJSONBuilder
                    .parseArrayMember(i)
                    .build()
                    .getJsonObject();

            String hh = contents.get("hh").toString();
            String mm = contents.get("mm").toString();
            int congestion = Integer.parseInt(contents.get("congestionTrain").toString());

            System.out.println("시간: " + hh);
            System.out.println("분: " + mm);
            System.out.println("혼잡도: " + congestion);
        }
        System.out.println(responseCode + " : " + responseMessage);

        System.out.println(startDate + " ~ " + endDate);
    }

}