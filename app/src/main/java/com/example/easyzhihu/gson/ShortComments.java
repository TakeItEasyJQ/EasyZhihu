package com.example.easyzhihu.gson;

import java.util.List;

/**
 * Created by My Computer on 2017/12/14.
 */

public class ShortComments {       //短评论

    public List<Comment> comments;

    public class Comment{
        public String author;
        public String cotent;
        public int id;
        public int likes;
        public int time;
        public String avatar;
    }
}
