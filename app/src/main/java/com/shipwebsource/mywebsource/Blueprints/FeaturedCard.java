package com.shipwebsource.mywebsource.Blueprints;


import android.graphics.drawable.Drawable;

public class FeaturedCard
{


    Drawable cardImage;

    public FeaturedCard()
    {

    }

    public FeaturedCard(Drawable imageView)
    {


        this.cardImage = imageView;


    }


    public Drawable getCardImage() {
        return cardImage;
    }
    public void setCardImage(Drawable imageView) {
        this.cardImage = imageView;
    }




}
