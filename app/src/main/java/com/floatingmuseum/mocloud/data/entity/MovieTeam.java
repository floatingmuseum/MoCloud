package com.floatingmuseum.mocloud.data.entity;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/1/20.
 */

public class MovieTeam {
    private People people;
    private List<Staff> detailShowList;

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public List<Staff> getDetailShowList() {
        return detailShowList;
    }

    public void setDetailShowList(List<Staff> detailShowList) {
        this.detailShowList = detailShowList;
    }
}
