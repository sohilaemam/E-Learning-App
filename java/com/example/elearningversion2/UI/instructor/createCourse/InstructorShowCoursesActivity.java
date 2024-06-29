package com.example.elearningversion2.UI.instructor.createCourse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.elearningversion2.Adapters.AdapterRecyclerCourses;
import com.example.elearningversion2.Const;
import com.example.elearningversion2.databinding.ActivityInstructorShowCoursesBinding;
import com.example.elearningversion2.models.ModelCourse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InstructorShowCoursesActivity extends AppCompatActivity {
    ActivityInstructorShowCoursesBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    ArrayList<ModelCourse> list = new ArrayList<>();
    AdapterRecyclerCourses adapterRecyclerCourses = new AdapterRecyclerCourses();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstructorShowCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addCourseClick();
        getData();
        onCourseClick();
    }
    private void addCourseClick()
    {
        binding.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorShowCoursesActivity.this, InstructorCreateCourseActivity.class);
                startActivity(intent);
                intent.putExtra("id", auth.getUid());
                startActivity(intent);
            }

        });
    }
    protected void getData() {
        reference.child(Const.COURSES).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            ModelCourse modelCourse = snap.getValue(ModelCourse.class);
                            if (modelCourse.getInstructorId().equals(auth.getUid())) {
                                list.add(snap.getValue(ModelCourse.class));
                            }
                        }
                        adapterRecyclerCourses.setList(list);
                        binding.recyclerInstructorCourse.setAdapter(adapterRecyclerCourses);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    private void onCourseClick()
    {
        adapterRecyclerCourses.setOnItemClick(new AdapterRecyclerCourses.OnItemClick() {
            @Override
            public void onClick(ModelCourse modelCourse) {
                Intent intent = new Intent(InstructorShowCoursesActivity.this, InstructorFunctionalityActivity.class);
                intent.putExtra("courseId", modelCourse.getCourseId());
                intent.putExtra("courseName" , modelCourse.getCourseName());
                startActivity(intent);
            }
        });
    }
}






