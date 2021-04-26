package com.example.abdulrahman190194;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.abdulrahman190194.network.SingletonClass;

public class WeatherCurrentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_current);


        TextView data = findViewById(R.id.weather_details);

        data.setText(
                "Pressure: "+ String.valueOf(SingletonClass.getWeatherResponse().getCurrent().getPressure())+"\n\n"+
                "Temperature: "+ String.valueOf(SingletonClass.getWeatherResponse().getCurrent().getTemp())+"\n\n"+
                "Humidity: "+ String.valueOf(SingletonClass.getWeatherResponse().getCurrent().getHumidity())
        );

    }
}