package com.example;

import static com.example.MessageException.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
     * @exception MessageException IOExceptionでもなんでもとりあえず変換してコレで投げるヤツ
     */
    public T getJsonObj() throws MessageException {
        String json = null;
        try {

            json = getJsonString(t.jsonPath());

        } catch (IOException e) {

            /*
             * Jsonが存在していなかった場合は雛形を作成して編集してもらう
             */
            jsonBuilder(t.jsonPath(), t.getHinagata());

            throw new MessageException(NOT_FOUND_NENESARY_MESSAGE);
        }

        Gson gson = new Gson();

        try {

            t = gson.fromJson(json, c);
        } catch (JsonSyntaxException e) {

            throw new MessageException(JSON_SYNTAX_MESSAGE, json);
        }

        return t;
    }

    /**
     * パスから読み込んだファイルをString型に置き換える<br/>
     * 
     * @param jsonPath Jsonファイルのある場所の文字列
     * @return Jsonのファイルを文字列に置換したもの
     * @throws FileNotFoundException ファイルが読み込めない場合などに投げる
     * @throws MessageException      IOExceptionを変換して投げます
     * 
     */
    private static String getJsonString(String jsonPath) throws IOException {
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

    public static void jsonBuilder(String jsonPath, String hinagata) throws MessageException {

        Path p = Paths.get(".", jsonPath);

        BufferedWriter bw = null;
        try {
            bw = Files.newBufferedWriter(p);

            bw.write(hinagata);

        } catch (IOException e) {
            throw new MessageException(IOEXCEPTIN_MESSAGE);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new MessageException(IOEXCEPTIN_MESSAGE);
            }
        }
    };
}
