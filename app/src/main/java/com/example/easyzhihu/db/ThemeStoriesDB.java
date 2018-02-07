package com.example.easyzhihu.db;

import org.litepal.crud.DataSupport;

/**
 * Created by My Computer on 2018/2/7.
 */

public class ThemeStoriesDB extends DataSupport {

    public int newsid;
    public String title;
    public boolean hasreaded;
    public int themeid;

    public int getNewsid() {
        return newsid;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasreaded() {
        return hasreaded;
    }

    public void setHasreaded(boolean hasreaded) {
        this.hasreaded = hasreaded;
    }

    public int getThemeid() {
        return themeid;
    }

    public void setThemeid(int themeid) {
        this.themeid = themeid;
    }
}
