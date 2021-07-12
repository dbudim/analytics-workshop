package com.dbudim.analytics.api.interceptors;

import com.dbudim.analytics.api.TrelloClient;
import com.moczul.ok2curl.CurlBuilder;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by dbudim on 10.07.2021
 */

public class LogInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(TrelloClient.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        StringBuilder log = new StringBuilder();
        Request request = chain.request();
        log.append("\nCURL:\n" + new CurlBuilder(request).build() + "\n");
        logger.info(log.toString());
        return chain.proceed(request);
    }
}
