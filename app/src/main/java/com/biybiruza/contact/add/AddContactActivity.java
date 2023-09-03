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
import com.biybiruza.contact.detail.DetailContactActivity;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddContactActivity extends AppCompatActivity {

    private ActivityAddContactBinding binding;
    private MyShared myShared;
    String toolbarTitle; int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myShared = new MyShared(this, new Gson());
        toolbarTitle = getIntent().getStringExtra("title");
        Toast.makeText(this, "toolbarTitle: "+toolbarTitle, Toast.LENGTH_SHORT).show();
        position = getIntent().getIntExtra("position",0);
        binding.tvToolbarTitle.setText(toolbarTitle);
        toolbarTitleChange();

        binding.ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBackButton();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(toolbarTitle, "Edit contact")) {

                    if (myShared.getList("key_",ContactModels.class) != null){
                        contactList.addAll(myShared.getList("key_",ContactModels.class));

                        //set data to ui
                       contactList.get(position).setName(binding.etName.getText().toString());
                       contactList.get(position).setSurname(binding.etSurname.getText().toString());
                       contactList.get(position).setPhoneNumber(binding.etPhoneNumber.getText().toString());

                        Intent intent = new Intent(AddContactActivity.this, DetailContactActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else if (Objects.equals(toolbarTitle, "Add new contact")) {

                    isEmpty();
                }
            }
        });
    }

    List<ContactModels> contactList = new ArrayList<>();

    void onClickBackButton() { //ishlayabdi
        if (Objects.equals(toolbarTitle, "Edit contact")) {

            Intent intent = new Intent(AddContactActivity.this, DetailContactActivity.class);
            startActivity(intent);
            finish();

        } else if (Objects.equals(toolbarTitle, "Add new contact")) {

            Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
    void toolbarTitleChange() {
        if (toolbarTitle == "Edit contact") {

            if (myShared.getList("key_",ContactModels.class) != null){
                contactList.addAll(myShared.getList("key_",ContactModels.class));

                //get data to ui
                binding.etName.setText(contactList.get(position).getName());
                binding.etSurname.setText(contactList.get(position).getSurname());
                binding.etPhoneNumber.setText(contactList.get(position).getPhoneNumber());
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void isEmpty(){
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

    private void save() {

        List<ContactModels> contactList = new ArrayList<>();
        if (myShared.getList("key_",ContactModels.class) != null){
            contactList.addAll(myShared.getList("key_",ContactModels.class));
            Toast.makeText(this, "list: "+myShared.getList("key_",ContactModels.class).toString(), Toast.LENGTH_SHORT).show();
        }

        contactList.add(new ContactModels(binding.etName.getText().toString(), binding.etSurname.getText().toString(),
                binding.etPhoneNumber.getText().toString()));


        myShared.putList("key_", contactList);

        Toast.makeText(AddContactActivity.this, "Saved", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}