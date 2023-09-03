package com.biybiruza.contact;

import static android.Manifest.permission.CALL_PHONE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.biybiruza.contact.adapter.AdapterContact;
import com.biybiruza.contact.add.AddContactActivity;
import com.biybiruza.contact.data.ContactModels;
import com.biybiruza.contact.databinding.ActivityMainBinding;
import com.biybiruza.contact.detail.DetailContactActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    AdapterContact adapterContact;
    private MyShared myShared;
    List<ContactModels> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myShared = new MyShared(this, new Gson());

        loadData();

        binding.fbAddContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                intent.putExtra("title", "Add new contact");
                startActivity(intent);
                finish();
            }
        });



    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapterContact = new AdapterContact(contactList);
//    }

    public void loadData() {
        if (myShared.getList("key_",ContactModels.class) != null){
            contactList.addAll(myShared.getList("key_",ContactModels.class));
            binding.tvTextView.setVisibility(View.GONE);
            binding.rvContact.setVisibility(View.VISIBLE);
        } else {
            binding.tvTextView.setVisibility(View.VISIBLE);
            binding.rvContact.setVisibility(View.GONE);
        }

        Log.d("main", ""+contactList.toString());
        //get phone
            adapterContact = new AdapterContact(contactList,
                    new AdapterContact.OnItemClickListener() { //item click
                @Override
                public void onItemClick(int position) {
                    onItemClickListener(position);
                }
            },
                    new AdapterContact.OnCallClickListener() { //call click of itemView
                @Override
                public void onCallClick(int position) {
                    String phone = "+998"+contactList.get(position).getPhoneNumber();
                    Log.d("tel:", "tel:"+phone);
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                    if (ActivityCompat.checkSelfPermission(MainActivity.this,
                            CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(callIntent);
                    } else {
                        int REQUEST_CALL_PERMISSION = 100;
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{CALL_PHONE}, REQUEST_CALL_PERMISSION);
                    }
                }
            });

        adapterContact.notifyDataSetChanged();
        binding.rvContact.setAdapter(adapterContact);

    }

    private void onItemClickListener(int position) {
        Toast.makeText(this, contactList.get(position).getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailContactActivity.class);
        intent.putExtra("name", contactList.get(position).getName());
        intent.putExtra("surname", contactList.get(position).getSurname());
        intent.putExtra("phone", contactList.get(position).getPhoneNumber());
        intent.putExtra("position", position);
        startActivity(intent);
    }
}