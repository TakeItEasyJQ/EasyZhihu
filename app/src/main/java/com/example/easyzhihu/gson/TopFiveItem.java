package com.example.easyzhihu.gson;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by My Computer on 2017/12/13.
 */

public class TopFiveItem  {


    public String title;
    public String image;

    @SerializedName("id")
    public int newsid;

}
