package com.lunkes.verifymy.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class UtilsResources {

    public static String getProperty(String key) {
        Properties prop = new Properties();
        try {
            prop.load(Files.newInputStream(Paths.get("src/main/resources/config.properties")));
            return prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getJsonOfObject(String path, Class<T> clazz){
        try {
            T json = new ObjectMapper().readValue(
                    Files.newInputStream(Paths.get(path))
                    ,clazz
            );
            return json;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> T getJsonOfObject(String path, TypeReference<T> valueTypeRef){
        try {
            T json = new ObjectMapper().readValue(
                    Files.newInputStream(Paths.get(path))
                    ,valueTypeRef
            );
            return json;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
