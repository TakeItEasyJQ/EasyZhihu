package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.R;
import com.example.easyzhihu.db.LatestStoryDB;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by My Computer on 2017/12/26.
 */

public class LatestAdapter extends ArrayAdapter {

    private int resourceId;

    public LatestAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LatestStoryDB latestStory= (LatestStoryDB) getItem(position);
        View view;
        ViewHolder holder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            holder=new ViewHolder();
            holder.titie=(TextView)view.findViewById(R.id.item_title);
            holder.images=(ImageView)view.findViewById(R.id.item_images);
            if (latestStory.isHasreaded()){
                holder.titie.setTextColor(Color.GRAY);
            }
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder) view.getTag();
        }

        holder.titie.setText(latestStory.getTitle());
        Glide.with(getContext()).load(latestStory.getImages().get(0)).into(holder.images);

        return view;

    }
    class ViewHolder{
        TextView titie;
        ImageView images;
    }
}
