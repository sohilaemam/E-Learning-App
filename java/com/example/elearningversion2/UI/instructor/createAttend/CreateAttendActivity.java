package com.example.elearningversion2.UI.instructor.createAttend;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningversion2.Const;
import com.example.elearningversion2.databinding.ActivityCreateAttendCodeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class CreateAttendActivity extends AppCompatActivity {

    ActivityCreateAttendCodeBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAttendCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        generateCodeClick();
        confirmClick();
        copy(binding.attendText.getText().toString());
    }

    private String generateRandomCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }
        return codeBuilder.toString();
    }
    private void generateCodeClick()
    {
        binding.generateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String randomCode = generateRandomCode(4);
                binding.attendText.setText(randomCode);
            }
        });
    }
    private void confirmClick()
    {
        binding.confirmCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.attendText.getText().toString().trim();
                String id = getIntent().getStringExtra("id");
                ref.child(Const.ATTEND).child(id).child(code+"").setValue("").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateAttendActivity.this , "Attend Code is considered" , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void copy(String textToCopy)
    {
        binding.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied Text", textToCopy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "Text copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
