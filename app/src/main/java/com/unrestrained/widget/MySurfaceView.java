package com.unrestrained.widget;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.AttributeSet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wangxiaofei on 2016/12/16.
 */

public class MySurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private int width, height;

    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setRenderer(this);
        this.width = 400;
        this.height = 400;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        gl.glViewport(0, 0, 400, 400);

//        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//
//        gl.glDisable(GL10.GL_DITHER); // 关闭抗抖动
//
//        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);//设置系统对透视进行修正
//
//        gl.glClearColor(0, 0, 0, 0);
//
//        gl.glShadeModel(GL10.GL_SMOOTH); //设置阴影平滑模式
//
//        gl.glEnable(GL10.GL_DEPTH_TEST);//启用深度测试
//
//        gl.glDepthFunc(GL10.GL_LEQUAL);//设置深度测试的类型

        GLES10.glViewport(0, 0, width, height); // 设置视口宽度高度。
        // 修改投影矩阵
        GLES10.glMatrixMode(GLES10.GL_PROJECTION); // 修改投影矩阵
        GLES10.glLoadIdentity(); // 复位，将投影矩阵归零
        GLU.gluPerspective(gl, 60.0f, ((float) width) / height, 1.0f, 300.0f); // 最近和最远可以看到的距离，超过最远将不显示。

        float[] vertexs = new float[]{
                -5.0f,  5.0f, -20.0f,
                5.0f, -5.0f, -20.0f,
                5.0f,  5.0f, -20.0f,
        };
        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertexs.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(vertexs);
        vertexBuffer.position(0);

       // setBackgroundColor(Color.WHITE);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置3D视窗的大小及位置
        gl.glViewport(0, 0, width, height);
        //将当前矩阵模式设为投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //初始化单位矩阵
        gl.glLoadIdentity();
        //计算透视视窗的宽度、高度比
        float ratio = (float) width / height;
        //调用此方法设置透视视窗的空间大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES10.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // 清空场景为黑色。
        GLES10.glClear(GLES10.GL_COLOR_BUFFER_BIT | GLES10.GL_DEPTH_BUFFER_BIT);// 清空相关缓存。

        GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);

        float[] vertexs = new float[]{
                -5.0f,  5.0f, -20.0f,
                5.0f, -5.0f, -20.0f,
                5.0f,  5.0f, -20.0f,
        };
        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(vertexs.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        GLES10.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexBuffer);

        GLES10.glDrawArrays(GLES10.GL_TRIANGLES, 0, 3);

        GLES10.glFlush();
    }


}
