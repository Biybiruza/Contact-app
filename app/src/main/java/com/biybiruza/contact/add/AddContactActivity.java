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
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myShared = new MyShared(this, new Gson());
        sharedPreferences = getSharedPreferences("Count", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        count = sharedPreferences.getInt("count", 0);

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
        List<ContactModels> list = new ArrayList<>();

        list.add(new ContactModels(binding.etName.getText().toString(), binding.etSurname.getText().toString(),
                binding.etPhoneNumber.getText().toString()));

        count++;
        editor.putInt("count", count);
        editor.apply();
        myShared.putList("key_"+count, list);

        Toast.makeText(AddContactActivity.this, "key_"+count, Toast.LENGTH_SHORT).show();
        Toast.makeText(AddContactActivity.this, "Saved", Toast.LENGTH_SHORT).show();

        Log.d("TAG", "list: ");
        binding.etName.setText("");
        binding.etSurname.setText("");
        binding.etPhoneNumber.setText("");

        Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
        intent.putExtra("key", count);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        count = sharedPreferences.getInt("count", 0);
    }
}