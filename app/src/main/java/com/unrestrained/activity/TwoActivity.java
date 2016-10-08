package com.unrestrained.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unrestrained.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TwoActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        lists = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            lists.add(String.valueOf("num") + i);
        }
        adapter = new LocalAdapter(lists);

        recyclerView.setLayoutManager(lm);

        recyclerView.setAdapter(adapter);


    }

    private List<String> lists;

    private LocalAdapter adapter;


    /**
     * 按钮点击事件
     *
     * @param view
     */
    public void visitWeb(View view) {
//        lists.add(">>>>>>>>>" + System.nanoTime());
//        adapter.notifyDataSetChanged();
//        recyclerView.scrollToPosition(lists.size() - 1);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("http://www.baidu.com").build();

        try {
//            Response response =   client.newCall(request).execute();
//            if(null != response && response.isSuccessful()){
//               String string =  response.body().string();
//                LoggerFactory.getLogger(TwoActivity.class).info(string);
//            }
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String result = response.body().string();
                    System.out.print(result);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder> {
        private List<String> lists;

        public LocalAdapter(List<String> lists) {
            this.lists = lists;
        }

        @Override
        public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, null);
            LocalViewHolder viewHolder = new LocalViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(LocalViewHolder holder, int position) {
            holder.textView.setText(lists.get(position));
        }

        @Override
        public int getItemCount() {
            return null == lists ? 0 : lists.size();
        }

        class LocalViewHolder extends RecyclerView.ViewHolder {
            public LocalViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.tv);
            }

            private TextView textView;

        }
    }

}
