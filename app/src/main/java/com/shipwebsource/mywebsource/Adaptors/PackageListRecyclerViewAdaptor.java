package com.shipwebsource.mywebsource.Adaptors;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Blueprints.PackageObject;
import com.shipwebsource.mywebsource.R;

import java.util.ArrayList;


public class PackageListRecyclerViewAdaptor extends RecyclerView.Adapter<PackageListRecyclerViewAdaptor.MyViewHolder>
{
    private static String TAG = PackageListRecyclerViewAdaptor.class.getSimpleName();
    private LayoutInflater inflator;
    private ArrayList<PackageObject> packageObjects;
    private static ClickListener clickListener;

    private static int previousPosition = -1;
    int selectedPosition=-1;


    public PackageListRecyclerViewAdaptor(ArrayList<PackageObject> packageObjects)
    {
        this.packageObjects = packageObjects;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    public interface ClickListener
    {
        void onItemClick(int position, int previousPostion, View v);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        PackageObject current = packageObjects.get(position);
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
        return packageObjects.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
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
             itemView.setOnClickListener(this);

         }

        @Override
        public void onClick(View v)
        {

            if (clickListener != null)
            {
                clickListener.onItemClick(getAdapterPosition(), previousPosition, v);
            }

            Log.d(TAG, "I was clicked.");

            selectedPosition=getAdapterPosition();
            notifyDataSetChanged();
        }
    }

    public void setClickListener(ClickListener clickListener)
    {
        PackageListRecyclerViewAdaptor.clickListener = clickListener;
    }

}
