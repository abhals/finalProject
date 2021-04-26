package com.example.abdulrahman190194;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewUserActivity extends AppCompatActivity {

    Context context = this;

    EditText fname, lname, email, phone;
    Button adduserButton, deleteuserButton;

    UsersDB usersDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        usersDB = new UsersDB(context);

        fname = findViewById(R.id.edittext_fname);
        lname = findViewById(R.id.edittext_lname);
        email = findViewById(R.id.edittext_email);
        phone = findViewById(R.id.edittext_phone);
        adduserButton = findViewById(R.id.button_add_user);
        deleteuserButton = findViewById(R.id.button_delete_user);

        if (getIntent().getStringExtra("activity").equals("add"))
        {
            adduserButton.setText("Add User");
        }
        else
        {
            deleteuserButton.setVisibility(View.VISIBLE);
            adduserButton.setText("Update User");

            fname.setText(getIntent().getStringExtra("fname"));
            lname.setText(getIntent().getStringExtra("lname"));
            email.setText(getIntent().getStringExtra("email"));
            phone.setText(getIntent().getStringExtra("phone"));

        }

        adduserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("datatype").equals("firebase")) {
                    addToFirebase();
                }
                else
                {
                    addToSQLite();
                }
            }
        });

        deleteuserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("datatype").equals("firebase")) {
                    deletFromFirebase();
                }
                else
                {
                    usersDB.deletUser(Integer.parseInt(getIntent().getStringExtra("userid")));
                    finish();
                }
            }
        });

    }

    private void addToSQLite() {
        if (getIntent().getStringExtra("activity").equals("add"))
        {
            adduserButton.setText("Add User");

            if (usersDB.insertUser(new User(
                    Integer.parseInt(getIntent().getStringExtra("userid")),
                    fname.getText().toString(), lname.getText().toString(), phone.getText().toString(), email.getText().toString()
            )))
            {
                Toast.makeText(context, "Data is inserted!", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
        else
        {
            if (usersDB.updateUser(new User(
                    Integer.parseInt(getIntent().getStringExtra("userid")),
                    fname.getText().toString(), lname.getText().toString(), phone.getText().toString(), email.getText().toString()
            )))
            {
                Toast.makeText(context, "Data is updated!", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    private void addToFirebase() {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Saving Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        User user = new User(Integer.parseInt(getIntent().getStringExtra("userid")), fname.getText().toString(), lname.getText().toString(), phone.getText().toString(), email.getText().toString());

        myRef.child(getIntent().getStringExtra("userid")).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Data is added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void deletFromFirebase() {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Deleting Data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.child(getIntent().getStringExtra("userid")).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Data is deleted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}