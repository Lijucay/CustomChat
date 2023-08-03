package com.lijukay.customchat.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.elevation.SurfaceColors;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.lijukay.customchat.R;
import com.lijukay.customchat.utils.FileHandler;
import com.lijukay.customchat.utils.adapter.ContactsAdapter;
import com.lijukay.customchat.utils.interfaces.OnClickListener;
import com.lijukay.customchat.utils.items.Contact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int color = SurfaceColors.SURFACE_2.getColor(this);
        findViewById(R.id.materialToolbar).setBackgroundColor(color);
        getWindow().setStatusBarColor(color);

        RecyclerView recyclerView = findViewById(R.id.contactsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ExtendedFloatingActionButton efab = findViewById(R.id.newChatFAB);

        FileHandler fileHandler = new FileHandler(this);

        try {
            contacts = fileHandler.getContacts();
            if (contacts != null) {
                findViewById(R.id.noItems).setVisibility(View.GONE);
            } else {
                findViewById(R.id.noItems).setVisibility(View.VISIBLE);
                contacts = new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        contactsAdapter = new ContactsAdapter(this, contacts, this);
        recyclerView.setAdapter(contactsAdapter);


        efab.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);

            @SuppressLint("InflateParams") View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.new_chatbsd, null);
            TextInputLayout contactFNameTIL = view.findViewById(R.id.contactFName);
            TextInputLayout contactLNameTIL = view.findViewById(R.id.contactLName);
            TextInputLayout contactNumberTIL = view.findViewById(R.id.contactNumber);

            view.findViewById(R.id.addContact).setOnClickListener(v1 -> {

                String firstname = Objects.requireNonNull(contactFNameTIL.getEditText()).getText().toString().trim();
                String number = Objects.requireNonNull(contactNumberTIL.getEditText()).getText().toString().trim();
                String lastName = Objects.requireNonNull(contactLNameTIL.getEditText()).getText().toString().trim();

                if (!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(number)) {

                    Random random = new Random();
                    StringBuilder id = new StringBuilder();
                    for (int i = 0; i < 10; i++) {
                        id.append(random.nextInt(9));
                    }

                    System.out.println(id);

                    if (fileHandler.isDuplicatedNumber(contacts, number)) {
                        Toast.makeText(this, "Number already exists", Toast.LENGTH_SHORT).show();
                    } else {

                        Contact contact;

                        if (!TextUtils.isEmpty(lastName)) {
                            contact = new Contact(firstname, lastName, number, id.toString());
                        } else {
                            contact = new Contact(firstname, null, number, id.toString());
                        }

                        contacts.add(contact);
                        contactsAdapter.contactAdded();

                        if (findViewById(R.id.noItems).getVisibility() == View.VISIBLE) {
                            findViewById(R.id.noItems).setVisibility(View.GONE);
                        }
                        try {
                            fileHandler.saveContactFileContent(contacts);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Name and Number required", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.setContentView(view);
            dialog.show();
        });
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(MainActivity.this, MessagesActivity.class);
        if (contacts.get(position).getLastName() != null) {
            intent.putExtra("DisplayFirstName", contacts.get(position).getFirstName());
            intent.putExtra("DisplayLastName", contacts.get(position).getLastName());
        } else {
            intent.putExtra("DisplayFirstName", contacts.get(position).getFirstName());
            intent.putExtra("DisplayLastName", "");
        }
        intent.putExtra("FileName", contacts.get(position).getNumber());
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        contactsAdapter.notifyDataSetChanged();
    }
}