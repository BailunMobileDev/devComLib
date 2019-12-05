package com.bailun.bl_commonlib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.IntDef;
import androidx.annotation.RequiresPermission;
import android.telephony.TelephonyManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 类说明：获取网络信息工具类
 *
 * @author yh
 * @date 2018/9/26
 */
public class NetWorkInfoUtils {

    /**
     * 判断网络是否连接
     *
     * @param context Context
     * @return 网络是否连接
     */
    @RequiresPermission(value = "android.permission.ACCESS_NETWORK_STATE")
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo mNetworkInfo = getNetworkInfo(context);
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

    @IntDef({NetworkType.NETWORK_ETHERNET, NetworkType.NETWORK_WIFI,
            NetworkType.NETWORK_4G, NetworkType.NETWORK_3G,
            NetworkType.NETWORK_2G, NetworkType.NETWORK_UNKNOWN, NetworkType.NETWORK_NO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkType {
        /**
         * 以太网
         */
        int NETWORK_ETHERNET = 1;
        int NETWORK_WIFI = 2;
        int NETWORK_4G = 3;
        int NETWORK_3G = 4;
        int NETWORK_2G = 5;
        /**
         * 未知网络
         */
        int NETWORK_UNKNOWN = 6;
        /**
         * 没有网络
         */
        int NETWORK_NO = 7;
    }

    /**
     * 判断是否Wifi在线
     */
    @RequiresPermission(value = "android.permission.ACCESS_NETWORK_STATE")
    public static boolean isWifiConnect(Context mContext) {
        return getNetworkTypeForLink(mContext) == NetworkType.NETWORK_WIFI;
    }

    /**
     * 判断是否是手机网络
     */
    @RequiresPermission(value = "android.permission.ACCESS_NETWORK_STATE")
    public static boolean isMobile(Context mContext) {
        return getNetworkTypeForLink(mContext) != NetworkType.NETWORK_NO && getNetworkTypeForLink(mContext) != NetworkType.NETWORK_WIFI;
    }

    /**
     * 获取当前网络的类型
     */
    @RequiresPermission(value = "android.permission.ACCESS_NETWORK_STATE")
    public static int getNetworkTypeForLink(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info == null || !info.isAvailable()) {
            return NetworkType.NETWORK_NO;
        }
        if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {
            return NetworkType.NETWORK_ETHERNET;
        }
        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            return NetworkType.NETWORK_WIFI;
        }
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (info.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NetworkType.NETWORK_2G;
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return NetworkType.NETWORK_3G;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return NetworkType.NETWORK_4G;
                default://有机型返回16,17
                    //中国移动 联通 电信 三种3G制式
                    if (info.getSubtypeName().equalsIgnoreCase("TD-SCDMA") ||
                            info.getSubtypeName().equalsIgnoreCase("WCDMA") ||
                            info.getSubtypeName().equalsIgnoreCase("CDMA2000")) {
                        return NetworkType.NETWORK_3G;
                    } else {
                        return NetworkType.NETWORK_UNKNOWN;
                    }
            }
        }
        return NetworkType.NETWORK_UNKNOWN;
    }

    @RequiresPermission(value = "android.permission.ACCESS_NETWORK_STATE")
    private static NetworkInfo getNetworkInfo(Context mContext) {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return null;
        }
        return manager.getActiveNetworkInfo();
    }
}
