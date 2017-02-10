package com.unrestrained.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.unrestrained.R;
import com.yuyh.library.EasyGuide;
import com.yuyh.library.support.HShape;

import butterknife.ButterKnife;

/**
 * 新手引导图<br>
 * 蒙版阴影
 */
public class EasyguideviewActivity extends AppCompatActivity {

    EasyGuide easyGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easyguideview);
        ButterKnife.bind(this);
    }

    /**
     * 显示自定义提示布局
     *
     * @param view
     */
    public void btnShow(View view) {
        int[] loc = new int[2];
        view.getLocationOnScreen(loc);

        View tipsView = createTipsView();

        if (easyGuide != null && easyGuide.isShowing()) {
            easyGuide.dismiss();
        }

        easyGuide = new EasyGuide.Builder(this)
                // 增加View高亮区域，可同时显示多个
                .addHightArea(view, HShape.RECTANGLE)
                // 复杂的提示布局，建议通过此方法，较容易控制
                .addView(tipsView, 0, loc[1] + view.getHeight(), new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .build();

        easyGuide.show();
    }

    private View createTipsView() {
        View view = LayoutInflater.from(this).inflate(R.layout.tips_view, null);

        ImageView ivIsee = (ImageView) view.findViewById(R.id.ivIsee);
        ivIsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (easyGuide != null) {
                    easyGuide.dismiss();
                }
            }
        });
        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (easyGuide != null && easyGuide.isShowing()) {
            easyGuide.dismiss();
        }
    }
}
