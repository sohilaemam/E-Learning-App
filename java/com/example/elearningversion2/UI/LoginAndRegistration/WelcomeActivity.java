package com.example.elearningversion2.UI.LoginAndRegistration;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.icu.text.UnicodeSetIterator;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.elearningversion2.Const;
import com.example.elearningversion2.R;
import com.example.elearningversion2.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding binding ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkBoxClick();
        nextClick();
    }
    private void checkBoxClick()
    {
        binding.studentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.doctorCheckBox.setChecked(!b);
                    Const.userType = Const.STUDENT_TYPE;
                    binding.studentCheckBox.setButtonTintList(ColorStateList.valueOf(getColor(R.color.primaryTextColor)));
                }
                else
                {
                    Const.userType = "";
                }
            }
        });

        binding.doctorCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.studentCheckBox.setChecked(!b);
                    Const.userType = Const.INSTRUCTOR_TYPE;
                    binding.doctorCheckBox.setButtonTintList(ColorStateList.valueOf(getColor(R.color.primaryTextColor)));
                }
                else {
                    Const.userType = "";
                }
            }
        });
    }

    private void nextClick()
    {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.studentCheckBox.isChecked() || binding.doctorCheckBox.isChecked())
                {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                else
                {
                    Toast.makeText(WelcomeActivity.this , getString(R.string.noUserTypeSelected) , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null ;
    }
}