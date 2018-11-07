package cn.zhyan.mvpdemo.http;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MyObserver<T> implements Observer<T> ,ProgressCancelListener{
    private static final String TAG = "MyObserver";

    private OnHttpApiListener<T> onHttpApiListener;
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;
    private Disposable d;

    public MyObserver(OnHttpApiListener<T> onHttpApiListener) {
        this.onHttpApiListener = onHttpApiListener;
    }

    public MyObserver( Context context,OnHttpApiListener<T> onHttpApiListener) {
        this.context = context;
        this.onHttpApiListener = onHttpApiListener;
        mProgressDialogHandler = new ProgressDialogHandler(context,this,true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)
                    .sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        showProgressDialog();
        Log.d(TAG,"-------onSubscribe()=="+d.toString());
    }

    @Override
    public void onNext(T t) {
        if (null!=onHttpApiListener)
            onHttpApiListener.onNext(t);

        Log.d(TAG,"-------onNext()=="+t.toString());
    }

    @Override
    public void onError(Throwable e) {
        if (null!=onHttpApiListener)
            onHttpApiListener.onError(e);
        dismissProgressDialog();
        Log.d(TAG,"-------onError()=="+e.toString());
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
        Log.d(TAG,"-------onComplete()");
    }

    @Override
    public void onCancelProgress() {
        if (!d.isDisposed()) {
            d.dispose();
        }
    }
}
