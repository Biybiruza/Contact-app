package com.biybiruza.contact.detail;

import static android.Manifest.permission.CALL_PHONE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.biybiruza.contact.MainActivity;
import com.biybiruza.contact.MyShared;
import com.biybiruza.contact.add.AddContactActivity;
import com.biybiruza.contact.data.ContactModels;
import com.biybiruza.contact.databinding.ActivityDetailContactBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DetailContactActivity extends AppCompatActivity {

    private ActivityDetailContactBinding binding;
    List<ContactModels> contactList = new ArrayList<>();
    MyShared myShared;
    int position;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailContactBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        myShared = new MyShared(this, new Gson());

        position = getIntent().getIntExtra("position", 0);
        Toast.makeText(this, "position: "+position, Toast.LENGTH_SHORT).show();

        if (myShared.getList("key_",ContactModels.class) != null) {
            contactList.addAll(myShared.getList("key_", ContactModels.class));

            binding.tvName.setText(contactList.get(position).getName() + " " + contactList.get(position).getSurname());
            binding.tvPhoneNumber.setText("+998" + contactList.get(position).getPhoneNumber());
        }

        binding.ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailContactActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //call action
        binding.ivCallBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //get phone
                String phone = binding.tvPhoneNumber.getText().toString().trim();

                Log.d("tel:", "tel:"+phone);
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(DetailContactActivity.this,
                        CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                } else {
                    int REQUEST_CALL_PERMISSION = 100;
                    ActivityCompat.requestPermissions(DetailContactActivity.this,
                            new String[]{CALL_PHONE}, REQUEST_CALL_PERMISSION);
                }
            }
        });

        binding.ivMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailContactActivity.this, "message function has not been added yet", Toast.LENGTH_SHORT).show();
                
            }
        });

        //edit click
        binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editContact();
            }
        });
    }

    private void editContact() {
        Intent intent = new Intent(this, AddContactActivity.class);
        intent.putExtra("title", "Edit contact");
        intent.putExtra("position", position);
        startActivity(intent);
        finish();
    }
}