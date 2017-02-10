package com.unrestrained.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.unrestrained.R;
import com.unrestrained.jpushdemo.ExampleUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import hugo.weaving.DebugLog;


public class MainActivity extends AppCompatActivity {

    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        registerMessageReceiver();
        JPushInterface.init(getApplicationContext());

        EventBus.getDefault().register(this);

        logger.info("oncreate>>>>");

        if (null != webView) {
            webView.loadUrl("http://www.baidu.com");
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);


            //   webView.setOnClickListener(view -> {});

            webView.setWebViewClient(new WebViewClient() {
                @Override
                @SuppressWarnings(value = {"unchecked", "deprecation"})
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return false;
                }
            });
        }


    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
//                setCostomMsg(showMsg.toString());
            }
        }
    }

    public void forward(View view) {
        Intent intent = new Intent(this, TwoActivity.class);
        startActivity(intent);
    }

    public void forward001(View view) {
        Intent intent = new Intent(this, UploadFileActivity.class);
        startActivity(intent);

    }

    public void testMaterialDesign(View view) {
        Intent intent = new Intent(this, NewsPageActivity.class);
        startActivity(intent);
    }


    public void refreshlistview(View view) {
        Intent intent = new Intent(this, SwipeRefreshLayoutActivity.class);
        startActivity(intent);
    }


    public void testScalpe(View view) {
        Intent intent = new Intent(this, ScalpeActivity.class);
        startActivity(intent);
    }

    public void baidumap(View view) {
        Intent intent = new Intent(this, BaiduMapActivity.class);
        startActivity(intent);
    }

    public void animator(View view) {
        startActivity(new Intent(this, AnimatorActivity.class));
    }

    public void showNotification(View view) {
//        NewMessageNotification.notify(getApplicationContext(), "通知来了", 0);
    }

    public void localGPS(View view){
        startActivity(new Intent(this,LocalGPSActivity.class));

    }

    public void defineView(View view){
        startActivity(new Intent(this,ViewActivity.class));
    }

    public void navView(View view ){
        startActivity(new Intent(this,EasyguideviewActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (this.getClass().equals(MainActivity.class)) {
//            Snackbar.make(webView, "456", Snackbar.LENGTH_LONG).show();
//        } else {
////            super.onBackPressed();
//        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    @DebugLog
    public String callMethod(String msg) {
        Toast.makeText(this, this.getClass().getSimpleName() + "  >>> " + msg, Toast.LENGTH_SHORT).show();
        return "huge log";
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    @DebugLog
    public String callFunction(Integer num) {
        System.out.println("num = " + num);
        // Timber.i("","");
        return "huge log";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(mMessageReceiver);

        //android.service.quicksettings.Tile

    }


}
