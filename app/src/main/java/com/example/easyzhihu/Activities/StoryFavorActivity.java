package com.example.easyzhihu.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.easyzhihu.Adapters.FavorListviewAdapter;
import com.example.easyzhihu.R;
import com.example.easyzhihu.db.StoryFavoriteDB;

import org.litepal.crud.DataSupport;

import java.util.List;

public class StoryFavorActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private ListView favorlistview;
    private List<StoryFavoriteDB> storyFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_favor);
        favorlistview=(ListView)findViewById(R.id.favor_listview);
        storyFavorites= DataSupport.findAll(StoryFavoriteDB.class);
        back=(ImageView)findViewById(R.id.favor_back);
        back.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (storyFavorites.size()!=0){
            FavorListviewAdapter adapter=new FavorListviewAdapter(StoryFavorActivity.this,R.layout.favor_item,storyFavorites);
            favorlistview.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.favor_back:
                StoryFavorActivity.this.finish();
            default:
                break;
        }
    }
}
