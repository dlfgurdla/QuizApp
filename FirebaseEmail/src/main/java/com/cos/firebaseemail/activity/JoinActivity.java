package com.cos.firebaseemail.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cos.firebaseemail.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JoinActivity extends BasicActivity {
    private static final String TAG = "JoinActivity";
    private Button btnJoin,btnGoLogin;

    private FirebaseAuth mAuth;
    private EditText etEmail,etPassword,etPasswordCheck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        init();
        initLr();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public void init() {
        btnJoin = findViewById(R.id.btnJoin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordCheck = findViewById(R.id.etPasswordCheck);
        btnGoLogin = findViewById(R.id.btnGoLogin);

        mAuth = FirebaseAuth.getInstance();
    }

    private void initLr() {
        btnJoin.setOnClickListener(v -> {
            Log.d(TAG, "initData: 클릭됨");
            join();
        });
        btnGoLogin.setOnClickListener(v -> {
            myStartActivity(LoginActivity.class);
        });
    }

    private void join() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String passwordCheck = etPasswordCheck.getText().toString();

        if(email.length() > 0 && password.length() >0 && passwordCheck.length() >0) {
            final RelativeLayout layoutLoader = findViewById(R.id.layoutLoader);
            layoutLoader.setVisibility(View.VISIBLE);
            if (password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        layoutLoader.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
//                          Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast("성공적으로 회원가입이 되었습니다.");
                            myStartActivity(LoginActivity.class);
                        } else {
                            if (task.getException() != null) {
                                Toast(task.getException().toString());
                            }
                        }
                    }
                });
            } else {
                Toast("비밀번호가 일치하지 않습니다.");
            }
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
