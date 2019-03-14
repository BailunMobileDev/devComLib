package com.bailun.bl_localeslib.api_scenes.api_host_depend;

import com.bailun.bl_localeslib.api_scenes.default_set.EnvHostBase;

/**
 * Created by kingpang on 2018/8/23.
 */

public class BaseApi {
    public EnvHostBase Host = null;
    public BaseApi(EnvHostBase host) {
        this.Host = host;
    }
}
