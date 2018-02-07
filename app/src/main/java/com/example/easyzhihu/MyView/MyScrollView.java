package com.example.easyzhihu.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * Created by My Computer on 2017/12/12.
 */

public class MyScrollView extends ScrollView {

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View v=(View)getChildAt(getChildCount()-1);
        int d=v.getBottom();
        if (d==getHeight()+getScrollY()){
//            Toast.makeText(getContext(),"bottom: "+String.valueOf(getBottom()+"height: "+String.valueOf(getHeight())+" measureheight: "+String.valueOf(getMeasuredHeight())),Toast.LENGTH_SHORT).show();
        }
    }
}
