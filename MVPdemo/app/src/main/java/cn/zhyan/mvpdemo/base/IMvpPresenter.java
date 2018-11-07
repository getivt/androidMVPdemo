package cn.zhyan.mvpdemo.base;

import android.os.Bundle;

/**
 *定义P层生命周期与V层同步
 */
public interface IMvpPresenter<V extends IMvpView> {
    /**
     * 绑定view
     * @param view
     */
    void onMvpAttachView(V view);
    /**
     * 解绑view
     */
    void onMvpDetachView();


    void onMvpStart();

    void onMvpResume();

    void onMvpPause();

    void onMvpStop();

    void onMvpSaveInstanceState(Bundle savedInstanceState);


    void onMvpDestroy();
}
