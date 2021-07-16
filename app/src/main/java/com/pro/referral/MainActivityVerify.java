package com.pro.referral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class MainActivityVerify extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    PhoneAuthProvider.ForceResendingToken token;
    private String verificationId;
    private static final String KEY_VERIFICATION_ID = "key_verification_id";
    public static String str;

    ProgressBar otp_progress,verify_progress_bar;

    ImageView sed_otp_ima;
    EditText phnumber,otp_v;
    TextView phnumber_view,otp_v_view;
    ImageView verify_otp;
    TextView otp_text,otp_verify_text;

    String id="";
    String num="";



    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_verify);

        if (id == null && savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        verify_progress_bar=findViewById(R.id.verify_progress_bar);
        otp_verify_text=findViewById(R.id.verify_text);
        otp_text=findViewById(R.id.send_otp_text);
        otp_progress=findViewById(R.id.send_otp_progress);
        sed_otp_ima=findViewById(R.id.send_otp_image);
        otp_v=findViewById(R.id.otp);
        verify_otp=findViewById(R.id.verify_otp_image);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        phnumber=(EditText)findViewById(R.id.number);
        otp_progress.setVisibility(View.INVISIBLE);
        if(firebaseAuth.getCurrentUser()!=null)
        {
            check();
        }

        phnumber_view=findViewById(R.id.number_view);
        otp_v_view=findViewById(R.id.otp_view);


        phnumber_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phnumber_view.setVisibility(View.INVISIBLE);
                phnumber.setVisibility(View.VISIBLE);
            }
        });

        otp_v_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp_v_view.setVisibility(View.INVISIBLE);
                otp_v.setVisibility(View.VISIBLE);

            }
        });


        sed_otp_ima.setEnabled(true);
        sed_otp_ima.setVisibility(View.VISIBLE);
        otp_text.setVisibility(View.VISIBLE);

        verify_otp.setVisibility(View.VISIBLE);
        otp_verify_text.setVisibility(View.VISIBLE);
        verify_progress_bar.setVisibility(View.INVISIBLE);


        sed_otp_ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sed_otp_ima.setEnabled(false);
                otp_text.setVisibility(View.INVISIBLE);
                sed_otp_ima.setVisibility(View.INVISIBLE);
                otp_progress.setVisibility(View.VISIBLE);
                num="+91"+phnumber.getText().toString();
                request(num);
            }
        });

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verify_otp.setVisibility(View.INVISIBLE);
                otp_verify_text.setVisibility(View.INVISIBLE);
                verify_progress_bar.setVisibility(View.VISIBLE);

                try {
                    PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(id,otp_v.getText().toString());
                    verify(phoneAuthCredential);
                }catch (Exception e)
                {
                    Toast.makeText(MainActivityVerify.this, "cant send the message "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_VERIFICATION_ID,id);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        id = savedInstanceState.getString(KEY_VERIFICATION_ID);
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
                    Intent intent=new Intent(MainActivityVerify.this,choice.class);
                    startActivity(intent);
                }else
                {
                                Intent intent = new Intent(MainActivityVerify.this, details.class);
                                startActivity(intent);
                }

            }
        });
    }



    public void request(String phone)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                id=s;
                token=forceResendingToken;


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(MainActivityVerify.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void verify(PhoneAuthCredential phoneAuthCredential) {

        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    check();
                }else
                {
                    Toast.makeText(MainActivityVerify.this, "faild", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



}
