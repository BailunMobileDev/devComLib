package com.bailun.localeslibrary.core;

import com.bailun.localeslibrary.config_support.api_scenes.api_host_depend.BaseHostApiUtil;
import com.bailun.localeslibrary.config_support.api_scenes.default_set.HostApiUtil;
import com.bailun.localeslibrary.type.EnvironmentEnum;

import java.util.Locale;

/**
 * Created by kingpang on 2018/8/20.
 */

public class LocalesModel {

    private String m_strLanguage = "";
    private String m_strCountry = "";
    private BaseHostApiUtil m_HostApiUtil = null;

    public LocalesModel(String language, String country, BaseHostApiUtil util) {
        m_strLanguage = language;
        m_strCountry = country;
        if(null != util) {
            this.m_HostApiUtil = util;
        } else {
            this.m_HostApiUtil = new HostApiUtil();
        }
    }

    public Locale createLocale() {
        return new Locale(m_strLanguage, m_strCountry);
    }

    public BaseHostApiUtil activationAPI(EnvironmentEnum env) {
        this.m_HostApiUtil.activation(env);
        return this.m_HostApiUtil;
    }
}
