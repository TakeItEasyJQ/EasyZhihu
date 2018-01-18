package com.example.easyzhihu.gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by My Computer on 2018/1/15.
 */

public class ThemeID {

    public List<Story> stories;
    public String description;
    public String background;
    public int color;
    public String name;
    public String image;    //背景图片的小图(长方形)
    public List<Editor> editors;
    public String image_source;     //图片来源


    public class Story {
        public List<String> images;
        public int id;
        public String title;

    }

    public class Editor implements Serializable{
        public String url;  //个人主页
        public String bio;  //个人标签
        public int id;      //用户id
        public String avatar;   //头像
        public String name;   //昵称
    }


}
