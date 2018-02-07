package com.example.easyzhihu.HttpUtils;

import com.example.easyzhihu.Utils.Configs;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by My Computer on 2017/12/20.
 */

public class HttpUtils {

    //最新消息
    public static void getNewsLatest(Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                            .url(Configs.News_Latest)
                            .build();
        client.newCall(request).enqueue(callback);
    }

//    public static HomePageContent getNewsLatest(){
//        OkHttpClient client=new OkHttpClient();
//        Request request=new Request.Builder()
//                                    .url(Configs.News_Latest)
//                                    .build();
//        try {
//            Response response=client.newCall(request).execute();
//            Gson gson=new Gson();
//            return gson.fromJson(response.body().string(),HomePageContent.class);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    //具体内容
    public static void getNewsContent(int newsId,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                            .url(Configs.News_Content+newsId)
                            .build();
        client.newCall(request).enqueue(callback);
    }

    //过往消息
    public static void getNewsBefore(String data,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                            .url(Configs.News_Before+data)
                            .build();
        client.newCall(request).enqueue(callback);
    }

    //所有类别
    public static void getNewsThemes(Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(Configs.News_Themes)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //具体类别
    public  static void getNewsTheme(int themeId,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                            .url(Configs.News_Theme+themeId)
                            .build();
        client.newCall(request).enqueue(callback);
    }

    public static void getThemeNewsBefore(int themeid , int latestnewsid ,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                            .url(Configs.News_Theme+themeid+Configs.Nesw_Theme_Foot+latestnewsid)
                            .build();
        client.newCall(request).enqueue(callback);
    }

    //额外信息
    public static void getExtra(int newsId,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(Configs.News_Extra+newsId)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //长评论
    public static void getLongComments(int newsId,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                            .url(Configs.News_EXTRA_Head+newsId+Configs.News_LongComments_Foot)
                            .build();
        client.newCall(request).enqueue(callback);
    }

    //获取更多长评
    public static void getMoreLongComments(int newsid, int userid,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                            .url(Configs.News_EXTRA_Head+newsid+Configs.News_LongComments_Foot+Configs.News_MoreComments_Foot +userid)
                            .build();
        client.newCall(request).enqueue(callback);
    }

    //短评论
    public static void getShortComments(int newsid,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(Configs.News_EXTRA_Head+newsid+Configs.News_ShortComments_Foot)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //获取更多短评
    public static void getMoreShortComments(int newsid,int userid,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                            .url(Configs.News_EXTRA_Head+newsid+Configs.News_ShortComments_Foot+Configs.News_MoreComments_Foot+userid)
                            .build();
        client.newCall(request).enqueue(callback);
    }

    //推荐者
    public static void getEditors(int newsId,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(Configs.News_EXTRA_Head+newsId+Configs.News_Editor_Foot)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //推荐者主页
    public static void getEditorHomePage(int editorId,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(Configs.Editor_Home_Page_Head+editorId+Configs.Editor_Home_Page_Foot)
                .build();
        client.newCall(request).enqueue(callback);
    }
    public static void getSection(int sectionid ,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(Configs.Section_Head+sectionid)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void getSectionBefore(int sectionid ,int timestamp ,Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                            .url(Configs.Section_Head+sectionid+Configs.Section_foot+timestamp)
                            .build();
        client.newCall(request).enqueue(callback);
    }

}
