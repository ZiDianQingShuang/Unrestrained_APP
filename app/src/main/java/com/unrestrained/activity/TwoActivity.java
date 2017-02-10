package com.unrestrained.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.blankj.utilcode.utils.SDCardUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.unrestrained.R;
import com.unrestrained.service.TimeService;
import com.unrestrained.utils.ToastUtility;
import com.yuyh.library.EasyGuide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.res.Configuration.UI_MODE_NIGHT_MASK;
import static android.content.res.Configuration.UI_MODE_NIGHT_NO;
import static android.content.res.Configuration.UI_MODE_NIGHT_UNDEFINED;
import static android.content.res.Configuration.UI_MODE_NIGHT_YES;

public class TwoActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.switchCompat)
    SwitchCompat switchCompat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setTheme(sp.getBoolean("APPTHEME", false) ? R.style.NightTheme : R.style.DayTheme);

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

//        SharedPreferences.Editor editor = SharedPreferencesCompat

        switchCompat.setChecked(sp.getBoolean("APPTHEME", false));

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("APPTHEME", isChecked);
                editor.apply();
                Configuration config = getResources().getConfiguration();
            }
        });

    }

    private List<String> lists;

    private LocalAdapter adapter;


    /**
     * 按钮点击事件
     *
     * @param view
     */
    public void visitWeb(View view) {
        TimeService.startService(this);
    }


//    // 获取指定Activity的截屏，保存到png文件
//    String filenameTemp = "/mnt/sdcard/temp";

    public void testOtto(View view) {
//        mBus.post("this is otto message");
//        switchThemeMode();
//        generateCaptureScrennImage();

//        getWindow().getDecorView().setBackgroundColor(Color.RED);

        String freeSpace = SDCardUtils.getFreeSpace();

        ((Button) view).setText(freeSpace);
    }

    private void generateCaptureScrennImage() {
        View rootView = getWindow().getDecorView().getRootView();
        rootView.setDrawingCacheEnabled(true);

        Bitmap bitmap = rootView.getDrawingCache();
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + System.currentTimeMillis() + ".png";

        try {
            FileOutputStream fos = new FileOutputStream(path);
            boolean isOK = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            ToastUtility.showToast("" + isOK);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 系统自带的切换白天、夜间模式，有闪屏现象
     */
    private void switchThemeMode() {
        int uiMode = getResources().getConfiguration().uiMode & UI_MODE_NIGHT_MASK;
        switch (uiMode) {
            case UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
                break;
            case UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
                break;
            case UI_MODE_NIGHT_UNDEFINED:
                System.out.println("undefiened");
                break;
            default:
                break;
        }
    }

    /**
     * 设备截屏，不包括状态栏
     *
     * @param activity
     * @return
     */
    private static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Log.i("TAG", "" + statusBarHeight);

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        // Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight
        ); //
        view.destroyDrawingCache();
        return b;
    }

    @Subscribe
    public void ottoString0(String content) {
        ToastUtility.showToast("0: " + content);
    }

//    @Subscribe
//    public void ottoString1(String content) {
//        ToastUtility.showToast("1: " + content);
//    }


    private Bus mBus;

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mBus) {
            mBus = new Bus();
        }
        mBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mBus) {
            mBus.unregister(this);
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
