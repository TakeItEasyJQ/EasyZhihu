package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easyzhihu.Activities.EditorHomePageActivity;
import com.example.easyzhihu.R;
import com.example.easyzhihu.gson.ThemeID;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by My Computer on 2018/1/17.
 */

public class ThemeEditorAdapter extends ArrayAdapter {

    private int resourceId;

    private List<ThemeID.Editor> editors;

    public ThemeEditorAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resourceId=resource;
        this.editors=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        ViewHolder holder;
        if  (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            holder=new ViewHolder();
            holder.editorImg=(CircleImageView)view.findViewById(R.id.theme_editor_item_img);
            holder.editorname=(TextView)view.findViewById(R.id.theme_editor_item_name);
            holder.editorbio=(TextView)view.findViewById(R.id.theme_editor_item_bio);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder)view.getTag();
        }

            final ThemeID.Editor editor=editors.get(position);

        holder.editorname.setText(editor.name);
        if (editor.bio!=null){
            holder.editorbio.setText(editor.bio);
        }else {
            holder.editorbio.setText("该用户很懒Ծ‸ Ծ 没有做任何自我介绍...");
        }
        Glide.with(getContext()).load(editor.avatar).into(holder.editorImg);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=editor.url;
                int id=editor.id;
                Intent intent=new Intent(getContext(), EditorHomePageActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("id",id);
                getContext().startActivity(intent);
            }
        });

        return view;
    }

    public class ViewHolder {
        private CircleImageView editorImg;
        private TextView editorname;
        private TextView editorbio;
    }
}
