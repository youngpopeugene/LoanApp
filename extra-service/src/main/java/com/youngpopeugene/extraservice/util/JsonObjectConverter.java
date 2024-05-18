package com.youngpopeugene.extraservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youngpopeugene.extraservice.exception.JsonConvertingException;

public class JsonObjectConverter {
    public static String fromObjectToJson(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonConvertingException("Problems with a json converting");
        }
        return json;
    }
    public static <T> T fromJsonToObject(String json, Class<T> classToReturn){
        ObjectMapper objectMapper = new ObjectMapper();
        T object;
        try {
            object = objectMapper.readValue(json, classToReturn);
        } catch (JsonProcessingException e) {
            throw new JsonConvertingException("Problems with a json converting");
        }
        return object;
    }

}