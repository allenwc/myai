package net.ryian.flow.common.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * @author allenwc
 * @date 2024/5/4 11:09
 */
public class JsonUtil {

    private JsonUtil() {
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String jsonToString(JsonNode jsonNode) {
        try {
            return objectMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            throw new RuntimeException("Error converting JsonNode to String", e);
        }
    }

    public static JsonNode stringToJson(String data) {
        if(Strings.isNullOrEmpty(data)) {
            return objectMapper.createObjectNode();
        }
        try {
            return objectMapper.readTree(data);
        } catch (Exception e) {
            throw new RuntimeException("Error converting String to JsonNode", e);
        }
    }

    public static String listToString(List<?> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            throw new RuntimeException("Error converting List to String", e);
        }
    }

    public static List<?> stringToList(String data) {
        try {
            return objectMapper.readValue(data, new TypeReference<List<?>>() {});
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public static JsonNode tryParseJson(String inputData) throws Exception{

        try {
            return objectMapper.readTree(inputData);
        } catch (JsonProcessingException e) {
            Pattern pattern = Pattern.compile("```.*?\n(.*?)\n```", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(inputData);
            if (!matcher.find()) {
                throw new Exception("Invalid JSON format");
            }
            String jsonBlock = matcher.group(1);
            return objectMapper.readTree(jsonBlock);
        }
    }

}
