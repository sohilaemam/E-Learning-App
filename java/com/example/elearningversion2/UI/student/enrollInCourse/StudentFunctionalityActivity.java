package com.example.elearningversion2.UI.student.enrollInCourse;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningversion2.UI.instructor.chat.ChatActivity;
import com.example.elearningversion2.UI.student.attend.AttendLectureActivity;
import com.example.elearningversion2.UI.student.downloadMaterial.DownloadMaterial;
import com.example.elearningversion2.UI.student.grades.StudentGradesActivity;
import com.example.elearningversion2.UI.student.solveQuiz.SolveQuizActivity;
import com.example.elearningversion2.databinding.ActivityInstructorFunctionalityBinding;
import com.example.elearningversion2.databinding.ActivityStudentFunctionalityBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentFunctionalityActivity extends AppCompatActivity {
    ActivityStudentFunctionalityBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentFunctionalityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getCourseName();
        gradesCardClick();
        materialCardClick();
        solveQuizCardClick();
        chatCardClick();
        attendCardClick();
    }
    private void getCourseName()
    {
        String courseName = getIntent().getStringExtra("courseName");
        binding.courseWord.setText(courseName);
    }

    private void attendCardClick()
    {
        binding.cardViewAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentFunctionalityActivity.this , AttendLectureActivity.class);
                String courseId = getIntent().getStringExtra("courseId");
                intent.putExtra("courseId" , courseId);
                startActivity(intent);
            }
        });
    }
    private void gradesCardClick()
    {
        binding.cardViewGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentFunctionalityActivity.this , StudentGradesActivity.class);
                String courseId = getIntent().getStringExtra("courseId");
                intent.putExtra("courseId" , courseId);
                startActivity(intent);
            }
        });
    }
    private void materialCardClick() {
        binding.cardViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentFunctionalityActivity.this, DownloadMaterial.class);
                String courseId = getIntent().getStringExtra("courseId");
                intent.putExtra("courseId", courseId);
                startActivity(intent);
            }
        });
    }
    private void solveQuizCardClick()
    {
        binding.cardViewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentFunctionalityActivity.this , SolveQuizActivity.class);
                String courseId = getIntent().getStringExtra("courseId");
                intent.putExtra("courseId" , courseId);
                startActivity(intent);
            }
        });
    }
    private void chatCardClick()
    {
        binding.cardViewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentFunctionalityActivity.this , ChatActivity.class);
                String courseId = getIntent().getStringExtra("courseId");
                intent.putExtra("id" , courseId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null ;
    }
}
