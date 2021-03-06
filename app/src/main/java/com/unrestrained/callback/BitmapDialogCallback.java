package com.unrestrained.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.lzy.okhttputils.callback.BitmapCallback;
import com.lzy.okhttputils.request.BaseRequest;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：请求图图片的时候显示对话框
 * 修订历史：
 * ================================================
 */
public abstract class BitmapDialogCallback extends BitmapCallback {

    private ProgressDialog dialog;

    public BitmapDialogCallback(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    @Override
    public void onBefore(BaseRequest request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

//    @Override
//    public void onAfter(@Nullable Bitmap bitmap, @Nullable Exception e) {
//        super.onAfter(bitmap, e);
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
//    }
}
