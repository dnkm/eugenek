package com.example.reddit2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MessageAdapter extends ArrayAdapter<Message> {
    private Context mContext;
    private List<Message> list = new ArrayList<>();
    private MainActivity mainActivity;

    public MessageAdapter(Context context, List<Message> list) {
        super(context, 0, list);
        mContext = context;
        this.list = list;
        this.mainActivity = mainActivity;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.message_view, parent, false);

        final Message msg = list.get(position);
        TextView rowText = (TextView) listItem.findViewById(R.id.message_row_text);
        rowText.setText(msg.text);

        Button voteBtn = listItem.findViewById(R.id.vote_btn);
        voteBtn.setText("VOTE (" + msg.score + ")");

        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("messages").child(msg.id).child("score").setValue(msg.score+1);
            }
        });

        final EditText reply_text_box= listItem.findViewById(R.id.reply_text_box);
        Button reply_btn = listItem.findViewById(R.id.reply_btn);
        reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg.replies.add(reply_text_box.getText().toString());
                FirebaseDatabase.getInstance().getReference("messages").child(msg.id).child("replies").setValue(msg.replies);
                reply_text_box.setText("");
            }
        });

        TextView replies_text_view = listItem.findViewById(R.id.replies_text_view);
        String replies = "";
        for(String s : msg.replies)
            replies += s + "\n";
        replies_text_view.setText(replies);


//        ArrayAdapter<String> replyAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, R.id.replies_list_view , msg.replies);
//        ListView replies_list_view = (ListView) listItem.findViewById(R.id.replies_list_view);
//        replies_list_view.setAdapter(replyAdapter);

        return listItem;
    }
}
