package cn.zhyan.mvpdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.zhyan.mvpdemo.base.BaseMvpActivity;
import cn.zhyan.mvpdemo.utils.StateBarUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseMvpActivity<MainActivity, MainPresenter> implements MainView{
    private static final String TAG = "MainActivity";
    TextView textView;


    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public MainActivity createView() {
        return this;
    }



    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        textView = findViewById(R.id.tv_text);

    }



    public void onclick(View view) {
        getPresneter().loginto();
        StateBarUtil.setStatusTextColor(this,true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLogin(String result) {
        textView.setText(result);
    }
}
