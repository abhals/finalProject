package com.example.abdulrahman190194;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

    Context context = this;

    RecyclerView usersRecyclerView;

    FloatingActionButton addUserButton;

    UsersDB usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        usersDB = new UsersDB(context);

        addUserButton = findViewById(R.id.add_new_user_button);
        usersRecyclerView = findViewById(R.id.users_recyclerview);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        loadData();

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddNewUserActivity.class)
                        .putExtra("activity", "add")
                        .putExtra("datatype", getIntent().getStringExtra("datatype"))
                        .putExtra("userid", String.valueOf(userArrayList.size()+1))
                );
            }
        });

    }

    private void loadData() {
        if (getIntent().getStringExtra("datatype").equals("firebase"))
        {
            loadDataFromFirebase();
        }
        else
        {
            loadDataFromSQLite();
        }

    }

    @Override
    protected void onResume() {
        loadData();

        super.onResume();
    }

    private void loadDataFromSQLite() {
        userArrayList = new ArrayList<>();
        userArrayList = usersDB.getUsersList();

        usersRecyclerView.setAdapter(new UsersAdapter(context, userArrayList, "sqlite"));
    }

    ArrayList<User> userArrayList;

    private void loadDataFromFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);

                userArrayList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userArrayList.add(user);
                }

                usersRecyclerView.setAdapter(new UsersAdapter(context, userArrayList, "firebase"));

//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}