package com.example.easyzhihu.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by My Computer on 2018/1/31.
 */

public class NewsBefore {

    public String date;
    public List<Story> stories;

    public class Story{

        public List<String> images;
        @SerializedName("id")
        public int newsid;
        public String title;

    }

}
