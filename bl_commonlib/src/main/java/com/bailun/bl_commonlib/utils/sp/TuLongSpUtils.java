package com.bailun.bl_commonlib.utils.sp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.WorkerThread;

/**
 * TuLong SharedPreferences Base Utils
 *
 * @author yh
 * @date 2019/11/19
 */
public class TuLongSpUtils {

    private TuLongSpUtils() {
    }

    private static class SingletonHolder {
        private final static TuLongSpUtils INSTANCE = new TuLongSpUtils();
    }

    public static TuLongSpUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Must Application Context
     */
    private Context context;
    private String defaultSpFileName;
    private DefaultValueBuilder defaultValueBuilder;

    /**
     * 初始化TuLong SharedPreferences
     *
     * @param context Application
     */
    public void init(Context context) {
        init(context, TuLongSpConstant.DEFAULT_SP_NAME);
    }

    /**
     * 初始化TuLong SharedPreferences
     *
     * @param context           Application
     * @param defaultSpFileName 默认的文件名称
     */
    public void init(Context context, String defaultSpFileName) {
        init(context, defaultSpFileName, new DefaultValueBuilder().build());
    }

    /**
     * 初始化TuLong SharedPreferences
     *
     * @param context             Application
     * @param defaultValueBuilder 配置项目的默认值
     */
    public void init(Context context, DefaultValueBuilder defaultValueBuilder) {
        init(context, TuLongSpConstant.DEFAULT_SP_NAME, defaultValueBuilder);
    }


    /**
     * 初始化TuLong SharedPreferences
     *
     * @param context                Application
     * @param defaultSpFileName      默认的文件名称
     * @param newDefaultValueBuilder 配置项目的默认值
     */
    public void init(Context context, String defaultSpFileName, DefaultValueBuilder newDefaultValueBuilder) {
        if (context == null) {
            throw new NullPointerException("Context can not be empty");
        }
        this.defaultSpFileName = defaultSpFileName;
        this.context = context.getApplicationContext();
        this.defaultValueBuilder = newDefaultValueBuilder;
        if (this.defaultValueBuilder == null) {
            this.defaultValueBuilder = new DefaultValueBuilder().build();
        }
    }

    /**
     * 初始化一个SharePreferences的，首次加载比较耗时
     *
     * @return 默认的SharedPreferences文件
     */
    @WorkerThread
    public SharedPreferences initSharedPreferences() {
        if (context == null) {
            throw new RuntimeException("You must first init context");
        }
        return getBestSharePreferences(context, defaultSpFileName);
    }


    /**
     * Get SharedPreferences
     *
     * @param name Desired preferences file.
     * @return {@link SharedPreferences}
     */
    private SharedPreferences getSharePreferences(String name) {
        if (context == null) {
            throw new RuntimeException("You must init TuLongSpUtils");
        }
        //其他模式官方不推荐使用了，这里默认Context.MODE_PRIVATE
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * Get SharedPreferences
     *
     * @param activity Activity to get SharedPreferences
     * @return {@link SharedPreferences}
     */
    private SharedPreferences getSharePreferences(Activity activity) {
        if (defaultValueBuilder == null) {
            throw new RuntimeException("You must init TuLongSpUtils");
        }
        return activity.getPreferences(Context.MODE_PRIVATE);
    }

    /**
     * Get Best SharedPreferences
     * 如果使用了Activity获取SharedPreferences，希望这个SharedPreferences文件是和Activity
     * 高度契合的文件。所以默认会使用当前Activity的名称进行命名。
     *
     * @param context Context to get SharedPreferences
     * @param name    Desired preferences file.
     * @return {@link SharedPreferences}
     */
    public SharedPreferences getBestSharePreferences(Context context, String name) {
        if (context instanceof Activity) {
            return getSharePreferences((Activity) context);
        } else {
            return getSharePreferences(name);
        }
    }


    /**
     * Get SharedPreferences.Editor
     *
     * @param context Context to get SharedPreferences.Editor
     * @param name    Desired preferences file.
     * @return SharedPreferences.Editor
     */
    public SharedPreferences.Editor getEditor(Context context, String name) {
        return getBestSharePreferences(context, name).edit();
    }

    /**
     * Get SharedPreferences.Editor
     *
     * @param activity Activity to get SharedPreferences.Editor
     * @return SharedPreferences.Editor
     */
    public SharedPreferences.Editor getEditor(Activity activity) {
        return getSharePreferences(activity).edit();
    }


    /**
     * 保存String对象（忽略结果）
     *
     * @param spFileName Desired preferences file.
     * @param key        The name of the preference to modify.
     * @param value      The new value for the preference.
     */
    public void putString(String spFileName, String key, String value) {
        getEditor(context, spFileName).putString(key, value).apply();
    }

    /**
     * 保存String对象（忽略结果）
     *
     * @param key   The name of the preference to modify.
     * @param value The new value for the preference.
     */
    public void putString(String key, String value) {
        putString(defaultSpFileName, key, value);
    }

    /**
     * 保存String对象（忽略结果）
     *
     * @param activity Context to get SharedPreferences
     * @param key      The name of the preference to modify.
     * @param value    The new value for the preference.
     */
    public void putString(Activity activity, String key, String value) {
        getEditor(activity).putString(key, value).apply();
    }


    /**
     * 保存int对象（忽略结果）
     *
     * @param context Context to get SharedPreferences
     * @param key     The name of the preference to modify.
     * @param value   The new value for the preference.
     */
    public void putInt(Context context, String key, Integer value) {
        getEditor(context, key).putInt(key, value).apply();
    }

    /**
     * 保存Boolean对象（忽略结果）
     *
     * @param context Context to get SharedPreferences
     * @param key     The name of the preference to modify.
     * @param value   The new value for the preference.
     */
    public void putBoolean(Context context, String key, boolean value) {
        getEditor(context, key).putBoolean(key, value).apply();
    }

    /**
     * 保存Float对象（忽略结果）
     *
     * @param context Context to get SharedPreferences
     * @param key     The name of the preference to modify.
     * @param value   The new value for the preference.
     */
    public void putFloat(Context context, String key, float value) {
        getEditor(context, key).putFloat(key, value).apply();
    }

    /**
     * 保存Long对象（忽略结果）
     *
     * @param context Context to get SharedPreferences
     * @param key     The name of the preference to modify.
     * @param value   The new value for the preference.
     */
    public void putLong(Context context, String key, long value) {
        getEditor(context, key).putLong(key, value).apply();
    }


    /**
     * 获取String对象
     *
     * @param spFileName   Desired preferences file.
     * @param key          The name of the preference to modify.
     * @param defaultValue The default value for the preference.
     * @return String 对象
     */
    public String getString(String spFileName, String key, String defaultValue) {
        return getSharePreferences(spFileName).getString(key, defaultValue);
    }

    /**
     * 获取String对象
     *
     * @param spFileName Desired preferences file.
     * @param key        The name of the preference to modify.
     * @return String 对象
     */
    public String getString(String spFileName, String key) {
        return getString(spFileName, key, defaultValueBuilder.defaultStringValue);
    }

    /**
     * 获取String对象
     * 适用于默认的preferences file.
     *
     * @param key The name of the preference to modify.
     * @return String 对象
     */
    public String getString(String key) {
        return getString(defaultSpFileName, key);
    }


    /**
     * 获取String对象
     *
     * @param activity     Activity to get SharedPreferences
     * @param key          The name of the preference to modify.
     * @param defaultValue The default value for the preference.
     * @return String 对象
     */
    public String getString(Activity activity, String key, String defaultValue) {
        return getSharePreferences(activity).getString(key, defaultValue);
    }


    /**
     * 获取String对象
     *
     * @param activity Activity to get SharedPreferences
     * @param key      The name of the preference to modify.
     * @return String 对象
     */
    public String getString(Activity activity, String key) {
        return getString(activity, key, defaultValueBuilder.defaultStringValue);
    }


    /**
     * 获取Int对象
     *
     * @param spFileName   Desired preferences file.
     * @param key          The name of the preference to modify.
     * @param defaultValue The default value for the preference.
     * @return Int 对象
     */
    public int getInt(String spFileName, String key, int defaultValue) {
        return getSharePreferences(spFileName).getInt(key, defaultValue);
    }

    /**
     * 获取Int对象
     *
     * @param spFileName Desired preferences file.
     * @param key        The name of the preference to modify.
     * @return Int 对象
     */
    public int getInt(String spFileName, String key) {
        return getInt(spFileName, key, defaultValueBuilder.defaultIntValue);
    }

    /**
     * 获取Int对象
     *
     * @param key The name of the preference to modify.
     * @return Int 对象
     */
    public int getInt(String key) {
        return getInt(defaultSpFileName, key);
    }

    /**
     * 获取Int对象
     *
     * @param activity     Activity to get SharedPreferences
     * @param key          The name of the preference to modify.
     * @param defaultValue The default value for the preference.
     * @return Int 对象
     */
    public int getInt(Activity activity, String key, int defaultValue) {
        return getSharePreferences(activity).getInt(key, defaultValue);
    }

    /**
     * 获取Int对象
     *
     * @param activity Activity to get SharedPreferences
     * @param key      The name of the preference to modify.
     * @return Int 对象
     */
    public int getInt(Activity activity, String key) {
        return getInt(activity, key, defaultValueBuilder.defaultIntValue);
    }

    /**
     * 获取Boolean对象
     *
     * @param spFileName   Desired preferences file.
     * @param key          The name of the preference to modify.
     * @param defaultValue The default value for the preference.
     * @return Boolean对象
     */
    public boolean getBoolean(String spFileName, String key, boolean defaultValue) {
        return getSharePreferences(spFileName).getBoolean(key, defaultValue);
    }

    /**
     * 获取Boolean对象
     *
     * @param spFileName Desired preferences file.
     * @param key        The name of the preference to modify.
     * @return Boolean对象
     */
    public boolean getBoolean(String spFileName, String key) {
        return getBoolean(spFileName, key, defaultValueBuilder.defaultBooleanValue);
    }

    /**
     * 获取Boolean对象
     *
     * @param key The name of the preference to modify.
     * @return Boolean对象
     */
    public boolean getBoolean(String key) {
        return getBoolean(defaultSpFileName, key);
    }

    /**
     * 获取Boolean对象
     *
     * @param activity     Activity to get SharedPreferences
     * @param key          The name of the preference to modify.
     * @param defaultValue The default value for the preference.
     * @return Boolean对象
     */
    public boolean getBoolean(Activity activity, String key, boolean defaultValue) {
        return getSharePreferences(activity).getBoolean(key, defaultValue);
    }

    /**
     * 获取Boolean对象
     *
     * @param activity Activity to get SharedPreferences
     * @param key      The name of the preference to modify.
     * @return Boolean对象
     */
    public boolean getBoolean(Activity activity, String key) {
        return getBoolean(activity, key, defaultValueBuilder.defaultBooleanValue);
    }

    /**
     * 获取Long对象
     *
     * @param spFileName   Desired preferences file.
     * @param key          The name of the preference to modify.
     * @param defaultValue The default value for the preference.
     * @return Long 对象
     */
    public long getLong(String spFileName, String key, long defaultValue) {
        return getSharePreferences(spFileName).getLong(key, defaultValue);
    }

    /**
     * 获取Long对象
     *
     * @param spFileName Desired preferences file.
     * @param key        The name of the preference to modify.
     * @return Long 对象
     */
    public long getLong(String spFileName, String key) {
        return getLong(spFileName, key, defaultValueBuilder.defaultLongValue);
    }

    /**
     * 获取Long对象
     *
     * @param key The name of the preference to modify.
     * @return Long 对象
     */
    public long getLong(String key) {
        return getLong(defaultSpFileName, key);
    }

    /**
     * 获取Long对象
     *
     * @param activity     Activity to get SharedPreferences
     * @param key          The name of the preference to modify.
     * @param defaultValue The default value for the preference.
     * @return Long 对象
     */
    public long getLong(Activity activity, String key, long defaultValue) {
        return getSharePreferences(activity).getLong(key, defaultValue);
    }

    /**
     * 获取Long对象
     *
     * @param activity Activity to get SharedPreferences
     * @param key      The name of the preference to modify.
     * @return Long 对象
     */
    public long getLong(Activity activity, String key) {
        return getLong(activity, key, defaultValueBuilder.defaultLongValue);
    }

    /**
     * 获取Float对象
     *
     * @param spFileName   Desired preferences file.
     * @param key          The name of the preference to modify.
     * @param defaultValue The default value for the preference.
     * @return Float 对象
     */
    public float getFloat(String spFileName, String key, float defaultValue) {
        return getSharePreferences(spFileName).getFloat(key, defaultValue);
    }

    /**
     * 获取Float对象
     *
     * @param spFileName Desired preferences file.
     * @param key        The name of the preference to modify.
     * @return Float 对象
     */
    public float getFloat(String spFileName, String key) {
        return getFloat(spFileName, key, defaultValueBuilder.defaultFloatValue);
    }

    /**
     * 获取Float对象
     *
     * @param key The name of the preference to modify.
     * @return Float 对象
     */
    public float getFloat(String key) {
        return getFloat(defaultSpFileName, key);
    }

    /**
     * 获取Float对象
     *
     * @param activity     Activity to get SharedPreferences
     * @param key          The name of the preference to modify.
     * @param defaultValue The default value for the preference.
     * @return Float对象
     */
    public float getFloat(Activity activity, String key, float defaultValue) {
        return getSharePreferences(activity).getFloat(key, defaultValue);
    }

    /**
     * 获取Float对象
     *
     * @param activity Activity to get SharedPreferences
     * @param key      The name of the preference to modify.
     * @return Float对象
     */
    public float getFloat(Activity activity, String key) {
        return getFloat(activity, key, defaultValueBuilder.defaultFloatValue);
    }

    /**
     * 移除某一个Key，相当于重置了数据
     *
     * @param activity Activity to get SharedPreferences
     * @param key      The name of the preference to modify.
     */
    public void removeKey(Activity activity, String key) {
        getEditor(activity).remove(key).apply();
    }

    /**
     * 移除某一个Key，相当于重置了数据
     *
     * @param spFileName Desired preferences file.
     * @param key        The name of the preference to modify.
     */
    public void removeKey(String spFileName, String key) {
        getEditor(context, spFileName).remove(key).apply();
    }

    /**
     * 移除某一个Key，相当于重置了数据
     *
     * @param key The name of the preference to modify.
     */
    public void removeKey(String key) {
        removeKey(defaultSpFileName, key);
    }

    /**
     * 清空整个Sp文件
     *
     * @param activity Activity to get SharedPreferences
     */
    public void clearSpFile(Activity activity) {
        getEditor(activity).clear().apply();
    }

    /**
     * 清空整个Sp文件
     *
     * @param spFileName Desired preferences file.
     */
    public void clearSpFile(String spFileName) {
        getEditor(context, spFileName).clear().apply();
    }

    /**
     * 清空整个Sp文件
     */
    public void clearSpFile() {
        getEditor(context, defaultSpFileName).clear().apply();
    }


    public static class DefaultValueBuilder {

        private String defaultStringValue = TuLongSpConstant.DEFAULT_STRING_VALUE;
        private int defaultIntValue = TuLongSpConstant.DEFAULT_INT_VALUE;
        private boolean defaultBooleanValue = TuLongSpConstant.DEFAULT_BOOLEAN_VALUE;
        private float defaultFloatValue = TuLongSpConstant.DEFAULT_FLOAT_VALUE;
        private long defaultLongValue = TuLongSpConstant.DEFAULT_LONG_VALUE;

        public DefaultValueBuilder setDefaultStringValue(String spDefaultStringValue) {
            this.defaultStringValue = spDefaultStringValue;
            return this;
        }

        public DefaultValueBuilder setDefaultIntValue(int spDefaultIntValue) {
            this.defaultIntValue = spDefaultIntValue;
            return this;
        }

        public DefaultValueBuilder setDefaultLongValue(long spDefaultLongValue) {
            this.defaultLongValue = spDefaultLongValue;
            return this;
        }

        public DefaultValueBuilder setDefaultFloatValue(float spDefaultFloatValue) {
            this.defaultFloatValue = spDefaultFloatValue;
            return this;
        }

        public DefaultValueBuilder setDefaultBooleanValue(boolean spDefaultBooleanValue) {
            this.defaultBooleanValue = spDefaultBooleanValue;
            return this;
        }

        public DefaultValueBuilder build() {
            return this;
        }

    }

}
