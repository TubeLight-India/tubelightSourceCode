package com.pro.referral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.net.URL;
import java.util.ArrayList;

public class CategoryAdapter_24_7 extends RecyclerView.Adapter<CategoryAdapter_24_7.MyViewHolder> {


    ArrayList<CategoryProduct_24_7> categoryProductsArrayList;
    Context context;
    private CategoryAdapter_24_7.OnItemClickLisner mlisner;

    public interface OnItemClickLisner{
        void onItemClick(int position);

    }

    public void setOnItemClickedLisner(CategoryAdapter_24_7.OnItemClickLisner listener)
    {
        mlisner=listener;
    }


    public CategoryAdapter_24_7( ArrayList<CategoryProduct_24_7> categoryProductsArrayList, Context context) {
        this.categoryProductsArrayList = categoryProductsArrayList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image_url;
        TextView name;
        TextView desc;
        MyViewHolder(View itemview, final CategoryAdapter_24_7.OnItemClickLisner lisner)
        {
            super(itemview);

            this.image_url=(ImageView)itemview.findViewById(R.id.imageView4_24_7);
            this.name=(TextView)itemview.findViewById(R.id.textView4_24_7);
            this.desc=(TextView)itemview.findViewById(R.id.desc);

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(lisner!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            lisner.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public CategoryAdapter_24_7.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.category_products_24_7,parent,false);
        CategoryAdapter_24_7.MyViewHolder myViewHolder=new CategoryAdapter_24_7.MyViewHolder(view,mlisner);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter_24_7.MyViewHolder holder, int position) {

        ImageView img_thum=holder.image_url;
        TextView name=holder.name;
        TextView desc=holder.desc;

        desc.setText(categoryProductsArrayList.get(position).getDesc());
        name.setText(categoryProductsArrayList.get(position).getName());

        try {

            URL imag_url=new URL(categoryProductsArrayList.get(position).image_url);

            RequestOptions options=new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);

            Glide.with(context).load(imag_url).apply(options).into(img_thum);


        }catch (Exception f)
        {
            Toast.makeText(context, ""+categoryProductsArrayList.size(), Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public int getItemCount() {

        return categoryProductsArrayList.size();
    }


}
