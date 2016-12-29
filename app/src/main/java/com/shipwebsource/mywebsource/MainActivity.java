package com.shipwebsource.mywebsource;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Adaptors.GenericListStringRecyclerViewAdaptor;
import com.shipwebsource.mywebsource.Blueprints.PackageObject;
import com.shipwebsource.mywebsource.Helpers.DividerItemDecoration;
import com.shipwebsource.mywebsource.Helpers.SettingsBuddy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private TextView loggedInUser;
    private SettingsBuddy settingsBuddy;

    private GenericListStringRecyclerViewAdaptor adaptor;
    private RecyclerView recyclerViewPackageHistory;
    private RecyclerView recyclerViewIncomingPackages;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<PackageObject> history;
    private ArrayList<PackageObject> incoming;
    private RelativeLayout incomingPackagesRelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        settingsBuddy = SettingsBuddy.getInstance(getApplicationContext());
        String name = settingsBuddy.getData("Name");
        String accountNumber = settingsBuddy.getData("AccountNumber");

        recyclerViewPackageHistory = (RecyclerView) findViewById(R.id.recyclerview_packageHistory);
        recyclerViewIncomingPackages = (RecyclerView) findViewById(R.id.recyclerview_incomingPackages);


        history = new ArrayList<>();
        incoming = new ArrayList<>();

        recyclerViewPackageHistory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        recyclerViewPackageHistory.addItemDecoration(dividerItemDecoration);

        recyclerViewPackageHistory.setLayoutManager(linearLayoutManager);
        adaptor = new GenericListStringRecyclerViewAdaptor(history);
        generateDummyData();
        recyclerViewPackageHistory.setAdapter(adaptor);

        if (incoming.isEmpty())
        {
            incomingPackagesRelativeLayout = (RelativeLayout) findViewById(R.id.layout_emptyView_incomingPackages);
            incomingPackagesRelativeLayout.setVisibility(View.VISIBLE);
            recyclerViewIncomingPackages.setVisibility(View.INVISIBLE);
        }

        else
        {
            incomingPackagesRelativeLayout.setVisibility(View.GONE);
            recyclerViewIncomingPackages.setVisibility(View.VISIBLE);
        }



    }



    @Override
    protected void onResume()
    {
        super.onResume();

    }

    private void generateDummyData()
    {

        PackageObject object = new PackageObject("HAWB0000000029510012", "Kitchen Utensils", "Amazon", "100.50", "Delivered");
        history.add(object);

        PackageObject object2 = new PackageObject("HAWB0000000029510013", "Apparel", "EBAY", "80.30", "Delivered");
        history.add(object2);

        PackageObject object3 = new PackageObject("HAWB0000000029510014", "Toys", "USPS", "250.99", "Delivered");
        history.add(object3);

        PackageObject object4 = new PackageObject("HAWB0000000029510015", "Video Game", "FedEx", "41.75", "Delivered");
        history.add(object4);

        PackageObject object5 = new PackageObject("HAWB0000000029510016", "Laptop/ TV stick", "DHL", "104.50", "Delivered");
        history.add(object5);

//        PackageObject object6 = new PackageObject("HAWB0000000029510017", "Cell Parts", "Canada Post", "53.17", "Delivered");
//        history.add(object6);
//
//        PackageObject object7 = new PackageObject("HAWB0000000029510018", "Book/ Home Accessories", "UPS", "1018", "Delivered");
//        history.add(object7);



    }
}
