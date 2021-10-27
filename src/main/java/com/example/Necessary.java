
package com.example;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Necessary {

    @SerializedName("nece")
    @Expose
    private List<String> nece = null;
    @SerializedName("except")
    @Expose
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
