package com.example.finalproject.network;

import com.example.finalproject.model.SearchPageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetItemDataService {

    @GET("search?order=desc&sort=creation&site=stackoverflow")
    Call<SearchPageResult> getSearchResult(
            @Query("tagged") String search);
}
