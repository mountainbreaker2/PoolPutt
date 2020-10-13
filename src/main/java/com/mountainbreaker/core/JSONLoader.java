package com.mountainbreaker.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class JSONLoader {
    private static ObjectMapper mapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return defaultObjectMapper;
    }

    public static JsonNode parse(InputStream file) throws IOException {
        return mapper.readTree(file);
    }

    @Nullable
    public static <T> T fromJSON(JsonNode node, Class<T> returnClass) {
        try {
            return mapper.treeToValue(node, returnClass);
        } catch (JsonProcessingException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
