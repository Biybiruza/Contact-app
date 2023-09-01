package com.biybiruza.contact.add;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
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
    private static final String TAG = "Contact_tag";
    private static final int WRITE_CONTACT_PERMISSION_CODE = 100;
    private static final int IMAGE_PICK_GALLERY_CODE = 100;
    private String[] contactPermission;

    private Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryIntent();
            }
        });

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
                    saveContact();
                }
            }
        });
    }

    private void openGalleryIntent() {
        //intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //handle result either image picked or not
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //image picked
                image_uri = data.getData();
                //set to imageview
                binding.ivImg.setImageURI(image_uri);
            }
        } else {
            //cancelled
            Toast.makeText(this, "Cancelled...", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveContact() {

        //input data
        String name = binding.etName.getText().toString().trim();
        String surname = binding.etSurname.getText().toString().trim();
        String phone = binding.etPhoneNumber.getText().toString().trim();

        ArrayList<ContactModels> list = new ArrayList<>();

        //contact id
        int rawContactId = list.size();




        list.add(new ContactModels(binding.etName.getText().toString(), binding.etSurname.getText().toString(),
                binding.etPhoneNumber.getText().toString()));


        Log.d("TAG", "list: ");
        binding.etName.setText("");
        binding.etSurname.setText("");
        binding.etPhoneNumber.setText("");

        Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}