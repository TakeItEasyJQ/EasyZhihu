package com.example.easyzhihu.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.easyzhihu.R;

/**
 * Created by My Computer on 2017/12/29.
 */

public class MyListView extends ListView implements ListView.OnScrollListener{

    private View footer;
    private int totalItemCount;  //总数量
    private int lastVisibleItem;  //最后一个可见Item
    private  boolean isLoading;
    private ILoadListener listener;

    public MyListView(Context context) {
        super(context);
//        init(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
    /*MeasureSpec的specMode一共三种类型:
    EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
    AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
    UNSPECIFIED：表示子布局想要多大就多大（很少使用）*/

    public void init(Context context){
        footer= LayoutInflater.from(context).inflate(R.layout.homepage_listview_foot,null);
        footer.findViewById(R.id.homepage_footer).setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem=firstVisibleItem+visibleItemCount;
        this.totalItemCount=totalItemCount;

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastVisibleItem==totalItemCount&&scrollState==SCROLL_STATE_IDLE){
            if (!isLoading){
                isLoading=true;
                footer.findViewById(R.id.homepage_footer).setVisibility(View.VISIBLE);
                //加载数据
                listener.onLoad();
            }
        }
    }

    public void setListener(ILoadListener Ilistener){
        this.listener=Ilistener;
    }

    public interface ILoadListener{
        public void onLoad();
    }
}
