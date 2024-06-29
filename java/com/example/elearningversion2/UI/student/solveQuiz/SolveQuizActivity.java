package com.example.elearningversion2.UI.student.solveQuiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearningversion2.Adapters.StudentQuizAdapter;
import com.example.elearningversion2.Const;
import com.example.elearningversion2.databinding.ActivityAvailableQuizzesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SolveQuizActivity extends AppCompatActivity {
    ActivityAvailableQuizzesBinding binding ;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    StudentQuizAdapter adapter = new StudentQuizAdapter();
    ArrayList<String> list = new ArrayList<String>();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAvailableQuizzesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String courseId = getIntent().getStringExtra("courseId");
        getData(courseId);
        quizClick(courseId);
    }
    private void getData(String courseId)
    {
        ref.child(Const.COURSE_QUIZ).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    list.add(snapshot1.getKey());
                }
                adapter.setList(list);
                binding.recyclerViewStudentQuizes.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void quizClick(String courseId)
    {
        adapter.setOnItemClick(new StudentQuizAdapter.OnItemClick() {
            @Override
            public void onClick(String quizId) {
                ref.child(Const.COURSE_QUIZ_ANSWER).child(courseId).child(quizId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(auth.getUid()))
                        {
                            Toast.makeText(SolveQuizActivity.this, "Already attempted once before", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(SolveQuizActivity.this , QuizActivity.class);
                            String courseId = getIntent().getStringExtra("courseId");
                            intent.putExtra("quizId" , quizId);
                            intent.putExtra("courseId" , courseId);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
