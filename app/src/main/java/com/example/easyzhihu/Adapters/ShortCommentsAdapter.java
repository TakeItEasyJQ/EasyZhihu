package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.R;
import com.example.easyzhihu.Utils.TimeUtil;
import com.example.easyzhihu.gson.ShortComments;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by My Computer on 2018/1/23.
 */

public class ShortCommentsAdapter extends RecyclerView.Adapter<ShortCommentsAdapter.ViewHolder> {

    private Context context;
    private List<ShortComments.Comment> comments;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView avatar;
        public TextView likecounts;
        public TextView author;
        public TextView content;
        public TextView reply_author;
        public TextView reply_content;
        public TextView time;

        public ViewHolder(View view) {
            super(view);
            avatar=(CircleImageView)view.findViewById(R.id.comments_long_item_avatar);
            author=(TextView)view.findViewById(R.id.comments_long_item_author);
            content=(TextView)view.findViewById(R.id.comments_long_item_content);
            likecounts=(TextView)view.findViewById(R.id.comments_long_item_likecounts);
            reply_author=(TextView)view.findViewById(R.id.comments_reply_name);
            reply_content=(TextView)view.findViewById(R.id.comments_reply_content);
            time=(TextView)view.findViewById(R.id.comments_item_time);
        }
    }

    public ShortCommentsAdapter(Context context,List<ShortComments.Comment> comments) {
        super();
        this.context=context;
        this.comments=comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShortComments.Comment comment=comments.get(position);

        Glide.with(context).load(comment.avatar).into(holder.avatar);
        holder.author.setText(comment.author);
        holder.likecounts.setText(String.valueOf(comment.likes) );
        holder.content.setText(comment.content);
        holder.time.setText(TimeUtil.getDateToString(comment.time));
        if (comment.reply_to!=null){
            if (comment.reply_to.status==0){
                holder.reply_author.setText("// "+comment.reply_to.author+":");
                holder.reply_content.setText(comment.reply_to.content);
            }else {
                holder.reply_content.setText(comment.reply_to.err_msg);
            }
            holder.reply_author.setVisibility(View.VISIBLE);
            holder.reply_content.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


}


