package com.example.abdulrahman190194;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    String datatype;

    public UsersAdapter(Context context, ArrayList<User> userArrayList, String datatype) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.datatype = datatype;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_singleitem_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.name.setText("Name: "+userArrayList.get(i).getFirstName() + " " + userArrayList.get(i).getLastName());
        viewHolder.email.setText("Email: "+userArrayList.get(i).getEmailAddress());
        viewHolder.phone.setText("Phone: "+userArrayList.get(i).getPhoneNumber());
        viewHolder.id.setText("User ID: "+userArrayList.get(i).getUserId());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AddNewUserActivity.class)
                        .putExtra("fname", userArrayList.get(i).getFirstName())
                        .putExtra("lname", userArrayList.get(i).getLastName())
                        .putExtra("email", userArrayList.get(i).getEmailAddress())
                        .putExtra("phone", userArrayList.get(i).getPhoneNumber())
                        .putExtra("activity", "update")
                        .putExtra("datatype", datatype)
                        .putExtra("userid", String.valueOf(userArrayList.get(i).getUserId()))
                );
            }
        });
    }

    @Override
    public int getItemCount() {

        return userArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, email, phone, id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name);
            email = itemView.findViewById(R.id.user_email);
            phone = itemView.findViewById(R.id.user_phone);
            id = itemView.findViewById(R.id.user_id);

        }

    }

}
