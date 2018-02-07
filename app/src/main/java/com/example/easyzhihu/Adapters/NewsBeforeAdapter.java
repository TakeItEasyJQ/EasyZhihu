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

import com.bumptech.glide.Glide;
import com.example.easyzhihu.Activities.ActivityContent;
import com.example.easyzhihu.R;
import com.example.easyzhihu.db.NewsBeforeDB;
import com.example.easyzhihu.gson.NewsBefore;

import java.util.List;

/**
 * Created by My Computer on 2018/2/1.
 */

public class NewsBeforeAdapter extends RecyclerView.Adapter <NewsBeforeAdapter.ViewHolder> {

    private Context context;
    private List<NewsBeforeDB> newsBeforeDBs;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public ImageView images;

        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.item_title);
            images=(ImageView)itemView.findViewById(R.id.item_images);
        }
    }

    public NewsBeforeAdapter(Context context,List<NewsBeforeDB> newsBeforeDBs) {
        super();
        this.context=context;
        this.newsBeforeDBs=newsBeforeDBs;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.main_activity_listview_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NewsBeforeDB newsBefore=newsBeforeDBs.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ActivityContent.class);
                intent.putExtra("newsid",newsBefore.getNewsid());
                intent.putExtra("fromtheme",false);
                context.startActivity(intent);
                holder.title.setTextColor(Color.GRAY);

                if (!newsBefore.isHasreaded()){
                    newsBefore.setHasreaded(true);
                    newsBefore.save();
                }

            }
        });
        if (newsBefore.isHasreaded()){
            holder.title.setTextColor(Color.GRAY);
        }
        holder.title.setText(newsBefore.getTitle());
        Glide.with(context).load(newsBefore.getImages().get(0)).into(holder.images);

    }

    @Override
    public int getItemCount() {
        return newsBeforeDBs.size() ;
    }




}
