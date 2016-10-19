package com.unrestrained.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;

import com.unrestrained.listeners.OnItemClickListener;

import java.util.List;

/**
 * Created by wangxiaofei on 2016/10/18.
 */

public class BaseRecyclerViewAdapter<T> extends Adapter {

    private List<T> mLists;

    private OnItemClickListener mOnItemClickListener;//单项点击事件

    private boolean mIsShowFooter;//是否显示顶部加载条

    protected static final int TYPE_ITEM = 1 << 1;

    protected static final int TYPE_FOOTER = 1 << 2;

    protected int mLastPosition = -1;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
