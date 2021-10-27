
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
public class Necessary {

    @SerializedName("nece")
    @Expose
    /**
     * コピーするファイルを指定するためのリスト
     */
    private List<String> nece = null;
    @SerializedName("except")
    @Expose
    /**
     * 例外的に不要なファイルを指定するリスト
     */
    private List<String> except = null;

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

}
