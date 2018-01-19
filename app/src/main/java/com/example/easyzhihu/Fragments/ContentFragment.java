package com.example.easyzhihu.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyzhihu.Activities.MessageActivity;
import com.example.easyzhihu.Interfaces.IFragmentContentListener;
import com.example.easyzhihu.R;


/**
 * Created by My Computer on 2017/12/12.
 */

public class ContentFragment extends Fragment {


    public ImageView back;
    public ImageView share;
    public ImageView favor;
    public ImageView message;
    public ImageView agree;
    public TextView messageCount;
    public TextView agreeCount;

    public Toolbar toolbar;
    public ActionBar actionBar;

    public TextView headlineContentTitle;
    public TextView headlineSource;
    public ImageView headlineImg;

    public WebView webView;
    public TextView viewMore;

    public RelativeLayout layout;
    public TextView sectionName;
    public ImageView sectionImag;

    public WebSettings settings;

    IFragmentContentListener listener;

    public boolean isFinished;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_content,container,false);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        toolbar=(Toolbar)view.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        back=(ImageView)view.findViewById(R.id.fragment_back);                      //title
        share=(ImageView)view.findViewById(R.id.fragment_share);
        favor=(ImageView)view.findViewById(R.id.fragment_favor);
        message=(ImageView)view.findViewById(R.id.fragment_message);
        agree=(ImageView)view.findViewById(R.id.fragment_agree);
        messageCount=(TextView)view.findViewById(R.id.fragment_messagetxt);
        agreeCount=(TextView)view.findViewById(R.id.fragment_agreetxt);


        headlineContentTitle=(TextView)view.findViewById(R.id.headline_title);      //headline
        headlineSource=(TextView)view.findViewById(R.id.headline_source);
        headlineImg=(ImageView)view.findViewById(R.id.headline_img);
        headlineImg.setScaleType(ImageView.ScaleType.CENTER_CROP);


        webView=(WebView)view.findViewById(R.id.web_view);                      //content
        viewMore=(TextView)view.findViewById(R.id.button_view_more);


        layout=(RelativeLayout) view.findViewById(R.id.section_layout);          //section
        sectionName=(TextView)view.findViewById(R.id.content_section_name);
        sectionImag=(ImageView)view.findViewById(R.id.content_section_img);

        sectionImag.setScaleType(ImageView.ScaleType.CENTER_CROP);

        settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);


//        util=new BodyUtil(Configs.content);
//        util.init();
//        ShareUrl=util.getViewMoreUrl();
//        util.RemoveViewMore();
//        webView.loadDataWithBaseURL(null,util.getNewContent(),"text/html","GBK",null);


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listener.setFragmentContent();
    }



    public void setFragmentContentListenter(IFragmentContentListener listenter){
        this.listener=listenter;

    }



    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu,menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menu_night_mode:
//                Toast.makeText(getContext(),"1",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.menu_settings:
//                Toast.makeText(getContext(),"2",Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                return false;
//        }
//
//        return true;
//    }




}
