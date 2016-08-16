package com.floatingmuseum.mocloud.data.entity;

import java.util.List;

/**
 * Created by yan on 2016/8/9.
 */
public class People {
    private List<Actor> cast;
    private Crew crew;

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }

    public List<Actor> getCast() {
        return cast;
    }

    public void setCast(List<Actor> cast) {
        this.cast = cast;
    }
}
