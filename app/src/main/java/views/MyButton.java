package views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

/**
 * Created by Administrator on 2017/2/20.
 */
public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (getX()==getLeft()){
            Log.e("(getX()==getLeft()","相等");
        }else {
            Log.e("(getX()==getLeft()","不相等");
        }
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
