package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2016/11/23.
 */

public class BaseFanart {
    private String id;
    private String url;
    private String lang;
    private String likes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
