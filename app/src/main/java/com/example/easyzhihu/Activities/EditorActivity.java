package com.example.easyzhihu.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.easyzhihu.Adapters.ThemeEditorAdapter;
import com.example.easyzhihu.R;
import com.example.easyzhihu.gson.ThemeID;

import java.util.List;

public class EditorActivity extends AppCompatActivity {

    private ImageView back;

    private Intent intent;
    private List<ThemeID.Editor> editors;

    private ListView editorlistview;
    private ThemeEditorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        back=(ImageView)findViewById(R.id.theme_editor_activity_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditorActivity.this.finish();
            }
        });

        intent=getIntent();
        editorlistview=(ListView)findViewById(R.id.theme_editor_listview);
        adapter=new ThemeEditorAdapter(EditorActivity.this,R.layout.theme_editors_item, (List) intent.getSerializableExtra("editorlist"));
        editorlistview.setAdapter(adapter);



    }

}
