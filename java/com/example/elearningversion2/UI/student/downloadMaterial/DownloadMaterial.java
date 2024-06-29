package com.example.elearningversion2.UI.student.downloadMaterial;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningversion2.Adapters.StudentMaterialAdapter;
import com.example.elearningversion2.Const;
import com.example.elearningversion2.databinding.ActivityDwnloadMaterialBinding;
import com.example.elearningversion2.models.ModelMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DownloadMaterial extends AppCompatActivity {

    ArrayList<ModelMaterial> list = new ArrayList<>();
    StudentMaterialAdapter adapter = new StudentMaterialAdapter();
    ActivityDwnloadMaterialBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDwnloadMaterialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getFileData();
        onMaterialClick();
    }
    private void getFileData()
    {
        String courseId = getIntent().getStringExtra("courseId");
        ref.child(Const.COURSE_MATERIAL).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list.add((snapshot1.getValue(ModelMaterial.class)));
                }
                adapter.setList(list);
                binding.recyclerMaterial.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public void onMaterialClick() {
        adapter.setOnItemClick(new StudentMaterialAdapter.OnItemClick() {
            @Override
            public void onClick(ModelMaterial m) {
                String link = m.getFileLink();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
            }
        });
    }
}
