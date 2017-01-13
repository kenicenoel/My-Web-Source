package com.shipwebsource.mywebsource.Adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Blueprints.FeaturedCard;
import com.shipwebsource.mywebsource.R;

import java.util.ArrayList;


public class FeaturedCardsRecyclerviewAdaptor extends RecyclerView.Adapter<FeaturedCardsRecyclerviewAdaptor.MyViewHolder>
{
    private static String TAG = FeaturedCardsRecyclerviewAdaptor.class.getSimpleName();
    private LayoutInflater inflator;
    private ArrayList<FeaturedCard> featuredCards;


    public FeaturedCardsRecyclerviewAdaptor(ArrayList<FeaturedCard> featuredCards)
    {
        this.featuredCards = featuredCards;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_featured_cards, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        FeaturedCard current = featuredCards.get(position);
        holder.cardImage.setImageDrawable(current.getCardImage());
        holder.count.setText(position+1+" of "+getItemCount());



    }

    @Override
    public int getItemCount()
    {
        return featuredCards.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView cardImage;
        TextView count;




        public MyViewHolder(View itemView)
         {
             super(itemView);

             cardImage = (ImageView) itemView.findViewById(R.id.featureCardImage);
             count = (TextView) itemView.findViewById(R.id.textview_cardCount);

         }
    }
}
