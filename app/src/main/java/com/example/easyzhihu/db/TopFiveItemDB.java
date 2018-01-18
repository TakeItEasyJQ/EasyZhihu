package com.example.easyzhihu.db;


import org.litepal.crud.DataSupport;

/**
 * Created by My Computer on 2017/12/22.
 */

public class TopFiveItemDB extends DataSupport {

    public int id;
    public String date;
    public String title;
    public String image;
    public int newsid;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getNewsid() {
        return newsid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }
}
