package com.pro.referral.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pro.referral.Highlight_adapter;
import com.pro.referral.Highlight_product;
import com.pro.referral.R;
import com.pro.referral.ui.home.HomeFragment;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    FirebaseFirestore firebaseFirestore,firebaseFirestore2;
    TextView own_name,own_ids,total_mem;
    ImageView t_earn,clo;
    String own_Id;
    ConstraintLayout withdraw;
    RecyclerView.LayoutManager HighlightlayoutManager;
    public ArrayList<Highlight_product> highlightProductArrayList;
    public RecyclerView highlightProductRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore2=FirebaseFirestore.getInstance();
        own_name=root.findViewById(R.id.name_member);
        own_ids=root.findViewById(R.id.id_member);
        total_mem=root.findViewById(R.id.total_member);
        withdraw=root.findViewById(R.id.t_earn_with);
        t_earn=root.findViewById(R.id.total_earnings);
        clo=root.findViewById(R.id.close);

        withdraw.setVisibility(View.INVISIBLE);









        t_earn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                withdraw.setVisibility(View.VISIBLE);

            }
        });

        clo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdraw.setVisibility(View.INVISIBLE);

            }
        });

        firebaseFirestore.collection("users").whereEqualTo(FieldPath.documentId(), HomeFragment.id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult())
                    {

                        own_Id=documentSnapshot.get("ownid").toString();
                        String name_o=documentSnapshot.get("name").toString();

                        own_ids.setText(own_Id);
                        own_name.setText(name_o);


                    }
                }

            }
        });




        highlightProductArrayList=new ArrayList<>();

        highlightProductRecyclerView=root.findViewById(R.id.highlighted_names);
        HighlightlayoutManager=new LinearLayoutManager(root.getContext(), RecyclerView.VERTICAL,false);
        highlightProductRecyclerView.setLayoutManager(HighlightlayoutManager);
        Highlight_adapter highlightAdapter=new Highlight_adapter(highlightProductArrayList,root.getContext());
        highlightProductRecyclerView.setAdapter(highlightAdapter);
        highlightAdapter.notifyDataSetChanged();




        firebaseFirestore.collection("groups").whereEqualTo(FieldPath.documentId(),HomeFragment.gvlobal_id.toString()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                                            highlightProductArrayList.add(new Highlight_product( documentSnapshot.get("name").toString(),documentSnapshot.get("activated").toString()));
                                            highlightAdapter.notifyDataSetChanged();
                                            total_mem.setText(""+highlightProductArrayList.size()+" Members");
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



        return root;
    }
}