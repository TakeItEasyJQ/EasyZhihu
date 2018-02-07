package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.Activities.ThemeContentActivity;
import com.example.easyzhihu.R;
import com.example.easyzhihu.db.ThemeStoriesDB;
import com.example.easyzhihu.gson.ThemeID;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by My Computer on 2018/1/15.
 */

public class ThemeListviewAdapter extends ArrayAdapter {

    private int resourceId;
    private List<ThemeID.Story> stories;
    public ThemeListviewAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resourceId=resource;
        this.stories=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ThemeID.Story story=stories.get(position);
        View view;
        ViewHolder holder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            holder=new ViewHolder();
            holder.title=(TextView)view.findViewById(R.id.theme_item_title);
            holder.img=(ImageView)view.findViewById(R.id.theme_item_img);
            holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder) view.getTag();
        }

        List<ThemeStoriesDB> themeStory= DataSupport.where("newsid = ?",String.valueOf(story.id)).find(ThemeStoriesDB.class);
        if (themeStory.size()!=0){
            if (themeStory.get(0).isHasreaded()){
                holder.title.setTextColor(Color.parseColor("#a9a9a9"));
            }
        }

        holder.title.setText(story.title);
        if (story.images!=null){
            Glide.with(getContext()).load(story.images.get(0)).into(holder.img);
            holder.img.setVisibility(View.VISIBLE);
        }else if (story.images==null){
            holder.img.setVisibility(View.GONE);
        }
        return view;
    }


    public class ViewHolder{
        public TextView title;
        public ImageView img;
    }
}
