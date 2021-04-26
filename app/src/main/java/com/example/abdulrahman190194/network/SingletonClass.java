package com.example.abdulrahman190194.network;

import com.example.abdulrahman190194.WeatherResponse;

public class SingletonClass {

    public static SingletonClass instance = null;

    public static WeatherResponse weatherResponse = null;

    public static WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public static void setWeatherResponse(WeatherResponse weatherResponse) {
        SingletonClass.weatherResponse = weatherResponse;
    }

    synchronized public static SingletonClass getInstance()
    {
        if (instance == null)
        {
            instance = new SingletonClass();
            return instance;
        }
        else
            return instance;
    }

}
