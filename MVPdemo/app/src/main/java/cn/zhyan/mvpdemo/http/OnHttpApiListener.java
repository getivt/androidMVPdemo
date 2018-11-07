package cn.zhyan.mvpdemo.http;

public interface OnHttpApiListener<T> {
    void onNext(T result);
    void onError(Throwable e);
}
