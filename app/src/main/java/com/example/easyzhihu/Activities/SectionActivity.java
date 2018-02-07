package com.example.easyzhihu.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyzhihu.Adapters.SectionActivityAdapter;
import com.example.easyzhihu.HttpUtils.HttpUtils;
import com.example.easyzhihu.R;
import com.example.easyzhihu.gson.Section;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SectionActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private TextView sectionname;
    private RecyclerView sectionlistview;

    private Intent intent;
    private int sectionid;
    private String sectionName;
    private int timestamp;

    private List<Section.Stories> stories;
    private SectionActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        intent=getIntent();
        sectionid=intent.getIntExtra("sectionid",0);
        sectionName=intent.getStringExtra("sectionname");
        back=(ImageView)findViewById(R.id.section_back);
        sectionname=(TextView)findViewById(R.id.section_name);
        sectionlistview=(RecyclerView)findViewById(R.id.section_listview);
        LinearLayoutManager manager=new LinearLayoutManager(SectionActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        sectionlistview.setLayoutManager(manager);
        sectionname.setText(sectionName);

        sectionlistview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)){
                    getSectionBefore();
                }
            }
        });

        back.setOnClickListener(this);
        getSection();

    }



    public void getSection(){

        if (sectionid!=0){
            HttpUtils.getSection(sectionid, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson  gson=new Gson();
                    final Section section=gson.fromJson(response.body().string(),Section.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (section!=null){
                                stories=section.stories;
                                timestamp=section.timestamp;
                                if (stories.size()!=0){
                                    adapter=new SectionActivityAdapter(SectionActivity.this,stories);
                                    sectionlistview.setAdapter(adapter);
                                }
                            }
                        }
                    });
                }
            });
        }

    }

    public void getSectionBefore(){
        if (sectionid!=0&&timestamp!=0){
            HttpUtils.getSectionBefore(sectionid, timestamp, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(SectionActivity.this,"网络错误，请检查后重试...",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson=new Gson();
                    final Section section=gson.fromJson(response.body().string(),Section.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ( section!=null){
                                timestamp=section.timestamp;
                                List<Section.Stories> storiesList=section.stories;
                                if (storiesList.size()!=0){
                                    for (int i=0;i<storiesList.size();i++){
                                        stories.add(storiesList.get(i));
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.section_back:
                finish();
                break;




            default:
                break;

        }
    }
}
