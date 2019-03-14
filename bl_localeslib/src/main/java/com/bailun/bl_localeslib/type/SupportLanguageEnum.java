package com.bailun.bl_localeslib.type;


import com.bailun.bl_localeslib.api_scenes.api_host_depend.BaseHostApiUtil;
import com.bailun.bl_localeslib.core.LocalesModel;

/**
 * Created by kingpang on 2018/8/23.
 */

public enum SupportLanguageEnum {
    /**
     * 中文环境
     */
    ZH_CN(createLocales("zh", "CN", null));

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
