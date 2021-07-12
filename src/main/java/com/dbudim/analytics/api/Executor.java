package com.dbudim.analytics.api;

import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by dbudim on 09.07.2021
 */

public class Executor {

    public static <T> Response<T> execute(Call<T> call) {
        try {
            return call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
