package com.shipwebsource.mywebsource;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Helpers.MenuViewPager;
import com.shipwebsource.mywebsource.Helpers.SettingsBuddy;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener
{

    private TextView loggedInUser;
    private SettingsBuddy settingsBuddy;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuViewPager adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Package history"));
        tabLayout.addTab(tabLayout.newTab().setText("Incoming packages"));
        tabLayout.addTab(tabLayout.newTab().setText("Pre-alerts"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        adapter = new MenuViewPager(getSupportFragmentManager(), getApplicationContext());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);


        settingsBuddy = SettingsBuddy.getInstance(getApplicationContext());
        String name = settingsBuddy.getData("Name");
        String accountNumber = settingsBuddy.getData("AccountNumber");

        loggedInUser = (TextView) findViewById(R.id.titleLoggedInUser);
        loggedInUser.setText(name+" ("+accountNumber+")");


    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        viewPager.setCurrentItem(tab.getPosition());
        int tabNumber = tab.getPosition();
        switch (tabNumber)
        {
            case 0:
//                tabLayout.getTabAt(tabNumber).setIcon(R.drawable.ic_default_active);
                tabLayout.getTabAt(tabNumber).setText("Package history");
                Fragment a = ((MenuViewPager)viewPager.getAdapter()).getFragment(0);
                if (a != null)
                {
                    Bundle args = new Bundle();
                    a.onResume();
                }
                break;

            case 1:
                tabLayout.getTabAt(tabNumber).setText("Incoming packages");
                Fragment b = ((MenuViewPager)viewPager.getAdapter()).getFragment(1);
                if (b != null)
                {
                    b.onResume();
                }
                break;

            case 2:
                tabLayout.getTabAt(tabNumber).setText("Pre-alerts");
                Fragment c = ((MenuViewPager)viewPager.getAdapter()).getFragment(2);
                if (c != null)
                {
                    c.onResume();
                }
                break;


        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (viewPager != null)
        {
            viewPager.setCurrentItem(0);
        }
    }
}
