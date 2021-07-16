package com.pro.referral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pro.referral.ui.notifications.NotificationsFragment;

import java.util.HashMap;
import java.util.Map;

public class editProDetails extends AppCompatActivity {

    EditText first_name,last_name,age,num,email,add,pin,state,gender,district,nominy,relationship;
    ImageView update,backedit;
    TextView display_name;
    FirebaseFirestore firebaseFirestore,firebaseFirestore2;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pro_details);

        backedit=findViewById(R.id.back_edit);
        firebaseFirestore2=FirebaseFirestore.getInstance();
        display_name=findViewById(R.id.disp_name);
        firebaseFirestore=FirebaseFirestore.getInstance();
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        first_name=findViewById(R.id.f_name);
        last_name=findViewById(R.id.l_name);
        age=findViewById(R.id.u_age);
        num=findViewById(R.id.u_num);
        email=findViewById(R.id.u_email);
        add=findViewById(R.id.u_address);
        pin=findViewById(R.id.u_pincode);
        state=findViewById(R.id.u_state);
        gender=findViewById(R.id.u_gender);
        district=findViewById(R.id.u_district);
        nominy=findViewById(R.id.u_nomini);
        relationship=findViewById(R.id.u_relation);
        update=findViewById(R.id.update_image);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Map<String, Object> group1 = new HashMap<>();
                if(!(first_name.getText().toString().equals("") || last_name.getText().toString().equals("")))
                group1.put("name", first_name.getText().toString()+" "+last_name.getText().toString());
                if(!age.getText().toString().equals(""))
                group1.put("age",age.getText().toString());
                if(!num.getText().toString().equals(""))
                group1.put("phone",num.getText().toString());
                if(!email.getText().toString().equals(""))
                group1.put("email",email.getText().toString());
                if(!add.getText().toString().equals(""))
                group1.put("address",add.getText().toString());
                if(!pin.getText().toString().equals(""))
                group1.put("pincode",pin.getText().toString());
                if(!state.getText().toString().equals(""))
                group1.put("state",state.getText().toString());
                if(!gender.getText().toString().equals(""))
                group1.put("gender",gender.getText().toString());
                if(!district.getText().toString().equals(""))
                group1.put("district",district.getText().toString());
                if(!nominy.getText().toString().equals(""))
                group1.put("nominy",nominy.getText().toString());
                if(!relationship.getText().toString().equals(""))
                group1.put("relation",relationship.getText().toString());



                firebaseFirestore
                        .collection("users")
                        .document(uid)
                        .update(group1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(editProDetails.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editProDetails.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });


        firebaseFirestore2.collection("users").whereEqualTo(FieldPath.documentId(),uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult())
                    {

                        String own_name=documentSnapshot.get("name").toString();
                        display_name.setText(own_name);

                    }
                }

            }
        });



        backedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(editProDetails.this, choice.class);
                startActivity(intent);
            }
        });


    }
}