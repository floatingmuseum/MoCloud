package com.floatingmuseum.mocloud.data.entity;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/9.
 */

public class PeopleCredit {

    private List<Staff> cast;
    private Crew crew;

    public List<Staff> getCast() {
        return cast;
    }

    public void setCast(List<Staff> cast) {
        this.cast = cast;
    }

    public Crew getCrew() {
        return crew;
    }

    public void setCrew(Crew crew) {
        this.crew = crew;
    }
}
