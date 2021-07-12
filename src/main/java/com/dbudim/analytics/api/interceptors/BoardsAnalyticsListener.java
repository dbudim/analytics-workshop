package com.dbudim.analytics.api.interceptors;

import com.dbudim.analytics.tools.ElasticApi;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by dbudim on 09.07.2021
 */

public class BoardsAnalyticsListener implements ISuiteListener, Interceptor {

    private static int boardsCount;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equals("POST") && request.url().pathSegments().contains("boards")) {
            boardsCount++;
        }
        return chain.proceed(chain.request());
    }

    @Override
    public void onFinish(ISuite suite) {
        new ElasticApi().pushData("aqa-boards-created", Map.of("timestamp", new Date(), "boards", boardsCount));
    }

}
