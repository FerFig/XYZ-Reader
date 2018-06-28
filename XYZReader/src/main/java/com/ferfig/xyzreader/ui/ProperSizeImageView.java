package com.ferfig.xyzreader.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class ProperSizeImageView extends AppCompatImageView {

    public ProperSizeImageView(Context context) {
        super(context);
    }

    public ProperSizeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProperSizeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int doisTercosAltura = MeasureSpec.getSize(widthMeasureSpec) * 2/3;
        int doisTercosAlturaSpec = MeasureSpec.makeMeasureSpec(doisTercosAltura, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, doisTercosAlturaSpec);
    }
}
