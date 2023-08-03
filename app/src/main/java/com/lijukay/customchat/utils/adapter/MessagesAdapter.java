package com.lijukay.customchat.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lijukay.customchat.R;
import com.lijukay.customchat.utils.items.Message;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_YOUR_MESSAGE = 0;
    private static final int VIEW_TYPE_OTHERS_MESSAGE = 1;
    private final Context context;
    private final ArrayList<Message> messages;

    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_YOUR_MESSAGE) {
            View yourMessageView = LayoutInflater.from(context).inflate(R.layout.message_item_you, parent, false);
            return new YourMessageViewHolder(yourMessageView);
        } else {
            View othersMessageView = LayoutInflater.from(context).inflate(R.layout.message_item_them, parent, false);
            return new OthersMessageViewHolder(othersMessageView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (holder.getItemViewType() == VIEW_TYPE_YOUR_MESSAGE) {
            YourMessageViewHolder yourHolder = (YourMessageViewHolder) holder;
            yourHolder.bind(message);
        } else {
            OthersMessageViewHolder othersHolder = (OthersMessageViewHolder) holder;
            othersHolder.bind(message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        String writer = messages.get(position).getWriter();
        if (writer.equals("You")) {
            return VIEW_TYPE_YOUR_MESSAGE;
        } else {
            return VIEW_TYPE_OTHERS_MESSAGE;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public static class YourMessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView message;
        private final TextView time;

        public YourMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            itemView.setOnClickListener(v -> {
                time.setVisibility(time.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            });
        }

        public void bind(Message message) {
            this.message.setText(message.getMessage());
            this.time.setText(message.getTime());
            // Additional bindings for your messages
        }
    }

    public static class OthersMessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView message;
        private final TextView time;

        public OthersMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(v -> {
                time.setVisibility(time.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            });
        }

        public void bind(Message message) {
            this.message.setText(message.getMessage());
            this.time.setText(message.getTime());
            // Additional bindings for others' messages
        }
    }

}
