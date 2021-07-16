package com.pro.referral;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class details extends AppCompatActivity {
    EditText name,email,phno,degig,address,blood;
    FirebaseFirestore firebaseFirestore;
    String uid="";
    TextView t_name,t_age,t_ph,t_email,t_ref,t_add;
    ImageView lets,image_name,image_byke,image_address,image_mobile,image_mail,image_blood;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    String ref_id_join;
    String ref_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        t_name=findViewById(R.id.name_textView);
        t_age=findViewById(R.id.age_textView1);
        t_ph=findViewById(R.id.num_textView6);
        t_email=findViewById(R.id.gmail_textView5);
        t_ref=findViewById(R.id.refferal_textView7);
        t_add=findViewById(R.id.add_textView4);


        image_name=findViewById(R.id.imageView_name);
        image_byke=findViewById(R.id.imageView_byke);
        image_address=findViewById(R.id.imageView_address);
        image_mobile=findViewById(R.id.imageView_number);
        image_mail=findViewById(R.id.imageView_mail);
        image_blood=findViewById(R.id.imageView_blood);

        address=(EditText) findViewById(R.id.address);
        blood=(EditText) findViewById(R.id.blood_group);
        blood.setVisibility(View.INVISIBLE);

        lets=findViewById(R.id.go);
        progressBar=findViewById(R.id.progressBar3);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        phno=(EditText)findViewById(R.id.number);
        degig=(EditText)findViewById(R.id.designation);
        firebaseAuth=FirebaseAuth.getInstance();
        uid=firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);


        int ran = (int) (Math.random() * 1000000);
        ref_id = ran + "";



        image_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_name.setVisibility(View.INVISIBLE);
                name.setVisibility(View.VISIBLE);
            }
        });

        image_byke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_age.setVisibility(View.INVISIBLE);
                degig.setVisibility(View.VISIBLE);

            }
        });

        image_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_add.setVisibility(View.INVISIBLE);
                address.setVisibility(View.VISIBLE);
            }
        });

        image_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_ph.setVisibility(View.INVISIBLE);
                phno.setVisibility(View.VISIBLE);
            }
        });

        image_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_email.setVisibility(View.INVISIBLE);
                email.setVisibility(View.VISIBLE);
            }
        });

        image_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_ref.setVisibility(View.INVISIBLE);
                blood.setVisibility(View.VISIBLE);
            }
        });

        lets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                lets.setVisibility(View.INVISIBLE);

                ref_id_join=blood.getText().toString();



                Map<String, Object> users = new HashMap<>();
                users.put("name", name.getText().toString());
                users.put("email", email.getText().toString());
                users.put("phone", phno.getText().toString());
                users.put("age", degig.getText().toString());
                users.put("address", address.getText().toString());
                users.put("ref",ref_id_join);
                users.put("ownid",ref_id.toString());
                users.put("activated", "0");
                users.put("pincode","");
                users.put("state","");
                users.put("gender","");
                users.put("district","");
                users.put("nominy","");
                users.put("relation","");

                firebaseFirestore.collection("users").document(uid).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(details.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                            GroupCreation();
                            if(!ref_id_join.equals(""))
                            join_group();
                            Intent intent=new Intent(details.this,choice.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(details.this, "faild", Toast.LENGTH_SHORT).show();
                        }

                    }
                });












            }
        });

    }



    public void GroupCreation()
    {
        Map<String, Object> group = new HashMap<>();
        group.put(uid, uid);

        firebaseFirestore.collection("groups").document(ref_id).set(group).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(details.this, "Gang Created", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(details.this, "Gang creation faild", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    public void join_group()
    {

        Map<String, Object> group = new HashMap<>();
        group.put(uid, uid);


        firebaseFirestore.collection("groups").whereEqualTo(FieldPath.documentId(),ref_id_join).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //WriteBatch batch = db.batch();

                            for (DocumentSnapshot document : task.getResult()) {
                                DocumentReference docRef = document.getReference();

                                docRef.set(group, SetOptions.merge());

                                       //merzing 2
                                       /* Map<String, Object> new_map = new HashMap<>();
                                        new_map.put(key, value);
                                        batch.update(docRef, new_map);*/
                                Toast.makeText(details.this, "Gang Joined", Toast.LENGTH_SHORT).show();

                            }
                            //batch.commit();
                        } else {
                            // ... "Error adding field -> " + task.getException()
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // ... "Failure getting documents -> " + e
                    }
                });

    }





}
