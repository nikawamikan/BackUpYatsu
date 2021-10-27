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

        } catch (FileNotFoundException e) {

            /*
             * Jsonが存在していなかった場合は雛形を作成して編集してもらう
             */
            Path p = Paths.get(".", jsonPath);

            // 空テンプレートのString
            final String hinagata = "{" + System.lineSeparator() + "\t\"nece\":[\"\"]," + System.lineSeparator()
                    + "\t\"except\":[\"\"]" + System.lineSeparator() + "}";

            BufferedWriter bw = Files.newBufferedWriter(p);

            bw.write(hinagata);
            bw.close();

            throw e;
        }

        Gson gson = new Gson();
        Necessary necessary = gson.fromJson(json, Necessary.class);

        return necessary;
    }

    /**
     * パスから読み込んだファイルをString型に置き換える<br/>
     * String型の仕様を考えると少し心苦しい(ConstantPoolにやたら長い文字列が並びかねない)
     * 
     * @param jsonPath Jsonファイルのある場所の文字列
     * @return Jsonのファイルを文字列に置換したもの
     * @throws FileNotFoundException ファイルが読み込めない場合などに投げる
     * @throws IOException           いつ投げるか知らない
     * 
     */
    private static String getJsonString(String jsonPath) throws FileNotFoundException, IOException {
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
