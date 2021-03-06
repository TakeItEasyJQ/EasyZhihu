package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.Activities.ActivityContent;
import com.example.easyzhihu.Activities.MainActivity;
import com.example.easyzhihu.R;
import com.example.easyzhihu.Utils.TimeUtil;
import com.example.easyzhihu.db.LatestStoryDB;

import java.text.ParseException;
import java.util.List;

/**
 * Created by My Computer on 2018/1/30.
 */

public class LatestAdapter extends RecyclerView.Adapter<LatestAdapter.ViewHolder> {

    private List<LatestStoryDB> latestStories;
    private Context context;

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public ImageView images;

        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.item_title);
            images=(ImageView)itemView.findViewById(R.id.item_images);
        }

    }

    public LatestAdapter(Context context, List<LatestStoryDB> latestStories) {
        super();
        this.context=context;
        this.latestStories=latestStories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.main_activity_listview_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final LatestStoryDB latestStory=latestStories.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ActivityContent.class);
                intent.putExtra("newsid",latestStory.getNewsid());
                intent.putExtra("fromtheme",false);
                context.startActivity(intent);
                holder.title.setTextColor(Color.GRAY);

                //如果数据库中此项数据的已读标识为false 则改为true即标记为已读
                LatestStoryDB story=MainActivity.latestStoryList.get(position);
                if (!latestStory.isHasreaded()){
                    story.setHasreaded(true);
                    story.save();
                }


            }
        });

        if (latestStory.isHasreaded()){
            holder.title.setTextColor(Color.GRAY);
        }
        holder.title.setText(latestStory.getTitle());
        Glide.with(context).load(latestStory.getImages().get(0)).into(holder.images);
    }

    @Override
    public int getItemCount() {
        return latestStories.size();
    }



}
