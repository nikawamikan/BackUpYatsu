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

/**
 * Json形式のファイルからJson文字列を取り出してクラス化するヤツです<br/>
 * コンストラクタを作成するときはHinagataクラスを継承したオブジェクトを引数としなくてはなりません。
 * 
 * @author nikawamikan
 */
public class JsonChecker<T extends Hinagata<T>> {

    // リターンする系オブジェクト
    private T t;
    // Gsonで何型のオブジェクトか判定するために必要
    private final Class<T> c;
    // Jsonファイルのあるファイルパス

    /**
     * 専用オブジェクトから位置インスタンス生成します。<br/>
     * 専用オブジェクトはHinagataを実装する必要があります。
     * 
     * @param t
     */
    public JsonChecker(T t) {
        this.t = t;
        this.c = t.getClazz();
    }

    /**
     * ジェネリクスで指定されたJsonの専用オブジェクトを返します
     * 
     * @return Json専用オブジェクト
     * @throws FileNotFoundException ファイルが見つからない時に処理
     * @throws IOException           出たことないヤツだけど異常終了系で処理する
     */
    public T getJsonObj() throws FileNotFoundException, IOException {
        String json = null;
        try {

            json = getJsonString(t.jsonPath());

        } catch (FileNotFoundException e) {

            /*
             * Jsonが存在していなかった場合は雛形を作成して編集してもらう
             */
            Path p = Paths.get(".", t.jsonPath());

            BufferedWriter bw = Files.newBufferedWriter(p);

            bw.write(t.getHinagata());
            bw.close();

            throw e;
        }

        Gson gson = new Gson();

        t = gson.fromJson(json, c);

        return t;
    }

    /**
     * パスから読み込んだファイルをString型に置き換える<br/>
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
