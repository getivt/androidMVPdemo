package cn.zhyan.mvpdemo;


import android.content.Context;

import cn.zhyan.mvpdemo.base.BaseMvpModel;
import cn.zhyan.mvpdemo.http.ApiMethods;
import cn.zhyan.mvpdemo.http.MyObserver;
import cn.zhyan.mvpdemo.http.OnHttpApiListener;

public class MainModel extends BaseMvpModel{
    public MainModel(Context context) {
        super(context);
    }

    public void login(String name, final MainView mainView){
        ApiMethods.getTqyb(new MyObserver<String>(new OnHttpApiListener<String>() {
            @Override
            public void onNext(String result) {
                mainView.onLogin(result);
            }

            @Override
            public void onError(Throwable e) {

            }
        }),name);
    }



}
