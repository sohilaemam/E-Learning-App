package com.example.elearningversion2.UI.instructor.createCourse;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningversion2.UI.instructor.chat.ChatActivity;
import com.example.elearningversion2.UI.instructor.createAttend.CreateAttendActivity;
import com.example.elearningversion2.UI.instructor.grades.ShowGradesToInstructorActivity;
import com.example.elearningversion2.UI.instructor.material.UploadMaterialActivity;
import com.example.elearningversion2.UI.instructor.quiz.CreateQuizActivity;
import com.example.elearningversion2.databinding.ActivityInstructorFunctionalityBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InstructorFunctionalityActivity extends AppCompatActivity {
    ActivityInstructorFunctionalityBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstructorFunctionalityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getCourseName();
        getCourseId();
        attendClick();
        gradesClick();
        materialClick();
        quizClick();
        chatClick();
    }
    private void getCourseId ()
    {
        String courseId = getIntent().getStringExtra("courseId");
        binding.courseId.setText(courseId);
        copy(courseId);
    }
    private void getCourseName()
    {
        String courseName = getIntent().getStringExtra("courseName");
        binding.courseWord.setText(courseName);
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
    private void attendClick()
    {
        binding.cardViewAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorFunctionalityActivity.this , CreateAttendActivity.class);
                intent.putExtra("id" , binding.courseId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
    private void gradesClick()
    {
        binding.cardViewGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorFunctionalityActivity.this , ShowGradesToInstructorActivity.class);
                intent.putExtra("id" , binding.courseId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
    private void materialClick()
    {
        binding.cardViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorFunctionalityActivity.this , UploadMaterialActivity.class);
                intent.putExtra("id" , binding.courseId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
    private void quizClick()
    {
        binding.cardViewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorFunctionalityActivity.this , CreateQuizActivity.class);
                intent.putExtra("id" , binding.courseId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
    private void chatClick()
    {
        binding.mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorFunctionalityActivity.this , ChatActivity.class);
                intent.putExtra("id" , binding.courseId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}
