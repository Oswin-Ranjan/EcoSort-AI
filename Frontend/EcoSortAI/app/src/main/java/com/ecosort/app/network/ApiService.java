package com.ecosort.app.network;

import com.ecosort.app.models.QueryRequest;
import com.ecosort.app.models.QueryResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/query")
    Call<QueryResponse> askQuestion(
            @Body QueryRequest request
    );
}