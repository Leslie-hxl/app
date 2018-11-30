package com.cn.android_testtwo;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MyTokens {

//    https://www.apiopen.top/createUserKey?appId=com.chat.peakchao&passwd=123456
    @GET("createUserKey?appId=com.android_testtwo&passwd=123456")
    Observable<tokenBean> getToken();
}
