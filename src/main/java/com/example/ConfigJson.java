
package com.example;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Gsonが認識するために必要なクラスです。<br/>
 * 形式等名前が一致したJsonオブジェクトをこのクラスに変換する
 * 
 * @param nece   コピーするファイルを指定するためのリストです。
 * @param except 例外的に不要なファイルを指定するリストです。
 * 
 * @author nikawamikan
 */
public class ConfigJson implements Hinagata<ConfigJson> {

    /**
     * カレントディレクトリの指定
     */
    @SerializedName("from")
    @Expose
    private String from = null;

    /**
     * 宛先ディレクトリの指定
     */
    @SerializedName("to")
    @Expose
    private String to = null;

    /**
     * コピーするファイルを指定するためのリスト
     */
    @SerializedName("nece")
    @Expose
    private List<String> nece = null;

    /**
     * 例外的に不要なファイルを指定するリスト
     */
    @SerializedName("except")
    @Expose
    private List<String> except = null;

    // getter setter
    public List<String> getNece() {
        return nece;
    }

    public void setNece(List<String> nece) {
        this.nece = nece;
    }

    public List<String> getExcept() {
        return except;
    }

    public void setExcept(List<String> except) {
        this.except = except;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public final static String HINAGATA = """
            {
                \"from\":\".\",
                \"to\":\".\",
                \"nece\":[\"\"],
                \"except\":[\"\"]
            }
            """;

    // 色々と設定用
    @Override
    public String getHinagata() {
        return HINAGATA;
    }

    @Override
    public Class<ConfigJson> getClazz() {
        return ConfigJson.class;
    }

    public static final String JSON_PATH = "config.json";

    @Override
    public String jsonPath() {
        return JSON_PATH;
    }

}
