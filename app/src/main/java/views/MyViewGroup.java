package views;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/12/28.
 */
public class MyViewGroup extends LinearLayout {
    public MyViewGroup(Context context) {
        super(context);
    }

    /**
     * onDraw是实际绘制的部分，也就是我们真正关心的部分，使用的是Canvas绘图。继承自view
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 建议使用onDraw()
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /**
     * 当继承viewGroup时，在基类viewGroup中onlayout（）为抽象类
     * 确定布局的函数是onLayout，
     * 它用于确定子View的位置，在自定义ViewGroup中会用到，他调用的是子View的layout函数。
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //在自定义ViewGroup中，onLayout一般是循环取出子View，
        // 然后经过计算得出各个子View位置的坐标值，然后用函数child.layout(l, t, r, b);设置子View位置。
        //l	--View左侧距父View左侧的距离	getLeft();
        //t	--View顶部距父View顶部的距离	getTop();
        //r	--View右侧距父View左侧的距离	getRight();
        //b	--View底部距父View顶部的距离	getBottom();

    }

    /**
     * 测量View大小使用的是onMeasure函数，我们可以从onMeasure的两个参数中取出宽高的相关数据：
     * int widthsize = MeasureSpec.getSize(widthMeasureSpec);      //取出宽度的确切数值
     *int widthmode = MeasureSpec.getMode(widthMeasureSpec);      //取出宽度的测量模式
     *int heightsize = MeasureSpec.getSize(heightMeasureSpec);    //取出高度的确切数值
     *int heightmode = MeasureSpec.getMode(heightMeasureSpec);    //取出高度的测量模式
     *
     * 测量模式一共有三种：
     * UNSPECIFIED  默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
     * EXACTLY	    表示父控件已经确切的指定了子View的大小。
     * AT_MOST	    表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * TODO 如果对View的宽高进行修改了，不要调用super.onMeasure(widthMeasureSpec,heightMeasureSpec);
     * TODO 要调用setMeasuredDimension(widthsize,heightsize); 这个函数。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * View的大小不仅由View本身控制，而且受父控件的影响，
     * 所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。
     * 我们只需关注 宽度(w), 高度(h) 即可，这两个参数就是View最终的大小。
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
