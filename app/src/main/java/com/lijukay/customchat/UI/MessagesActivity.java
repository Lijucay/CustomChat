package com.lijukay.customchat.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.elevation.SurfaceColors;
import com.lijukay.customchat.R;
import com.lijukay.customchat.utils.FileHandler;
import com.lijukay.customchat.utils.adapter.MessagesAdapter;
import com.lijukay.customchat.utils.items.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MessagesActivity extends AppCompatActivity {

    private ArrayList<Message> messages;
    private MessagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        int color = SurfaceColors.SURFACE_2.getColor(this);
        TextView titleText = findViewById(R.id.titleText);
        RecyclerView messagesRV = findViewById(R.id.messages);
        messagesRV.setLayoutManager(new LinearLayoutManager(this));

        appBarLayout.setBackgroundColor(color);
        getWindow().setStatusBarColor(color);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String displayFirstName = intent.getStringExtra("DisplayFirstName");
            String displayLastName = intent.getStringExtra("DisplayLastName");
            String fileName = intent.getStringExtra("FileName");
            titleText.setText(displayFirstName + " " + displayLastName);
            String destination = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + getString(R.string.app_name) + "-" + fileName + ".ccmsg";

            FileHandler fileHandler = new FileHandler(this);
            try {
                messages = fileHandler.getMessages(destination);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (messages == null) {
                messages = new ArrayList<>();
            }

            adapter = new MessagesAdapter(this, messages);
            messagesRV.setAdapter(adapter);

            MaterialCardView otherMessageCard = findViewById(R.id.othersMessage);
            MaterialCardView yourMessageCard = findViewById(R.id.yourMessage);
            MaterialCardView contactNameCard = findViewById(R.id.contactNameCard);
            EditText messageText = findViewById(R.id.messageEditText);

            otherMessageCard.setOnClickListener(v -> {
                String message = messageText.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {

                    messages.add(new Message(message, displayFirstName + " " + displayLastName, getDate(), getTime()));
                    adapter.notifyItemInserted(messages.size() - 1);
                    messagesRV.scrollToPosition(messages.size() - 1);
                    messageText.setText("");
                    try {
                        fileHandler.saveMessageFileContent(messages, fileName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

            yourMessageCard.setOnClickListener(v -> {
                String message = messageText.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    messages.add(new Message(message, "You", getDate(), getTime()));
                    adapter.notifyItemInserted(messages.size() - 1);
                    messagesRV.scrollToPosition(messages.size() - 1);
                    messageText.setText("");
                    try {
                        fileHandler.saveMessageFileContent(messages, fileName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            contactNameCard.setOnClickListener(v -> {
                Intent intentCI = new Intent(MessagesActivity.this, ContactInfoActivity.class);
                intentCI.putExtra("Contacts first name", displayFirstName);
                intentCI.putExtra("Contacts last name", displayLastName);
                intentCI.putExtra("Phone", fileName);
                startActivity(intentCI);
            });
        }

        findViewById(R.id.backToOverview).setOnClickListener(v -> onBackPressed());
    }

    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR);
    }

    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.MINUTE);
        if (calendar.get(Calendar.MINUTE) < 10) {
            return calendar.get(Calendar.HOUR) + ":" + "0" + calendar.get(Calendar.MINUTE);
        } else {
            return calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
        }
    }
}