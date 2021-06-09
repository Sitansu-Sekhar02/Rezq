package com.sa.rezq.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.sa.rezq.R;

public class CustomSliderTextView extends BaseSliderView {
    private static Typeface font = null;
    private Context context ;
    public CustomSliderTextView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_text,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.description_layout);
        linearLayout.setBackgroundColor(Color.TRANSPARENT);
        this.bindEventAndShow(v, target);
        return v;
    }
}
