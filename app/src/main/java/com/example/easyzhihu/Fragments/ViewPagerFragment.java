package com.example.easyzhihu.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.Activities.ActivityContent;
import com.example.easyzhihu.Activities.MainActivity;
import com.example.easyzhihu.Adapters.LatestAdapter;
import com.example.easyzhihu.R;
import com.example.easyzhihu.db.LatestStoryDB;
import com.example.easyzhihu.db.TopFiveItemDB;

import org.litepal.crud.DataSupport;

/**
 * Created by My Computer on 2018/1/3.
 */

public class ViewPagerFragment extends Fragment {

    public ImageView imageView;
    public TextView textView;
    public int newsId;

    public ViewPagerFragment() {
        super();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frament_viewpager,container,false);
        imageView=(ImageView)view.findViewById(R.id.view_pager_image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        textView=(TextView)view.findViewById(R.id.view_pager_title);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),textView.getText(),Toast.LENGTH_SHORT).show();
                if (newsId!=0){
//                    TopFiveItemDB topFiveItem= DataSupport.where("title = ?" ,textView.getText().toString()).find(TopFiveItemDB.class).get(0);
                    Intent intent=new Intent(getContext(), ActivityContent.class);
                    intent.putExtra("newsid",newsId);
                    startActivity(intent);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                LatestStoryDB latestStory=DataSupport.where("newsid = ?",String.valueOf(newsId)).find(LatestStoryDB.class).get(0);
                                if (!latestStory.isHasreaded()){
                                    latestStory.setHasreaded(true);
                                    latestStory.save();
                                }
                            }catch (IndexOutOfBoundsException e){
                                return;
                            }
                            MainActivity.latestStoryList=DataSupport.order("id desc").find(LatestStoryDB.class);
                        }
                    }).start();
                }
            }
        });
        return view;
    }

    public void setImage(String url){
        Glide.with(getContext()).load(url).into(imageView);
    }
    public void settitle(String title){
        textView.setText(title);
    }
    public void setNewsId(int id){
        newsId=id;
    }
}
