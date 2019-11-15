package com.example.reddit2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final List<Message> messages = new ArrayList<>();
    DatabaseReference ref = null;

    public MainActivity() {
        ref = FirebaseDatabase.getInstance().getReference("messages");

//        List<Message> list= new ArrayList<>();
//        list.add(new Message(-1, "hello"));
//        list.add(new Message(-1, "bnye"));
//        list.add(new Message(-1, "new york"));
//        ref.setValue(list);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // view
        final ListView listview = (ListView) findViewById(R.id.list);
//        final ArrayAdapter<Message> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, messages);
        final MessageAdapter adapter =new MessageAdapter(this, messages);
        listview.setAdapter(adapter);

        // add post
        final EditText add_post_text_input = (EditText) findViewById(R.id.add_post_text_input);
        final Button add_post_btn = findViewById(R.id.add_post_btn);

        add_post_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String key = ref.push().getKey();
                ref.child(key).setValue(new Message(key, null, add_post_text_input.getText().toString()));
                add_post_text_input.setText("");
            }
        });

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message msg = dataSnapshot.getValue(Message.class);
                messages.add(msg);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message msg = dataSnapshot.getValue(Message.class);
                for (int i = 0; i < messages.size(); i++) {
                    if (messages.get(i).id.equals(msg.getId())) {
                        messages.set(i, msg);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

