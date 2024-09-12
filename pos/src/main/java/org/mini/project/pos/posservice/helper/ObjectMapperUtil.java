package org.mini.project.pos.posservice.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mini.project.pos.posservice.exception.JsonParseFailedException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.util.Map;

public class ObjectMapperUtil {

  private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();

  private static final MapTypeReference MAP_TYPE = new MapTypeReference();

  public static <T> T readValue(String content, Class<T> valueType) {
    try {
      return OBJECT_MAPPER.readValue(content, valueType);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to deserialize: " + content);
    }
  }

  public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
    try {
      return OBJECT_MAPPER.readValue(content, valueTypeRef);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to deserialize: " + content);
    }
  }

  public static <T> T readValue(String content, Class<T> valueType, T defaultValue) {
    try {
      return OBJECT_MAPPER.readValue(content, valueType);
    } catch (IOException e) {
      return defaultValue;
    }
  }

  public static <T> T readValue(String content, TypeReference<T> valueTypeRef, T defaultValue) {
    try {
      return OBJECT_MAPPER.readValue(content, valueTypeRef);
    } catch (IOException e) {
      return defaultValue;
    }
  }

  public static <T> T readValue(byte[] content, Class<T> valueType) {
    try {
      return OBJECT_MAPPER.readValue(content, valueType);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to deserialize byte array");
    }
  }

  public static <T> T readValue(byte[] content, TypeReference<T> valueTypeRef) {
    try {
      return OBJECT_MAPPER.readValue(content, valueTypeRef);
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to deserialize byte array");
    }
  }

  public static <T> T readValue(byte[] content, Class<T> valueType, T defaultValue) {
    try {
      return OBJECT_MAPPER.readValue(content, valueType);
    } catch (IOException e) {
      return defaultValue;
    }
  }

  public static <T> T readValue(byte[] content, TypeReference<T> valueTypeRef, T defaultValue) {
    try {
      return OBJECT_MAPPER.readValue(content, valueTypeRef);
    } catch (IOException e) {
      return defaultValue;
    }
  }

  public static String writeValueAsString(Object value) {
    try {
      return OBJECT_MAPPER.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Unable to serialize: " + value);
    }
  }

  public static String writeValueAsString(Object value, String defaultValue) {
    try {
      return OBJECT_MAPPER.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      return defaultValue;
    }
  }

  public static Map<String, Object> convertValueToMap(Object value) {
    return OBJECT_MAPPER.convertValue(value, MAP_TYPE);
  }

  public static <T extends Map<String, Object>> T convertValueToMap(Object value, Class<T> mapType) {
    return OBJECT_MAPPER.convertValue(value, mapType);
  }

  public static <T extends Map<String, Object>> T convertValueToMap(Object value, TypeReference<T> mapTypeRef) {
    return OBJECT_MAPPER.convertValue(value, mapTypeRef);
  }

  public static <T> T convertMapToValue(Map<String, Object> map, Class<T> valueType) {
    return OBJECT_MAPPER.convertValue(map, valueType);
  }

  public static <T> T convertMapToValue(Map<String, Object> map, TypeReference<T> valueTypeRef) {
    return OBJECT_MAPPER.convertValue(map, valueTypeRef);
  }

  private static class MapTypeReference extends TypeReference<Map<String, Object>> {

  }
}