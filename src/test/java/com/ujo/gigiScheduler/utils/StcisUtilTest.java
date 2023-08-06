package com.ujo.gigiScheduler.utils;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class StcisUtilTest {

    @Test
    void getCongestion() {
        //진입 역 기준 열차 혼잡도
        //https://apis.openapi.sk.com/puzzle/subway/congestion/stat/train/stations/{stationCode}

        String url = "https://apis.openapi.sk.com/puzzle/subway/congestion/stat/train/stations";

        String station = "/D12";

        String params = "?dow=MON&hh=19";

        url += station;

        requestPuzzleApi(url, params);

    }

    void requestPuzzleApi(String url, String parameters) {
        String apiKey = "Z1iuLatVLQ9FsfIlE7R0H1ti9esZmLkUa996f691";
        StringBuffer result = new StringBuffer();
        String strResult = "";

        try {
            //URL 설정
            StringBuilder urlBuilder = new StringBuilder(url);

            urlBuilder.append(parameters);
            //URL 셍성
            URL cUrl = new URL(urlBuilder.toString());

            //http 연결
            HttpURLConnection conn = (HttpURLConnection) cUrl.openConnection();

            //request 형식 설정
            conn.setRequestMethod("GET");

            //헤더 설정
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("appKey", apiKey);

            BufferedReader rd;
            if(conn.getResponseCode() >= 200 & conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            while((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            conn.disconnect();
            strResult = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println(strResult);
    }

    @Test
    void puzzleRegionTest(){
        // 데이터 제공 가능 지하철역
        // https://apis.openapi.sk.com/puzzle/subway/meta/stations

        String apiKey = "Z1iuLatVLQ9FsfIlE7R0H1ti9esZmLkUa996f691";
        StringBuffer result = new StringBuffer();
        String strResult = "";


        try {
            //URL 설정
            StringBuilder urlBuilder = new StringBuilder("https://apis.openapi.sk.com/puzzle/subway/meta/stations");

            urlBuilder.append("?type=exit");
            //URL 셍성
            URL url = new URL(urlBuilder.toString());

            //http 연결
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //request 형식 설정
            conn.setRequestMethod("GET");

            //헤더 설정
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("appKey", apiKey);
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 & conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            while((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            conn.disconnect();
            strResult = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println(strResult);
    }
    @Test
    void stcisRegionTest() {
        // 지역코드
        // https://stcis.go.kr/openapi/areacode.json?apikey=(인증받은 API키)&sdCd=(시도코드)&sggCd=(시군구코드)
        // 15분단위 실시간
        //https://stcis.go.kr/openapi/quarterod.json?apikey=(인증받은 API키)&opratDate=(운행일자)&stgEmdCd=(읍면동코드)&arrEmdCd=(읍면동코드)
        //인증키 20230523202628e35bsd80bq1tndtj0u73pao3u

        String apiKey = "20230523202628e35bsd80bq1tndtj0u73pao3u";
        StringBuffer result = new StringBuffer();
        String strResult = "";
        //경기도 41
        String sdCd = "41";
        //41110
        //41130
        //수원시 영통구 41117
        //성남시 수정구 41131
        //성남시 분당구 41135
        String ssgCd = "41135";

        //망포동 4111710700
        //정자동 4113510300
        String startCd = "4113000000";
        String endCd = "4111000000";
        String dt = "20230522";

        try {
            StringBuilder urlBuilder = new StringBuilder("https://stcis.go.kr/openapi/quarterod.json");
            urlBuilder.append("?apikey=" + apiKey)
                    .append("&opratDate=" + dt)
                    .append("&stgEmdCd=" + startCd)
                    .append("&arrEmdCd=" + endCd);

            //URL 셍성
            URL url = new URL(urlBuilder.toString());

            //http 연결
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //request 형식 설정
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            BufferedReader rd;
            if(conn.getResponseCode() >= 200 & conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            while((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            conn.disconnect();
            strResult = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println(strResult);
    }
}