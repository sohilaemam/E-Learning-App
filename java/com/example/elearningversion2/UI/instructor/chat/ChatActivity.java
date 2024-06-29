package com.example.elearningversion2.UI.instructor.chat;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningversion2.Adapters.ChatAdapter;
import com.example.elearningversion2.Const;
import com.example.elearningversion2.R;
import com.example.elearningversion2.databinding.ActivityChatBinding;
import com.example.elearningversion2.models.ModelChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding ;
    String courseId ;
    ChatAdapter adapter = new ChatAdapter();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private ArrayList<ModelChat> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        courseId = getIntent().getStringExtra("id");
        getData(courseId);
        sendClick();
    }

    private void sendClick()
    {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String massage = binding.editEmail.getText().toString().trim();
                if (!massage.equals("")){
                    binding.editEmail.setText("");
                    getUserName(massage);
                }else {
                    binding.editEmail.setError(getString(R.string.Required));
                }

            }
        });
    }
    private void getUserName(String message)
    {
        courseId = getIntent().getStringExtra("id");
        ref.child(Const.USERS).child(auth.getUid()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                sendData(courseId , message , name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void sendData (String courseId , String  massage  , String userName)
    {
        ref.child(Const.CHAT).child(courseId).push().setValue(new ModelChat(massage , auth.getUid() ,userName));
    }
    private void getData(String courseId)
    {
        ref.child(Const.CHAT).child(courseId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    list.add(snapshot1.getValue(ModelChat.class));
                }
                adapter.setList(list);
                binding.recyclerChat.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
