package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2016/8/10.
 */
public class Actor {
    private String character;
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

}
