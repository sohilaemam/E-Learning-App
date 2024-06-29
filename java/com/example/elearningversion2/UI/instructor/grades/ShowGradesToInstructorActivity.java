package com.example.elearningversion2.UI.instructor.grades;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningversion2.Adapters.CourseGradesAdapter;
import com.example.elearningversion2.Const;
import com.example.elearningversion2.databinding.ActivityInstructorGradesBinding;
import com.example.elearningversion2.models.ModelMember;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowGradesToInstructorActivity extends AppCompatActivity {
    ActivityInstructorGradesBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ArrayList<ModelMember> list = new ArrayList<>();
    CourseGradesAdapter adapter = new CourseGradesAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstructorGradesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getData();
    }
    private void getData()
    {
        String id = getIntent().getStringExtra("id");
        ref.child(Const.COURSES).child(id).child(Const.MEMBERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    list.add(snapshot1.getValue(ModelMember.class));
                }
                adapter.setList(list);
                binding.recyclerGrads.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
