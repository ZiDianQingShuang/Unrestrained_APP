package com.unrestrained.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;

import com.unrestrained.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsPageActivity extends AppCompatActivity {


    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void test_eventBus(View view){
        EventBus.getDefault().post("hahhah");
//        EventBus.builder().installDefaultEventBus().
        int num = 123;
        EventBus.getDefault().post(num);
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (this.getClass().equals(MainActivity.class)) {
            Snackbar.make(drawer_layout,"",Snackbar.LENGTH_LONG).show();
        } else {
            if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
                drawer_layout.closeDrawers();
            }else{
                super.onBackPressed();
            }
        }
    }
}
