package com.unrestrained.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.exception.OkHttpException;
import com.lzy.okhttputils.request.BaseRequest;
import com.unrestrained.R;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * 网络连接工具类
 *
 * @author liupeng
 */
public class NetUtils {

//    private final static String url = "http://gyl.yinghekeji.com:80/api/"; // 正式版

    //private final static String url = "http://125.64.43.37/api/"; //远程测试

    private static String url = "http://www.baidu.com"; //本地测试  MySSM/

    // private final static String url = "http://221.237.157.106:86/api/";// 新正式版
//    private static Context cx;
    private static NetUtils nUtils;

    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    public static NetUtils getInstance() {
        if (nUtils == null) {
            nUtils = new NetUtils();
        }
        return nUtils;
    }

    /**
     * 判断网络情况
     *
     * @param context 上下文
     * @return false 表示没有网络 true 表示有网络
     */
    public boolean isNetworkAvalible(Context context) {
        boolean isOk = false;

        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            isOk = false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isOk = true;
                    }
                }
            }
        }
        if (!isOk) {
            Toast.makeText(context, "当前没有可以使用的网络，请设置网络！",
                    Toast.LENGTH_SHORT).show();
        }
        return isOk;
    }

    /**
     * 判断网络状态
     *
     * @param ctx
     * @return
     */
    public Boolean isNetwork(Context ctx) {
        connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    /**
     * 判断是否是wifi网络环境
     *
     * @param ctx
     * @return
     */
    public Boolean isWifi(Context ctx) {
        connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return info.getType() == ConnectivityManager.TYPE_WIFI;
        } else {
            return false;
        }
    }

    /**
     * 获取网络连接
     *
     * @param context
     * @return
     */
    public String getNetWorkSubTypeName(Context context) {
        String newworkSubtype = null;
        // 获得网络状态管理器
        connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            newworkSubtype = null;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        newworkSubtype = net_info[i].getSubtypeName();
                    }
                }
            }
        }
        return newworkSubtype;
    }

    /**
     * 接口
     */
    public static final class API {
        public static final String LOGIN = "GetUserLogin";
    }


    /**
     * 通用带有ProgressDialog的请求
     *
     * @param method
     * @param params
     * @param pDialog
     * @return
     */
    public boolean getString(Context cx, String method, Map<String, String> params, final ProgressDialog pDialog, final StringCallbackWithError stringCallback) {
        if (checkNetwork(cx, method, pDialog)) {
            return false;
        }
        stringCallback.setpDialog(pDialog);
//        if (method.startsWith("http") || method.startsWith("https")) {
//            OkHttpUtils.post(method)
//                    .tag(this)
//                    .params(params)
//                    .execute(stringCallback);
//        } else {
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(stringCallback);
//        }
        return true;
    }


    /**
     * 通用带有ProgressDialog的请求
     *
     * @param method
     * @param params
     * @param progressDialogTitle
     * @return
     */
    public boolean getString(Context cx, String method, Map<String, String> params, final String progressDialogTitle, final StringCallbackWithError stringCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        final ProgressDialog pDialog = showProgress(cx, progressDialogTitle);
        stringCallback.setpDialog(pDialog);
//        if (method.startsWith("http") || method.startsWith("https")) {
//            OkHttpUtils.post(method)
//                    .tag(this)
//                    .params(params)
//                    .execute(stringCallback);
//        } else {
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(stringCallback);
//        }
        return true;
    }


    /**
     * 基于OkHttp通用的请求，需要手动添加服务端错误提示信息
     *
     * @param method
     * @param params
     * @return
     */
    public boolean getString(Context cx, String method, Map<String, String> params, final StringCallback stringCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
//        if (method.startsWith("http") || method.startsWith("https")) {
//            OkHttpUtils.post(method)
//                    .tag(this)
//                    .params(params)
//                    .execute(stringCallback);
//        } else {
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(stringCallback);
//        }

        return true;
    }

    /**
     * 通用没有ProgressDialog的请求，添加了错误信息提示{@link StringCallbackWithError}
     *
     * @param method
     * @param params
     * @return
     */
    public boolean getString(Context cx, String method, Map<String, String> params, final StringCallbackWithError stringCallbackWithError) {
        if (checkNetwork(cx, method)) {
            return false;
        }
//        if (method.startsWith("http") || method.startsWith("https")) {
//            OkHttpUtils.post(method)
//                    .tag(this)
//                    .params(params)
//                    .execute(stringCallbackWithError);
//        } else {
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(stringCallbackWithError);
//        }

        return true;
    }


    /**
     * 带有 ProgressDialog 的  Json String请求
     *
     * @param method
     * @param params
     * @param pDialog
     * @param entityCallback
     * @return
     */
    public <T> boolean getString(Context cx, String method, Map<String, String> params, final ProgressDialog pDialog, final Class<T> clazz, final StringCallbackWithError entityCallback) {
        if (checkNetwork(cx, method, pDialog)) {
            return false;
        }
        entityCallback.setpDialog(pDialog);
//        if (method.startsWith("http") || method.startsWith("https")) {
//            OkHttpUtils.post(method)
//                    .tag(this)
//                    .params(params)
//                    .execute(new StringCallbackWithError<T>() {
//                        @Override
//                        public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
//                            entityResolve(s, response, clazz, entityCallback);
//                        }
//
//                    });
//        } else {
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(new StringCallbackWithError<T>() {
                    @Override
                    public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                        entityResolve(s, response, clazz, entityCallback);
                    }

                });
//        }
        return true;
    }

    /**
     * 带有 ProgressDialog 的   Json String请求
     *
     * @param method
     * @param params
     * @param progressDialogTitle
     * @param entityCallback
     * @return
     */
    public <T> boolean getString(final Context cx, String method, Map<String, String> params, final String progressDialogTitle, final Class<T> clazz, final StringCallbackWithError entityCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        final ProgressDialog pDialog = showProgress(cx, progressDialogTitle);
        entityCallback.setpDialog(pDialog);
//        if (method.startsWith("http") || method.startsWith("https")) {
//            OkHttpUtils.post(method)
//                    .tag(this)
//                    .params(params)
//                    .execute(new StringCallbackWithError<T>() {
//                        @Override
//                        public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
//                            entityResolve(s, response, clazz, entityCallback);
//                        }
//
//                    });
//        } else {
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(new StringCallbackWithError<T>() {
                    @Override
                    public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                        entityResolve(s, response, clazz, entityCallback);
                    }

                });
//        }
        return true;
    }

    /**
     * 带有 ProgressDialog 的   Json String请求
     *
     * @param method
     * @param params
     * @param progressDialogTitle
     * @param entityCallback
     * @return
     */
    public <T> boolean getString(Context cx, String method, Map<String, String> params, ArrayList<File> files, final String progressDialogTitle, final Class<T> clazz, final StringCallbackWithError entityCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        final ProgressDialog pDialog = showProgress(cx, progressDialogTitle);
        entityCallback.setpDialog(pDialog);
//        if (method.startsWith("http") || method.startsWith("https")) {
//            OkHttpUtils.post(method)
//                    .tag(this)
//                    .params(params)
//                    .addFileParams("file", files)
//                    .execute(new StringCallbackWithError<T>() {
//                        @Override
//                        public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
//                            entityResolve(s, response, clazz, entityCallback);
//                        }
//                    });
//        } else {
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .addFileParams("file", files)
                .execute(new StringCallbackWithError<T>() {
                    @Override
                    public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                        entityResolve(s, response, clazz, entityCallback);
                    }
                });
//        }

        return true;
    }

    /**
     * @param method
     * @param params
     * @param clazz
     * @param entityCallback
     * @return
     */
    public <T extends ResponseBody> boolean getString(Context cx, String method, Map<String, String> params, ArrayList<File> files, final Class<T> clazz, final StringCallbackWithError entityCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
//        if (method.startsWith("http") || method.startsWith("https")) {
//            OkHttpUtils.post(method)
//                    .tag(this)
//                    .params(params)
//                    .addFileParams("file", files)
//                    .execute(new StringCallbackWithError<T>() {
//                        @Override
//                        public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
//                            entityResolve(s, response, clazz, entityCallback);
//                        }
//                    });
//        } else {
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .addFileParams("file", files)
                .execute(new StringCallbackWithError<T>() {
                    @Override
                    public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                        entityResolve(s, response, clazz, entityCallback);
                    }
                });
//        }

        return true;
    }

    /**
     * 结合Gson将json字符串转换为对应的实体对象
     *
     * @param s
     * @param response
     * @param clazz
     * @param entityCallback
     * @param <T>
     */
    private <T> void entityResolve(@Nullable String s, @Nullable Response response, Class<T> clazz, StringCallbackWithError entityCallback) {
        if (response.isSuccessful()) {
            T t = null;
            try {
                t = GsonUtils.fromJson(s, clazz);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof InvocationTargetException || e instanceof NumberFormatException || e instanceof com.google.gson.JsonSyntaxException) {
                    ToastUtility.showToast(R.string.resolve_data_error);
                } else {
                    ToastUtility.showToast(R.string.server_error);
                }
            }
            if (null != t) {
                entityCallback.onSuccess(t);
            } else {
                entityCallback.onFailed();
            }
        } else {
            entityCallback.onFailed();
        }
    }

    /**
     * @param method
     * @param params
     * @param clazz
     * @param entityCallback
     * @return
     */
    public <T extends ResponseBody> boolean getString(Context cx, String method, Map<String, String> params, final Class<T> clazz, final StringCallbackWithError entityCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
//        if (method.startsWith("http") || method.startsWith("https")) {
//            OkHttpUtils.post(method)
//                    .tag(this)
//                    .params(params)
//                    .execute(new StringCallbackWithError<T>() {
//                        @Override
//                        public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
//                            entityResolve(s, response, clazz, entityCallback);
//                        }
//                    });
//        } else {
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .params(params)
                .execute(new StringCallbackWithError<T>() {
                    @Override
                    public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                        entityResolve(s, response, clazz, entityCallback);
                    }
                });
//        }

        return true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param cx
     * @param method
     * @param jsonString
     * @param progressDialogTitle
     * @param clazz
     * @param jsonCallback
     * @param <T>
     * @return
     */
    public <T extends ResponseBody> boolean getJsonString(Context cx, String method, String jsonString, String progressDialogTitle, final Class<T> clazz, final StringCallbackWithError jsonCallback) {
        if (checkNetwork(cx, method)) {
            return false;
        }
        final ProgressDialog pDialog = showProgress(cx, progressDialogTitle);
        jsonCallback.setpDialog(pDialog);

//        if (method.startsWith("http") || method.startsWith("https")) {
//            OkHttpUtils.post(method)
//                    .tag(this)
//                    .upJson(jsonString)
//                    .execute(new StringCallbackWithError<T>() {
//                        @Override
//                        public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
//                            entityResolve(s, response, clazz, jsonCallback);
//                        }
//                    });
//        } else {
        OkHttpUtils.post(url + (TextUtils.isEmpty(method) ? "" : method))
                .tag(this)
                .upJson(jsonString)
                .execute(new StringCallbackWithError<T>() {
                    @Override
                    public void onSuccess(@Nullable String s, Call call, @Nullable Response response) {
                        entityResolve(s, response, clazz, jsonCallback);
                    }
                });
//        }
        return true;
    }

    /**
     * @param cx
     * @param method
     * @param jsonString
     * @param clazz
     * @param jsonCallback
     * @param <T>
     * @return
     */
    public <T extends ResponseBody> boolean getJsonString(Context cx, String method, String jsonString, final Class<T> clazz, final StringCallbackWithError jsonCallback) {
        return getJsonString(cx, method, jsonString, "请求中...", clazz, jsonCallback);
    }


    /**
     * 取消请求
     */
    public void cancelRequest() {
        //根据 Tag 取消请求
        OkHttpUtils.getInstance().cancelTag(this);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 统一带有服务端发生错误的响应回调
     */
    public static abstract class StringCallbackWithError<T> extends StringCallback implements IProgressDialogCallback<T> {

        private ProgressDialog pDialog;

        public void setpDialog(ProgressDialog pDialog) {
            this.pDialog = pDialog;
        }

        @Override
        public void onBefore(BaseRequest request) {
            super.onBefore(request);
            if (pDialog != null && !pDialog.isShowing()) {//开启进度条
                pDialog.show();
            }
        }


        @Override
        public void onAfter(@Nullable String s, @Nullable Exception e) {
            super.onAfter(s, e);
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }

        @Override
        public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);

            analyizeError(e);
        }


        @Override
        public void onSuccess(T t) {

        }

        @Override
        public void onFailed() {
            ToastUtility.showToast(R.string.request_error);
        }
    }

    /**
     * 带有进度条的请求回调
     */
    public interface IProgressDialogCallback<T> {
        void onSuccess(T t);

        void onFailed();
    }

//    /**
//     * 带有进度条的请求回调
//     */
//    public static abstract class StringCallbackWithError<T> extends StringCallbackWithError implements IProgressDialogCallback<T> {
//        @Override
//        public void onFailed() {
//            ToastUtility.showToast(R.string.request_error);
//        }
//    }

    /**
     * 分析服务端返回的异常,友好提示用户
     *
     * @param e
     */
    private static void analyizeError(Exception e) {
        if (e instanceof java.net.ConnectException) {
            ToastUtility.showToast(R.string.server_connect_exception);
        } else if (e instanceof java.net.SocketTimeoutException) {
            ToastUtility.showToast(R.string.server_connecttimeout_exception);
        } else if (e instanceof java.net.UnknownHostException) {
            ToastUtility.showToast(R.string.unknown_server_name_exception);
        } else if (e instanceof OkHttpException) {
            ToastUtility.showToast(R.string.server_url_error_exception);
        } else {
            ToastUtility.showToast(R.string.server_error);
        }
    }

    private static ProgressDialog m_pDialog;

    /**
     * 进度条
     *
     * @param context
     * @param message
     */
    public static ProgressDialog showProgress(Context context, String message) {
        if (null != m_pDialog) {
            if (m_pDialog.isShowing()) {//销毁掉之前的显示的加载窗
                return m_pDialog;
            }
        }
        m_pDialog = new ProgressDialog(context, ProgressDialog.THEME_DEVICE_DEFAULT_LIGHT);

        // 设置进度条风格，风格为圆形，旋转的
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // 设置ProgressDialog 提示信息
        m_pDialog.setMessage(message);

        // 设置ProgressDialog 的进度条是否不明确
        m_pDialog.setIndeterminate(true);

        // 设置ProgressDialog 是否可以按退回按键取消
        m_pDialog.setCancelable(false);

        // 让ProgressDialog显示
        m_pDialog.show();

        return m_pDialog;
    }


    /**
     * @param cx
     * @return
     */
    private boolean checkNetwork(Context cx, String method) {
        return checkNetwork(cx, method, null);
    }

    /**
     * 检查网络情况、网络地址url
     *
     * @param cx
     * @param pDialog
     * @return
     */
    private boolean checkNetwork(Context cx, String method, ProgressDialog pDialog) {
        if (!isNetwork(cx)) {
            closeDialogAndCancelRequest(pDialog);
            return true;
        }

        if (TextUtils.isEmpty(url) && TextUtils.isEmpty(method)) {
            ToastUtility.showToast("请求地址无效!");
            closeDialogAndCancelRequest(pDialog);
            return true;
        } else if (!TextUtils.isEmpty(url) && TextUtils.isEmpty(method)) {//url 非空， method 为空
            method = "";
            if (url.startsWith("http") || url.startsWith("https")) {
                return false;
            } else {
                ToastUtility.showToast("请设置正确的服务器地址！");
                closeDialogAndCancelRequest(pDialog);
                return true;
            }
        } else if (TextUtils.isEmpty(url) && !TextUtils.isEmpty(method)) {//url 为空， method 非空
            url = "";
            if (method.startsWith("http") || method.startsWith("https")) {
                return false;
            } else {
                ToastUtility.showToast("请求地址无效!");
                closeDialogAndCancelRequest(pDialog);
                return true;
            }
        } else {
            if (method.startsWith("http") || method.startsWith("https")) {
                url = "";
            }
        }
        return false;
    }

    /**
     * 关闭进度条、取消所有请求
     * @param pDialog
     */
    private void closeDialogAndCancelRequest(ProgressDialog pDialog) {
        if (pDialog != null && pDialog.isShowing()) {//关闭进度条
            pDialog.dismiss();
        }
        ToastUtility.showToast(R.string.network_error);
        cancelRequest();
    }

}
