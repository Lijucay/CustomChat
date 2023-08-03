package com.lijukay.customchat.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lijukay.customchat.R;
import com.lijukay.customchat.utils.FileHandler;
import com.lijukay.customchat.utils.interfaces.OnClickListener;
import com.lijukay.customchat.utils.items.Contact;

import java.io.IOException;
import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private final Context context;
    private final ArrayList<Contact> contacts;
    private final OnClickListener onClickListener;

    public ContactsAdapter(Context context, ArrayList<Contact> contacts, OnClickListener onClickListener) {
        this.context = context;
        this.contacts = contacts;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chatpartner_item, parent, false);
        return new ContactsViewHolder(view, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {

        String firstName = contacts.get(position).getFirstName();
        String lastName = contacts.get(position).getLastName();
        String lastMessage;
        try {
            lastMessage = new FileHandler(context).getLastMessage(contacts.get(position).getNumber());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (lastName != null) {
            holder.contactsName.setText(firstName + " " + lastName);
        } else {
            holder.contactsName.setText(firstName);
        }

        if (lastMessage != null) {
            holder.lastMessage.setText(lastMessage);
        }
    }

    public void contactAdded() {
        this.notifyItemChanged(contacts.size() - 1);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {
        private final TextView contactsName;
        private final TextView lastMessage;

        public ContactsViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);

            contactsName = itemView.findViewById(R.id.contactName);
            lastMessage = itemView.findViewById(R.id.lastMessage);

            itemView.setOnClickListener(v -> {
                if (onClickListener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        onClickListener.onClick(position);
                    }
                }
            });
        }
    }
}
