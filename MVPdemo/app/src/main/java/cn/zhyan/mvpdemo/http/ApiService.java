package cn.zhyan.mvpdemo.http;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {

    @FormUrlEncoded
    @POST("101190408.html")
    Observable<String> gettqyb(
            @Field("ad") String ad
    );

    @GET("101190408.html")
    Observable<String> gettt();
}
