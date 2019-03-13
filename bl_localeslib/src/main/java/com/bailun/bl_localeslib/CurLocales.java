package com.bailun.localeslibrary;

import android.content.Context;

import com.bailun.localeslibrary.config_support.api_scenes.api_host_depend.BaseHostApiUtil;
import com.bailun.localeslibrary.core.LocalesUtil;

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
