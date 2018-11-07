package cn.zhyan.mvpdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import cn.zhyan.mvpdemo.R;
import cn.zhyan.mvpdemo.utils.StateBarUtil;

public abstract class BaseActivity extends AppCompatActivity implements IMvpView {
    /**
     * 绑定布局文件
     *
     * @return
     */
    protected abstract
    @LayoutRes
    int getLayoutRes();

    /**
     * 初始化
     */
    public abstract void init();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StateBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this,R.color.transparent));
        StateBarUtil.setColor(this);
        setContentView(getLayoutRes());
        init();
    }
    @Override
    public void showProgress(boolean flag, String message) {

    }

    @Override
    public void showProgress(String message) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showProgress(boolean flag) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToast(int resId) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void close() {

    }
}
