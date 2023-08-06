package com.ujo.gigiScheduler.common.utils.jsonUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CommonJSON {

    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public JSONObject getJsonObject(){
        return this.jsonObject;
    }

    public JSONArray getJsonArray() {
        return this.jsonArray;
    }

    private CommonJSON(CommonJSONBuilder commonJSONBuilder) {
        this.jsonObject = commonJSONBuilder.jsonObject;
        this.jsonArray = commonJSONBuilder.jsonArray;
    }

     public static class CommonJSONBuilder{
        private JSONObject jsonObject;
        private JSONArray jsonArray;

        public CommonJSONBuilder(JSONObject jsonObject) {
            this.jsonObject = jsonObject;
        }

        public CommonJSONBuilder(JSONArray jsonArray) {
            this.jsonArray = jsonArray;
        }

        public CommonJSONBuilder parseObject(String key) {
            this.jsonObject = (JSONObject) this.jsonObject.get(key);
            return this;
        }

        public CommonJSONBuilder parseArray(String key) {
            this.jsonArray = (JSONArray) this.jsonObject.get(key);
            return this;
        }

        public CommonJSONBuilder parseArrayToArray(int i) {
            this.jsonArray = (JSONArray) this.jsonArray.get(i);
            return this;
        }
        public CommonJSONBuilder parseArrayMember(int i) {
            this.jsonObject = (JSONObject) this.jsonArray.get(i);
            return this;
        }

        public CommonJSON build(){
            return new CommonJSON(this);
        }
    }
}
