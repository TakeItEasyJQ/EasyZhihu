package com.example.easyzhihu.Tasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyzhihu.Activities.ActivityContent;
import com.example.easyzhihu.Activities.MainActivity;
import com.example.easyzhihu.Adapters.LatestAdapter;
import com.example.easyzhihu.R;
import com.example.easyzhihu.Utils.Configs;
import com.example.easyzhihu.Utils.TimeUtil;
import com.example.easyzhihu.db.LatestStoryDB;
import com.example.easyzhihu.gson.HomePageContent;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by My Computer on 2017/12/28.
 */

public class LatestTask extends AsyncTask<HomePageContent,Integer,Boolean> {
    private static final String TAG = "LatestTask";
    private Context  context;
    private RecyclerView recyclerView;

    public LatestTask(Context context, RecyclerView recyclerView) {
        super();
        this.context=context;
        this.recyclerView=recyclerView;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(HomePageContent... params) {

        HomePageContent homePageContent=params[0];

        if(homePageContent==null){
            publishProgress(Configs.TaskObject_Null);
            MainActivity.latestStoryList=DataSupport.order("id desc").find(LatestStoryDB.class);

            String date=MainActivity.latestStoryList.get(0).getDate();
            MainActivity.calendar.set(TimeUtil.getYear(date),TimeUtil.getMonth(date),TimeUtil.getDay(date));
            MainActivity.date=MainActivity.calendar.getTime();
            return false;
        }else {
            if (MainActivity.latestStoryList.size() == 0) {
                int length = homePageContent.latestStoriesList.size() - MainActivity.latestStoryList.size();
                for (int i = length - 1; i >= 0; i--) {
                    LatestStoryDB latestStory = new LatestStoryDB();
                    latestStory.setDate(homePageContent.date);
                    latestStory.setTitle(homePageContent.latestStoriesList.get(i).title);
                    latestStory.setNewsid(homePageContent.latestStoriesList.get(i).newsid);
                    latestStory.setImages(homePageContent.latestStoriesList.get(i).images);
                    latestStory.save();
                }
                MainActivity.latestStoryList = DataSupport.order("id desc").find(LatestStoryDB.class);
                String date=MainActivity.latestStoryList.get(0).getDate();
                MainActivity.calendar.set(TimeUtil.getYear(date),TimeUtil.getMonth(date),TimeUtil.getDay(date));
                MainActivity.date=MainActivity.calendar.getTime();
                return true;
            } else {
                int a = Integer.valueOf(MainActivity.latestStoryList.get(0).getDate()) - Integer.valueOf(homePageContent.date);

                if (a < 0 || a == 1130) {          //非当天,更新本地数据库数据为最新获取的数据

                    DataSupport.deleteAll(LatestStoryDB.class);
                    for (int i = homePageContent.latestStoriesList.size() - 1; i >= 0; i--) {    //-1
                        LatestStoryDB latestStory = new LatestStoryDB();
                        latestStory.setDate(homePageContent.date);
                        latestStory.setTitle(homePageContent.latestStoriesList.get(i).title);
                        latestStory.setNewsid(homePageContent.latestStoriesList.get(i).newsid);
                        latestStory.setImages(homePageContent.latestStoriesList.get(i).images);
                        latestStory.save();
                    }
                    MainActivity.latestStoryList = DataSupport.order("id desc").find(LatestStoryDB.class);
                    String date=MainActivity.latestStoryList.get(0).getDate();
                    MainActivity.calendar.set(TimeUtil.getYear(date),TimeUtil.getMonth(date),TimeUtil.getDay(date));
                    MainActivity.date=MainActivity.calendar.getTime();
                    return true;

                } else {                        //当天，根据数据长度差异来进行差异化处理

                    //当天的新获取数据长度多于本地数据,想本地数据库中追加新的数据
                    if (homePageContent.latestStoriesList.size() > MainActivity.latestStoryList.size()) {
                        int length = homePageContent.latestStoriesList.size() - MainActivity.latestStoryList.size();
                        for (int i = length - 1; i >= 0; i--) {
                            LatestStoryDB latestStory = new LatestStoryDB();
                            latestStory.setDate(homePageContent.date);
                            latestStory.setTitle(homePageContent.latestStoriesList.get(i).title);
                            latestStory.setNewsid(homePageContent.latestStoriesList.get(i).newsid);
                            latestStory.setImages(homePageContent.latestStoriesList.get(i).images);
                            latestStory.save();
                        }
                        MainActivity.latestStoryList = DataSupport.order("id desc").find(LatestStoryDB.class);
                        String date=MainActivity.latestStoryList.get(0).getDate();
                        MainActivity.calendar.set(TimeUtil.getYear(date),TimeUtil.getMonth(date),TimeUtil.getDay(date));
                        MainActivity.date=MainActivity.calendar.getTime();
                        return true;
                    } else if (homePageContent.latestStoriesList.size() < MainActivity.latestStoryList.size()) {

                        DataSupport.deleteAll(LatestStoryDB.class);
                        for (int i = homePageContent.latestStoriesList.size() - 1; i >= 0; i--) {    //-1
                            LatestStoryDB latestStory = new LatestStoryDB();
                            latestStory.setDate(homePageContent.date);
                            latestStory.setTitle(homePageContent.latestStoriesList.get(i).title);
                            latestStory.setNewsid(homePageContent.latestStoriesList.get(i).newsid);
                            latestStory.setImages(homePageContent.latestStoriesList.get(i).images);
                            latestStory.save();
                        }
                        MainActivity.latestStoryList = DataSupport.order("id desc").find(LatestStoryDB.class);
                        String date=MainActivity.latestStoryList.get(0).getDate();
                        MainActivity.calendar.set(TimeUtil.getYear(date),TimeUtil.getMonth(date),TimeUtil.getDay(date));
                        MainActivity.date=MainActivity.calendar.getTime();
                        return true;
                    }else {
                        MainActivity.latestStoryList = DataSupport.order("id desc").find(LatestStoryDB.class);
//                        MainActivity.latestStoryList = DataSupport.findAll(LatestStoryDB.class);
                        String date=MainActivity.latestStoryList.get(0).getDate();
                        MainActivity.calendar.set(TimeUtil.getYear(date),TimeUtil.getMonth(date),TimeUtil.getDay(date));
                        MainActivity.date=MainActivity.calendar.getTime();
                        return true;
                    }
                }
            }
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        switch (values[0]){
            case Configs.Http_Failure:
                Toast.makeText(context,"信息获取失败，请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostExecute(Boolean aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.adapter=new LatestAdapter(context,MainActivity.latestStoryList);
        recyclerView.setAdapter(MainActivity.adapter);
        MainActivity.adapter.notifyDataSetChanged();
        if (MainActivity.progressDialog.isShowing()){
            MainActivity.progressDialog.dismiss();
        }
    }


}
