package com.example.easyzhihu.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.Adapters.ThemeContentActivityRecyclerViewAdapter;
import com.example.easyzhihu.Adapters.ThemeListviewAdapter;
import com.example.easyzhihu.Adapters.ThemesListAdapter;
import com.example.easyzhihu.HttpUtils.HttpUtils;
import com.example.easyzhihu.MyView.MyListView;
import com.example.easyzhihu.R;
import com.example.easyzhihu.db.ThemeDB;
import com.example.easyzhihu.db.ThemeStoriesDB;
import com.example.easyzhihu.gson.ThemeID;
import com.example.easyzhihu.gson.Themes;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ThemeContentActivity extends AppCompatActivity
        implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ImageView themeHome;
    private TextView themeName;
    private ImageView themeheadlineImg;                 //headlineImg要用themeID中的image
    private TextView themeheadlineTitle;

    private RecyclerView themerecycler;
    private MyListView themelistview;
    private ThemeListviewAdapter listviewadapter;
    private ScrollView scrollView;

    private MyListView themeslistview;
    private ThemesListAdapter adapter;

    private DrawerLayout drawer;

    private String themename;
    private int themeid;
    private String themedescription;
    private String themedisplayimage;

    public List<ThemeID.Story> stories;

    private int latestnewsid;

    private ProgressDialog dialog;

    private LinearLayout layout_favor;
    private LinearLayout layout_homepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_content);
        themeHome=(ImageView)findViewById(R.id.theme_home);
        themeName=(TextView)findViewById(R.id.theme_activity_name);
        themeheadlineImg=(ImageView)findViewById(R.id.theme_activity_image);
        themeheadlineTitle=(TextView)findViewById(R.id.theme_activity_title);
        themerecycler=(RecyclerView)findViewById(R.id.theme_activity_recycler_view);

        themelistview=(MyListView)findViewById(R.id.theme_activity_listview);

        scrollView=(ScrollView)findViewById(R.id.theme_activity_scroll_view);

        themeheadlineImg.setScaleType(ImageView.ScaleType.CENTER_CROP);

        dialog=new ProgressDialog(ThemeContentActivity.this);
        dialog.setMessage("加载中，请稍候...");

        drawer=(DrawerLayout)findViewById(R.id.theme_activity_drawerlayout);
        themeslistview=(MyListView)findViewById(R.id.drawer_listview);

        layout_favor=(LinearLayout)findViewById(R.id.layout_favor);
        layout_homepage=(LinearLayout)findViewById(R.id.layout_homepage);


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        if (!scrollView.canScrollVertically(1)){
                            dialog.show();
                            getThemeContentBefore();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });


        init();
        setListeners();

    }

    public void init(){
        Intent intent=getIntent();
        themeid=intent.getIntExtra("themeid",0);
        themename=intent.getStringExtra("themename");
        themedescription=intent.getStringExtra("description");
        themedisplayimage=intent.getStringExtra("displayimage");
        if (themeid!=0){
            HttpUtils.getNewsTheme(themeid, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson=new Gson();
                    final ThemeID theme=gson.fromJson(response.body().string(),ThemeID.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (themename!=null){
                                themeName.setText(themename);
                            }
                            if (themedescription!=null){
                                themeheadlineTitle.setText(themedescription);
                            }
                            if (theme.image!=null){
                                Glide.with(ThemeContentActivity.this).load(theme.image).into(themeheadlineImg);
                            }else {
                                if (themedisplayimage!=null){
                                    Glide.with(ThemeContentActivity.this).load(themedisplayimage).into(themeheadlineImg);
                                }
                            }
                            stories=theme.stories;
                            if (stories!=null){
                                listviewadapter=new ThemeListviewAdapter(ThemeContentActivity.this,R.layout.theme_activity_listview_item,stories);
                                themelistview.setAdapter(listviewadapter);
                                latestnewsid=stories.get(stories.size()-1).id;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (int i=0;i<stories.size();i++){
                                            if (DataSupport.where("newsid = ?",String.valueOf(stories.get(i).id)).find(ThemeStoriesDB.class).size()==0){
                                                ThemeStoriesDB themeStorie=new ThemeStoriesDB();
                                                themeStorie.setNewsid(stories.get(i).id);
                                                themeStorie.setTitle(stories.get(i).title);
                                                themeStorie.setThemeid(themeid);
                                                themeStorie.save();
                                            }
                                        }
                                    }
                                }).start();
                            }


                            if (theme.editors!=null){
                                List<ThemeID.Editor> editors=theme.editors;

                                ThemeContentActivityRecyclerViewAdapter adapter=
                                        new ThemeContentActivityRecyclerViewAdapter(ThemeContentActivity.this,editors);
                                LinearLayoutManager manager=new LinearLayoutManager(ThemeContentActivity.this);
                                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                themerecycler.setLayoutManager(manager);
                                themerecycler.setAdapter(adapter);

                            }

                            themelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    final ThemeID.Story story=stories.get(position);
                                    Intent intent=new Intent(ThemeContentActivity.this,ActivityContent.class);

                                    intent.putExtra("newsid",story.id);
                                    intent.putExtra("fromtheme",true);
                                    intent.putExtra("image",theme.image);
                                    intent.putExtra("title",theme.name);

                                    ThemeListviewAdapter.ViewHolder holder= (ThemeListviewAdapter.ViewHolder)view.getTag();
                                    holder.title.setTextColor(Color.parseColor("#a9a9a9"));

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            List<ThemeStoriesDB>  themeStories=DataSupport.where("newsid = ?",String.valueOf(story.id)).find(ThemeStoriesDB.class);
                                            if (themeStories!=null&&themeStories.size()!=0){
                                                ThemeStoriesDB themeStory= themeStories.get(0);
                                                themeStory.setHasreaded(true);
                                                themeStory.save();
                                            }
                                        }
                                    }).start();

                                    startActivity(intent);
                                }
                            });

                        }
                    });
                }
            });

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.theme_home:
                if (MainActivity.themes!=null){
                    adapter=new ThemesListAdapter(ThemeContentActivity.this,R.layout.themes_item,MainActivity.themes);
                }
                themeslistview.setAdapter(adapter);
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.theme_activity_listview:

                break;
            case R.id.layout_favor:
                Intent intent=new Intent(ThemeContentActivity.this,StoryFavorActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_homepage:
                this.finish();
                break;
            default:
                break;
        }
    }

    public void setListeners(){
        themeHome.setOnClickListener(this);
        themeslistview.setOnItemClickListener(this);

        layout_favor.setOnClickListener(this);
        layout_homepage.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ThemeDB theme=MainActivity.themes.get(position);
        Intent intent=new Intent(ThemeContentActivity.this,ThemeContentActivity.class);
        intent.putExtra("themeid",theme.getThemeid());
        intent.putExtra("themename",theme.getName());
        intent.putExtra("description",theme.getDescription());
        intent.putExtra("displayimage",theme.getDisplayImage());
        startActivity(intent);
        this.finish();

    }

    public void getThemeContentBefore(){

        if (latestnewsid!=0){
            HttpUtils.getThemeNewsBefore(themeid, latestnewsid, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    dialog.dismiss();
                    Toast.makeText(ThemeContentActivity.this,"获取失败，请检查网络...",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson=new Gson();
                    final ThemeID themeID=gson.fromJson(response.body().string(),ThemeID.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (themeID!=null&& themeID.stories.size()!=0){
                                List<ThemeID.Story> storyList=themeID.stories;  //Gson
                                for (int i=0;i<storyList.size();i++){
                                    ThemeID.Story story=storyList.get(i);
                                    stories.add(story);

                                    List<ThemeStoriesDB> themeStories=DataSupport.where("newsid = ?",String.valueOf(story.id)).find(ThemeStoriesDB.class);
                                    if (themeStories.size()==0){
                                        ThemeStoriesDB themeStory=new ThemeStoriesDB();
                                        themeStory.setNewsid(story.id);
                                        themeStory.setThemeid(themeid);
                                        themeStory.setTitle(story.title);
                                        themeStory.save();
                                    }

                                }
                                listviewadapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            });
        }

    }

}
