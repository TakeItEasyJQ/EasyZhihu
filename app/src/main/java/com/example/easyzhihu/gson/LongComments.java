package com.example.easyzhihu.gson;

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
        public int time;
        public int id;
        public int likes;


        public Reply reply_to;
        public class Reply{
            public String content;
            public String avatar;
            public int id;
            public int status;
        }
    }
}
