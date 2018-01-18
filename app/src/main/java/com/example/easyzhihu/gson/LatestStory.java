package com.example.easyzhihu.gson;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by My Computer on 2017/12/21.
 */

public class LatestStory {

    @SerializedName("id")
    public int newsid;

    public String title;

    public List<String> images;
}
