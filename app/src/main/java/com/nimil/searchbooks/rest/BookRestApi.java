package com.nimil.searchbooks.rest;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookRestApi {
    //https://www.googleapis.com/books/v1/volumes?
    @GET("/books/v1/volumes")
    Call<Feed> fetchFeed(@Query("q") String q,
                         @Query("maxResults") Integer maxRes);
                         //@Query("page") long page,
                         //@Query("pageSize") int pageSize);
}
