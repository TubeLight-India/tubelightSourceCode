package com.pro.referral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.pro.referral.ui.home.HomeFragment;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class rozarPay extends AppCompatActivity  implements PaymentResultListener {

    ImageView pay;
    TextView payText,name_che,email_che,mob_che,price_text_2;
    FirebaseFirestore firebaseFirestore,firebaseFirestore2;
    String uid,mob,email,nama;
    String p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rozar_pay);

        Checkout.preload(getApplicationContext());
        pay=findViewById(R.id.now_purchase);
        payText=findViewById(R.id.price_check_paid);
        firebaseFirestore2=FirebaseFirestore.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        name_che=findViewById(R.id.name_dis_che);
        mob_che=findViewById(R.id.phone_check);
        email_che=findViewById(R.id.email_check);
        price_text_2=findViewById(R.id.price_check2);

        payText.setVisibility(View.INVISIBLE);

        p= HomeFragment.curr_price;

        firebaseFirestore2.collection("users").whereEqualTo(FieldPath.documentId(),uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult())
                    {
                        nama=documentSnapshot.get("name").toString();
                        email=documentSnapshot.get("email").toString();
                        mob=documentSnapshot.get("phone").toString();
                        name_che.setText(nama);
                        mob_che.setText(mob);
                        email_che.setText(email);

                    }
                }

            }
        });

        price_text_2.setText(""+(Integer.parseInt(p)/100)+" Rupees/Piece");



        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPayment();
            }
        });

    }



    public void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_7rQJ7PP3BjMdeC");

        checkout.setImage(R.drawable.pic);


        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();

            options.put("name", "TubeLight INDIA");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
           // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", p);//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact",mob);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

    }


    @Override
    public void onPaymentSuccess(String s) {

       payText.setVisibility(View.VISIBLE);


        Map<String, Object> group1 = new HashMap<>();
        group1.put("activated",""+(Integer.parseInt(p)/100));


        firebaseFirestore
                .collection("users")
                .document(uid)
                .update(group1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(rozarPay.this, "Price Updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(rozarPay.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }

    @Override
    public void onPaymentError(int i, String s) {

        payText.setText("Payment Failed, Try Again");

    }
}