package com.pro.referral.ui.home;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pro.referral.CategoryAdapter_24_7;
import com.pro.referral.CategoryProduct_24_7;
import com.pro.referral.R;
import com.pro.referral.common;
import com.pro.referral.rozarPay;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static String curr_price;
    ImageView purchase;
    public static ArrayList<CategoryProduct_24_7> categoryProductsArrayList_24_7;
    public RecyclerView common_recyclerview_24_7;
    public RecyclerView.LayoutManager common_layout_maneger20;
    public static String gvlobal_id;
    public static int entered;
    TextView own_id,own_id_sha,ext_plan;
    public static String id,namuna;
    FirebaseFirestore firebaseFirestore,firebaseFirestore2;
    ImageView re;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        curr_price="99900";

        own_id=root.findViewById(R.id.own_ids);
        own_id_sha=root.findViewById(R.id.own_id_s);
        ext_plan=root.findViewById(R.id.exis_plan);
        namuna="";
        entered=-1;
        categoryProductsArrayList_24_7=new ArrayList<>();
        firebaseFirestore2=FirebaseFirestore.getInstance();
        re=root.findViewById(R.id.refer_wats);
        purchase=root.findViewById(R.id.purchase_now);



        common_recyclerview_24_7=root.findViewById(R.id.common_recyclerview_24_7);
        common_layout_maneger20=new LinearLayoutManager(container.getContext(), RecyclerView.HORIZONTAL,false);
        common_recyclerview_24_7.setLayoutManager(common_layout_maneger20);
        final CategoryAdapter_24_7 categoryAdapter_24_7=new CategoryAdapter_24_7(categoryProductsArrayList_24_7,container.getContext());
        common_recyclerview_24_7.setAdapter(categoryAdapter_24_7);
        categoryAdapter_24_7.notifyDataSetChanged();
        firebaseFirestore2.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot queryDocumentSnapshot:task.getResult())
                    {
                        String url1=queryDocumentSnapshot.get("url1").toString();
                        String url2=queryDocumentSnapshot.get("url2").toString();
                        String name=queryDocumentSnapshot.get("name").toString();
                        String desc_small=queryDocumentSnapshot.get("small").toString();
                        String desc_big=queryDocumentSnapshot.get("big").toString();
                        String price=queryDocumentSnapshot.get("price").toString();
                        categoryProductsArrayList_24_7.add(new CategoryProduct_24_7(url1,name,desc_small,url2,desc_big,price));
                        categoryAdapter_24_7.notifyDataSetChanged();

                    }
                }

            }
        });

        categoryAdapter_24_7.setOnItemClickedLisner(new CategoryAdapter_24_7.OnItemClickLisner() {
            @Override
            public void onItemClick(int position) {
                entered=position;
                Intent intent=new Intent(root.getContext(), common.class);
                startActivity(intent);
            }
        });





        id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseFirestore= FirebaseFirestore.getInstance();



        firebaseFirestore.collection("users").whereEqualTo(FieldPath.documentId(),id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult())
                    {

                        String own_Id=documentSnapshot.get("ownid").toString();
                        String activated_Plan=documentSnapshot.get("activated").toString();
                        namuna=documentSnapshot.get("name").toString();
                        own_id.setText(own_Id);
                        gvlobal_id=own_Id;
                        own_id_sha.setText(own_Id);

                        if(activated_Plan.equals("0")) {
                            ext_plan.setText("No Activated Plans");
                        }else
                        {
                            ext_plan.setText(activated_Plan+" Rupees");
                        }

                    }
                }

            }
        });



        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");

                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hey this is "+namuna+" this side. Here is my REFERRAL CODE ("+gvlobal_id+") Please use it in the details page during the first Registration process.");
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(root.getContext(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(root.getContext(), rozarPay.class);
                startActivity(intent);

            }
        });



        return root;
    }
}