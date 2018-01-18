package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.Activities.EditorActivity;
import com.example.easyzhihu.R;
import com.example.easyzhihu.gson.ThemeID;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by My Computer on 2018/1/17.
 */

public class ThemeContentActivityRecyclerViewAdapter
        extends RecyclerView.Adapter <ThemeContentActivityRecyclerViewAdapter.ViewHolder>{

    private List<ThemeID.Editor> editors;
    private Context context;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView authorImg;
        public ViewHolder(View view){
            super(view);
            authorImg=(CircleImageView)view.findViewById(R.id.theme_recycler_view_img);
        }
    }


    public ThemeContentActivityRecyclerViewAdapter( Context context,List<ThemeID.Editor> editors) {
        super();
        this.context=context;
        this.editors=editors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.theme_recycler_view_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (editors!=null){

            Glide.with(context).load(editors.get(position).avatar).into(holder.authorImg);

        }



        holder.authorImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,EditorActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("editorlist", (Serializable) editors);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return editors.size();
    }

}
