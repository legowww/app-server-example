package com.example.domain;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import java.util.List;

public interface JsonApi {
    @POST("/route")
    Call<List<TimeRoute>> getTimeRoute(@Body LocationCoordinate locationCoordinate);
}
