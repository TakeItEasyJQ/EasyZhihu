package com.example.easyzhihu.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.easyzhihu.Adapters.LatestAdapter;
import com.example.easyzhihu.Adapters.NewsBeforeAdapter;
import com.example.easyzhihu.Adapters.NewsBeforeStoryAdapter;
import com.example.easyzhihu.Adapters.ThemesListAdapter;
import com.example.easyzhihu.HttpUtils.HttpUtils;
import com.example.easyzhihu.MyView.MyListView;
import com.example.easyzhihu.MyView.MyTextView;
import com.example.easyzhihu.R;
import com.example.easyzhihu.Tasks.LatestTask;
import com.example.easyzhihu.Tasks.ThemeTask;
import com.example.easyzhihu.Tasks.TopfiveTask;
import com.example.easyzhihu.Utils.TimeUtil;
import com.example.easyzhihu.db.LatestStoryDB;
import com.example.easyzhihu.db.NewsBeforeDB;
import com.example.easyzhihu.db.ThemeDB;
import com.example.easyzhihu.db.TopFiveItemDB;
import com.example.easyzhihu.gson.HomePageContent;
import com.example.easyzhihu.gson.NewsBefore;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,AdapterView.OnItemClickListener{
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TextView maintitle;
    private ActionBar actionBar;
    public SwipeRefreshLayout refresh;
    public ViewPager viewPager;
    public RadioGroup group;
    public RecyclerView latestListview;
    public ImageView viewPagerImage;
    public TextView viewpagertitle;
    public FragmentManager manager;

    public LinearLayout layout_favor;
    public LinearLayout layout_homepage;

    public static  LatestAdapter adapter;

    public static ImageView openDrawer;

    public static DrawerLayout drawer;

    public static LinearLayout nestedscrolllayout;

    public static NestedScrollView scrollView;

    public static MyListView themeListview;
    public static ThemesListAdapter themesListAdapter;

    public static List<ThemeDB> themes=DataSupport.findAll(ThemeDB.class);
    public static List<LatestStoryDB> latestStoryList=DataSupport.order("id desc").find(LatestStoryDB.class);
    public static List<TopFiveItemDB> topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);
    public List<NewsBeforeDB> newsBeforeList=new ArrayList<>();

    public static Date date;
    public SimpleDateFormat sf;
    public SimpleDateFormat df;
    public static Calendar calendar;

    public List<TextView> textViews;

    public static int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        refresh=(SwipeRefreshLayout)findViewById(R.id.refresh_layout);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        group=(RadioGroup)findViewById(R.id.auto_group);
        viewPagerImage=new ImageView(this);


        latestListview=(RecyclerView) findViewById(R.id.main_listview);
        LinearLayoutManager manager1=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);

        latestListview.setLayoutManager(manager1);
        latestListview.setNestedScrollingEnabled(false);


        viewpagertitle =(TextView)findViewById(R.id.view_pager_title);
        manager=getSupportFragmentManager();

        toolbar=(Toolbar)findViewById(R.id.toolbar_main);
        toolbar.setTitle("");
        maintitle=(TextView)findViewById(R.id.main_title);
        actionBar=getSupportActionBar();

        layout_favor=(LinearLayout)findViewById(R.id.layout_favor);
        layout_homepage=(LinearLayout)findViewById(R.id.layout_homepage);

        openDrawer=(ImageView)findViewById(R.id.main_home);

        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);

        scrollView=(NestedScrollView)findViewById(R.id.main_scroll_view);
        nestedscrolllayout=(LinearLayout)findViewById(R.id.home_nested_scrollview);

        themeListview=(MyListView)findViewById(R.id.drawer_listview) ;
        themeListview.setOnItemClickListener(this);

        date=new Date();
        sf=new SimpleDateFormat("MM月dd日", Locale.CHINA);
        df=new SimpleDateFormat("yyyyMMdd",Locale.CHINA);
        calendar=Calendar.getInstance();

        textViews=new ArrayList<>();
        
        setSupportActionBar(toolbar);
//        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getLatestNews();
//                refresh.setRefreshing(false);
//            }
//        });

//        latestListview.setOnScrollListener(this);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        View childview=scrollView.getChildAt(0);
                        if (childview!=null
                                &&childview.getMeasuredHeight()==v.getHeight()+v.getScrollY()
                                &&!scrollView.canScrollVertically(1)){    //监听到滑动至最底端了
                            getNewsBefore(MainActivity.date);
                        }
                        break;
//                    case MotionEvent.ACTION_MOVE:
//                       if (scrollView.== MainActivity.position){
//                           Toast.makeText(MainActivity.this,"131231",Toast.LENGTH_SHORT).show();
//                       }
//                        break;
                    default:
                        break;
                }
                return false;
            }
        });

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
                        LatestAdapter adapter=new LatestAdapter(MainActivity.this,list);
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
                new TopfiveTask(MainActivity.this,viewPager, viewpagertitle,manager).execute(homePageContent);

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
    protected void onResume() {
        super.onResume();
        MainActivity.adapter=new LatestAdapter(MainActivity.this,latestStoryList);
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



    public void getNewsBefore(Date date){


        List<NewsBeforeDB> newsBeforeDBs=DataSupport.where("date = ?",df.format(TimeUtil.getDateBefore(MainActivity.date)) ).find(NewsBeforeDB.class);

        if (newsBeforeDBs.size()!=0){
            MainActivity.date=TimeUtil.getDateBefore(MainActivity.date);        //日期调前一天
            MyTextView textView=(MyTextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.main_header,null);
            textView.setPadding(40,20,0,40);
            textView.setText(sf.format(MainActivity.date)+"  "+TimeUtil.getDayInWeek(MainActivity.date) );

            textViews.add(textView);
            nestedscrolllayout.addView(textView);


            RecyclerView recyclerView=new RecyclerView(MainActivity.this);
            recyclerView.setBackgroundColor(Color.parseColor("#e6e5e5"));
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
            NewsBeforeAdapter adapter=new NewsBeforeAdapter(MainActivity.this,newsBeforeDBs);
            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);
            nestedscrolllayout.addView(recyclerView);
            Toast.makeText(MainActivity.this,"have",Toast.LENGTH_SHORT).show();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (recyclerView.getBottom()<200){
                        Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this,"3",Toast.LENGTH_SHORT).show();

//                    Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
//                    Toast.makeText(MainActivity.this,"2",Toast.LENGTH_SHORT).show();
                }

            });


        }
        else {

            HttpUtils.getNewsBefore(df.format(MainActivity.date), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    Gson gson=new Gson();
                    NewsBefore newsBefore=gson.fromJson(response.body().string(),NewsBefore.class);
                    final List<NewsBefore.Story> stories=newsBefore.stories;
                    if (stories.size()!=0){

                        runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            MainActivity.date=TimeUtil.getDateBefore(MainActivity.date);
                            TextView textView=(TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.main_header,null);
                            textView.setPadding(40,20,0,40);
                            textView.setText(sf.format(MainActivity.date)+"  "+TimeUtil.getDayInWeek(MainActivity.date) );

                            textViews.add(textView);
                            nestedscrolllayout.addView(textView);

                            RecyclerView recyclerView=new RecyclerView(MainActivity.this);
                            recyclerView.setBackgroundColor(Color.parseColor("#e6e5e5"));
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                            NewsBeforeStoryAdapter adapter=new NewsBeforeStoryAdapter(MainActivity.this,stories);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setNestedScrollingEnabled(false);
                            nestedscrolllayout.addView(recyclerView);
                            Toast.makeText(MainActivity.this,"have no",Toast.LENGTH_SHORT).show();
//                        Toast.makeText(MainActivity.this,"1",Toast.LENGTH_SHORT).show();


                        }

                    });
                        for (int i=0;i<stories.size();i++){
                            NewsBefore.Story before=stories.get(i);
                            NewsBeforeDB news=new NewsBeforeDB();
                            news.setHasreaded(false);
                            news.setNewsid(before.newsid);
                            news.setTitle(before.title);
                            news.setDate(newsBefore.date);
                            news.setImages(before.images);
                            news.save();
                            newsBeforeList.add(news);
                        }

                    }
                }
            });
        }
    }

}
