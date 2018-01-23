package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.R;
import com.example.easyzhihu.db.StoryFavoriteDB;

import java.util.List;

/**
 * Created by My Computer on 2018/1/23.
 */

public class FavorListviewAdapter extends ArrayAdapter{

    private int resourceId;
    private List<StoryFavoriteDB> storyFavorites;

    public FavorListviewAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resourceId=resource;
        this.storyFavorites=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoryFavoriteDB storyFavorite=storyFavorites.get(position);
        View view;
        ViewHolder holder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.favor_item,parent,false);
            holder=new ViewHolder();
            holder.title=(TextView)view.findViewById(R.id.favor_item_title);
            holder.image=(ImageView) view.findViewById(R.id.favor_item_img);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder)view.getTag();
        }

        holder.title.setText(storyFavorite.getTitle());
        if (storyFavorite.getImageurl()!=null){
            Glide.with(getContext()).load(storyFavorite.getImageurl()).into(holder.image);
        }

        return view;
    }

    class ViewHolder{
        public TextView title;
        public ImageView image;
    }
}
