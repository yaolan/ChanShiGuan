package com.app.chanshiguan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.app.chanshiguan.R;


/**
 * Created by lyao on 16/7/21.
 */
public class LoginEditText extends EditText {

    Drawable drawable;

    public LoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        boolean useDark = false;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Crossed, 0, 0);
        //暂时去掉删除按钮
        try {
            useDark = a.getBoolean(R.styleable.Crossed_dark, false);
        } finally {
            a.recycle();
        }
        int crossedRes = useDark ? R.drawable.ic_clear_black_24dp : R.drawable.ic_clear_black_24dp;
        drawable = getResources().getDrawable(crossedRes);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                displayDelete(s.length() > 0);
            }
        });
    }

    private void displayDelete(boolean show) {
        if (show) {
            setDrawableRight(drawable);
        } else {
            setDrawableRight(null);
        }
    }


    private void setDrawableRight(Drawable drawable) {
        setCompoundDrawables(null, null, drawable, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

}
