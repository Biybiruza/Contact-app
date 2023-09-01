package com.biybiruza.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.biybiruza.contact.adapter.AdapterContact;
import com.biybiruza.contact.add.AddContactActivity;
import com.biybiruza.contact.data.ContactModels;
import com.biybiruza.contact.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    AdapterContact adapterContact;
    private MyShared myShared;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        count = getIntent().getIntExtra("key", 0);
        myShared = new MyShared(this, new Gson());

        loadData();

        binding.fbAddContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadData() {
        Toast.makeText(this, "count: "+count, Toast.LENGTH_SHORT).show();
        List<ContactModels> contactList = myShared.getList("key_"+count,ContactModels.class);

        if (contactList != null) {
            binding.tvTextView.setVisibility(View.GONE);
            binding.rvContact.setVisibility(View.VISIBLE);

            adapterContact = new AdapterContact(contactList);
            binding.rvContact.setAdapter(adapterContact);
        } else {
            binding.tvTextView.setVisibility(View.VISIBLE);
            binding.rvContact.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        List<ContactModels> contactList = myShared.getList("key_"+count,ContactModels.class);

        if (contactList != null) {
            binding.tvTextView.setVisibility(View.GONE);
            binding.rvContact.setVisibility(View.VISIBLE);

            adapterContact = new AdapterContact(contactList);
            binding.rvContact.setAdapter(adapterContact);
        } else {
            binding.tvTextView.setVisibility(View.VISIBLE);
            binding.rvContact.setVisibility(View.GONE);
        }

    }
}