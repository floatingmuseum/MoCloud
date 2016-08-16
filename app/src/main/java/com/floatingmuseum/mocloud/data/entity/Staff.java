package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by yan on 2016/8/10.
 */
public class Staff {
    private String job;
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
