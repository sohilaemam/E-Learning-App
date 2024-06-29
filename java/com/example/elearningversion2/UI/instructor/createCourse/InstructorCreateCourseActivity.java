package com.example.elearningversion2.UI.instructor.createCourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.elearningversion2.Const;
import com.example.elearningversion2.databinding.ActivityInstructorCreateCourseBinding;
import com.example.elearningversion2.models.ModelCourse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InstructorCreateCourseActivity extends AppCompatActivity {
    private ActivityInstructorCreateCourseBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInstructorCreateCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });
    }
    public void Validate()
    {
        String courseName=binding.coursenameedit.getText().toString();
        String quizGrades=binding.quizgradeedit.getText().toString();
        String projectGrades=binding.projectgradeedit.getText().toString();
        String attendGrades=binding.attandgradeedit.getText().toString();
        String courseGrades=binding.coursegradeedit.getText().toString();
        double quizGrade=Double.parseDouble(quizGrades);
        double projectGrade=Double.parseDouble(projectGrades);
        double attendGrade=Double.parseDouble(attendGrades);
        double courseGrade=Double.parseDouble(courseGrades);
        if(courseName.equals(" ") )
        {
            binding.coursenameedit.setError("Name is empty!");
        }
        else if(quizGrades.equals(" ")||quizGrade>60)
        {
            binding.quizgradeedit.setError("quiz grade is empty!");
        }
        else if(projectGrades.equals(" ")||projectGrade>60)
        {
            binding.projectgradeedit.setError("project grade is empty!");
        }
        else if(attendGrades.equals(" ")||attendGrade>60)
        {
            binding.attandgradeedit.setError("attend grade is empty!");
        }
        else if(courseGrades.equals(" ")||courseGrade>100)
        {
            binding.coursegradeedit.setError("this is empty");
        }
        else {
            SendDataToRealTime( courseName, quizGrades, projectGrades, attendGrades,courseGrades);

        }

    }
    public void SendDataToRealTime(String courseName,String quizGrade,String projectGrade,String attendGrade,String courseGrade)
    {
        String courseId=ref.push().getKey();
        ref.child(Const.COURSES).child(courseId)
                .setValue(new ModelCourse(courseId , courseName , auth.getUid() , projectGrade , quizGrade , attendGrade))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(InstructorCreateCourseActivity.this, InstructorShowCoursesActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}