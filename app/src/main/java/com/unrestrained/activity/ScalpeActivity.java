package com.unrestrained.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unrestrained.R;
import com.unrestrained.mvp.view.LoadDataView;
import com.unrestrained.presenter.LoadDataPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dalvik.system.DexClassLoader;

public class ScalpeActivity extends AppCompatActivity implements LoadDataView {
    private LoadDataPresenter mLoadDataPresenter;

    @BindView(R.id.listviewcompat)
    RecyclerView listviewcompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scalpe);
        ButterKnife.bind(this);

        mLoadDataPresenter = new LoadDataPresenter(this);
        mLoadDataPresenter.loadData(2, 20);
    }


    @Override
    public void showProgress() {
        //PathClassLoader lo = PathClassLoader.getSystemClassLoader();
        DexClassLoader dexClassLoader = new DexClassLoader("", "", "", null);
        //BaseDexClassLoader  DexClassLoader PathClassLoader

        Toast.makeText(this,"showProgress",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void hideProgress() {
        Toast.makeText(this,"hideProgress",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(List<String> stringList) {
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stringList);
        listviewcompat.setLayoutManager(new LinearLayoutManager(this));

        MyRecylerAdapter adapter = new MyRecylerAdapter(stringList);

        listviewcompat.setAdapter(adapter);

    }

    @Override
    public void showMsg(String msg) {
    }


    /**
     * Recycler适配器
     */
    public static class MyRecylerAdapter extends RecyclerView.Adapter<MyRecylerAdapter.MyViewHolder> {

        private List<String> mLists;

        public MyRecylerAdapter(List<String> mLists) {
            this.mLists = mLists;
        }

        @Override
        public MyRecylerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
            MyRecylerAdapter.MyViewHolder holder = new MyRecylerAdapter.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyRecylerAdapter.MyViewHolder holder, int position) {
            String string = mLists.get(position);
            holder.textView.setText(string);
        }

        @Override
        public int getItemCount() {
            return null == mLists ? 0 : mLists.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            @BindView(android.R.id.text1)
            TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }


        }

    }


}
