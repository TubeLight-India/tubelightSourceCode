package com.pro.referral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pro.referral.ui.home.HomeFragment;
import com.pro.referral.ui.notifications.NotificationsFragment;

import java.util.ArrayList;

public class orderHistoryDetails extends AppCompatActivity {

    RecyclerView recyclerView_his;
    RecyclerView.LayoutManager HistorylayoutManager;
    public ArrayList<CategoryProducts> categoryProductsArrayList;
    FirebaseFirestore firebaseFirestore,firebaseFirestore2;
    ImageView back_his;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore2=FirebaseFirestore.getInstance();
        back_his=findViewById(R.id.back_histo);

        categoryProductsArrayList=new ArrayList<>();

        recyclerView_his=findViewById(R.id.recyclerview_history);
        HistorylayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        recyclerView_his.setLayoutManager(HistorylayoutManager);
        CategoryAdapter categoryAdapter=new CategoryAdapter(categoryProductsArrayList,this);
        recyclerView_his.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();





        firebaseFirestore.collection("groups").whereEqualTo(FieldPath.documentId(), HomeFragment.gvlobal_id.toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult())
                    {
                        String pdfName=documentSnapshot.getData().toString().replace(",","");
                        pdfName=pdfName.replace("{","");
                        pdfName=pdfName.replace("}","");
                        pdfName=pdfName.replace("="," ");
                        String[]tokens = pdfName.split(" ");

                        for(int i=1;i<tokens.length;i+=2)
                        {

                            firebaseFirestore2.collection("users").whereEqualTo(FieldPath.documentId(),tokens[i]).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if(task.isSuccessful())
                                    {
                                        for(QueryDocumentSnapshot documentSnapshot :task.getResult())
                                        {

                                            categoryProductsArrayList.add(new CategoryProducts(documentSnapshot.get("name").toString(),documentSnapshot.get("activated").toString(),documentSnapshot.get("ownid").toString(),"11/07/2021"));
                                            categoryAdapter.notifyDataSetChanged();
                                            //   Log.e("xfcgvhbjn",highlightProductArrayList.get(a).getName().toString());
                                        }
                                    }

                                }
                            });

                        }

                    }
                }else
                {
                    Log.e("failed ","fghj");
                }

            }
        });




        back_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(orderHistoryDetails.this, choice.class);
                startActivity(intent);
            }
        });





    }
}