package com.example.easyzhihu.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.easyzhihu.Adapters.LongCommentsAdapter;
import com.example.easyzhihu.HttpUtils.HttpUtils;
import com.example.easyzhihu.MyView.MyListView;
import com.example.easyzhihu.R;
import com.example.easyzhihu.gson.LongComments;
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

    private RelativeLayout layoutnolongcomments;
    private RelativeLayout layoutshowshortcomments;

    private MyListView longcommentslistview;
    private MyListView shortcommentslisview;



    private boolean shortcommentsshowed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        back=(ImageView)findViewById(R.id.comments_back);

        layoutshowshortcomments=(RelativeLayout)findViewById(R.id.show_short_comments_layout);

        layoutnolongcomments=(RelativeLayout)findViewById(R.id.comments_nocomment_layout);

        longcommentslistview=(MyListView)findViewById(R.id.comments_long_listview);
        shortcommentslisview=(MyListView)findViewById(R.id.comments_short_listview);

        arrow=(ImageView)findViewById(R.id.comments_show_short);

        back.setOnClickListener(this);
        layoutshowshortcomments.setOnClickListener(this);

        showComments();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comments_back:
                this.finish();
                break;
            case R.id.show_short_comments_layout:
                if (!shortcommentsshowed){
                    shortcommentsshowed=true;
                    arrow.setImageResource(R.drawable.comments_down);
                }else {
                    shortcommentsshowed=false;
                    arrow.setImageResource(R.drawable.comments_up);
                }
            default:
                break;
        }
    }

    public void showComments(){

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
                    LongComments longComments=gson.fromJson(response.body().string(),LongComments.class);
                    final List<LongComments.Comment> comments=longComments.comments;


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (comments.size()!=0){

                                    LongCommentsAdapter adapter=new LongCommentsAdapter(MessageActivity.this,R.layout.comments_long_item,comments);
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

}
