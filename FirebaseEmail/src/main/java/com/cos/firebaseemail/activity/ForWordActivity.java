package com.cos.firebaseemail.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cos.firebaseemail.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForWordActivity extends BasicActivity {
    private static final String TAG = "ForWordActivity";

    private TextView tvQuiz;
    private Button btnIndex0, btnIndex1, btnIndex2;
    private FirebaseFirestore database;
    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forword);

        init();
        initData();
        initLr();
    }

    public void init() {
        tvQuiz = findViewById(R.id.tvQuiz);
        btnIndex0 = findViewById(R.id.btnIndex0);
        btnIndex1 = findViewById(R.id.btnIndex1);
        btnIndex2 = findViewById(R.id.btnIndex2);
    }

    public void initData() {
        final RelativeLayout layoutLoader = findViewById(R.id.layoutLoader);
        layoutLoader.setVisibility(View.VISIBLE);
        Log.d(TAG, "initData: 실행");
        database = FirebaseFirestore.getInstance();

//        database.collection("forword")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//
//
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//
//                    }
//                });

        DocumentReference docRef = database.collection("forword").document("forword11");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        layoutLoader.setVisibility(View.GONE);
                        String[] s1 = document.get("text").toString().split(",");
                        tvQuiz.setText((String)document.get("quiz"));
                        s1[0] = s1[0].replace("[","").trim();
                        s1[1] = s1[1].trim();
                        s1[2] = s1[2].replace("]","").trim();
                        btnIndex0.setText(s1[0]);
                        btnIndex1.setText(s1[1]);
                        btnIndex2.setText(s1[2]);
                        answer = document.get("answer").toString();

                        Log.d(TAG, "onComplete: "+s1[0]);
                        Log.d(TAG, "onComplete: "+s1[1]);
                        Log.d(TAG, "onComplete: "+s1[2]);
                        Log.d(TAG, "answer: "+answer);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    public void initLr(){
        btnIndex0.setOnClickListener(v -> {
            Log.d(TAG, "initData: "+btnIndex0.getText());
            if(btnIndex0.getText().equals(answer)) {
                Log.d(TAG, "initData: 정답");
                btnIndex0.setBackgroundColor(Color.GREEN);
            }
        });
    }
}
