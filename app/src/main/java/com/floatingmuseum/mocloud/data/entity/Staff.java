package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2016/8/10.
 */
public class Staff {
    public static final String CAST_ITEM = "Actors";
    public static final String DIRECTING_ITEM = "Directing";
    public static final String WRITING_ITEM = "Writing";
    public static final String PRODUCTION_ITEM = "Production";
    public static final String EDITING_ITEM = "Editing";
    public static final String ART_ITEM = "Art";
    public static final String CAMERA_ITEM = "Camera";
    public static final String COSTUME_AND_MAKEUP_ITEM = "Costume & Make-Up";
    public static final String SOUND_ITEM = "Sound";
    public static final String VISUAL_EFFECTS_ITEM = "Visual Effects";
    public static final String LIGHTING_ITEM = "Lighting";

    private boolean adult;
    private String credit_id;
    private String department;
    private int id;
    private String job;
    private String original_title;
    private String poster_path;
    private String profile_path;
    private String release_date;
    private String title;
    private String character;
    private String name;
    private int cast_id;
    private int order;
    private String itemType;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
