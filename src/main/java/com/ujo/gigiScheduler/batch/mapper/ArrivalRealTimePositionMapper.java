package com.ujo.gigiScheduler.batch.mapper;

import com.ujo.gigiScheduler.common.constants.BundangLine;
import com.ujo.gigiScheduler.common.constants.TrainDirectInfo;
import com.ujo.gigiScheduler.common.constants.TrainUpDownInfo;
import com.ujo.gigiScheduler.common.exception.BusinessException;
import com.ujo.gigiScheduler.common.exception.ErrorCode;
import com.ujo.gigiScheduler.common.utils.DateUtils;
import com.ujo.gigiScheduler.common.utils.jsonUtils.CommonJSON;
import com.ujo.gigiScheduler.common.utils.jsonUtils.CustomJSONParser;
import com.ujo.gigiScheduler.entity.ArrivalRealTimeEntity;
import com.ujo.gigiScheduler.entity.ArrivalRealTimePositionEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ArrivalRealTimePositionMapper {
    public List<ArrivalRealTimePositionEntity> jsonToList(String responseJson) {

        CustomJSONParser jsonParser = new CustomJSONParser();
        JSONObject jsonObject = null;
        List<ArrivalRealTimePositionEntity> realTimePositionList = new ArrayList<>();

        try {
            jsonObject = jsonParser.parse(responseJson);

            JSONArray realtimeArrivalList = new CommonJSON.CommonJSONBuilder(jsonObject)
                    .parseArray("realtimePositionList")
                    .build().getJsonArray();
            for (Object arrivalPosition : realtimeArrivalList) {
                JSONObject arrivalInfo = (JSONObject) arrivalPosition;

                String trainName = arrivalInfo.get("statnTnm").toString();

                String trainNo = arrivalInfo.get("trainNo").toString();
                String arrivalStation = arrivalInfo.get("statnNm").toString() + "역";
                String arrivalCode = arrivalInfo.get("trainSttus").toString();

                String direct = arrivalInfo.get("directAt").toString();
                String updnLine = arrivalInfo.get("updnLine").toString();
                String createTime = arrivalInfo.get("recptnDt").toString();

                if (updnLine.equals("0") || trainName.equals("죽전") || trainName.equals("오이도") ||trainName.equals("인천")) {
                    continue;
                }

                //맵에 저장
                Map<String, Object> realtimePositionMap = new HashMap<>();
                realtimePositionMap.put("trainNo", trainNo);
                realtimePositionMap.put("trainName", trainName);
                realtimePositionMap.put("arrivalStationCode", BundangLine.valueOfName(arrivalStation).code());
                realtimePositionMap.put("directAt", direct);
                realtimePositionMap.put("arrivalCode", arrivalCode);
                realtimePositionMap.put("arrivalDate", DateUtils.addDate("yyyyMMdd",0));
                realtimePositionMap.put("createdAt", createTime);
                realtimePositionMap.put("upDnLine", updnLine);
                //MAP -> ENTITY 변환 후 리스트에 추가
                realTimePositionList.add(ArrivalRealTimePositionEntity.from(realtimePositionMap));
            }

            return realTimePositionList;
        } catch (ParseException e) {
            throw new BusinessException("json -> list 과정 중 에러 발생", ErrorCode.SD01);
        }
    }

}
