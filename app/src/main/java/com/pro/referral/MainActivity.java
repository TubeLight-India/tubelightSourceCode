package com.pro.referral;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    ImageView start;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        start=findViewById(R.id.start_button);
        start.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                if (firebaseAuth.getCurrentUser() != null) {
                    check();
                } else {
                    Intent intent = new Intent(MainActivity.this, MainActivityVerify.class);
                    startActivity(intent);
                }

            }
        });

    }

    public void check()
    {
        final String id=firebaseAuth.getCurrentUser().getUid();

        DocumentReference docref=firebaseFirestore.collection("users").document(id);

        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists())
                {
                    Toast.makeText(MainActivity.this, "Vitara hela", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,choice.class);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(MainActivity.this, "Vitara helani", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, details.class);
                    startActivity(intent);
                }

            }
        });
    }

}