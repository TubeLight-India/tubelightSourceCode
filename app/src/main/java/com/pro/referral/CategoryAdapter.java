package com.pro.referral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {


    ArrayList<CategoryProducts> categoryProductsArrayList;
    Context context;
    private CategoryAdapter.OnItemClickLisner mlisner;

    public interface OnItemClickLisner{
        void onItemClick(int position);

    }

    public void setOnItemClickedLisner(CategoryAdapter.OnItemClickLisner listener)
    {
        mlisner=listener;
    }


    public CategoryAdapter( ArrayList<CategoryProducts> categoryProductsArrayList, Context context) {
        this.categoryProductsArrayList = categoryProductsArrayList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView name;
        TextView desc;
        TextView id,date;
        MyViewHolder(View itemview, final CategoryAdapter.OnItemClickLisner lisner)
        {
            super(itemview);

            this.name=(TextView)itemview.findViewById(R.id.textView4);
            this.desc=(TextView)itemview.findViewById(R.id.textView8);
            this.id=(TextView)itemview.findViewById(R.id.ref_id_his);
            this.date=(TextView)itemview.findViewById(R.id.date_join);
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
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.category_products,parent,false);
        CategoryAdapter.MyViewHolder myViewHolder=new CategoryAdapter.MyViewHolder(view,mlisner);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, int position) {


        TextView name=holder.name;
        TextView desc=holder.desc;
        TextView id_his=holder.id;
        TextView date=holder.date;

        name.setText(categoryProductsArrayList.get(position).getName());
        desc.setText(categoryProductsArrayList.get(position).getDesc());
        id_his.setText(categoryProductsArrayList.get(position).getId());
        date.setText(categoryProductsArrayList.get(position).getSong_url());

    }


    @Override
    public int getItemCount() {

        return categoryProductsArrayList.size();
    }


}
