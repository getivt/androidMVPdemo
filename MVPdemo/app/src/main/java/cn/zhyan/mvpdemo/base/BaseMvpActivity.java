package cn.zhyan.mvpdemo.base;

import android.os.Bundle;

@SuppressWarnings("unchecked")
public abstract class BaseMvpActivity<V extends IMvpView, P extends IMvpPresenter> extends BaseActivity {
    private P presenter;
    private V view;

    public P getPresneter() {
        return presenter;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.presenter == null) {
            presenter = createPresenter();
        }
        if (this.view == null) {
            this.view = createView();
        }
        if (presenter == null) {
            throw new NullPointerException("Presenter 不能为空");
        }
        if (this.view == null) {
            throw new NullPointerException("view不能够为空");
        }
        presenter.onMvpAttachView(view);
    }

    //并不知道具体的P是哪一个实现类，由他的子类决定(BaseActivity子类决定具体类型)
    protected abstract P createPresenter();

    //并不知道具体的V是哪一个实现类，由他的子类决定(BaseActivity子类决定具体类型)
    public abstract V createView();

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onMvpStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onMvpResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onMvpPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onMvpStop();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenter.onMvpSaveInstanceState(outState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onMvpDetachView();
            presenter.onMvpDestroy();
        }
    }

}
