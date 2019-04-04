package com.bailun.bl_commonlib.utils;

import android.content.Context;

/**
 * 屏幕工具类
 *
 * @author yh
 * @date 2019/03/15
 */
public class DisplayUtils {

    public static int dip2px(Context mContext, float dipFloat) {
        float f = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipFloat * f + 0.5F);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
