package com.example.elearningversion2.UI.instructor.quiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.elearningversion2.Const;
import com.example.elearningversion2.R;
import com.example.elearningversion2.UI.instructor.createAttend.CreateAttendActivity;
import com.example.elearningversion2.UI.instructor.createCourse.InstructorFunctionalityActivity;
import com.example.elearningversion2.databinding.ActivityQuestionsBinding;
import com.example.elearningversion2.models.ModelQuiz;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateQuizActivity extends AppCompatActivity {
    private ActivityQuestionsBinding binding;
    private ArrayList<ModelQuiz> listQuiz = new ArrayList<>();
    ArrayList<ModelQuiz> list = new ArrayList<>();
    int rightAnswer = 0 ;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String courseId = getIntent().getStringExtra("id");
        createQuiz();
        checkBoxClick();
        onConfirmClick(courseId);
    }

    public void upload(ArrayList<ModelQuiz> questions, String courseId)
    {
        ref.child(Const.COURSE_QUIZ).child(courseId).push().setValue(questions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CreateQuizActivity.this , "The Quiz is uploaded" , Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onConfirmClick(String courseId)
    {
        binding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload(list , courseId);
            }
        });
    }
    public void createQuiz()
    {
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = binding.questionText.getText().toString().trim();
                String Answer1 = binding.answer1Text.getText().toString().trim();
                String Answer2 = binding.answer2Text.getText().toString().trim();
                String Answer3 = binding.answer3Text.getText().toString().trim();
                String Answer4 = binding.answer4Text.getText().toString().trim();
                if (binding.correctAnswer1.isChecked())
                {
                    rightAnswer = 1 ;
                    list.add(new ModelQuiz(question , Answer1 , Answer2 , Answer3 , Answer4 , rightAnswer));
                    binding.questionText.setText("");
                    binding.answer1Text.setText("");
                    binding.answer2Text.setText("");
                    binding.answer3Text.setText("");
                    binding.answer4Text.setText("");
                    binding.correctAnswer1.setChecked(false);
                }
                else if (binding.correctAnswer2.isChecked())
                {
                    rightAnswer = 2;
                    list.add(new ModelQuiz(question , Answer1 , Answer2 , Answer3 , Answer4 , rightAnswer));
                    binding.questionText.setText("");
                    binding.answer1Text.setText("");
                    binding.answer2Text.setText("");
                    binding.answer3Text.setText("");
                    binding.answer4Text.setText("");
                    binding.correctAnswer2.setChecked(false);
                }
                else if (binding.correctAnswer3.isChecked())
                {
                    rightAnswer = 3;
                    list.add(new ModelQuiz(question , Answer1 , Answer2 , Answer3 , Answer4 , rightAnswer));
                    binding.questionText.setText("");
                    binding.answer1Text.setText("");
                    binding.answer2Text.setText("");
                    binding.answer3Text.setText("");
                    binding.answer4Text.setText("");
                    binding.correctAnswer3.setChecked(false);
                }
                else if (binding.correctAnswer4.isChecked())
                {
                    rightAnswer = 4;
                    list.add(new ModelQuiz(question , Answer1 , Answer2 , Answer3 , Answer4 , rightAnswer));
                    binding.questionText.setText("");
                    binding.answer1Text.setText("");
                    binding.answer2Text.setText("");
                    binding.answer3Text.setText("");
                    binding.answer4Text.setText("");
                    binding.correctAnswer4.setChecked(false);
                }
            }
        });

    }
    public void checkBoxClick()
    {
        binding.correctAnswer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.correctAnswer2.setChecked(!b);
                    binding.correctAnswer3.setChecked(!b);
                    binding.correctAnswer4.setChecked(!b);
                    binding.correctAnswer1.setButtonTintList(ColorStateList.valueOf(getColor(R.color.primaryTextColor)));
                }
            }
        });
        binding.correctAnswer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.correctAnswer1.setChecked(!b);
                    binding.correctAnswer3.setChecked(!b);
                    binding.correctAnswer4.setChecked(!b);
                    binding.correctAnswer2.setButtonTintList(ColorStateList.valueOf(getColor(R.color.primaryTextColor)));
                }
            }
        });
        binding.correctAnswer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.correctAnswer1.setChecked(!b);
                    binding.correctAnswer2.setChecked(!b);
                    binding.correctAnswer4.setChecked(!b);
                    binding.correctAnswer3.setButtonTintList(ColorStateList.valueOf(getColor(R.color.primaryTextColor)));
                }
            }
        });
        binding.correctAnswer4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.correctAnswer2.setChecked(!b);
                    binding.correctAnswer1.setChecked(!b);
                    binding.correctAnswer3.setChecked(!b);
                    binding.correctAnswer4.setButtonTintList(ColorStateList.valueOf(getColor(R.color.primaryTextColor)));
                }
            }
        });
    }
}
