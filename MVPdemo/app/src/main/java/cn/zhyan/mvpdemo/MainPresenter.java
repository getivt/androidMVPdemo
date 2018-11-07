package cn.zhyan.mvpdemo;

import cn.zhyan.mvpdemo.base.BaseMvpPresenter;

public class MainPresenter extends BaseMvpPresenter<MainActivity,MainModel> {


    @Override
    public MainModel createModel() {
        return new MainModel(getView());
    }

    public void loginto(){
        getModel().login("你好！", new MainView() {
            @Override
            public void onLogin(String result) {
                getView().onLogin(result);

                getView().showProgress();
            }
        });
    }

}
