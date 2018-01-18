package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.easyzhihu.Activities.MainActivity;
import com.example.easyzhihu.R;
import com.example.easyzhihu.db.ThemeDB;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by My Computer on 2018/1/12.
 */

public class ThemesListAdapter extends ArrayAdapter {              //MainActivity中的DrawerLayout中的Themes
    private int resourceId;

    public ThemesListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ThemeDB theme= MainActivity.themes.get(position);

        View view;
        ViewHolder holder;

        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            holder=new ViewHolder();
            holder.textView=(TextView)view.findViewById(R.id.themes_item_name);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder) view.getTag();
        }
        holder.textView.setText(theme.getName());
        return view;

    }

    class ViewHolder{
        public TextView textView;
    }
}
