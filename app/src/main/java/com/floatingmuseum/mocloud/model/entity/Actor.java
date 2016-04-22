package com.floatingmuseum.mocloud.model.entity;

/**
 * Created by Administrator on 2016/4/13.
 */
public class Actor {

    /**
     * name : Bryan Cranston
     * ids : {"trakt":142,"slug":"bryan-cranston","imdb":"nm0186505","tmdb":17419,"tvrage":1797}
     */

    private String name;
    /**
     * trakt : 142
     * slug : bryan-cranston
     * imdb : nm0186505
     * tmdb : 17419
     * tvrage : 1797
     */

    private IdsBean ids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IdsBean getIds() {
        return ids;
    }

    public void setIds(IdsBean ids) {
        this.ids = ids;
    }

    public static class IdsBean {
        private int trakt;
        private String slug;
        private String imdb;
        private int tmdb;
        private int tvrage;


    }
}
