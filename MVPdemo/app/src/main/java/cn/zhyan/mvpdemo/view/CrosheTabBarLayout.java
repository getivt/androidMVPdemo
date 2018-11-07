package cn.zhyan.mvpdemo.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.zhyan.mvpdemo.R;
import cn.zhyan.mvpdemo.utils.StateBarUtil;

public class CrosheTabBarLayout extends LinearLayout {

    private boolean fullscreen, light, titleAlignLeft;
    private boolean isBringToFront;
    private int lineColor, titleColor;
    private int lineWidth;
    private String title;
    private int titleFontSize;
    private int navigationIcon, navigationIconColor;
    private LinearLayout llItemContainer;
    private View lineView;
    private TextView titleView;
    private ImageView navigationImageView;

    private LinearLayout navigationView;


    public CrosheTabBarLayout(@NonNull Context context) {
        super(context);
    }

    public CrosheTabBarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initValue(attrs);
    }

    public CrosheTabBarLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValue(attrs);
    }


    private void initValue(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CrosheTabBarLayout);
            fullscreen = a.getBoolean(R.styleable.CrosheTabBarLayout_tabBar_fullscreen, false);
            light = a.getBoolean(R.styleable.CrosheTabBarLayout_tabBar_light, false);
            lineWidth = a.getDimensionPixelSize(R.styleable.CrosheTabBarLayout_tabBar_lineWidth, 0);
            lineColor = a.getColor(R.styleable.CrosheTabBarLayout_tabBar_lineColor, Color.parseColor("#cccccc"));
            titleColor = a.getColor(R.styleable.CrosheTabBarLayout_tabBar_titleColor, getResources().getColor(R.color.colorAccent));
            titleFontSize = a.getDimensionPixelSize(R.styleable.CrosheTabBarLayout_tabBar_titleFontSize, dip2px(18));
            title = a.getString(R.styleable.CrosheTabBarLayout_tabBar_title);
            titleAlignLeft = a.getBoolean(R.styleable.CrosheTabBarLayout_tabBar_titleAlignLeft, false);
            navigationIcon = a.getResourceId(R.styleable.CrosheTabBarLayout_tabBar_navigationIcon, -1);
            navigationIconColor = a.getColor(R.styleable.CrosheTabBarLayout_tabBar_navigationIconColor, getResources().getColor(R.color.colorAccent));
            a.recycle();
        }


        setOrientation(VERTICAL);

        llItemContainer = new LinearLayout(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        llItemContainer.setLayoutParams(layoutParams);
        llItemContainer.setOrientation(HORIZONTAL);
        llItemContainer.setGravity(Gravity.CENTER_VERTICAL);

        if (getBackground() == null) {
            setBackgroundColor(Color.TRANSPARENT);
        }

        titleView = new TextView(getContext());
        titleView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (navigationIcon != -1) {
            titleView.setPadding(dip2px(55), 0, dip2px(65), 0);
        } else {
            titleView.setPadding(dip2px(20), 0, dip2px(65), 0);
        }

        if (titleAlignLeft) {
            titleView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else {
            titleView.setGravity(Gravity.CENTER);
            titleView.setPadding(dip2px(65), 0, dip2px(65), 0);
        }


        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleFontSize);
        titleView.setTextColor(titleColor);
        titleView.setId(R.id.tv_tab_title);

        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        }

        if (navigationIcon != -1) {
            navigationView = new LinearLayout(getContext());
            navigationView.setLayoutParams(new LayoutParams(dip2px(55), ViewGroup.LayoutParams.MATCH_PARENT));
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.actionBarItemBackground, outValue, true);
            navigationView.setBackgroundResource(outValue.resourceId);
            navigationView.setClickable(true);
            navigationView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getContext() instanceof Activity) {
                        Activity activity = (Activity) getContext();
                        activity.finish();
                    }
                }
            });

            navigationImageView = new ImageView(getContext());
            navigationImageView.setLayoutParams(new LayoutParams(dip2px(24), dip2px(24)));
            navigationImageView.setId(R.id.navigation_icon);

            Drawable bmpDrawable = ContextCompat.getDrawable(getContext(), navigationIcon);
            DrawableCompat.setTint(bmpDrawable, navigationIconColor);

            navigationImageView.setImageDrawable(bmpDrawable);
            navigationView.addView(navigationImageView);
            navigationView.setGravity(Gravity.CENTER);

        }
    }

    public int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获得状态栏高度
     */
    private int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    private void initPosition() {
        if (getParent() instanceof FrameLayout) {
            if (!isBringToFront) {
                isBringToFront = true;
                bringToFront();
            }
        }
    }


    private void refreshView() {

        llItemContainer.removeAllViews();

        if (navigationView != null) {
            llItemContainer.addView(navigationView);
        }

        List<View> childViews = new ArrayList<>();
        for (int i = 0; i < this.getChildCount(); i++) {
            childViews.add(this.getChildAt(i));
        }
        this.removeAllViews();


        for (View childView : childViews) {
            llItemContainer.addView(childView);
        }

        FrameLayout frameLayout = new FrameLayout(getContext());
        LinearLayout.LayoutParams frameLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        frameLayoutParams.height = 0;
        frameLayoutParams.weight = 1;
        frameLayout.setLayoutParams(frameLayoutParams);
        frameLayout.addView(llItemContainer);

        if (titleView != null) {
            frameLayout.addView(titleView);
        }

        lineView = new View(getContext());
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, lineWidth);
        lineView.setBackgroundColor(lineColor);
        lineView.setLayoutParams(layoutParams);

        this.addView(frameLayout);
        this.addView(lineView);
    }

    private void initLayout() {
        if (fullscreen) {
            int marginHeight = 0;
            if (getContext() instanceof Activity) {
                Activity activity = (Activity) getContext();
                StateBarUtil.setStatusTextColor(activity, light);

                if (Build.VERSION.SDK_INT >= 21) {
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                    setPadding(0, getStatusBarHeight(getContext()), 0, 0);
                    marginHeight = getStatusBarHeight(getContext());

                } else {
                    StateBarUtil.setColor(activity);
                }
            }
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = dip2px(48) + lineWidth + marginHeight;
            setLayoutParams(layoutParams);
        } else {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = dip2px(48) + lineWidth;
            setLayoutParams(layoutParams);
        }
    }


    /**
     * 设置背景透明度
     *
     * @param alpha 0~255
     */
    public void setBackgroundColorAlpha(int alpha) {
        if (this.getBackground() != null) {
            this.getBackground().mutate().setAlpha(alpha);
        }
        if (lineView.getBackground() != null) {
            lineView.getBackground().mutate().setAlpha(alpha);
        }
    }


    public ImageView getNavigationImageView() {
        return navigationImageView;
    }

    public void setNavigationImageView(ImageView navigationImageView) {
        this.navigationImageView = navigationImageView;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
        initLayout();
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        if (lineView != null) {
            lineView.setBackgroundColor(lineColor);
        }
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        if (titleView != null) {
            titleView.setTextColor(titleColor);
        }
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        if (lineView != null) {
            LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, lineWidth);
            lineView.setLayoutParams(layoutParams);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    public int getTitleFontSize() {
        return titleFontSize;
    }

    public void setTitleFontSize(int titleFontSize) {
        this.titleFontSize = titleFontSize;
        if (titleView != null) {
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleFontSize);
        }
    }


    public View getLineView() {
        return lineView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public boolean isTitleAlignLeft() {
        return titleAlignLeft;
    }

    public void setTitleAlignLeft(boolean titleAlignLeft) {
        this.titleAlignLeft = titleAlignLeft;
        if (titleAlignLeft) {
            titleView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initLayout();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPosition();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initPosition();
        refreshView();
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        initLayout();
    }
}