package com.unrestrained.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unrestrained.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangxiaofei on 2016/10/21.
 */

public class SomethingAdapter extends BaseRecyclerViewAdapter<String> {
    public SomethingAdapter(List<String> mLists) {
        super(mLists);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        myViewHolder = new MyViewHolder(getView(parent, R.layout.item_something));
//        switch (viewType) {
//            case TYPE_ITEM:
//                myViewHolder = new MyViewHolder(getView(parent, R.layout.item_something));
//                break;
//            default:
//                break;
//        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.tv_title.setText("title>>>" + mLists.get(position));
            myViewHolder.tv_content.setText("content >>>" + mLists.get(position));
        }
        itemLoadAnimation(holder, position, R.anim.anim_right_fadein);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;

        @BindView(R.id.tv_content)
        TextView tv_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
