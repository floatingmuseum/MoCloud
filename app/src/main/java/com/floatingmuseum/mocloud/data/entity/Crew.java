package com.floatingmuseum.mocloud.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/8/10.
 */
public class Crew {
    private List<Staff> production;
    @SerializedName("costume & make-up") private List<Staff> costumeAndMakeUp;
    private List<Staff> art;
    private List<Staff> crew;
    private List<Staff> directing;
    private List<Staff> writing;
    private List<Staff> sound;
    private List<Staff> camera;
    private List<Staff> editing;
    private List<Staff> lighting;
    @SerializedName("visual effects") private List<Staff> visualEffects;

    public List<Staff> getDirecting() {
        return directing;
    }

    public void setDirecting(List<Staff> directing) {
        this.directing = directing;
    }

    public List<Staff> getProduction() {
        return production;
    }

    public void setProduction(List<Staff> production) {
        this.production = production;
    }

    public List<Staff> getCostumeAndMakeUp() {
        return costumeAndMakeUp;
    }

    public void setCostumeAndMakeUp(List<Staff> costumeAndMakeUp) {
        this.costumeAndMakeUp = costumeAndMakeUp;
    }

    public List<Staff> getArt() {
        return art;
    }

    public void setArt(List<Staff> art) {
        this.art = art;
    }

    public List<Staff> getCrew() {
        return crew;
    }

    public void setCrew(List<Staff> crew) {
        this.crew = crew;
    }

    public List<Staff> getWriting() {
        return writing;
    }

    public void setWriting(List<Staff> writing) {
        this.writing = writing;
    }

    public List<Staff> getSound() {
        return sound;
    }

    public void setSound(List<Staff> sound) {
        this.sound = sound;
    }

    public List<Staff> getCamera() {
        return camera;
    }

    public void setCamera(List<Staff> camera) {
        this.camera = camera;
    }

    public List<Staff> getEditing() {
        return editing;
    }

    public void setEditing(List<Staff> editing) {
        this.editing = editing;
    }

    public List<Staff> getVisualEffects() {
        return visualEffects;
    }

    public void setVisualEffects(List<Staff> visualEffects) {
        this.visualEffects = visualEffects;
    }

    public List<Staff> getLighting() {
        return lighting;
    }

    public void setLighting(List<Staff> lighting) {
        this.lighting = lighting;
    }
}
