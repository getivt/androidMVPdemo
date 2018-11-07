package cn.zhyan.mvpdemo.base;

import android.content.Context;

public class BaseMvpModel implements IMvpModel {
    private Context context;

    public BaseMvpModel(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
