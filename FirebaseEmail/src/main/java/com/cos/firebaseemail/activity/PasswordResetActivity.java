package com.cos.firebaseemail.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cos.firebaseemail.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends BasicActivity {

    private static final String TAG = "PasswordReset";
    private EditText etEmail;
    private Button btnSend;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        init();
        initLr();
    }

    public void init() {
        btnSend = findViewById(R.id.btnSend);
        etEmail = findViewById(R.id.etEmail);

        mAuth = FirebaseAuth.getInstance();
    }

    private void initLr() {
        btnSend.setOnClickListener(v -> {
            Log.d(TAG, "initData: 클릭됨");
            Send();
        });
    }

    private void Send() {
        String email = etEmail.getText().toString();

        if(email.length() > 0 ) {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast("이메일을 보냈습니다.");
                    }
                }
            });
        }else {
            Toast("이메일을 입력해주세요.");
        }
    }

    // Toast 띄우기
    private void Toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
