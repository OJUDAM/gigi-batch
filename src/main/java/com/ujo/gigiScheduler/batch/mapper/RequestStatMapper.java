package com.ujo.gigiScheduler.batch.mapper;

import com.ujo.gigiScheduler.common.constants.StatConstant;
import com.ujo.gigiScheduler.common.exception.BusinessException;
import com.ujo.gigiScheduler.common.exception.ErrorCode;
import com.ujo.gigiScheduler.entity.RequestStatEntity;
import com.ujo.gigiScheduler.entity.StationEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestStatMapper extends BaseMapper{

    /**ㅇ
     * StationEntity 리스트로 받아 17~19 시간 설정 후 RequestStatEntity 리스트로 반환
     * */
    public List<RequestStatEntity> stationsToRequestList(List<StationEntity> stations) {
        List<RequestStatEntity> requestList = new ArrayList<>();

        for (StationEntity station : stations) {
            requestList.addAll(stationToRequestList(station));
        }

        return requestList;
    }

    /**
     * StationEntity 받아 17~19 시간 설정 후 RequestStatEntity 리스트로 반환
     * */
    public List<RequestStatEntity> stationToRequestList(StationEntity station) {
        try {
            List<RequestStatEntity> requestList = new ArrayList<>();

            for (String hour : StatConstant.STAT_TIME_3_HOUR) {
                RequestStatEntity requestStat = new RequestStatEntity();
                requestStat.setStationCode(station.getStationCode());
                requestStat.setRequestHour(hour);

                requestList.add(requestStat);
            }

            return requestList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("StationEntity 에서 StatRequestEntity 변환하는 과정에서 에러 발생", ErrorCode.E001);
        }
    }
}
