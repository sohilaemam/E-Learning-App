package com.example.elearningversion2.UI.LoginAndRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.elearningversion2.Const;
import com.example.elearningversion2.R;
import com.example.elearningversion2.UI.instructor.createCourse.InstructorShowCoursesActivity;
import com.example.elearningversion2.UI.student.enrollInCourse.StudentCourseActivity;
import com.example.elearningversion2.databinding.ActivityRegisterBinding;
import com.example.elearningversion2.models.ModelUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    boolean isPasswordVisible = false ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showPassword();
        createAccountClick();
    }
    private void showPassword()
    {
        binding.showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    binding.editTextPassword.setTransformationMethod(null);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                            isPasswordVisible = false;
                        }
                    }, 2000);
                } else {
                    binding.editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }
    private void validate(String name , String email , String password)
    {
        if (email.isEmpty())
        {
            binding.editTextEmail.setError(getString(R.string.Required));
        }
        else if (password.isEmpty())
        {
            binding.editTextPassword.setError(getString(R.string.Required));
        }
        else if (password.isEmpty() && email.isEmpty())
        {
            binding.editTextPassword.setError(getString(R.string.Required));
            binding.editTextEmail.setError(getString(R.string.Required));
        }
        else if (password.length()<6)
        {
            binding.editTextPassword.setError(getString(R.string.passwordLessThanSix));
        }
        else if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty())
        {
            register(email , password , name) ;
        }
    }
    private void createAccountClick()
    {
        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString().trim();
                String email = binding.editTextEmail.getText().toString().trim();
                String password = binding.editTextPassword.getText().toString().trim();
                validate(name , email , password);
                binding.editTextName.setText("");
                binding.editTextEmail.setText("");
                binding.editTextPassword.setText("");
            }
        });
    }
    private void register(String email , String password , String name)
    {
        binding.progress.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                binding.progress.setVisibility(View.GONE);
                if  (Const.userType.equals(Const.STUDENT_TYPE))
                {
                    sendDataToRealtime(email , name , Const.userType);
                    Intent intent = new Intent(RegisterActivity.this , StudentCourseActivity.class) ;
                    startActivity(intent);
                }
                else if (Const.userType.equals(Const.INSTRUCTOR_TYPE))
                {
                    sendDataToRealtime(email , name , Const.userType);
                    startActivity(new Intent(RegisterActivity.this , InstructorShowCoursesActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this , e.getLocalizedMessage().toString() , Toast.LENGTH_LONG).show();
            }
        });
    }
    private void sendDataToRealtime(String email , String name , String type)
    {
        ref.child(Const.USERS).child(auth.getUid()).setValue(new ModelUser(auth.getUid() , name , email , type));
    }
}
