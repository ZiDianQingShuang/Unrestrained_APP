package com.unrestrained.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.unrestrained.R;
import com.unrestrained.widget.ItemFragment;
import com.unrestrained.widget.dummy.DummyContent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnimatorActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;


    @BindView(R.id.tv)
    TextView textview;

    @BindView(R.id.btn_click0)
    Button btn_click0;

    @BindView(R.id.btn_click1)
    Button btn_click1;

    @BindView(R.id.btn_click2)
    Button btn_click2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        getSupportFragmentManager()
    }


    public void click0(final View view) {
        //translation  rotation scale alpha
        ObjectAnimator moveIn0 = ObjectAnimator.ofFloat(btn_click1, "translationX", 0, -1 * getWindowManager().getDefaultDisplay().getWidth());
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(btn_click2, "translationX", getWindowManager().getDefaultDisplay().getWidth(), 0);
        // ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        // ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
//        animSet.play(moveIn0).after(moveIn);
        animSet.play(moveIn0).with(moveIn);
        animSet.setDuration(300);

        if (animSet.isRunning()) {
            moveIn0.setRepeatCount(0);
            moveIn.setRepeatCount(0);
            animSet.start();
        } else {
            moveIn0.setRepeatCount(ValueAnimator.INFINITE);
            moveIn.setRepeatCount(ValueAnimator.INFINITE);
            animSet.start();
        }

//        Snackbar.make(view,"view", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"ok",0).show();
//            }
//        }).show();

//        animSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//        });


//        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder().;

//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, getWindow().getWindowManager().getDefaultDisplay().getWidth());


//        AnimationSet as = new AnimationSet(true);
//
//
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.repeatani);
//        //animation.setRepeatCount(Animation.INFINITE);
//        view.startAnimation(animation);
//
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
