package com.rasel.recyclerviewexpandable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface JsonPlaceHolderApi {

    @Headers("authorization: 32DFCFD@#&DSFDSFSDF!L@?hh7@32DF")
    @GET("client")
    Call<ApiResponse> getApiResponse();
}