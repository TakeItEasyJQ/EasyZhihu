package com.example.easyzhihu.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by My Computer on 2017/12/21.
 */

public class HomePageContent {

    public String date;                                     //日期

    @SerializedName("stories")
    public List<LatestStory> latestStoriesList;           //最新新闻列表

    @SerializedName("top_stories")
    public List<TopFiveItem> topFiveList;                  //top5列表

}