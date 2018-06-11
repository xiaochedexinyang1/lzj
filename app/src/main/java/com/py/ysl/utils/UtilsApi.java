package com.py.ysl.utils;



import com.py.ysl.bean.BaseBean;
import com.py.ysl.bean.UpdataVerCode;
import com.py.ysl.bean.UserBean;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface UtilsApi {

//    String BASEURL = "https://x.aiyingshi.com:20443/AppShoppingService.asmx/";
    String BASEURL = "http://beta9.kkcredit.cn/kkCreditLife/data/ws/rest/app/";
    String dd="";

    /**
     * 获取APP版本
     */
    @GET("ListVersionInfo")
    Observable<Object> getCodeVesion(
            @Query("device") String device
    );

    /**
     * 获取APP版本
     */
    @Headers("Content-Type: application/json;charset=UTF-8")//解决HTTP 415 Unsupported Media Type
    @POST("resource")
    Observable<Object> bindDevices(
            @QueryMap Map<String,String> map
                                   );
    @Headers("Content-Type: application/json;charset=UTF-8")//解决HTTP 415 Unsupported Media Type
    @POST(" umengpush/bindDevice")
    Observable<Object> umengPush(
            @QueryMap Map<String,String> map
    );
}
