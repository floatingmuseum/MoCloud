package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2016/12/21.
 */

public class Reply {

    private String comment;
    private boolean spoiler;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    public void setSpoiler(boolean spoiler) {
        this.spoiler = spoiler;
    }
}
