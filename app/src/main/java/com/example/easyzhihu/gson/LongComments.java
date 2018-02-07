package com.example.easyzhihu.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by My Computer on 2017/12/14.
 */

public class LongComments {

    public List<Comment> comments;

    public class Comment{
        public String author;
        public String content;
        public String avatar;
        public long time;
        @SerializedName("id")
        public int authorid;
        public int likes;


        public Reply reply_to;
        public class Reply{
            public String content;
            public int id;
            public int status;
            public String author;
            public String err_msg;   //status非0时出现
        }
    }
}
