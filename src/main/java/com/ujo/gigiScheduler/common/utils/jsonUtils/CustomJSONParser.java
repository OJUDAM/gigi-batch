package com.ujo.gigiScheduler.common.utils.jsonUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CustomJSONParser extends JSONParser {

    @Override
    public JSONObject parse(String s) throws ParseException {
        return (JSONObject) parse(s, (ContainerFactory)null);
    }
}
