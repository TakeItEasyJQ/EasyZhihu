package com.example.easyzhihu.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.easyzhihu.Adapters.LatestAdapter;
import com.example.easyzhihu.Adapters.ThemesListAdapter;
import com.example.easyzhihu.HttpUtils.HttpUtils;
import com.example.easyzhihu.MyView.MyListView;
import com.example.easyzhihu.R;
import com.example.easyzhihu.Tasks.LatestTask;
import com.example.easyzhihu.Tasks.ThemeTask;
import com.example.easyzhihu.Tasks.TopfiveTask;
import com.example.easyzhihu.db.LatestStoryDB;
import com.example.easyzhihu.db.ThemeDB;
import com.example.easyzhihu.db.TopFiveItemDB;
import com.example.easyzhihu.gson.HomePageContent;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AbsListView.OnScrollListener,AdapterView.OnItemClickListener{
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private ActionBar actionBar;
    public SwipeRefreshLayout refresh;
    public ViewPager viewPager;
    public RadioGroup group;
    public MyListView latestListview;
    public ImageView viewPagerImage;
    public TextView textView;
    public FragmentManager manager;

    public LinearLayout layout_favor;
    public LinearLayout layout_homepage;

    public static ArrayAdapter adapter;


    public static ImageView openDrawer;

    public static DrawerLayout drawer;

    public static MyListView themeListview;
    public static ThemesListAdapter themesListAdapter;

    public static List<ThemeDB> themes=DataSupport.findAll(ThemeDB.class);
    public static List<LatestStoryDB> latestStoryList=DataSupport.order("id desc").find(LatestStoryDB.class);
    public static List<TopFiveItemDB> topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        refresh=(SwipeRefreshLayout)findViewById(R.id.refresh_layout);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        group=(RadioGroup)findViewById(R.id.auto_group);
        viewPagerImage=new ImageView(this);
        latestListview=(MyListView)findViewById(R.id.main_listview);
        textView=(TextView)findViewById(R.id.view_pager_title);
        manager=getSupportFragmentManager();

        toolbar=(Toolbar)findViewById(R.id.toolbar_main);
        toolbar.setTitle("");
        actionBar=getSupportActionBar();

        layout_favor=(LinearLayout)findViewById(R.id.layout_favor);
        layout_homepage=(LinearLayout)findViewById(R.id.layout_homepage);

        openDrawer=(ImageView)findViewById(R.id.main_home);

        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);

        themeListview=(MyListView)findViewById(R.id.drawer_listview) ;
        themeListview.setOnItemClickListener(this);

//        if (actionBar!=null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.img_menu);
//        }
        setSupportActionBar(toolbar);
//        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getLatestNews();
//                refresh.setRefreshing(false);
//            }
//        });
        latestListview.setOnScrollListener(this);
        getLatestNews();
        setListeners();

        new ThemeTask(MainActivity.this,themeListview).execute();



    }

    public void getLatestNews(){
        HttpUtils.getNewsLatest(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<LatestStoryDB> list= DataSupport.order("id desc").find(LatestStoryDB.class);
                        LatestAdapter adapter=new LatestAdapter(MainActivity.this,R.layout.main_activity_listview_item,list);
                        latestListview.setAdapter(adapter);
                        getLatestNews();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                HomePageContent homePageContent=gson.fromJson(response.body().string(),HomePageContent.class);
                new LatestTask(MainActivity.this,latestListview).execute(homePageContent);
                new TopfiveTask(MainActivity.this,viewPager,textView,manager).execute(homePageContent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menu_night_mode:

                break;
            case R.id.menu_settings:

                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_home:
                drawer.openDrawer(GravityCompat.START);
                MainActivity.themesListAdapter=new ThemesListAdapter(MainActivity.this,R.layout.themes_item,MainActivity.themes);
                themeListview.setAdapter(MainActivity.themesListAdapter);
                themesListAdapter.notifyDataSetChanged();
                break;
            case R.id.layout_favor:
                Intent intent=new Intent(MainActivity.this,StoryFavorActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_homepage:
                drawer.closeDrawers();
            default:
                break;
        }

    }

    private void setListeners(){
        openDrawer.setOnClickListener(this);
        layout_favor.setOnClickListener(this);
        layout_homepage.setOnClickListener(this);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        boolean enable = false;
//        if(latestListview != null && latestListview.getChildCount() > 0){
//            // check if the first item of the list is visible
//            boolean firstItemVisible = latestListview.getFirstVisiblePosition() == 0;
//            // check if the top of the first item is visible
//            boolean topOfFirstItemVisible = latestListview.getChildAt(0).getTop() == 0;
//            // enabling or disabling the refresh layout
//            enable = firstItemVisible && topOfFirstItemVisible;
//        }
//        refresh.setEnabled(enable);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.adapter=new LatestAdapter(MainActivity.this,R.layout.main_activity_listview_item,MainActivity.latestStoryList);
        if(latestListview!=null){
            latestListview.setAdapter(MainActivity.adapter);
            MainActivity.adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (themes==null){
            themes=DataSupport.findAll(ThemeDB.class);
        }
        ThemeDB theme=themes.get(position);
        Intent intent=new Intent(MainActivity.this,ThemeContentActivity.class);
        intent.putExtra("themeid",theme.getThemeid());
        intent.putExtra("themename",theme.getName());
        intent.putExtra("description",theme.getDescription());
        intent.putExtra("displayimage",theme.getDisplayImage());
        startActivity(intent);

    }
}
