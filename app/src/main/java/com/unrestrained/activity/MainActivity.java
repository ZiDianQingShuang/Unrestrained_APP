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

import com.unrestrained.R;
import com.unrestrained.jpushdemo.ExampleUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


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

        logger.info("oncreate>>>>");

        if (null != webView) {
            webView.loadUrl("http://www.baidu.com");
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(true);


         //   webView.setOnClickListener(view -> {});

        webView.setWebViewClient(new WebViewClient() {
            @Override
            @SuppressWarnings(value={"unchecked", "deprecation"})
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }

//        ObjectAnimator animator = ObjectAnimator.of
//        animator.start();

//        RxPermissions.getInstance(this).ensure()


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

    public void forward(View view){
        Intent intent = new Intent(this, TwoActivity.class);
        startActivity(intent);
    }

    public void forward001(View view){
        Intent intent = new Intent(this, UploadFileActivity.class);
        startActivity(intent);

    }



    public void testMaterialDesign(View view){
        Intent intent = new Intent(this,NewsPageActivity.class);
        startActivity(intent);
    }


    public void refreshlistview(View view){
        Intent intent = new Intent(this,ListRefreshActivity.class);
        startActivity(intent);
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

}
