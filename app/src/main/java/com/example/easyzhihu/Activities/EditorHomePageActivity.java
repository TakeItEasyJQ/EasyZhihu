package com.example.easyzhihu.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyzhihu.R;
import com.example.easyzhihu.Utils.Configs;

public class EditorHomePageActivity extends AppCompatActivity {

    private WebView webView;
    private TextView gotozhihu;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_home_page);
        webView=(WebView)findViewById(R.id.webview_editor);
        gotozhihu=(TextView)findViewById(R.id.go_to_editor_zhihu);

        intent=getIntent();
        final String zhihuurl=intent.getStringExtra("url");
        int id=intent.getIntExtra("id",0);
        webView.loadUrl(Configs.Editor_Home_Page_Head+id+Configs.Editor_Home_Page_Foot);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, final String url) {
                super.onPageFinished(view, url);
                gotozhihu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (zhihuurl!=null){
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(zhihuurl));
                            startActivity(intent);
                        }else {

                            Toast.makeText(EditorHomePageActivity.this,"该用户很懒Ծ‸ Ծ 还没申请自己的知乎主页...",Toast.LENGTH_LONG).show();

                        }

                    }
                });
                gotozhihu.setVisibility(View.VISIBLE);
            }
        });

    }
}
