package com.baccaventuri.flicking.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Title {

    @SerializedName("_content")
    @Expose
    private String content;

    /**
     * No args constructor for use in serialization
     *
     */
    public Title() {
    }

    /**
     *
     * @param content
     */
    public Title(String content) {
        super();
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Title withContent(String content) {
        this.content = content;
        return this;
    }

}