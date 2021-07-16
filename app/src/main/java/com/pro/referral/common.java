package com.pro.referral;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pro.referral.ui.home.HomeFragment;

import java.net.URL;

public class common extends AppCompatActivity {
    ImageView imagesec;
    TextView namu,pricu,descu;

    ConstraintLayout purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        imagesec=findViewById(R.id.image_sec);
        namu=findViewById(R.id.namu);
        pricu=findViewById(R.id.pricu);
        descu=findViewById(R.id.descu);
        purchase=findViewById(R.id.pur_click_on);
       // Toast.makeText(this,""+ HomeFragment.entered, Toast.LENGTH_SHORT).show();

        namu.setText(HomeFragment.categoryProductsArrayList_24_7.get(HomeFragment.entered).getName());
        pricu.setText(HomeFragment.categoryProductsArrayList_24_7.get(HomeFragment.entered).getPrice()+" Rupees/ Piece");
        descu.setText(HomeFragment.categoryProductsArrayList_24_7.get(HomeFragment.entered).getBig_des());

        HomeFragment.curr_price=HomeFragment.categoryProductsArrayList_24_7.get(HomeFragment.entered).getPrice()+"00";

        try {

            URL imag_url=new URL(HomeFragment.categoryProductsArrayList_24_7.get(HomeFragment.entered).image_url2);

            RequestOptions options=new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);

            Glide.with(this).load(imag_url).apply(options).into(imagesec);


        }catch (Exception f)
        {
            Toast.makeText(this, ""+HomeFragment.categoryProductsArrayList_24_7.size(), Toast.LENGTH_SHORT).show();
        }

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(common.this,rozarPay.class);
                startActivity(intent);

            }
        });




    }
}