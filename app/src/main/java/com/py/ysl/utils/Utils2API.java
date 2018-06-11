package com.py.ysl.utils;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Utils2API {

    @GET("http://www.kkcredit.cn/ccl/data/ws/rest/app/getRequestIp")
    Observable<Object> getIp();



}
