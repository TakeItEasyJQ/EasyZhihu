package com.example.easyzhihu.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.easyzhihu.Adapters.LongCommentsAdapter;
import com.example.easyzhihu.Adapters.ShortCommentsAdapter;
import com.example.easyzhihu.HttpUtils.HttpUtils;
import com.example.easyzhihu.MyView.MyListView;
import com.example.easyzhihu.R;
import com.example.easyzhihu.gson.LongComments;
import com.example.easyzhihu.gson.ShortComments;
import com.example.easyzhihu.gson.StoryExtra;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener{

    private Intent intent;

    private ImageView back;
    private ImageView arrow;

    private TextView commentsCounts;
    private TextView longCounts;
    private TextView shortCounts;

    private RelativeLayout layoutnolongcomments;
    private RelativeLayout layoutshowshortcomments;

    private RecyclerView longcommentslistview;
    private RecyclerView shortcommentslisview;

    private ShortCommentsAdapter shortAdapter;
    private ShortCommentsAdapter savedAdapter;              //用于保存shortlistview的adapter的


    private boolean hasLongComments;
    private boolean shortcommentsshowed;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        back=(ImageView)findViewById(R.id.comments_back);

        commentsCounts=(TextView)findViewById(R.id.comments_counts);
        longCounts=(TextView)findViewById(R.id.long_counts);
        shortCounts=(TextView)findViewById(R.id.short_counts);

        layoutshowshortcomments=(RelativeLayout)findViewById(R.id.show_short_comments_layout);
        layoutnolongcomments=(RelativeLayout) findViewById(R.id.comments_nocomment_layout);

        longcommentslistview=(RecyclerView)findViewById(R.id.comments_long_listview);
        shortcommentslisview=(RecyclerView)findViewById(R.id.comments_short_listview);

        arrow=(ImageView)findViewById(R.id.comments_show_short);

        back.setOnClickListener(this);
        layoutshowshortcomments.setOnClickListener(this);

        LinearLayoutManager longmanager=new LinearLayoutManager(MessageActivity.this);
        longmanager.setOrientation(LinearLayoutManager.VERTICAL);
        longcommentslistview.setLayoutManager(longmanager);

        LinearLayoutManager shortmanager=new LinearLayoutManager(MessageActivity.this);
        shortmanager.setOrientation(LinearLayoutManager.VERTICAL);
        shortcommentslisview.setLayoutManager(shortmanager);



        showCommentsCounts();
        showLongComments();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comments_back:
                this.finish();
                break;
            case R.id.show_short_comments_layout:
                if (!shortcommentsshowed){                                  //未显示
                    arrow.setImageResource(R.drawable.comments_down);
                    if (!hasLongComments){
                        layoutnolongcomments.setVisibility(View.GONE);
                    }
                    shortcommentsshowed=true;
                    showShortComments();
                }else {                                                     //显示中
                    shortcommentsshowed=false;
                    arrow.setImageResource(R.drawable.comments_up);
                    if (!hasLongComments){
                        layoutnolongcomments.setVisibility(View.VISIBLE);
                    }
                    shortAdapter=null;
                    shortcommentslisview.setAdapter(shortAdapter);
                }
            default:
                break;
        }
    }

    public void showLongComments(){

        intent=getIntent();
        int newsid=getIntent().getIntExtra("newsid",0);
        if (newsid!=0){
            HttpUtils.getLongComments(newsid, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    Gson gson=new Gson();
                    final LongComments longComments=gson.fromJson(response.body().string(),LongComments.class);
                    final List<LongComments.Comment> comments=longComments.comments;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (comments.size()!=0){
                                    hasLongComments=true;
                                    LongCommentsAdapter adapter=new LongCommentsAdapter(MessageActivity.this,comments);
                                    longcommentslistview.setAdapter(adapter);
                                    layoutnolongcomments.setVisibility(View.GONE);

                                }else {
                                    layoutnolongcomments.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                }
            });
        }
    }

    public void showShortComments(){
        Intent shortIntent;
        if (intent==null){
            shortIntent=getIntent();
        }else {
            shortIntent=intent;
        }
        int id=shortIntent.getIntExtra("newsid",0);
        if (id!=0){
            HttpUtils.getShortComments(id, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson=new Gson();
                    ShortComments shortComments=gson.fromJson(response.body().string(),ShortComments.class);
                    if (shortComments!=null){
                        final List<ShortComments.Comment> comments=shortComments.comments;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                /*
                                如果adpter为空（第一次打开或者收起短评后再度打开时）
                                如果备份的adapter也为空（第一次打开）的话
                                new一个adapter并且存一份savedadapter备份
                                new完后将次adapter适配到shortcommentslistview中

                                当点击收起时将adapter置空，并将空的adpter配置给shortcommentslistview
                                再次点击时检查当前adapter是否为空（点击收起时置空）
                                若为空则将备份savedadapter赋值给adapter在重新将adapter配置给shortcommentslistview即可

                                savedadpter会一直保存直到此活动销毁
                                 */
                                if (shortAdapter==null){
                                    if (savedAdapter==null){
                                        shortAdapter=new ShortCommentsAdapter(MessageActivity.this,comments);
                                        savedAdapter=shortAdapter;
                                    }else {
                                        shortAdapter=savedAdapter;
                                    }
                                }

                                shortcommentslisview.setAdapter(shortAdapter);

                            }
                        });
                    }
                }
            });
        }
    }

    private void showCommentsCounts(){
        Intent intent;
        if (this.intent!=null){
            intent=this.intent;
        }else {
            intent=getIntent();
        }
        HttpUtils.getExtra(intent.getIntExtra("newsid", 0), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                final StoryExtra extra=gson.fromJson(response.body().string(),StoryExtra.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commentsCounts.setText(String.valueOf(extra.comments));
                        longCounts.setText(String.valueOf(extra.long_comments) );
                        shortCounts.setText(String.valueOf(extra.short_comments) );
                    }
                });

            }
        });
    }
}
