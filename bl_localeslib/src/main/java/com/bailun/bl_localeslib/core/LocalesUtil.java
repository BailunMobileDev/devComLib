package com.bailun.localeslibrary.core;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import com.bailun.localeslibrary.config_support.api_scenes.api_host_depend.BaseHostApiUtil;
import com.bailun.localeslibrary.type.EnvironmentEnum;
import com.bailun.localeslibrary.type.SupportLanguageEnum;
import java.util.Locale;

/**
 * Created by kingpang on 2018/8/20.
 */

public class LocalesUtil {

    private SupportLanguageEnum m_Language = SupportLanguageEnum.ZH_CN;
    private EnvironmentEnum m_Environment = EnvironmentEnum.TEST;

    private Context m_Context = null;

    public BaseHostApiUtil API = null;

    public LocalesUtil(Context context) {
        m_Context = context;
    }

    public void Set(SupportLanguageEnum language, EnvironmentEnum env) {
        m_Language = language;
        m_Environment = env;

        // 激活
        this.API = m_Language.Value().activationAPI(m_Environment);
        setConfiguration(m_Language.Value().createLocale());
    }

    public void Set(SupportLanguageEnum language) {
        this.Set(language, m_Environment);
    }

    public void Set(EnvironmentEnum env) {
        this.Set(m_Language, env);
    }

    public String getCurConfigString(){
        return "Self.value: Language=" + m_Language.toString() + " | Environment=" + m_Environment.toString();
    }

    /**
     * 设置语言
     */
    public void setConfiguration(Locale setLocale) {
        if(m_Context != null && m_Context.getResources() != null) {
            Configuration configuration = m_Context.getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                configuration.setLocale(setLocale);
            } else {
                configuration.locale = setLocale;
            }

            Resources resources = m_Context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            resources.updateConfiguration(configuration, dm);//语言更换生效的代码!
        }
    }
}
