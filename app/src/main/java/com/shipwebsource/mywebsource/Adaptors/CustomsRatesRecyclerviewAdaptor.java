package com.shipwebsource.mywebsource.Adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Blueprints.CustomsRatesObject;
import com.shipwebsource.mywebsource.R;

import java.util.ArrayList;


public class CustomsRatesRecyclerviewAdaptor extends RecyclerView.Adapter<CustomsRatesRecyclerviewAdaptor.MyViewHolder>
{
    private static String TAG = CustomsRatesRecyclerviewAdaptor.class.getSimpleName();
    private LayoutInflater inflator;
    private ArrayList<CustomsRatesObject> customsRatesObjects;


    public CustomsRatesRecyclerviewAdaptor(ArrayList<CustomsRatesObject> customsRatesObjects)
    {
        this.customsRatesObjects = customsRatesObjects;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_customs_rates_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        CustomsRatesObject current = customsRatesObjects.get(position);
        holder.category.setText(current.getCategory());
        holder.duty.setText(current.getDuty()+" DUTY");
        holder.vat.setText(current.getVat()+"% VAT");


    }

    @Override
    public int getItemCount()
    {
        return customsRatesObjects.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView category;
        TextView duty;
        TextView vat;



        public MyViewHolder(View itemView)
         {
             super(itemView);

             category = (TextView) itemView.findViewById(R.id.itemCategory);
             duty = (TextView) itemView.findViewById(R.id.itemDuty);
             vat = (TextView) itemView.findViewById(R.id.itemVat);

         }
    }
}
