package com.example.easyzhihu.db;



import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by My Computer on 2017/12/22.
 */

public class LatestStoryDB extends DataSupport {


    private int id;
    private String date;
    private int newsid;
    private String title;
    private List<String> images;
    private boolean hasreaded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public int getNewsid() {
        return newsid;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getImages() {
        return images;
    }

    public boolean isHasreaded() {
        return hasreaded;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNewsid(int newsid) {
        this.newsid = newsid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setHasreaded(boolean hasreaded) {
        this.hasreaded = hasreaded;
    }

}
