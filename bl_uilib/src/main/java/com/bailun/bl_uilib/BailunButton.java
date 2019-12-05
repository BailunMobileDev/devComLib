package com.bailun.bl_uilib;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bailun.bl_commonlib.utils.DisplayUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 汇查查通用按钮
 *
 * @author yh
 * @date 2019/04/01
 */
public class BailunButton extends RelativeLayout {

    /**
     * Progress的颜色
     */
    @ColorInt
    private static final int PROGRESS_BAR_COLOR = Color.WHITE;

    /**
     * 只显示文字
     */
    public static final int BTN_STYLE_ONLY_TEXT = 0;
    /**
     * 携带进度条
     */
    public static final int BTN_STYLE_WITH_LOADING = 1;
    /**
     * 带图标
     */
    public static final int BTN_STYLE_WITH_ICON = 2;

    @IntDef({BTN_STYLE_ONLY_TEXT, BTN_STYLE_WITH_LOADING, BTN_STYLE_WITH_ICON})
    @Retention(RetentionPolicy.SOURCE)
    private @interface BtnStyle {

    }


    private Context context;

    private TextView tvContent;
    private ProgressBar pbLoading;
    private ImageView ivContent;

    @BtnStyle
    private int btnStyle;
    private String strContent;
    private ColorStateList tvColor;
    private int tvTextSize;


    private boolean enabled;

    private Drawable bgColor;

    private Drawable ivRes;
    private float ivWidth;
    private float ivHeight;

    /**
     * 加载中背景
     */
    private Drawable loadingBg;
    /**
     * 加载中文字颜色
     */
    private ColorStateList loadingTvColor;
    /**
     * 是否在加载中
     */
    private boolean isLoading;

    public BailunButton(Context context) {
        this(context, null);
    }

    public BailunButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BailunButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BailunButton);
        strContent = array.getString(R.styleable.BailunButton_bl_btn_content);
        btnStyle = array.getInt(R.styleable.BailunButton_bl_btn_style, BTN_STYLE_ONLY_TEXT);
        enabled = array.getBoolean(R.styleable.BailunButton_bl_btn_enabled, true);
        bgColor = array.getDrawable(R.styleable.BailunButton_bl_btn_bg);
        tvColor = array.getColorStateList(R.styleable.BailunButton_bl_tv_color);
        tvTextSize  =array.getDimensionPixelSize(R.styleable.BailunButton_bl_tv_text_size,DisplayUtils.sp2px(context,18));
        loadingBg = array.getDrawable(R.styleable.BailunButton_bl_btn_loading_bg);
        loadingTvColor = array.getColorStateList(R.styleable.BailunButton_bl_tv_loading_color);
        ivRes = array.getDrawable(R.styleable.BailunButton_bl_iv_res);
        ivWidth = array.getDimensionPixelOffset(R.styleable.BailunButton_bl_iv_width, DisplayUtils.dip2px(context,20));
        ivHeight = array.getDimensionPixelOffset(R.styleable.BailunButton_bl_iv_height, DisplayUtils.dip2px(context,20));
        array.recycle();
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        setClickable(true);
        if (loadingTvColor == null) {
            loadingTvColor = ContextCompat.getColorStateList(context, R.color.selector_bailun_btn_tv_color);
        }
        if (bgColor == null) {
            bgColor = ContextCompat.getDrawable(context, R.drawable.selector_bailun_btn_bg);
        }
        setBackground(bgColor);
        switch (btnStyle) {
            case BTN_STYLE_ONLY_TEXT:
                initOnlyTextLayout();
                break;
            case BTN_STYLE_WITH_LOADING:
                initOnlyTextLayout();
                initLoadingLayout();
                break;
            case BTN_STYLE_WITH_ICON:
                initOnlyTextLayout();
                initImageLayout();
                break;
            default:
                break;
        }
        setEnabled(enabled);
    }


    private void initOnlyTextLayout() {
        tvContent = new TextView(context);
        tvContent.setId(R.id.tv_content_bailun_btn);
        tvContent.setClickable(false);
        tvContent.setLongClickable(false);
        tvContent.setTypeface(Typeface.DEFAULT_BOLD);
        tvContent.setText(strContent);
        tvContent.setTextSize(DisplayUtils.px2sp(context,tvTextSize));
        if (tvColor == null) {
            tvColor = ContextCompat.getColorStateList(context, R.color.selector_bailun_btn_tv_color);
        }
        tvContent.setTextColor(tvColor);
        tvContent.setDuplicateParentStateEnabled(true);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        tvContent.setLayoutParams(layoutParams);
        addView(tvContent);
    }

    private void initLoadingLayout() {
        pbLoading = new ProgressBar(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList colorStateList = ColorStateList.valueOf(PROGRESS_BAR_COLOR);
            pbLoading.setIndeterminateTintList(colorStateList);
            pbLoading.setIndeterminateTintMode(PorterDuff.Mode.SRC_ATOP);
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = DisplayUtils.dip2px(context, 36);
        layoutParams.height = DisplayUtils.dip2px(context, 36);
        layoutParams.rightMargin = DisplayUtils.dip2px(context, 8);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.START_OF, tvContent.getId());
        pbLoading.setLayoutParams(layoutParams);
        pbLoading.setVisibility(GONE);
        addView(pbLoading);
    }

    private void initImageLayout() {
        ivContent = new ImageView(context);
        ivContent.setImageDrawable(ivRes);
        ivContent.setId(R.id.iv_content_bailun_btn);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = (int)ivWidth;
        layoutParams.height = (int)ivHeight;
        layoutParams.rightMargin = DisplayUtils.dip2px(context, 8);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.START_OF, tvContent.getId());
        ivContent.setLayoutParams(layoutParams);
        addView(ivContent);
    }

    public void setStrContent(@StringRes int resId) {
        String strContent = context.getResources().getString(resId);
        setStrContent(strContent);
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
        if (tvContent != null) {
            tvContent.setText(strContent);
        }
    }

    public void setImagePic(int resId) {
        ivContent.setImageResource(resId);
    }

    public void setImagePic(Drawable drawable) {
        ivContent.setImageDrawable(drawable);
    }

    public void startLoading() {
        if (btnStyle != BTN_STYLE_WITH_LOADING) {
            return;
        }
        isLoading = true;
        setLoadingLayout();
        pbLoading.setVisibility(VISIBLE);
    }


    public void stopLoading() {
        if (btnStyle != BTN_STYLE_WITH_LOADING) {
            return;
        }
        isLoading = false;
        setLoadingLayout();
        pbLoading.setVisibility(GONE);
    }

    private void setLoadingLayout() {
        if (btnStyle == BTN_STYLE_WITH_LOADING) {
            if (isLoading) {
                setEnabled(false);
                setBackground(loadingBg);
                tvContent.setTextColor(loadingTvColor);
            } else {
                setEnabled(true);
                setBackground(bgColor);
                tvContent.setTextColor(tvColor);
            }
        }
    }

}
