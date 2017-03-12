package com.floatingmuseum.mocloud.base;

/**
 * Created by Floatingmuseum on 2017/1/6.
 */

public abstract class ListPresenter extends Presenter {
    protected int limit = 12;
    protected int pageNum = 1;
    protected boolean shouldClean;

    public abstract void start(boolean shouldClean);

    protected int getPageNum(boolean shouldClean) {
        this.shouldClean =shouldClean;
        if (shouldClean) {
            pageNum = 1;
            return pageNum;
        }else{
            //这里pageNum只是临时+1，以防请求失败时，再请求时页码却变为下一页的情况
            return pageNum+1;
        }
    }
}
