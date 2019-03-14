package com.bailun.bl_localeslib;

import android.content.Context;

import com.bailun.bl_localeslib.core.LocalesUtil;


/**
 * Created by kingpang on 2018/8/20.
 */
public class CurLocales {

    private static LocalesUtil m_locales = null;

    public static void init(Context context) {
        if (m_locales == null) {
            synchronized (CurLocales.class) {
                if (m_locales == null) {
                    m_locales = new LocalesUtil(context);
                }
            }
        }
    }

    public static LocalesUtil instance() {
        if (m_locales == null) {
            throw new IllegalStateException("You must be init locales first");
        }

        return m_locales;
    }
}
