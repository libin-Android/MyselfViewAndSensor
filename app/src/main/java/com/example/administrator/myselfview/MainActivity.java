package com.example.administrator.myselfview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private SensorManager sensorManager;
    private Sensor defaultSensor;
    /**
     * 摇一摇
     */
    private int mInterval = 70;
    //上一次摇晃的时间
    private long lastTime;
    private float lastX;
    private float lastY;
    private float lastZ;
    private Vibrator vibrator;//震动器
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        if (sensorManager!=null) {
            //注册传感器监听器
            //第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
            sensorManager.registerListener(sensorEventListener, defaultSensor,  SensorManager.SENSOR_DELAY_NORMAL);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 取消传感器监听器
        if (sensorManager!=null){
            sensorManager.unregisterListener(sensorEventListener);
        }
        super.onPause();
    }

    private void init() {
        //下面的代码即可获取屏幕的尺寸：
//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metric);
        //int height = metric.heightPixels;  // 高度（PX）
//        int heightPixels = metric.heightPixels;

        //获取传感器的管理对象
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //获得一个具体的传感器对象,,Sensor.TYPE_ACCELEROMETER为加速度传感器
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //获取震动的对象
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        iv1 = ((ImageView) findViewById(R.id.yaoYiYao1));
        iv2 = ((ImageView) findViewById(R.id.yaoYiYao2));
        rotateImage(iv2);
        iv3 = ((ImageView) findViewById(R.id.iv3));
        //在调用oncreate()方法时，界面处于不可见状态，内存加载组件还没有绘制出来，你是无法获取他的尺寸，getHeight=0；
        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fL);
        //布局绘制观察者
        ViewTreeObserver viewTreeObserver = frameLayout.getViewTreeObserver();
        //添加布局绘制监听器
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                i = frameLayout.getMeasuredHeight()/4;
                iv3.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, i * 2));
                iv3.setY(i);
                //此处必须返回true，默认为false，为false时不显示UI
                return true;
            }
        });
    }

    private SensorEventListener sensorEventListener=new SensorEventListener() {
        /**
         * //传感器信息改变时调用该方法
         * @param event
         */
        @Override
        public void onSensorChanged(SensorEvent event) {
            long nowTime = System.currentTimeMillis();
            long times = nowTime - lastTime;
            if (0 < times && times < mInterval) {
                return;
            }
            lastTime=nowTime;
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            float deltaX = x - lastX;
            float deltaY = y - lastY;
            float deltaZ = z - lastZ;
            lastX=x;
            lastY=y;
            lastZ = z;
            double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ)/times*1000;
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            if (speed >= 200&&speed<=500000) {
                // 传感器信息改变时执行该方法
                vibrator.vibrate(200);
                handler.sendEmptyMessage(0);
            }
        }

        /**
         * //当传感器精度发生变化时执行该方法
         * @param sensor
         * @param accuracy
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    yaoYiYao();
                    break;
            }
        }
    };



    private void yaoYiYao() {
        Toast.makeText(this,"摇一摇",Toast.LENGTH_SHORT).show();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a);
        Bitmap bitmapB = BitmapFactory.decodeResource(getResources(), R.mipmap.b);
        iv1.setImageBitmap(bitmap);
        iv2.setImageBitmap(bitmap);
        startAniTop(iv1);
        startAniBottom(iv2);
        iv3.setVisibility(View.VISIBLE);
        iv3.setImageBitmap(bitmapB);
    }
    private void rotateImage(View view){
        // 创建ObjectAnimator属性对象，参数分别是动画要设置的View对象、动画属性、属性值
        //属性动画默认旋转中心为view的对称中心
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                view,
                "rotation",
                0,
                180);
        animator1.setDuration(0);
        animator1.start();
    }
    private void startAniTop(View view) {
        // 创建ObjectAnimator属性对象，参数分别是动画要设置的View对象、动画属性、属性值
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                view,
                "translationY",
                -i);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                view,
                "translationY",
                0f);
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.setDuration(2000);
//        aniSet.setInterpolator(new BounceInterpolator());// 弹跳效果的插值器
        aniSet.playSequentially(
                animator1,
                animator2);
        aniSet.start();
        aniSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                iv3.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    private void startAniBottom(View view) {
        // 创建ObjectAnimator属性对象，参数分别是动画要设置的View对象、动画属性、属性值
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(
                view,
                "translationY",
                i);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(
                view,
                "translationY",
                0f);
        AnimatorSet aniSet = new AnimatorSet();
        aniSet.setDuration(2000);
//        aniSet.setInterpolator(new BounceInterpolator());// 弹跳效果的插值器
        aniSet.playSequentially(
                animator1,
                animator2);
        aniSet.start();
        //给动画设置监听
        aniSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
