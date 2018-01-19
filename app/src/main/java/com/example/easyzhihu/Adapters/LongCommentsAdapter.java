package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.easyzhihu.R;
import com.example.easyzhihu.gson.LongComments;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by My Computer on 2018/1/19.
 */

public class LongCommentsAdapter extends ArrayAdapter {

    private int resourceId;
    private List<LongComments.Comment> longCommentses;



    public LongCommentsAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resourceId=resource;
        this.longCommentses=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LongComments.Comment longComment=longCommentses.get(position);
        View view;
        ViewHolder holder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            holder=new ViewHolder();
            holder.avatar=(CircleImageView)view.findViewById(R.id.comments_long_item_avatar);
            holder.author=(TextView)view.findViewById(R.id.comments_long_item_author);
            holder.content=(TextView)view.findViewById(R.id.comments_long_item_content);
            holder.likecounts=(TextView)view.findViewById(R.id.comments_long_item_likecounts);
            holder.reply_author=(TextView)view.findViewById(R.id.comments_reply_name);
            holder.reply_content=(TextView)view.findViewById(R.id.comments_reply_content);
            holder.time=(TextView)view.findViewById(R.id.comments_item_time);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder)view.getTag();
        }

        Glide.with(getContext()).load(longComment.avatar).into(holder.avatar);
        holder.author.setText(longComment.author);
        holder.likecounts.setText(String.valueOf(longComment.likes) );
        holder.content.setText(longComment.content);

        if (longComment.reply_to!=null){
            if (longComment.reply_to.status==0){
                holder.reply_author.setText("//"+longComment.reply_to.author+":");
                holder.reply_content.setText(longComment.reply_to.content);
            }else {
                holder.reply_content.setText(longComment.reply_to.err_msg);
            }
        }else {
            holder.reply_author.setVisibility(View.GONE);
            holder.reply_content.setVisibility(View.GONE);
        }

        return view;

    }

    class ViewHolder{

        public CircleImageView avatar;
        public TextView likecounts;
        public TextView author;
        public TextView content;
        public TextView reply_author;
        public TextView reply_content;
        public TextView time;







    }

}
