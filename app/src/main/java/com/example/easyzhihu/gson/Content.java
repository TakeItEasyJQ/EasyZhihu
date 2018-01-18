package com.example.easyzhihu.gson;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by My Computer on 2017/12/14.
 */

public class Content {

    public String body;
    public String image_source;
    public String title;
    @SerializedName("image")
    public String headlineimage; //headline原图
    public String share_url;
    @SerializedName("images")
    public List<String> images;         //缩略图,只有一个
    public int id;
    public Section section;


    public class Section{
        public String thumbnail;
        public String name;
        public int id;
    }




}
