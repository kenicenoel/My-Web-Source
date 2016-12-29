package com.shipwebsource.mywebsource;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CustomsRatesFragment extends Fragment
{


    private View view;

    public CustomsRatesFragment()
    {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_customs_rates, container, false);
       return view;
    }



}
