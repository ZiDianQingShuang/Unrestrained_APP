package com.unrestrained.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.util.LruCache;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.unrestrained.R;
import com.unrestrained.widget.MyExpandTextView;

import org.xutils.view.annotation.ContentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 盛放自定义视图的容器
 */
@ContentView(R.layout.activity_view)
public class ViewActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.expandableTextView)
    ExpandableTextView expandableTextView;

    @BindView(R.id.myExpandTextView)
    MyExpandTextView myExpandTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);

        // 4.4及以上版本开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager sbtm = new SystemBarTintManager(this);
        sbtm.setNavigationBarTintEnabled(true);
        sbtm.setStatusBarTintEnabled(true);
        // 自定义颜色
        sbtm.setTintColor(Color.parseColor("#24b7a4"));

//        MySurfaceView msv = new MySurfaceView(this);
//        setContentView(msv);
//        GLES32 gles32 = GLES32.get;

        final int drawableRes = R.drawable.ic_launcher;
        //ImageView imageView = ...;
//        Observable.create(new Observable.OnSubscribe<Drawable>() {
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = getResources().getDrawable(drawableRes);
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
//            }
//        }).subscribe(new Observer<Drawable>() {
//            @Override
//            public void onNext(Drawable drawable) {
//                imageView.setImageDrawable(drawable);
//            }
//
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
//            }
//        });

        // Observable.create(null).distinct().filter().take(1).buffer(10).

        Observable.just("start with you and you know?").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                if (!TextUtils.isEmpty(s)) {
                    return "<h1>" + s + "</h1>";
                }
                return null;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Spanned spanned = Html.fromHtml(s);

                SpannableStringBuilder ssb = new SpannableStringBuilder(spanned);

                ssb.setSpan(new BackgroundColorSpan(Color.RED), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(ssb);
            }
        });

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            sb.append(i + "\n");
        }

        expandableTextView.setText(sb.toString());

        myExpandTextView.setText(sb.toString(), false);

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

        LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(10) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return super.sizeOf(key, value);
            }
        };

        try {
            DiskLruCache.create(FileSystem.SYSTEM, null, getPackageManager().getPackageInfo(getPackageName(), 0).versionCode, 0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
