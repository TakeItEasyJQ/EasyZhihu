package com.example.easyzhihu.db;

import org.litepal.crud.DataSupport;

/**
 * Created by My Computer on 2018/1/12.
 */

public class ThemeDB extends DataSupport {

    private int limit;
    private int color;
    private int themeid;
    private String name ;
    private String displayImage;
    private String description;

    public int getLimit() {
        return limit;
    }

    public int getColor() {
        return color;
    }

    public int getThemeid() {
        return themeid;
    }

    public String getName() {
        return name;
    }

    public String getDisplayImage() {
        return displayImage;
    }

    public String getDescription() {
        return description;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setThemeid(int themeid) {
        this.themeid = themeid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
