package com.dbudim.analytics.api.services;

import com.dbudim.analytics.api.models.MeModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dbudim on 12.07.2021
 */

public interface MemberService {

    @GET("Members/me")
    Call<MeModel> getMe(@Query("boards") String boards);

}
