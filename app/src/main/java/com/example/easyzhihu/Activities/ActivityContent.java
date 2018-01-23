package com.example.easyzhihu.Activities;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.Fragments.ContentFragment;
import com.example.easyzhihu.HttpUtils.HttpUtils;
import com.example.easyzhihu.Interfaces.IFragmentContentListener;
import com.example.easyzhihu.R;
import com.example.easyzhihu.Utils.BodyUtil;
import com.example.easyzhihu.db.StoryFavoriteDB;
import com.example.easyzhihu.gson.Content;
import com.example.easyzhihu.gson.StoryExtra;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by My Computer on 2018/1/4.
 */

public class ActivityContent extends AppCompatActivity  implements IFragmentContentListener ,View.OnClickListener{


    private ContentFragment contentFragment;

    private String body;
    private String image_source;
    private String title;
    private String Image;
    private String image;
    private String share_url;

    private String sectionImage;
    private String sectionName;
    private int sectionId;

    private int commentsCounts;
    private int popularCounts;

    private BodyUtil util;

    private boolean isContentFinished;
    private boolean isExtraFinished;
    private boolean hasSection;

    private String view_more;

    private Content newsContent;

    private RelativeLayout layout;

    private IContralListener listener;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        contentFragment=(ContentFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_content);
        layout=(RelativeLayout)findViewById(R.id.headline_layout);

        contentFragment.setFragmentContentListenter(ActivityContent.this);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getContent();

            }
        });
    }
    public void getContent(){
        final int newsid=getIntent().getIntExtra("newsid",0);

        if (newsid!=0){

            HttpUtils.getNewsContent(newsid, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    isContentFinished=true;
                    Toast.makeText(ActivityContent.this,"网络错误，请检查后重试",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson=new Gson();
                    Content content=gson.fromJson(response.body().string(),Content.class);
                    newsContent=content;

                    util=new BodyUtil(content.body);
                    util.init();
                    view_more=util.getViewMoreUrl();
                    util.RemoveViewMore();
                    body=util.getNewContent();


                    image_source=content.image_source;
                    title=content.title;

                    Image=content.headlineimage;

                    if (content.images!=null){
                        image=content.images.get(0);
                    }

                    share_url=content.share_url;

                    if (content.section!=null){
                        sectionId=content.section.id;
                        sectionImage=content.section.thumbnail;
                        sectionName=content.section.name;
                        hasSection=true;
                    }

                    if (content.section!=null){

                        sectionId=content.section.id;
                        sectionName=content.section.name;
                        sectionImage=content.section.thumbnail;
                    }

                    share_url=content.share_url;
                    isContentFinished=true;
                }
            });
            HttpUtils.getExtra(newsid, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    isExtraFinished=true;

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson=new Gson();
                    StoryExtra extra=gson.fromJson(response.body().string(),StoryExtra.class);
                    commentsCounts=extra.comments;
                    popularCounts=extra.popularity;
                    isExtraFinished=true;

                }
            });
        }

    }

    @Override
    public void setFragmentContent() {

        while (true){
            if (isExtraFinished&&isContentFinished){
                break;
            }
        }
                if (DataSupport.where("newsid = ?",String.valueOf(newsContent.id)).find(StoryFavoriteDB.class).size()!=0){
                    Glide.with(ActivityContent.this).load(R.drawable.fragment_favor_bg2).into(contentFragment.favor);
                }else {
                    Toast.makeText(ActivityContent.this,"no",Toast.LENGTH_SHORT).show();
                    Glide.with(ActivityContent.this).load(R.drawable.fragment_favor_bg1).into(contentFragment.favor);
                }

                contentFragment.agreeCount.setText(String.valueOf(popularCounts));
                contentFragment.messageCount.setText(String.valueOf(commentsCounts));


                contentFragment.headlineContentTitle.setText(title);
                contentFragment.headlineSource.setText(image_source);
                Glide.with(ActivityContent.this).load(Image).into(contentFragment.headlineImg);

                if (getIntent().getBooleanExtra("fromtheme",false)){
                    layout.setVisibility(View.GONE);
                }

                //以下没出过毛病

                contentFragment.webView.loadDataWithBaseURL(null,body,"text/html","gbk",null);
                contentFragment.webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        contentFragment.viewMore.setVisibility(View.VISIBLE);
                        if (hasSection){
                            Glide.with(ActivityContent.this).load(sectionImage).into(contentFragment.sectionImag);
                            contentFragment.sectionName.setText(sectionName);
                            contentFragment.layout.setVisibility(View.VISIBLE);
                        }else if (getIntent().getBooleanExtra("fromtheme",false)){
                            Intent intent=getIntent();
                            Glide.with(ActivityContent.this).load(intent.getStringExtra("image")).into(contentFragment.sectionImag);
                            contentFragment.sectionName.setText(intent.getStringExtra("title"));
                            contentFragment.layout.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {

                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);

                        return true;
                    }
                });
        setListeners();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_back:
                contentFragment.getActivity().finish();
                break;
            case R.id.fragment_share:

                break;
            case R.id.fragment_favor:


                if (DataSupport.where("newsid = ?",String.valueOf(newsContent.id)).find(StoryFavoriteDB.class).size()!=0){ //收藏了
                    DataSupport.deleteAll(StoryFavoriteDB.class,"newsid = ?",String.valueOf(newsContent.id));
                    Glide.with(ActivityContent.this).load(R.drawable.fragment_favor_bg1).into(contentFragment.favor);
                }else {
                    Glide.with(ActivityContent.this).load(R.drawable.fragment_favor_bg2).into(contentFragment.favor);
                    StoryFavoriteDB storyFavor=new StoryFavoriteDB();
                    storyFavor.setNewsid(newsContent.id);
                    storyFavor.setTitle(newsContent.title);
                    storyFavor.setImageurl(newsContent.images.get(0));
                    storyFavor.save();
                }




                break;
            case R.id.fragment_message:

                Intent intentmessage=new Intent(ActivityContent.this, MessageActivity.class);
                intentmessage.putExtra("newsid",getIntent().getIntExtra("newsid",0));
                startActivity(intentmessage);
                break;

            case R.id.fragment_agree:

                break;
            case R.id.button_view_more:
                Intent intentviewmore=new Intent(Intent.ACTION_VIEW);
                intentviewmore.setData(Uri.parse(view_more));
                startActivity(intentviewmore);
                break;
            case R.id.section_layout:
                Toast.makeText(ActivityContent.this,"123",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    private void setListeners(){

        contentFragment.back.setOnClickListener(this);
        contentFragment.share.setOnClickListener(this);
        contentFragment.favor.setOnClickListener(this);
        contentFragment.message.setOnClickListener(this);
        contentFragment.agree.setOnClickListener(this);
        contentFragment.viewMore.setOnClickListener(this);
        contentFragment.layout.setOnClickListener(this);
    }



    public void setContralListener(IContralListener listener){
        this.listener=listener;
    }


    public interface IContralListener{
        void showContent();
    }


    @Override
    protected void onResume() {
        super.onResume();
        listener.showContent();
    }
}
