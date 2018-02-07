package com.example.easyzhihu.gson;

import java.util.List;

/**
 * Created by My Computer on 2018/1/25.
 */

public class Section {

    public int timestamp;
    public List<Stories> stories;

    public class Stories{
        public List<String> images;
        public String date;
        public String display_date;
        public int id;
        public String title;
    }
}
