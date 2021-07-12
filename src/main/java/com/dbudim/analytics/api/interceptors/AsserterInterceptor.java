package com.dbudim.analytics.api.interceptors;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by dbudim on 10.07.2021
 */

public class AsserterInterceptor implements Interceptor {


    private boolean isEnabled = true;
    private boolean disableOnce = false;

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isEnabled) chain.proceed(chain.request());
        if (disableOnce) {
            try {
                return chain.proceed(chain.request());
            } finally {
                disableOnce = false;
            }
        }
        Response proceed = chain.proceed(chain.request());
        if (!proceed.isSuccessful()) throw new AssertionError(proceed.body().string());
        return proceed;
    }

    public void disableOnce() {
        disableOnce = true;
    }
}
