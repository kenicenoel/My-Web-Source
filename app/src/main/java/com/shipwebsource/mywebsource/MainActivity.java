package com.shipwebsource.mywebsource;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Adaptors.GenericListStringRecyclerViewAdaptor;
import com.shipwebsource.mywebsource.Blueprints.PackageHistoryObject;
import com.shipwebsource.mywebsource.Helpers.DividerItemDecoration;
import com.shipwebsource.mywebsource.Helpers.SettingsBuddy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private TextView loggedInUser;
    private SettingsBuddy settingsBuddy;

    private GenericListStringRecyclerViewAdaptor adaptor;
    private RecyclerView recyclerViewPackageHistory;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<PackageHistoryObject> dummyData;


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
        dummyData = new ArrayList<>();
        recyclerViewPackageHistory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        recyclerViewPackageHistory.addItemDecoration(dividerItemDecoration);

        recyclerViewPackageHistory.setLayoutManager(linearLayoutManager);
        adaptor = new GenericListStringRecyclerViewAdaptor(dummyData);
        generateDummyData();
        recyclerViewPackageHistory.setAdapter(adaptor);



    }



    @Override
    protected void onResume()
    {
        super.onResume();

    }

    private void generateDummyData()
    {

        PackageHistoryObject object = new PackageHistoryObject("HAWB0000000029510012", "Kitchen Utensils", "Amazon", "100.50", "Delivered");
        dummyData.add(object);

        PackageHistoryObject object2 = new PackageHistoryObject("HAWB0000000029510013", "Apparel", "EBAY", "80.30", "Delivered");
        dummyData.add(object2);

        PackageHistoryObject object3 = new PackageHistoryObject("HAWB0000000029510014", "Toys", "USPS", "250.99", "Delivered");
        dummyData.add(object3);

        PackageHistoryObject object4 = new PackageHistoryObject("HAWB0000000029510015", "Video Game", "FedEx", "41.75", "Delivered");
        dummyData.add(object4);

        PackageHistoryObject object5 = new PackageHistoryObject("HAWB0000000029510016", "Laptop/ TV stick", "DHL", "104.50", "Delivered");
        dummyData.add(object5);

        PackageHistoryObject object6 = new PackageHistoryObject("HAWB0000000029510017", "Cell Parts", "Canada Post", "53.17", "Delivered");
        dummyData.add(object6);

        PackageHistoryObject object7 = new PackageHistoryObject("HAWB0000000029510018", "Book/ Home Accessories", "UPS", "1018", "Delivered");
        dummyData.add(object7);



    }
}
