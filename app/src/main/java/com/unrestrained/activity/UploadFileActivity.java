package com.unrestrained.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.unrestrained.R;
import com.unrestrained.utils.NetUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class UploadFileActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        ButterKnife.bind(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    public void download(View view) {
//        OkHttpClient client = new OkHttpClient();
////      RequestBody build = new FormEncodingBuilder().add("key0","6666").build();
//        RequestBody requestBody = new MultipartBuilder().type(MultipartBuilder.FORM).addPart(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),"{\"key0\":\"888777\"}")).build();//new Headers.Builder().add("head-key000","head-value00").build(),
//        Request request = new Request.Builder().url("http://192.168.1.81:8080/file/getfile").post(requestBody).build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                System.err.print(">>>>" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                System.out.print("]]]]]]]]]]]]]]]]]]"  + response.body().string());
//            }
//        });


//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Func1<String, Observable<?>>() {
//            @Override..

//            public Observable<?> call(String s) {
//                return null;
//            }
//        }).subscribe();

        String url = "http://www.tencent.com";

        NetUtils.getInstance().getString(this, url, null,"加载页面中...", new NetUtils.StringCallbackWithError<String>() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                System.out.println(s);
                textView.setText(s);
            }
        });

        Observable.just(1, 2, 3, 4, 5, 6)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        return null;
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        }).unsubscribe();


        try {
            ZipFile zipFile = new ZipFile("");
        } catch (ZipException e) {
            e.printStackTrace();
        }


    }

}
