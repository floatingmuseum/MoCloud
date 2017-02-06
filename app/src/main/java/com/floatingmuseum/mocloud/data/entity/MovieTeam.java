package com.floatingmuseum.mocloud.data.entity;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/1/20.
 */

public class MovieTeam {
    private PeopleCredit peopleCredit;
    private List<Staff> detailShowList;

    public PeopleCredit getPeopleCredit() {
        return peopleCredit;
    }

    public void setPeopleCredit(PeopleCredit peopleCredit) {
        this.peopleCredit = peopleCredit;
    }

    public List<Staff> getDetailShowList() {
        return detailShowList;
    }

    public void setDetailShowList(List<Staff> detailShowList) {
        this.detailShowList = detailShowList;
    }
}
