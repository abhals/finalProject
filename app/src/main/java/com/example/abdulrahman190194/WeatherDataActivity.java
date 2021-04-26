package com.example.abdulrahman190194;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.abdulrahman190194.network.SingletonClass;

public class WeatherDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_data);

        TextView data = findViewById(R.id.weather_details);

        data.setText(
                "Weather: "+ SingletonClass.getWeatherResponse().getCurrent().getWeather().get(0).getMain()+"\n\n"+
                "Description: "+ SingletonClass.getWeatherResponse().getCurrent().getWeather().get(0).getDescription()
        );

    }
}