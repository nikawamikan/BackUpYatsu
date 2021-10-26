
package com.example;

import java.util.List;

import javax.annotation.processing.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Necessary {

    @SerializedName("necessary")
    @Expose
    private List<String> necessary = null;
    @SerializedName("except")
    @Expose
    private List<Object> except = null;

    public List<String> getNecessary() {
        return necessary;
    }

    public void setNecessary(List<String> necessary) {
        this.necessary = necessary;
    }

    public List<Object> getExcept() {
        return except;
    }

    public void setExcept(List<Object> except) {
        this.except = except;
    }

}
