package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.Activities.ActivityContent;
import com.example.easyzhihu.Activities.SectionActivity;
import com.example.easyzhihu.R;
import com.example.easyzhihu.gson.Content;
import com.example.easyzhihu.gson.Section;

import java.util.List;

/**
 * Created by My Computer on 2018/1/25.
 */

public class SectionActivityAdapter extends RecyclerView.Adapter<SectionActivityAdapter.ViewHolder> {

    private Context context;
    private List<Section.Stories> stories;

    public SectionActivityAdapter(Context context,List<Section.Stories> stories) {
        super();
        this.context=context;
        this.stories=stories;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView images;
        public TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.section_item_title);
            images=(ImageView)itemView.findViewById(R.id.section_item_images);
            date=(TextView)itemView.findViewById(R.id.section_item_date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.setion_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Section.Stories storie=stories.get(position);
        holder.title.setText(storie.title);
        holder.date.setText(storie.display_date);
        if (storie.images.size()!=0){
            Glide.with(context).load(storie.images.get(0)).into(holder.images);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ActivityContent.class);
                intent.putExtra("newsid",storie.id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }
}
