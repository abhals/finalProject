package com.example.abdulrahman190194.network;

import com.example.abdulrahman190194.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface ServicesAPI {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("")
    Call<WeatherResponse> requestForWeather(@Url String url);

}
