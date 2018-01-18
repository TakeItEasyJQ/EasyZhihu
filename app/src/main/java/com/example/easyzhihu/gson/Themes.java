package com.example.easyzhihu.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by My Computer on 2017/12/14.
 */

public class Themes {

    public int limit;

    public List<Other> others;

    public class Other{
        public int color;
        @SerializedName("thumbnail")
        public String displayImage;//图片 点进去title处
        public String description;
        public int id;
        public String name;
    }

}
