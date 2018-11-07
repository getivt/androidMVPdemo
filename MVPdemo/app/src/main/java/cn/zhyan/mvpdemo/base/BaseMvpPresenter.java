package cn.zhyan.mvpdemo.base;

import android.os.Bundle;

import java.lang.ref.WeakReference;

public abstract class BaseMvpPresenter<V extends  IMvpView,M extends IMvpModel>  implements IMvpPresenter<V> {
    private WeakReference<V> viewRef;
    private M model;


    public abstract M createModel();

    public M getModel(){
        return model;
    }

    protected V getView() {
        return viewRef.get();
    }
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    //绑定view
    private void attach(V view) {
        viewRef = new WeakReference<V>(view);
        model = createModel();
    }
    //解绑view
    private void detach() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    @Override
    public void onMvpAttachView(V view) {
        attach(view);
    }
    @Override
    public void onMvpDetachView() {
        detach();
    }

    @Override
    public void onMvpStart() {

    }

    @Override
    public void onMvpResume() {

    }

    @Override
    public void onMvpPause() {

    }

    @Override
    public void onMvpStop() {

    }

    @Override
    public void onMvpSaveInstanceState(Bundle savedInstanceState) {

    }



    @Override
    public void onMvpDestroy() {

    }
}
