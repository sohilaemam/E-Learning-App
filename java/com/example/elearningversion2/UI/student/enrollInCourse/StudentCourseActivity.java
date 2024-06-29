package com.example.elearningversion2.UI.student.enrollInCourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.elearningversion2.Adapters.AdapterRecyclerMember;
import com.example.elearningversion2.Const;
import com.example.elearningversion2.UI.LoginAndRegistration.LoginActivity;
import com.example.elearningversion2.databinding.ActivityStudentCourseBinding;
import com.example.elearningversion2.models.ModelMember;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentCourseActivity extends AppCompatActivity {

    ActivityStudentCourseBinding binding;
    ArrayList<ModelMember> list = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    AdapterRecyclerMember adapterRecyclerMember=new AdapterRecyclerMember();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addMember();
        showCourses();
        logoutClick();
        courseClick();
    }

    private void addMember() {
        String courseName = "courseName";
        binding.btnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference valueRef = FirebaseDatabase.getInstance().getReference().child(Const.COURSES).child(binding.editEmail.getText().toString()).child(courseName);
                valueRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String value = dataSnapshot.getValue(String.class);
                            String courseId = binding.editEmail.getText().toString().trim();
                            validate(courseId);
                            ref.child(Const.COURSES).child(courseId).child(Const.MEMBERS).child(auth.getUid()).setValue(new ModelMember(courseId, value, auth.getUid(), 0, 0));
                            ref.child(Const.MEMBERS).child(auth.getUid()).setValue(new ModelMember(courseId, value, auth.getUid(), 0, 0));
                            binding.editEmail.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
    private void validate(String id)
    {
        if (id.isEmpty())
        {
            binding.editEmail.setError("Required");
        }
    }
    private void showCourses()
    {
        ref.child(Const.MEMBERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    ModelMember modelMember = snapshot1.getValue(ModelMember.class);
                    if (modelMember.getStudentId().equals(auth.getUid())) {
                        list.add(snapshot1.getValue(ModelMember.class));
                    }
                }
                adapterRecyclerMember.setList(list);
                binding.recyclerViewHomeStudent.setAdapter(adapterRecyclerMember);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void courseClick() {
        adapterRecyclerMember.setOnItemClick(new AdapterRecyclerMember.OnItemClick() {
            @Override
            public void onClick(ModelMember m) {
                Intent intent = new Intent(StudentCourseActivity.this , StudentFunctionalityActivity.class);
                intent.putExtra("courseName" , m.getCourseName());
                intent.putExtra("studentId" , m.getStudentId());
                intent.putExtra("courseId" , m.getCourseId());
                startActivity(intent);
            }
        });
    }
    private void logoutClick()
    {
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(StudentCourseActivity.this , LoginActivity.class));
                finish();
            }
        });
    }
}
