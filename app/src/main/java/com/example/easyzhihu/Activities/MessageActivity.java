package com.example.easyzhihu.Activities;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyzhihu.Adapters.LongCommentsAdapter;
import com.example.easyzhihu.Adapters.ShortCommentsAdapter;
import com.example.easyzhihu.HttpUtils.HttpUtils;
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

    private int newsid;

    private ImageView back;
    private ImageView arrow;

    private TextView commentsCounts;
    private TextView longCounts;
    private TextView shortCounts;

    private ScrollView scrollView;
    private RelativeLayout layoutnolongcomments;
    private RelativeLayout layoutshowshortcomments;

    private LinearLayout messageavtivity;
    private LinearLayout messagetitle;

    private RecyclerView longcommentslistview;
    private RecyclerView shortcommentslisview;

    private LongCommentsAdapter longadapter;

    private ShortCommentsAdapter shortAdapter;
//    private ShortCommentsAdapter savedAdapter;              //用于保存shortlistview的adapter的


    private boolean hasLongComments;
    private boolean shortcommentsshowed;
    private boolean latestCommentsHasShowd;
    private boolean needscrollby;

    private int longCommentsLastetUserId;
    private int shortCommentsLatestUserId;
    private List<LongComments.Comment> longCommentsList;
    private List<ShortComments.Comment> shortCommentsList;

    private ProgressDialog dialog;

    private int displayheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        newsid=getIntent().getIntExtra("newsid",0);
        dialog=new ProgressDialog(MessageActivity.this);
        dialog.setCancelable(true);

        back=(ImageView)findViewById(R.id.comments_back);

        commentsCounts=(TextView)findViewById(R.id.comments_counts);
        longCounts=(TextView)findViewById(R.id.long_counts);
        shortCounts=(TextView)findViewById(R.id.short_counts);

        scrollView=(ScrollView)findViewById(R.id.scroll_view);
        layoutshowshortcomments=(RelativeLayout)findViewById(R.id.show_short_comments_layout);
        layoutnolongcomments=(RelativeLayout) findViewById(R.id.comments_nocomment_layout);

        messageavtivity=(LinearLayout)findViewById(R.id.message_activity_layout);
        messagetitle=(LinearLayout)findViewById(R.id.message_title_layou);

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

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        displayheight=dm.heightPixels;

        scrollView.setOnTouchListener(new View.OnTouchListener() {             //对scrollview是否滚动到底部的监听
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View childView=scrollView.getChildAt(0);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (childView!= null
                                && childView .getMeasuredHeight() ==v.getScrollY() +v. getMeasuredHeight()
                                &&childView.getMeasuredHeight()>messageavtivity.getHeight()) {
                            //如果子View不为空并且子view的高度小于scrollview的高度加上滑动的距离,表示到达底部
                            if (!shortcommentsshowed&&longCommentsList!=null){    //如果短评论没有显示（只显示长评论,并且是有长评论存在
                                dialog.setMessage("更多长评论加载中...");
                                dialog.show();
                                showMoreLongComments();

                            }else if (shortcommentsshowed){                     //如果短评论显示中
                                dialog.setMessage("更多短评论加载中...");
                                dialog.show();
                                showMoreShortComments();
                            }
                        }


                        break;
                }
                return false;
            }
        });

        showCommentsCounts();
        showLongComments();
        scrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                View childview=scrollView.getChildAt(0);    //得到子view

                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                int statusBarHeight1=0;
                if (resourceId > 0) {
                    //根据资源ID获取响应的尺寸值
                    statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
                }           //获取状态栏高度
                int bottom1=layoutshowshortcomments.getBottom();
                int scrollheight1=displayheight-messagetitle.getMeasuredHeight()-layoutshowshortcomments.getMeasuredHeight()-statusBarHeight1;
                int scrollheight2=layoutshowshortcomments.getTop()-childview.getTop();
                if  (childview.getBottom()>oldBottom&&needscrollby){
//                    Toast.makeText(MessageActivity.this,"3",Toast.LENGTH_SHORT).show();
                        if  (bottom1>=displayheight){
                            scrollView.smoothScrollBy(0, scrollheight1);
                            needscrollby=false;
//                            Toast.makeText(MessageActivity.this,"2",Toast.LENGTH_SHORT).show();
                        }else if (bottom1<displayheight){
                            scrollView.smoothScrollBy(0, scrollheight2);
//                            Toast.makeText(MessageActivity.this,"1",Toast.LENGTH_SHORT).show();
                        }


                }
            }
        });
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
                        layoutnolongcomments.setVisibility(View.VISIBLE);
                    }
                    shortcommentsshowed=true;
                    if (layoutshowshortcomments.getBottom()==scrollView.getChildAt(0).getBottom()){
                        needscrollby=true;
                    }
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

//                scrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                    @Override
//                    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//
//                        delta=v.getHeight()/2;
//                        if (needscrollby){         //第一次启动此活动时需要滚动的信标为true，表示此时点击显示短评时需要滚动
//                            if (scrollView.getChildAt(0).getBottom()>oldBottom) {
//                                scrollView.smoothScrollBy(0, delta);
//                            }
//                        }
//                    }
//                });
            default:
                break;
        }
    }

    public void showLongComments(){                                         //显示长评论

        if (newsid!=0){
            HttpUtils.getLongComments(newsid, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    Gson gson=new Gson();
                    LongComments longComments=gson.fromJson(response.body().string(),LongComments.class);
                    if (longComments!=null){
                        if  (longComments.comments!=null&&longComments.comments.size()!=0){
                            longCommentsList=longComments.comments;
                            longCommentsLastetUserId=longCommentsList.get(longCommentsList.size()-1).authorid;
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (longCommentsList!=null&&longCommentsList.size()!=0){
                                    hasLongComments=true;
                                    longadapter =new LongCommentsAdapter(MessageActivity.this,longCommentsList);
                                    longcommentslistview.setAdapter(longadapter);
                                    layoutnolongcomments.setVisibility(View.GONE);

                                }else {
                                    int height= messageavtivity.getHeight()-messagetitle.getMeasuredHeight()-2* layoutshowshortcomments.getMeasuredHeight();
                                    layoutnolongcomments.setMinimumHeight(height);
                                    layoutnolongcomments.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }

                }
            });
        }
    }

    public void showShortComments(){                                //显示短评论
        if (newsid!=0){
            HttpUtils.getShortComments(newsid, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson=new Gson();
                    ShortComments shortComments=gson.fromJson(response.body().string(),ShortComments.class);
                    if (shortComments!=null){
                        if (shortComments.comments!=null&&shortComments.comments.size()!=0){
                            shortCommentsList=shortComments.comments;
                            shortCommentsLatestUserId=shortCommentsList.get(shortCommentsList.size()-1).id;
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
//                                if (shortAdapter==null){
//                                    if (savedAdapter==null){
//                                        shortAdapter=new ShortCommentsAdapter(MessageActivity.this,shortCommentsList);
//                                        savedAdapter=shortAdapter;
//                                    }else {
//                                        shortAdapter=savedAdapter;
//                                    }
//                                }
                                    shortAdapter=new ShortCommentsAdapter(MessageActivity.this,shortCommentsList);
                                    shortcommentslisview.setAdapter(shortAdapter);
                                }
                            });

                        }

                    }
                }
            });
        }
    }

    private void showCommentsCounts(){

        HttpUtils.getExtra(newsid, new Callback() {
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
    private void showMoreLongComments(){
        HttpUtils.getMoreLongComments(newsid, longCommentsLastetUserId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                LongComments longComments=gson.fromJson(response.body().string(),LongComments.class);
                final List<LongComments.Comment> comments=longComments.comments;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (comments.size()!=0){
                            for (int i=0;i<comments.size();i++){
                                longCommentsList.add(comments.get(i));
                            }
                            longCommentsLastetUserId=comments.get(comments.size()-1).authorid;
                            longadapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }else {
                            dialog.dismiss();
                            Toast.makeText(MessageActivity.this,"没有新的长评论了",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private void showMoreShortComments(){
        HttpUtils.getMoreShortComments(newsid, shortCommentsLatestUserId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
               final ShortComments shortComments=gson.fromJson(response.body().string(),ShortComments.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<ShortComments.Comment> comments=shortComments.comments;
                        if (comments.size()!=0){
                            for (int i=0;i<comments.size();i++){
                                shortCommentsList.add(comments.get(i));
                                shortAdapter.notifyDataSetChanged();
                            }
                            shortCommentsLatestUserId=comments.get(comments.size()-1).id;
                            dialog.dismiss();
                        }else if (comments.size()==0){
                            dialog.dismiss();
                            Toast.makeText(MessageActivity.this,"没有新的短评论了",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }

}
