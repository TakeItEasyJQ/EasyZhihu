package com.example.easyzhihu.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.Activities.MainActivity;
import com.example.easyzhihu.Adapters.FragmentAdapter;
import com.example.easyzhihu.Fragments.ViewPagerFragment;
import com.example.easyzhihu.R;
import com.example.easyzhihu.Utils.Configs;
import com.example.easyzhihu.db.TopFiveItemDB;
import com.example.easyzhihu.gson.HomePageContent;
import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My Computer on 2017/12/29.
 */

public class TopfiveTask extends AsyncTask <HomePageContent,Integer,Boolean>
        implements ViewPager.OnPageChangeListener {

    private Context context;
    private ViewPager viewPager;
//    private List<TopFiveItemDB> topFiveItems;
//    private List<TopFiveItemDB> topFiveItemList;
    private List<String> images=new ArrayList<>();
    private List<Fragment> fragmentList=new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    private FragmentManager manager;

    private boolean topfiveUpdated;


//    Handler mhandler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 1:
//
//
//            }
//        }
//    };

    public TopfiveTask(Context context , ViewPager pager,TextView title,FragmentManager manager) {
        super();
        this.context=context;
        this.viewPager=pager;
        this.manager=manager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        topFiveItems= DataSupport.order("id desc").find(TopFiveItemDB.class);
    }

    @Override
    protected Boolean doInBackground(HomePageContent... params) {

        HomePageContent homePageContent=params[0];

        if (homePageContent==null){
            publishProgress(Configs.TaskObject_Null);
//            topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);
            MainActivity.topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);
            return false;
        }else {
            if (MainActivity.topFiveItemList.size()==0){                               //数据库无数据
                for (int i=0;i<homePageContent.topFiveList.size();i++){
                    TopFiveItemDB topFiveItem=new TopFiveItemDB();
                    topFiveItem.setDate(homePageContent.date);
                    topFiveItem.setTitle(homePageContent.topFiveList.get(i).title);
                    topFiveItem.setNewsid(homePageContent.topFiveList.get(i).newsid);
                    topFiveItem.setImage(homePageContent.topFiveList.get(i).image);
                    topFiveItem.save();
                }

                MainActivity.topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);

//                topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);
                return true;
            }else {                                                             //数据库已有数据

                for (int i=0;i<MainActivity.topFiveItemList.size();i++) {
                    if (MainActivity.topFiveItemList.get(i).getNewsid() != homePageContent.topFiveList.get(i).newsid) {
                        topfiveUpdated=true;
                        break;          //如果有对应位置id不同的情况则表明topfive更新了 跳出for煦循环去更新topfivelist
                    }
                }
                if (topfiveUpdated){
                    DataSupport.deleteAll(TopFiveItemDB.class);                 //数据库清空后重新添加数据

                    for (int i=0;i<homePageContent.topFiveList.size();i++){
                        TopFiveItemDB topFiveItem=new TopFiveItemDB();
                        topFiveItem.setDate(homePageContent.date);
                        topFiveItem.setTitle(homePageContent.topFiveList.get(i).title);
                        topFiveItem.setNewsid(homePageContent.topFiveList.get(i).newsid);
                        topFiveItem.setImage(homePageContent.topFiveList.get(i).image);
                        topFiveItem.save();
                    }
                    MainActivity.topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);
//                    topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);




                }else {
                    MainActivity.topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);
                    return false;
                }





//                if (MainActivity.topFiveItemList.get(0).getNewsid()!=homePageContent.topFiveList.get(0).newsid){
//
//                    DataSupport.deleteAll(TopFiveItemDB.class);                 //数据库清空后重新添加数据
//
//                    for (int i=0;i<homePageContent.topFiveList.size();i++){
//                        TopFiveItemDB topFiveItem=new TopFiveItemDB();
//                        topFiveItem.setDate(homePageContent.date);
//                        topFiveItem.setTitle(homePageContent.topFiveList.get(i).title);
//                        topFiveItem.setNewsid(homePageContent.topFiveList.get(i).newsid);
//                        topFiveItem.setImage(homePageContent.topFiveList.get(i).image);
//                        topFiveItem.save();
//                    }
//                    MainActivity.topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);
//                    topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);
//                }else if (MainActivity.topFiveItemList.get(0).getNewsid()==homePageContent.topFiveList.get(0).newsid){
////                    topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);
//
//                    MainActivity.topFiveItemList=DataSupport.findAll(TopFiveItemDB.class);
//
//                    return true;


            }

        }

        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        switch (values[0]){
            case 1:
                Toast.makeText(context,"wrong",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

        for (int i=0;i<MainActivity.topFiveItemList.size();i++){         //获取Top5的图片地址数组
            TopFiveItemDB topFiveItem=MainActivity.topFiveItemList.get(i);
            images.add(topFiveItem.getImage());
        }
        fragmentList=getFragmentList(images);
        fragmentAdapter=new FragmentAdapter(this.manager,fragmentList);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(this);

        onPageSelected(0);



    }

    public List<Fragment> getFragmentList(List<String> images){
        List<Fragment> fragments=new ArrayList<>();
        for (int i=0;i<images.size();i++){
            fragments.add(new ViewPagerFragment());
        }
        return fragments;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ViewPagerFragment fragment=
                (ViewPagerFragment) manager.findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem() );
        fragment.setImage(MainActivity.topFiveItemList.get(position).getImage());
        fragment.settitle(MainActivity.topFiveItemList.get(position).getTitle());
        fragment.setNewsId(MainActivity.topFiveItemList.get(position).getNewsid());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
