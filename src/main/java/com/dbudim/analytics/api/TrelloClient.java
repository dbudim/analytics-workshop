package com.dbudim.analytics.api;

import com.dbudim.analytics.api.interceptors.AsserterInterceptor;
import com.dbudim.analytics.api.interceptors.AuthInterceptor;
import com.dbudim.analytics.api.interceptors.BoardsAnalyticsListener;
import com.dbudim.analytics.api.interceptors.LogInterceptor;
import com.dbudim.analytics.api.services.BoardsService;
import com.dbudim.analytics.api.services.MemberService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.Duration;

/**
 * Created by dbudim on 09.07.2021
 */

public class TrelloClient {

    public BoardsService boardsService;
    public MemberService memberService;

    public Interceptors interceptors = new Interceptors();

    private static final String BASE_URL = "https://api.trello.com/1/";

    public TrelloClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptors.authInterceptor)
                .addInterceptor(interceptors.boardsAnalyticsListener)
                .addInterceptor(interceptors.logInterceptor)
                .addInterceptor(interceptors.asserterInterceptor)
                .callTimeout(Duration.ofSeconds(30))
                .connectTimeout(Duration.ofSeconds(30))
                .readTimeout(Duration.ofSeconds(30))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        boardsService = retrofit.create(BoardsService.class);
        memberService = retrofit.create(MemberService.class);
    }

    public class Interceptors {
        public AuthInterceptor authInterceptor = new AuthInterceptor();
        public BoardsAnalyticsListener boardsAnalyticsListener = new BoardsAnalyticsListener();
        public LogInterceptor logInterceptor = new LogInterceptor();
        public AsserterInterceptor asserterInterceptor = new AsserterInterceptor();
    }
}
