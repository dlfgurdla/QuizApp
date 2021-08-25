package com.cos.firebaseemail.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cos.firebaseemail.Dto.MemberInfo;
import com.cos.firebaseemail.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemberInitActivity extends BasicActivity {
    private static final String TAG = "MemberInitActivity";

    private Button btnCheck;
    private EditText etName, etPhoneNumber, etBirthday, etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init);

        init();
        initLr();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void init() {
        btnCheck = findViewById(R.id.btnCheck);
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etBirthday = findViewById(R.id.etBirthday);
        etAddress = findViewById(R.id.etAddress);

    }
    private void initLr() {
        btnCheck.setOnClickListener(v -> {
            Log.d(TAG, "initData: 클릭됨");
            profileUpdate();
        });

    }

    private void profileUpdate() {
        String name = etName.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String birthDay = etBirthday.getText().toString();
        String address = etAddress.getText().toString();

        if(name.length() >0 && phoneNumber.length() > 9 && birthDay.length() > 5 && address.length() >0) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            MemberInfo memberInfo = new MemberInfo(name, phoneNumber, birthDay, address);

            if(user != null) {
                db.collection("users").document(user.getUid()).set(memberInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast("회원정보 등록을 성공하였습니다.");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast("회원정보 등록에 실패하였습니다.");
                        Log.w(TAG, "Error writing document", e);
                    }
                });
            }
        }else {
            Toast("회원정보를 입력해주세요.");
        }
    }

    // Toast 띄우기
    private void Toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
