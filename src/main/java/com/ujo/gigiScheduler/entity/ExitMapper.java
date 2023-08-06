package com.ujo.gigiScheduler.entity;

import com.ujo.gigiScheduler.batch.mapper.BaseMapper;
import com.ujo.gigiScheduler.common.exception.BusinessException;
import com.ujo.gigiScheduler.common.exception.ErrorCode;
import com.ujo.gigiScheduler.common.utils.jsonUtils.CommonJSON;
import com.ujo.gigiScheduler.common.utils.jsonUtils.CustomJSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExitMapper extends BaseMapper {

    @Override
    public Map<String, Object> jsonToMap(String jsonString) {
        try{
            //String dateBefore = DateUtils.addDate("yyyyMMdd", StatConstant.EXIT_PREV_DAYS);

            CustomJSONParser jsonParser = new CustomJSONParser();
            JSONObject jsonObject = jsonParser.parse(jsonString);

            this.checkStatus(jsonObject);

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

                String dateBefore = datetime.substring(0, 8);

                if (!datetime.equals(dateBefore + "170000") && !datetime.equals(dateBefore + "180000") && !datetime.equals(dateBefore + "190000")) {
                    continue;
                }

                String userCountKey = "userCount" + datetime.substring(8, 10);

                if(countMap.containsKey(userCountKey)){
                    countMap.put(userCountKey, (int)countMap.get(userCountKey) + userCount);
                    continue;
                }
                countMap.put("date", dateBefore);
                countMap.put(userCountKey, userCount);
            }

            countMap.put("stationCode", stationCode);

            return countMap;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BusinessException("json 에서 맵으로 변환하는 과정에서 에러 발생", ErrorCode.E001);
        }
    }

    public List<Map<String, Object>> jsonArrayToMaps(List<String> jsonArray) {
        List<Map<String, Object>> exitMaps = new ArrayList<>();

        for (String json : jsonArray) {
            exitMaps.add(jsonToMap(json));
        }

        return exitMaps;
    }
}
