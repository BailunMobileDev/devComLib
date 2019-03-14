package com.bailun.bl_localeslib.api_scenes.api_host_depend;

import android.support.annotation.CallSuper;

import com.bailun.bl_localeslib.api_scenes.default_set.EnvHostBase;
import com.bailun.bl_localeslib.type.EnvironmentEnum;


/**
 * Created by kingpang on 2018/8/23.
 */
public class BaseHostApiUtil {
    //kingpang.test 需要使用API.XXX的形式来方便调用，就在这里补充，然后在createAPIs里补充：newApiVariable = new DefaultXXXX();

    public void activation(EnvironmentEnum env) {

        EnvHostBase curHost = null;
        switch (env) {
            case DEBUG:
                curHost = getDebugHost();
                break;
            case TEST:
                curHost = getTestHost();
                break;
            case PRE_RELEASE:
                curHost = getPreReleaseHost();
                break;
            case RELEASE:
                curHost = getReleaseHost();
                break;
            default:
                curHost = new EnvHostBase();
                break;
        }

        createAPIs(curHost);
    }

    //    @CallSuper
    public EnvHostBase getDebugHost() {
        return new EnvHostBase();
    }

    //    @CallSuper
    public EnvHostBase getTestHost() {
        return new EnvHostBase();
    }

    //    @CallSuper
    public EnvHostBase getPreReleaseHost() {
        return new EnvHostBase();
    }

    //    @CallSuper
    public EnvHostBase getReleaseHost() {
        return new EnvHostBase();
    }

    @CallSuper
    public void createAPIs(EnvHostBase host) {

    }
}
