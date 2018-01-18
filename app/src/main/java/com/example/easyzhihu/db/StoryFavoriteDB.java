package com.example.easyzhihu.db;

import org.litepal.crud.DataSupport;

/**
 * Created by My Computer on 2018/1/10.
 */

public class StoryFavoriteDB extends DataSupport {
    public int newsid;
    public String imageurl;
    public String title;

    public int getNewsid() {
        return newsid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
