package com.lijukay.customchat.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lijukay.customchat.R;

public class ContactInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            String contactsFirstName = intent.getStringExtra("Contacts first name");
            String contactsLastName = intent.getStringExtra("Contacts last name");
            String phone = intent.getStringExtra("Phone");

            TextView contactsFirstNameTV = findViewById(R.id.firstname);
            TextView contactsLastNameTV = findViewById(R.id.lastName);
            TextView contactsPhoneTV = findViewById(R.id.phoneNumber);

            if (contactsFirstName != null) {
                contactsFirstNameTV.setText(contactsFirstName);
            }
            if (contactsLastName != null) {
                contactsLastNameTV.setText(contactsLastName);
            }
            if (phone != null) {
                contactsPhoneTV.setText(phone);
            }

            findViewById(R.id.backToMessages).setOnClickListener(v -> onBackPressed());
        }
    }
}