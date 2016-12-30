package com.shipwebsource.mywebsource;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shipwebsource.mywebsource.Adaptors.CustomsRatesRecyclerviewAdaptor;
import com.shipwebsource.mywebsource.Blueprints.CustomsRatesObject;

import java.util.ArrayList;


public class CustomsRatesFragment extends Fragment
{

    private ArrayList<CustomsRatesObject> dutyFree;
    private ArrayList<CustomsRatesObject> dutyAndVat;
    private CustomsRatesRecyclerviewAdaptor adaptorA;
    private CustomsRatesRecyclerviewAdaptor adaptorB;
    private RecyclerView recyclerViewDutyFree;
    private RecyclerView recyclerViewDutyAndVat;
    private LinearLayoutManager linearLayoutManagerA;
    private LinearLayoutManager linearLayoutManagerB;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        dutyFree = new ArrayList<>();
        dutyAndVat = new ArrayList<>();

        linearLayoutManagerA = new LinearLayoutManager(getContext());
        linearLayoutManagerB = new LinearLayoutManager(getContext());

        recyclerViewDutyFree = (RecyclerView) view.findViewById(R.id.recyclerview_dutyFree);
        recyclerViewDutyAndVat = (RecyclerView) view.findViewById(R.id.recyclerview_dutyAndVat);

        adaptorA = new CustomsRatesRecyclerviewAdaptor(dutyFree);
        adaptorB = new CustomsRatesRecyclerviewAdaptor(dutyAndVat);

        recyclerViewDutyFree.setAdapter(adaptorA);
        recyclerViewDutyAndVat.setAdapter(adaptorB);

        recyclerViewDutyFree.setLayoutManager(linearLayoutManagerA);
        recyclerViewDutyAndVat.setLayoutManager(linearLayoutManagerB);

        addDutyFreeItems();
        addDutyAndVatItems();


    }

    private void addDutyFreeItems()
    {
        // Set Duty free Items
        CustomsRatesObject object0 = new CustomsRatesObject("Blank DVDs and CDs", "Free");
        CustomsRatesObject object1 = new CustomsRatesObject("Music and Software discs", "Free");
        CustomsRatesObject object2 = new CustomsRatesObject("Fax and Line Modems", "Free");
        CustomsRatesObject object3 = new CustomsRatesObject("Handheld tools", "Free");
        CustomsRatesObject object4 = new CustomsRatesObject("Computer cases", "Free");
        CustomsRatesObject object5 = new CustomsRatesObject("Motherboards", "Free");
        CustomsRatesObject object6 = new CustomsRatesObject("Processors", "Free");
        CustomsRatesObject object7 = new CustomsRatesObject("Ethernet hubs", "Free");
        CustomsRatesObject object8 = new CustomsRatesObject("Ink and printer catridges", "Free");
        CustomsRatesObject object9 = new CustomsRatesObject("Printers and All-in-Ones", "Free");
        CustomsRatesObject object10 = new CustomsRatesObject("Plumbing", "Free");
        CustomsRatesObject object11 = new CustomsRatesObject("Laptops and Desktops", "Free");
        CustomsRatesObject object12 = new CustomsRatesObject("Computer systems", "Free");
        CustomsRatesObject object13 = new CustomsRatesObject("Computer monitors", "Free");

        dutyFree.add(object0);
        dutyFree.add(object1);
        dutyFree.add(object2);
        dutyFree.add(object3);
        dutyFree.add(object4);
        dutyFree.add(object5);
        dutyFree.add(object6);
        dutyFree.add(object7);
        dutyFree.add(object8);
        dutyFree.add(object9);
        dutyFree.add(object10);
        dutyFree.add(object11);
        dutyFree.add(object12);
        dutyFree.add(object13);




    }

    private void addDutyAndVatItems()
    {
        // Set Duty Free Items
        CustomsRatesObject object1 = new CustomsRatesObject("Musical equipment", "10%");
        CustomsRatesObject object2 = new CustomsRatesObject("Clothing", "20%");
        CustomsRatesObject object3 = new CustomsRatesObject("Shoes e.g walking or casual", "20%");
        CustomsRatesObject object4 = new CustomsRatesObject("Digital cameras", "20%");
        CustomsRatesObject object5 = new CustomsRatesObject("Food supplements", "20%");
        CustomsRatesObject object6 = new CustomsRatesObject("Toys", "20%");
        CustomsRatesObject object7 = new CustomsRatesObject("Furniture", "20%");
        CustomsRatesObject object8 = new CustomsRatesObject("Appliances", "20%");
        CustomsRatesObject object9 = new CustomsRatesObject("Cellular phones", "20%");
        CustomsRatesObject object10 = new CustomsRatesObject("Speakers", "20%");
        CustomsRatesObject object11 = new CustomsRatesObject("DVDs", "20%");
        CustomsRatesObject object12 = new CustomsRatesObject("Cosmetics", "20%");
        CustomsRatesObject object13 = new CustomsRatesObject("Video games", "20%");
        CustomsRatesObject object14 = new CustomsRatesObject("Electronics", "20%");
        CustomsRatesObject object15 = new CustomsRatesObject("Album", "20%");
        CustomsRatesObject object16 = new CustomsRatesObject("Car parts", "30%");
        CustomsRatesObject object17 = new CustomsRatesObject("Jewellery", "30%");
        CustomsRatesObject object18 = new CustomsRatesObject("Costume jewellery", "30%");
        CustomsRatesObject object19 = new CustomsRatesObject("Watches", "30%");

        dutyAndVat.add(object1);
        dutyAndVat.add(object2);
        dutyAndVat.add(object3);
        dutyAndVat.add(object4);
        dutyAndVat.add(object5);
        dutyAndVat.add(object6);
        dutyAndVat.add(object7);
        dutyAndVat.add(object8);
        dutyAndVat.add(object9);
        dutyAndVat.add(object10);
        dutyAndVat.add(object11);
        dutyAndVat.add(object12);
        dutyAndVat.add(object13);
        dutyAndVat.add(object14);
        dutyAndVat.add(object15);
        dutyAndVat.add(object16);
        dutyAndVat.add(object17);
        dutyAndVat.add(object18);
        dutyAndVat.add(object19);
    }
}
