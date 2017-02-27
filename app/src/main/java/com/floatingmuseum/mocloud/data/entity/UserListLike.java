package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/2/26.
 */

public class UserListLike extends BaseUserLike{
    private CustomListInfo list;
    private User user;

    public CustomListInfo getList() {
        return list;
    }

    public void setList(CustomListInfo list) {
        this.list = list;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
