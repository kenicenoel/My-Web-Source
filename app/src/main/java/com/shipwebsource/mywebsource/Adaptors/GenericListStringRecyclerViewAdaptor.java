package com.shipwebsource.mywebsource.Adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Blueprints.PackageHistoryObject;
import com.shipwebsource.mywebsource.R;

import java.util.ArrayList;


public class GenericListStringRecyclerViewAdaptor extends RecyclerView.Adapter<GenericListStringRecyclerViewAdaptor.MyViewHolder>
{
    private static String TAG = GenericListStringRecyclerViewAdaptor.class.getSimpleName();
    private LayoutInflater inflator;
    private ArrayList<PackageHistoryObject> packageHistoryObjects;


    public GenericListStringRecyclerViewAdaptor(ArrayList<PackageHistoryObject> packageHistoryObjects)
    {
        this.packageHistoryObjects = packageHistoryObjects;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        PackageHistoryObject current = packageHistoryObjects.get(position);
        holder.packageNumber.setText(current.getPackageNumber());
        holder.description.setText(current.getDescription());
        holder.shipper.setText(current.getShipper());
        holder.price.setText("$"+current.getPrice());
        holder.status.setText(current.getStatus());

        switch (current.getShipper().toLowerCase())
        {
            case "amazon":
                holder.icon.setImageResource(R.drawable.amazon);
                break;

            case "ebay":
                holder.icon.setImageResource(R.drawable.ebay);
                break;

            case "usps":
                holder.icon.setImageResource(R.drawable.usps);
                break;

            case "dhl":
                holder.icon.setImageResource(R.drawable.dhl);
                break;

            case "fedex":
                holder.icon.setImageResource(R.drawable.fedex);
                break;

            case "ems":
                holder.icon.setImageResource(R.drawable.ems);
                break;

            case "ups":
                holder.icon.setImageResource(R.drawable.ups);
                break;

            default:
                holder.icon.setImageResource(R.drawable.other);
                break;


        }
    }

    @Override
    public int getItemCount()
    {
        return packageHistoryObjects.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView packageNumber;
        TextView description;
        TextView shipper;
        TextView price;
        TextView status;
        ImageView icon;



        public MyViewHolder(View itemView)
         {
             super(itemView);
             icon = (ImageView) itemView.findViewById(R.id.layout_shipper_icon);
             packageNumber = (TextView) itemView.findViewById(R.id.layout_packageNumber);
             description = (TextView) itemView.findViewById(R.id.layout_description);
             shipper = (TextView) itemView.findViewById(R.id.layout_shipper);
             price = (TextView) itemView.findViewById(R.id.layout_price);
             status = (TextView) itemView.findViewById(R.id.layout_packageStatus);

         }
    }
}
