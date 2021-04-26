package com.example.abdulrahman190194;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdulrahman190194.network.RestClient;
import com.example.abdulrahman190194.network.SingletonClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    Context context = this;

    Button currentDataButton, weatherDataButton, searchButton;
    EditText latEditText, longEditText;
    TextView cityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        currentDataButton = findViewById(R.id.currentdata);
        weatherDataButton = findViewById(R.id.weather);
        searchButton = findViewById(R.id.search);
        latEditText = findViewById(R.id.lat);
        longEditText = findViewById(R.id.lng);
        cityTextView = findViewById(R.id.city);

        requestForWeatherAPI("37.9838", "23.7275");

        currentDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, WeatherCurrentActivity.class));
            }
        });

        weatherDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, WeatherDataActivity.class));
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestForWeatherAPI(latEditText.getText().toString(), longEditText.getText().toString());
            }
        });

    }

    private void requestForWeatherAPI(String lat, String lng) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Call<WeatherResponse> apiForWeather = RestClient.getInstance().getApiServices().requestForWeather("https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lng+"&exclude=daily,minutely,hourly,alerts&appid=26c2931fff63916c8f92f0960fbc6c8a");

        apiForWeather.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body().getTimezone() != null)
                    {
                        SingletonClass.setWeatherResponse(response.body());
                        cityTextView.setText("Zone: "+response.body().getTimezone());
                    }
                }
                else
                {
                    Toast.makeText(WeatherActivity.this, "Data not received!", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(WeatherActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
}