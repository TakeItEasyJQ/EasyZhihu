package com.example.easyzhihu.Activities;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.example.easyzhihu.gson.ThemeID;
import com.example.easyzhihu.gson.Themes;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ThemeContentActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ImageView themeHome;
    private TextView themeName;
    private ImageView themeheadlineImg;                 //headlineImg要用themeID中的image
    private TextView themeheadlineTitle;

    private RecyclerView themerecycler;
    private MyListView themelistview;
    private ThemeListviewAdapter listviewadapter;

    private MyListView themeslistview;
    private ThemesListAdapter adapter;

    private DrawerLayout drawer;

    private String themename;
    private int themeid;
    private String themedescription;
    private String themedisplayimage;

    public List<ThemeID.Story> stories;



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

        themeheadlineImg.setScaleType(ImageView.ScaleType.CENTER_CROP);


        drawer=(DrawerLayout)findViewById(R.id.theme_activity_drawerlayout);
        themeslistview=(MyListView)findViewById(R.id.drawer_listview);



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
                                    ThemeID.Story story=stories.get(position);
                                    Intent intent=new Intent(ThemeContentActivity.this,ActivityContent.class);
                                    intent.putExtra("newsid",story.id);
                                    intent.putExtra("fromtheme",true);
                                    intent.putExtra("image",theme.image);
                                    intent.putExtra("title",theme.name);
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
            default:
                break;
        }
    }

    public void setListeners(){
        themeHome.setOnClickListener(this);
        themeslistview.setOnItemClickListener(this);
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
}
