package com.hopoong.featureflag_app.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class RandomJsonGenerator {

    public static void main(String[] args) {
        RandomJsonGenerator.generate();
    }

    public static void generate() {
        int numberOfPairs = 10000; // 원하는 key, value 쌍의 갯수

        JsonObject flagsObject = new JsonObject();
        String[] keySet = {"test", "service1", "service2", "service3", "service4"};

        // test-0, test-1, .... test-9999,
        // service1-0, service1-1, .... service1-9999, ....
        // service4-9999
        for (String s : keySet) {
            for (int i = 0; i < numberOfPairs; i++) {
                // key를 랜덤하게 생성하여 keySet 에서 선택
                String key = s + "-" + i;
                String value = "exampleValue-" + UUID.randomUUID();

                JsonObject keyObject = new JsonObject();
                keyObject.addProperty("state", "ENABLED");

                JsonObject variantsObject = new JsonObject();
                variantsObject.addProperty("key1", value);

                keyObject.add("variants", variantsObject);
                keyObject.addProperty("defaultVariant", "key1");

                flagsObject.add(key, keyObject);
            }
        }
        JsonObject resultObject = new JsonObject();
        resultObject.add("flags", flagsObject);

        // Gson 을 사용하여 JSON 형식의 문자열로 변환
        Gson gson = new Gson();
        String jsonString = gson.toJson(resultObject);

        try {
            // FileWriter 를 사용하여 파일 쓰기
            FileWriter fileWriter = new FileWriter("./generated.json");
            fileWriter.write(jsonString);
            fileWriter.close();

            System.out.println("File created successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
