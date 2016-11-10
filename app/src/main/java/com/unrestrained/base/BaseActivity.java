package com.unrestrained.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.OnClick;

/**
 * 基类
 * Created by wangxiaofei on 2016/10/26.
 */
public abstract class BaseActivity<T extends  BasePresentImpl> extends AppCompatActivity {

    protected abstract int getLayoutResID();

    protected abstract void initView();

    protected abstract void initData();

    protected  T t;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayoutResID());
    }

    @Nullable
    @OnClick
    protected void click(View view) {

    }


}
