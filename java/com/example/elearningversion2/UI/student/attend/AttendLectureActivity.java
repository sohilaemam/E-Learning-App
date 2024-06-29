package com.example.elearningversion2.UI.student.attend;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningversion2.Const;
import com.example.elearningversion2.databinding.ActivityStudentAttendBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AttendLectureActivity extends AppCompatActivity {


    ActivityStudentAttendBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentAttendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activateClick();
    }


    private void activateClick()
    {
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.attendText.getText().toString().trim();
                String courseId = getIntent().getStringExtra("courseId");
                ref.child(Const.ATTEND).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(code) && !code.isEmpty())
                        {
                            if (!snapshot.child(code).hasChild(auth.getUid()) && !code.isEmpty()) {
                                ref.child(Const.ATTEND).child(courseId).child(code).child(auth.getUid()).setValue(auth.getUid());
                                modifyAttendGrade(courseId);
                                binding.attendText.setText("");
                            }
                            else
                            {
                                Toast.makeText(AttendLectureActivity.this, "Already attended once before", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(AttendLectureActivity.this, "Wrong Code", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void modifyAttendGrade(String courseId)
    {
        ref.child(Const.COURSES).child(courseId).child(Const.MEMBERS).child(auth.getUid()).child("attendanceGrade").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int grade = snapshot.getValue(int.class);
                ref.child(Const.COURSES).child(courseId).child(Const.MEMBERS).child(auth.getUid()).child("attendanceGrade").setValue(grade+1);
                ref.child(Const.MEMBERS).child(auth.getUid()).child("attendanceGrade").setValue(grade+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
