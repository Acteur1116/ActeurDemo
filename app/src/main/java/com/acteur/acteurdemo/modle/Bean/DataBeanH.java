package com.acteur.acteurdemo.modle.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ${Riven} on ${rabbit}.
 */
public class DataBeanH {

    @SerializedName("date")
    private String date;

    @SerializedName("stories")
    private ArrayList<DataBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<DataBean> getStories() {
        return stories;
    }

    public void setStories(ArrayList<DataBean> stories) {
        this.stories = stories;
    }
}
