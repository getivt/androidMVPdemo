package cn.zhyan.mvpdemo.http;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiMethods {
    /**
     * 封装线程管理和订阅的过程
     */
    public static <T> void ApiSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getTqyb(Observer<String> observer, String name) {
        ApiSubscribe(Api.getApiService().gettt(), observer);
    }


}
