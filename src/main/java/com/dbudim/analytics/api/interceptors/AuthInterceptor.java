package com.dbudim.analytics.api.interceptors;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by dbudim on 10.07.2021
 */

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        HttpUrl url = original.url()
                .newBuilder()
                .addQueryParameter("key", "49da8e071672674f10c5e454d964de05")
                .addQueryParameter("token", "c1acfe80e2f6c9af19fa185c428995787d0a261df089b19cdbac21f65b15253a")
                .build();

        Request withAuth = original.newBuilder().url(url).build();
        return chain.proceed(withAuth);
    }
}
