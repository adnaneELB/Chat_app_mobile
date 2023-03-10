package com.example.application_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application_chat.R;
import com.example.chat.ChatAdapter;
import com.example.chat.ChatList;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Chat extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef1 = database.getReference("chat");

    private List<ChatList> chatLists = new ArrayList<>();
    String getChat_Key = "";
    String getUserID = "";

    private RecyclerView chatRECYCLE;
    private ChatAdapter chatAdapter;
    private boolean firstTime=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final ImageView backbtn = findViewById(R.id.backbtn);
        final TextView username = findViewById(R.id.username);
        final EditText messageEditText = findViewById(R.id.msgEditText);
        final ImageView profilePic = findViewById(R.id.profilePic);
        final ImageView sendBTN = findViewById(R.id.sendbtn);
        chatRECYCLE = findViewById(R.id.chatRecycleView);
        //get data from the adapter

        final String getName = getIntent().getStringExtra("name");
        getChat_Key = getIntent().getStringExtra("chat_Key");
        final String getid = getIntent().getStringExtra("id");

        //get userID from the memory
        try {
            getUserID = MemoryData.getData(Chat.this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        username.setText(getName);

        chatRECYCLE.setHasFixedSize(true);
        chatRECYCLE.setLayoutManager(new LinearLayoutManager(Chat.this));

        try {
            chatAdapter= new ChatAdapter(chatLists,Chat.this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        chatRECYCLE.setAdapter(chatAdapter);

        userRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getChat_Key.isEmpty()) {
                    getChat_Key = "1";
                    if (snapshot.exists()) {
                        getChat_Key = String.valueOf(snapshot.getChildrenCount() + 1);

                    }
                }

                if (snapshot.exists()){
                   // Toast.makeText(Chat.this, getChat_Key, Toast.LENGTH_SHORT).show();
                    if (snapshot.child(getChat_Key).hasChild("messages")){

                        chatLists.clear();
                        for (DataSnapshot messageSnap :snapshot.child(getChat_Key).child("messages").getChildren()){
                            if (messageSnap.hasChild("msg")&&messageSnap.hasChild("userID")){
                                final String messageTime=messageSnap.getKey();
                                final String getID=messageSnap.child("userID").getValue(String.class);
                                final String getMSG=messageSnap.child("msg").getValue(String.class);

                                ChatList chatList=new ChatList(getID,getName,getMSG);
                                chatLists.add(chatList);
                                try {
                                    if (firstTime || Long.parseLong(messageTime)>Long.parseLong(MemoryData.getlastMsg(getChat_Key,Chat.this))){
                                        firstTime=false;
                                        MemoryData.saveLastMsg(messageTime, getChat_Key, Chat.this);
                                       // Timestamp timestamp=new Timestamp(Long.parseLong(messageTime));

                                       chatAdapter.updateAdapter(chatLists);
                                       chatRECYCLE.scrollToPosition(chatLists.size()-1);
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //final String getPic=getIntent().getStringExtra("pic");
        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getTextMsg = messageEditText.getText().toString();
                final String getTime = String.valueOf(System.currentTimeMillis()).substring(0, 10);


                try {
                    MemoryData.saveLastMsg(getTime,getChat_Key,Chat.this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                userRef1.child(getChat_Key).child("user_1").setValue(getUserID);
                userRef1.child(getChat_Key).child("user_2").setValue(getid);
                userRef1.child(getChat_Key).child("messages").child(getTime).child("msg").setValue(getTextMsg);
                userRef1.child(getChat_Key).child("messages").child(getTime).child("userID").setValue(getUserID);
                messageEditText.setText("");
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}