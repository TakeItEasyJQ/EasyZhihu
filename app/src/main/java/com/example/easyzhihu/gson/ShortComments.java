package com.example.easyzhihu.gson;

import java.util.List;

/**
 * Created by My Computer on 2017/12/14.
 */

public class ShortComments {       //短评论

    public List<Comment> comments;

    public class Comment{
        public String author;
        public String content;
        public int id;
        public int likes;
        public int time;
        public String avatar;

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
