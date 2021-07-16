package com.pro.referral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pro.referral.ui.notifications.NotificationsFragment;

import java.util.HashMap;
import java.util.Map;

public class kyc_verify extends AppCompatActivity {

    EditText a_name,a_num,c_a_num,b_name,ifsc_code,a_type,aadhar,pan;
    ImageView verify_kyc_total,back_kyc;
    String uid;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc_verify);

        back_kyc=findViewById(R.id.back_kyc);
        a_name=findViewById(R.id.account_name);
        a_num=findViewById(R.id.account_number);
        c_a_num=findViewById(R.id.confirm_account_number);
        b_name=findViewById(R.id.bank_name);
        ifsc_code=findViewById(R.id.ifsc_code);
        a_type=findViewById(R.id.account_type);
        aadhar=findViewById(R.id.aadhar_num);
        pan=findViewById(R.id.pan_number);
        verify_kyc_total=findViewById(R.id.verify_kyc_t);
        firebaseFirestore=FirebaseFirestore.getInstance();
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        back_kyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(kyc_verify.this, choice.class);
                startActivity(intent);
            }
        });


        verify_kyc_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(a_num.getText().toString().equals(c_a_num.getText().toString())) {

                    Map<String, Object> users = new HashMap<>();
                    users.put("accountName", a_name.getText().toString());
                    users.put("accountNumber", a_num.getText().toString());
                    users.put("bankName", b_name.getText().toString());
                    users.put("ifscCode", ifsc_code.getText().toString());
                    users.put("accountType", a_type.getText().toString());
                    users.put("aadhar", aadhar.getText().toString());
                    users.put("pan", pan.getText().toString());


                    firebaseFirestore.collection("kyc").document(uid).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(kyc_verify.this, "KYC Verified successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(kyc_verify.this, "faild", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }else
                {
                    Toast.makeText(kyc_verify.this, "Account Number Miss Match", Toast.LENGTH_SHORT).show();
                }








            }
        });



    }
}