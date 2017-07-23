package com.ins.common.common;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ReplacementSpan;

import com.bumptech.glide.load.engine.Resource;
import com.ins.common.utils.L;

/**
 * Created by liaoinstan on 2017/7/23.
 */

public class RoundedBackgroundSpan extends ReplacementSpan {

    private static int padding_h = 10;
    private static int padding_v = 5;
    private static float coner = 10f;

    private int color_bk;
    private int color_text;

    public RoundedBackgroundSpan(int color_bk, int color_text) {
        this.color_bk = color_bk;
        this.color_text = color_text;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top - padding_v, x + measureText(paint, text, start, end) + padding_h * 2, bottom + padding_v);
        paint.setColor(color_bk);
        canvas.drawRoundRect(rect, coner, coner, paint);
        paint.setColor(color_text);
        canvas.drawText(text, start, end, x + padding_h, y, paint);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(measureText(paint, text, start, end) + padding_h * 2);
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}
