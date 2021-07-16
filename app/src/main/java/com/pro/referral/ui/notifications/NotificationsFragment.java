package com.pro.referral.ui.notifications;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pro.referral.MainActivity;
import com.pro.referral.R;
import com.pro.referral.choice;
import com.pro.referral.details;
import com.pro.referral.editProDetails;
import com.pro.referral.helpPage;
import com.pro.referral.kyc_verify;
import com.pro.referral.orderHistoryDetails;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    ImageView order_his,edit_pro,kyc;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ImageView green_indicator,red_indicator,help_page_;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        order_his=root.findViewById(R.id.order_his_tory);
        edit_pro=root.findViewById(R.id.edit_profile);
        kyc=root.findViewById(R.id.kyc_image);
        green_indicator=root.findViewById(R.id.green);
        red_indicator=root.findViewById(R.id.red);
        help_page_=root.findViewById(R.id.helppage);


        check();

        order_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(root.getContext(), orderHistoryDetails.class);
                startActivity(intent);

            }
        });

        help_page_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(root.getContext(), helpPage.class);
                startActivity(intent);

            }
        });

        edit_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(root.getContext(), editProDetails.class);
                startActivity(intent);
            }
        });


        kyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(root.getContext(), kyc_verify.class);
                startActivity(intent);

            }
        });


        return root;
    }




    public void check()
    {
        final String id=firebaseAuth.getCurrentUser().getUid();

        DocumentReference docref=firebaseFirestore.collection("kyc").document(id);

        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists())
                {
                    red_indicator.setVisibility(View.INVISIBLE);
                    green_indicator.setVisibility(View.VISIBLE);
                }else
                {

                    red_indicator.setVisibility(View.VISIBLE);
                    green_indicator.setVisibility(View.INVISIBLE);

                }

            }
        });
    }






}