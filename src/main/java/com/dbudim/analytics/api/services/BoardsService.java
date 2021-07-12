package com.dbudim.analytics.api.services;

import com.dbudim.analytics.api.models.Board;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by dbudim on 10.07.2021
 */

public interface BoardsService {

    @GET("boards")
    Call<ResponseBody> getBoards();

    @GET("boards/{id}")
    Call<Board> getBoard(@Path("id") String id);

    @POST("boards")
    Call<Board> createBoard(@Body Board board);

    @PUT("boards/{id}")
    Call<Board> updateBoard(@Path("id") String id, @Body Board board);

    @DELETE("boards/{id}")
    Call<ResponseBody> deleteBoard(@Path("id") String id);
}
