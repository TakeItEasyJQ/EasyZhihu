package com.example.easyzhihu.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyzhihu.Activities.MainActivity;

/**
 * Created by My Computer on 2018/2/4.
 */

public class MyTextView extends TextView {


    private Context context;

    public MyTextView(Context context) {
        super(context);
        this.context=context;
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

//        Toast.makeText(context,String.valueOf(top)+"    " +String.valueOf(bottom),Toast.LENGTH_SHORT).show();
        MainActivity.position=top;

    }






}
