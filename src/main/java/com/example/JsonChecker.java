package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class JsonChecker {
    public static Necessary getNecessary(String jsonPath) throws FileNotFoundException, IOException {
        String json = null;
        try {

            json = getJsonString(jsonPath);

            // System.out.println("jsonOK");

        } catch (FileNotFoundException e) {

            /*
             * Jsonが存在していなかった場合は雛形を作成して編集してもらう
             */
            Path p = Paths.get(".", jsonPath);

            final String hinagata = "{" + System.lineSeparator() + "\t\"nece\":[\"\"]," + System.lineSeparator()
                    + "\t\"except\":[\"\"]" + System.lineSeparator() + "}";

            BufferedWriter os = Files.newBufferedWriter(p);

            os.write(hinagata);
            os.close();

            throw e;

            // System.out.println("jsonNull");

        }

        Gson gson = new Gson();
        Necessary necessary = gson.fromJson(json, Necessary.class);

        return necessary;
    }

    public static String getJsonString(String jsonPath) throws FileNotFoundException, IOException {
        StringBuilder jsonString = null;

        try (BufferedReader bufreader = new BufferedReader(new FileReader(jsonPath))) {
            String tmpString = null;
            jsonString = new StringBuilder();
            while ((tmpString = bufreader.readLine()) != null) {
                jsonString.append(tmpString);
            }
        }

        return jsonString.toString();
    }
}
