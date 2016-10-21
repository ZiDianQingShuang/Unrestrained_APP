package com.unrestrained.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.unrestrained.listeners.OnItemClickListener;

import java.util.List;

/**
 * Created by wangxiaofei on 2016/10/18.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mLists;

    private OnItemClickListener mOnItemClickListener;//单项点击事件

    private boolean mIsShowFooter;//是否显示顶部加载条

    protected static final int TYPE_ITEM = 1 << 1;

    protected static final int TYPE_FOOTER = 1 << 2;

    protected int mLastPosition = Integer.MIN_VALUE;

    public BaseRecyclerViewAdapter(List<T> mLists) {
        this.mLists = mLists;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    //public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) ;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (null != lp && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams sglm_lp = (StaggeredGridLayoutManager.LayoutParams) lp;
                sglm_lp.setFullSpan(true);///////////
            }
        }
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (null == mLists) {
            return 0;
        }
        int size = mLists.size();
        if (mIsShowFooter) { //如果有底部需要总数加上1
            size++;
        }
        return size;
    }

    /**
     * 添加单项到指定的位置
     *
     * @param position
     * @param t
     */
    public void add(int position, T t) {
        if (null != mLists && null != t) {
            mLists.add(position, t);
            notifyItemInserted(position);
        }
    }

    /**
     * 添加单项到末尾
     *
     * @param t
     */
    public void add(T t) {
        if (null != mLists && null != t) {
            mLists.add(t);
            int position = mLists.size();
            notifyItemInserted(position - 1);
        }
    }

    /**
     * 添加集合
     *
     * @param lists
     */
    public void add(List<T> lists) {
        if (null != mLists && null != lists) {
            mLists.addAll(lists);
            notifyDataSetChanged();
        }
    }

    /**
     * 清空数据
     */
    public void clear() {
        if (null != mLists) {
            mLists.clear();
            notifyDataSetChanged();
        }
    }


    /**
     * 先清空然后重新添加单项数据
     *
     * @param t
     */
    public void clearAndAdd(T t) {
        clear();
        add(t);
    }

    /**
     * 先清空数据然后添加集合数据
     *
     * @param lists
     */
    public void clearAndAdd(List<T> lists) {
        clear();
        add(lists);
    }


    /**
     * 删除指定位置的数据
     *
     * @param position
     */
    public void remove(int position) {
        if (position >= 0 && position < getItemCount()) {
            mLists.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<T> getLists() {
        return mLists;
    }

    /**
     * 设置数据
     *
     * @param mLists
     */
    public void setLists(List<T> mLists) {
        this.mLists = mLists;
    }


    /**
     * 获取视图
     *
     * @param parentView
     * @param layoutId
     * @return
     */
    protected View getView(ViewGroup parentView, int layoutId) {
        return LayoutInflater.from(parentView.getContext()).inflate(layoutId, parentView, false);
    }

    /**
     * 加载更多项时候进入的动画
     *
     * @param viewHolder
     * @param position
     * @param animationId
     */
    protected void itemLoadAnimation(RecyclerView.ViewHolder viewHolder, int position, int animationId) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), animationId);
            viewHolder.itemView.setAnimation(animation);
            this.mLastPosition = position;
        }
    }


    protected boolean isFooterPosition(int position) {
        return (getItemCount() - 1) == position;
    }


}
