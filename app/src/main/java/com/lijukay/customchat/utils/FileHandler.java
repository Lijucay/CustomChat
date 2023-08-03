package com.lijukay.customchat.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lijukay.customchat.R;
import com.lijukay.customchat.utils.items.Contact;
import com.lijukay.customchat.utils.items.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileHandler {

    private final Context context;

    public FileHandler(Context context) {
        this.context = context;
    }

    public ArrayList<Contact> getContacts() throws IOException {
        String destination = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + context.getString(R.string.app_name) + ".ct";
        if (new File(destination).exists()) {
            StringBuilder fileContentBuilder = new StringBuilder();
            FileInputStream fis = new FileInputStream(destination);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                fileContentBuilder.append(line).append("\n");
            }

            br.close();

            Gson gson = new Gson();
            return gson.fromJson(fileContentBuilder.toString(), new TypeToken<ArrayList<Contact>>() {
            }.getType());
        } else {
            return null;
        }
    }

    public String getLastMessage(String number) throws IOException {
        String destination = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + context.getString(R.string.app_name) + "-" + number + ".ccmsg";

        if (new File(destination).exists()) {
            ArrayList<Message> messages = getMessages(destination);
            return messages.get(messages.size() - 1).getMessage();
        }
        return null;
    }

    public void saveContactFileContent(ArrayList<Contact> contacts) throws IOException {
        String destination = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + context.getString(R.string.app_name) + ".ct";

        Gson gson = new Gson();
        String jsonString = gson.toJson(contacts);

        FileOutputStream fos = new FileOutputStream(destination);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(jsonString);
        bw.close();

        Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Message> getMessages(String destination) throws IOException { //Because a file also includes the name of the chat partner
        if (new File(destination).exists()) {
            StringBuilder fileContentBuilder = new StringBuilder();
            FileInputStream fis = new FileInputStream(destination);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                fileContentBuilder.append(line).append("\n");
            }

            br.close();

            Gson gson = new Gson();
            return gson.fromJson(fileContentBuilder.toString(), new TypeToken<ArrayList<Message>>() {
            }.getType());
        } else {
            return null;
        }
    }

    public void saveMessageFileContent(ArrayList<Message> messages, String name) throws IOException {
        String destination = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + context.getString(R.string.app_name) + "-" + name + ".ccmsg";

        Gson gson = new Gson();
        String jsonString = gson.toJson(messages);

        FileOutputStream fos = new FileOutputStream(destination);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(jsonString);
        bw.close();
    }

    public boolean isDuplicatedNumber(ArrayList<Contact> contacts, String number) {
        for (Contact contact : contacts) {
            if (contact.getNumber().equals(number)) {
                return true;
            }
        }
        return false;
    }
}
