package com.sa.rezq.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class AppTextView extends androidx.appcompat.widget.AppCompatTextView {
    public AppTextView(Context context) {
        super(context);
        init();
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        Typeface otf = Typeface.createFromAsset(getContext().getAssets(),
                "regular.otf");
        setTypeface(otf);
    }
}
