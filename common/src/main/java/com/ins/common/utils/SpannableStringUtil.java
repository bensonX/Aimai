package com.ins.common.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.ins.common.common.RoundedBackgroundSpan;

/**
 * Created by liaoinstan
 * SpannableString工具类
 * <p>
 * 使用参考：
 * http://www.jianshu.com/p/84067ad289d2
 * <p>
 * new ForegroundColorSpan(Color.parseColor("#0099EE"));        //文字颜色
 * new BackgroundColorSpan(Color.parseColor("#AC00FF30"));      //文字背景
 * new RelativeSizeSpan(1.2f);  //字体大小（相对）
 * new StrikethroughSpan(); //删除线
 * new UnderlineSpan();     //下划线
 * new SuperscriptSpan();   //上标
 * new SubscriptSpan();     //下标
 * new StyleSpan(Typeface.BOLD);ITALIC    //粗体斜体
 * new ImageSpan(drawable); //图片
 * new URLSpan("http://www.jianshu.com/users/dbae9ac95c78"); //超链接
 * new ClickableSpan()      //点击事件，用法特殊，需要实现接口
 * <p>
 * 试试：SpannableStringBuilder
 */
public class SpannableStringUtil {

    //和下面方法功能一致，提供通过颜色资源id来生成SpannableString
    public static SpannableString create(Context context, String[] strs, int[] colorSrcs) {
        int[] colors = new int[colorSrcs.length];
        for (int i = 0; i < colorSrcs.length; i++) {
            colors[i] = ContextCompat.getColor(context, colorSrcs[i]);
        }
        return create(strs, colors);
    }

    //生成一条SpannableString，指定每一段文字和对应颜色，要求strs，colors不为空，且长度一样：不验证自行遵守
    public static SpannableString create(String[] strs, int[] colors) {
        String strall = "";
        for (String str : strs) {
            strall += str;
        }
        SpannableString strSpan = new SpannableString(strall);
        int end = 0;
        for (int i = 0; i < strs.length; i++) {
            int start = end;
            end += strs[i].length();
            strSpan.setSpan(new ForegroundColorSpan(colors[i]), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return strSpan;
    }

    /**
     * 上面方法{@link #create(String[], int[])}的优化版，使用SpannableStringBuilder实现，尚未测试
     */
    public static SpannableStringBuilder createV2(String[] strs, int[] colors) {
        SpannableStringBuilder strSpan = new SpannableStringBuilder("");
        for (int i = 0; i < strs.length; i++) {
            int start = strSpan.length();   //拼接前的长度
            strSpan.append(strs[i]);
            int end = strSpan.length();     //拼接后的长度
            strSpan.setSpan(new ForegroundColorSpan(colors[i]), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return strSpan;
    }

    //给Edit设置Hint文字（带图片）
    public static SpannableString makeImageStartStr(Context context, int src, Rect rect, String text) {
        SpannableString spannableString = new SpannableString(text);
        Drawable drawable = ContextCompat.getDrawable(context, src);
        drawable.setBounds(rect);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString makeConorBkStr(int color_bk, int color_text, String title, String content) {
        SpannableString spannableString = new SpannableString(title + content);
        RoundedBackgroundSpan roundedBackgroundSpan = new RoundedBackgroundSpan(color_bk, color_text);
        spannableString.setSpan(roundedBackgroundSpan, 0, title.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
