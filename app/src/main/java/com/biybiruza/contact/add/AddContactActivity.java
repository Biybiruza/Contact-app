package com.biybiruza.contact.add;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.biybiruza.contact.MainActivity;
import com.biybiruza.contact.MyShared;
import com.biybiruza.contact.data.ContactModels;
import com.biybiruza.contact.databinding.ActivityAddContactBinding;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    private ActivityAddContactBinding binding;
    private MyShared myShared;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myShared = new MyShared(this, new Gson());
        sharedPreferences = getSharedPreferences("Count", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.etName.getText().toString().isEmpty()){
                    Toast.makeText(AddContactActivity.this, "Enter name, please", Toast.LENGTH_SHORT).show();
                } else if (binding.etPhoneNumber.getText().toString().isEmpty()){
                    Toast.makeText(AddContactActivity.this, "Enter phone number, please", Toast.LENGTH_SHORT).show();
                } else if (binding.etName.getText().toString().isEmpty() &&
                        binding.etPhoneNumber.getText().toString().isEmpty()){
                    Toast.makeText(AddContactActivity.this, "Enter name and phone number, please", Toast.LENGTH_SHORT).show();
                } else {
                    save();
                }
            }
        });
    }

    private void save() {

        List<ContactModels> contactList = new ArrayList<>();
        contactList.addAll(myShared.getList("key_",ContactModels.class));


        contactList.add(new ContactModels(binding.etName.getText().toString(), binding.etSurname.getText().toString(),
                binding.etPhoneNumber.getText().toString()));


        myShared.putList("key_", contactList);

        Toast.makeText(AddContactActivity.this, "Saved", Toast.LENGTH_SHORT).show();


        binding.etName.setText("");
        binding.etSurname.setText("");
        binding.etPhoneNumber.setText("");

        Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}