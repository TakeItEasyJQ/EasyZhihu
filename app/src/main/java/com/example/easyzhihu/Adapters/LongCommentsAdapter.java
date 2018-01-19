package com.example.easyzhihu.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.easyzhihu.gson.LongComments;

import java.util.List;

/**
 * Created by My Computer on 2018/1/19.
 */

public class LongCommentsAdapter extends ArrayAdapter {

    private int resourceId;
    private List<LongComments> longCommentses;



    public LongCommentsAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resourceId=resource;
        this.longCommentses=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
