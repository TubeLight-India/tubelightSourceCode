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

public class Highlight_adapter extends RecyclerView.Adapter<Highlight_adapter.MyViewHolder> {


    ArrayList<Highlight_product> highlight_productArrayList;
    Context context;
    private Highlight_adapter.OnItemClickLisner mlisner;

    public interface OnItemClickLisner{
        void onItemClick(int position);

    }

    public void setOnItemClickedLisner(Highlight_adapter.OnItemClickLisner listener)
    {
        mlisner=listener;
    }


    public Highlight_adapter( ArrayList<Highlight_product> highlight_productArrayList, Context context) {
        this.highlight_productArrayList = highlight_productArrayList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //ImageView image_url;
        TextView name,amount;
        MyViewHolder(View itemview, final Highlight_adapter.OnItemClickLisner lisner)
        {
            super(itemview);
            this.name=(TextView)itemview.findViewById(R.id.textView4_24_7_dis);
            this.amount=(TextView)itemview.findViewById(R.id.textView4_24_7_price);
          //  this.image_url=(ImageView)itemview.findViewById(R.id.imageView4_24_7_dis);

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
    public Highlight_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.highlight_product,parent,false);
        Highlight_adapter.MyViewHolder myViewHolder=new Highlight_adapter.MyViewHolder(view,mlisner);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Highlight_adapter.MyViewHolder holder, int position) {

        TextView name1=holder.name;
        TextView price=holder.amount;

        name1.setText(highlight_productArrayList.get(position).getName().toString());
        price.setText(highlight_productArrayList.get(position).getAmount().toString());

    }


    @Override
    public int getItemCount() {

        return highlight_productArrayList.size();
    }


}
