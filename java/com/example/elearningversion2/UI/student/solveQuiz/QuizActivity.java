package com.example.elearningversion2.UI.student.solveQuiz;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.elearningversion2.Const;
import com.example.elearningversion2.R;
import com.example.elearningversion2.databinding.ActivityQuizBinding;
import com.example.elearningversion2.models.ModelQuiz;
import com.example.elearningversion2.models.ModelStudentQuizAnswer;
import com.example.elearningversion2.models.ModelUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    ActivityQuizBinding binding ;
    MutableLiveData<List<ModelQuiz>> quizLiveData = new MutableLiveData<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    List<ModelQuiz> list = new ArrayList<>();
    private int rightAnswer = 0 ;
    private int grade = 0 ;
    private int i = 0 ;
    private String courseId , quizId ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String courseId = getIntent().getStringExtra("courseId");
        String quizId = getIntent().getStringExtra("quizId");
        getData(courseId , quizId);
        confirmClick(list);
        observer();
    }

    private void getData(String courseId , String quizId)
    {
        ref.child(Const.COURSE_QUIZ).child(courseId).child(quizId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    list.add(snapshot1.getValue(ModelQuiz.class));
                }
                quizLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void confirmClick(List<ModelQuiz> list)
    {
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightAnswer != 0) {
                    checkRightAnswer(list);
                    changeToFinish();
                } else {
                    Toast.makeText(QuizActivity.this, "Choose Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.answer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    rightAnswer=1 ;
                    binding.answer2.setChecked(false);
                    binding.answer3.setChecked(false);
                    binding.answer4.setChecked(false);
                }
            }
        });
        binding.answer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    rightAnswer=2 ;
                    binding.answer1.setChecked(false);
                    binding.answer3.setChecked(false);
                    binding.answer4.setChecked(false);
                }
            }
        });
        binding.answer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    rightAnswer= 3 ;
                    binding.answer1.setChecked(false);
                    binding.answer2.setChecked(false);
                    binding.answer4.setChecked(false);
                }
            }
        });
        binding.answer4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    rightAnswer= 4 ;
                    binding.answer1.setChecked(false);
                    binding.answer2.setChecked(false);
                    binding.answer3.setChecked(false);
                }
            }
        });
    }

    private void checkRightAnswer(List<ModelQuiz> list) {
        if (list.get(i).getRightAnswer() == rightAnswer) {
            grade++;
        }
        if (i == (list.size()-1))
        {
            finish();
            uploadStudentAnswers();
        }
        else {
            i++;
        }
        resetAnswer();
    }

    private void uploadStudentAnswers()
    {
        courseId = getIntent().getStringExtra("courseId");
        quizId = getIntent().getStringExtra("quizId");
        ref.child(Const.USERS).child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelUser m = snapshot.getValue(ModelUser.class);
                String studentName = m.getUserName();
                String studentEmail = m.getUserEmail();
                ref.child(Const.COURSE_QUIZ_ANSWER).child(courseId).child(quizId).child(auth.getUid()).setValue(new ModelStudentQuizAnswer(auth.getUid() , studentEmail , grade , studentName));
                uploadStudentGrade();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadStudentGrade()
    {
        courseId = getIntent().getStringExtra("courseId");
        ref.child(Const.COURSES).child(courseId).child(Const.MEMBERS).child(auth.getUid()).child("quizGrade").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int oldGrade = snapshot.getValue(Integer.class);
                ref.child(Const.COURSES).child(courseId).child(Const.MEMBERS).child(auth.getUid()).child("quizGrade").setValue(oldGrade + grade);
                ref.child(Const.MEMBERS).child(auth.getUid()).child("quizGrade").setValue(oldGrade + grade);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void resetAnswer ()
    {
        rightAnswer = 0 ;
        binding.answer1.setChecked(false);
        binding.answer2.setChecked(false);
        binding.answer3.setChecked(false);
        binding.answer4.setChecked(false);
    }

    private void changeToFinish() {
        if (i == (list.size() - 1)){
            binding.nextButton.setText("Finish");
        }
        binding.questionText.setText(list.get(i).getQuestion());
        binding.answer1.setText(list.get(i).getAnswer1());
        binding.answer2.setText(list.get(i).getAnswer2());
        binding.answer3.setText(list.get(i).getAnswer3());
        binding.answer4.setText(list.get(i).getAnswer4());

    }
    private void observer (){
        quizLiveData.observe(QuizActivity.this, new Observer<List<ModelQuiz>>() {
            @Override
            public void onChanged(List<ModelQuiz> modelQuizes) {
                list = modelQuizes ;
                changeToFinish();
            }
        });

    }
}
