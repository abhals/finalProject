package com.example.abdulrahman190194;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Context context = this;

    Button firebaseDataButton, sqliteDataButton, weatherButton;

    UsersDB usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersDB = new UsersDB(context);

        firebaseDataButton = findViewById(R.id.firebasae_data_button);
        sqliteDataButton = findViewById(R.id.sqlite_data_button);
        weatherButton = findViewById(R.id.weather_button);

        clickListeners();

        String jsonString = String.valueOf(Html.fromHtml(loadJSONFromAsset()));
        Gson gson = new Gson();
        UsersJson usersJson = gson.fromJson(jsonString, UsersJson.class);
        Log.e("datajson", String.valueOf(usersJson.getUsers().size()));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        for (User user : usersJson.getUsers()) {
            myRef.child(String.valueOf(user.getUserId())).setValue(user);
        }

        if (usersDB.getUsersList().size() == 0) {
            for (User user : usersJson.getUsers()) {
                usersDB.insertUser(user);
            }
        }


    }

    private void clickListeners() {
        firebaseDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, UsersListActivity.class).putExtra("datatype", "firebase"));
            }
        });
        sqliteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, UsersListActivity.class).putExtra("datatype", "sqlite"));
            }
        });
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, WeatherActivity.class));
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("users.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}