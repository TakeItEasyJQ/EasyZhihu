package com.example.easyzhihu.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.widget.Toast;

import com.example.easyzhihu.Activities.MainActivity;
import com.example.easyzhihu.Adapters.ThemesListAdapter;
import com.example.easyzhihu.HttpUtils.HttpUtils;
import com.example.easyzhihu.MyView.MyListView;
import com.example.easyzhihu.R;
import com.example.easyzhihu.db.ThemeDB;
import com.example.easyzhihu.gson.Themes;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by My Computer on 2018/1/12.
 */

public class ThemeTask extends AsyncTask<Void,Integer,Boolean> {

    private Context context;
    private MyListView listView;


    public ThemeTask(Context context, MyListView listView) {
        super();
        this.context=context;
        this.listView=listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        HttpUtils.getNewsThemes(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (MainActivity.themes.size()==0){
                    Gson gson=new Gson();
                    Themes themeItem=gson.fromJson(response.body().string(),Themes.class);
                    for (int i=0;i<themeItem.others.size();i++){
                        Themes.Other other=themeItem.others.get(i);
                        ThemeDB theme=new ThemeDB();
                        theme.setThemeid(other.id);
                        theme.setName(other.name);
                        theme.setColor(other.color);
                        theme.setDisplayImage(other.displayImage);
                        theme.setDescription(other.description);
                        theme.save();
                    }
                }
                MainActivity.themes = DataSupport.findAll(ThemeDB.class);
            }
        });
      return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
