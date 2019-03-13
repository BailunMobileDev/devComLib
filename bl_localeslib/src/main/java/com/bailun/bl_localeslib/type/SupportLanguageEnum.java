package com.bailun.localeslibrary.type;

import com.bailun.localeslibrary.config_support.api_scenes.api_host_depend.BaseHostApiUtil;
import com.bailun.localeslibrary.config_support.api_scenes.zh_tw.HostApiUtil_zh_tw;
import com.bailun.localeslibrary.core.LocalesModel;

/**
 * Created by kingpang on 2018/8/23.
 */

public enum SupportLanguageEnum {

    ZH_CN(createLocales("zh", "CN", null)),
    ZH_TW(createLocales("zh", "TW", new HostApiUtil_zh_tw()));

    private static LocalesModel createLocales(String language, String country, BaseHostApiUtil util) {
        return new LocalesModel(language, country, util);
    }

    private LocalesModel localesModel;

    SupportLanguageEnum(LocalesModel locales) {
        localesModel = locales;
    }

    public LocalesModel Value() {
        return this.localesModel;
    }

}
