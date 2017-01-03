package com.floatingmuseum.mocloud.data.entity;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/11/30.
 */

public class TmdbPeople {

    private int id;
    private List<Staff> cast;
    private List<Staff> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Staff> getCast() {
        return cast;
    }

    public void setCast(List<Staff> cast) {
        this.cast = cast;
    }

    public List<Staff> getCrew() {
        return crew;
    }

    public void setCrew(List<Staff> crew) {
        this.crew = crew;
    }
}
