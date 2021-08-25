package com.cos.firebaseemail.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cos.firebaseemail.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BasicActivity {
    private static final String TAG = "LoginActivity";
    private Button btnLogin,btnGoPasswordReset;
    private EditText etEmail,etPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        initLr();
    }

    public void init() {
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnGoPasswordReset = findViewById(R.id.btnGoPasswordReset);

        mAuth = FirebaseAuth.getInstance();
    }

    private void initLr() {
        btnLogin.setOnClickListener(v -> {
            Log.d(TAG, "initData: 클릭됨");
            Login();
        });
        btnGoPasswordReset.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LoginActivity.this,
                    PasswordResetActivity.class
            );
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void Login() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(email.length() > 0 && password.length() >0 ) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast("성공적으로 로그인이 되었습니다.");
                        myStartActivity(MainActivity.class);
                    } else {
                        Toast("로그인 실패");
                    }
                }
            });

        }else {
            Toast("이메일 또는 비밀번호를 입력해주세요.");
        }
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // Toast 띄우기
    private void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
