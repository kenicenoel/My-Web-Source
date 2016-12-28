package com.shipwebsource.mywebsource.Helpers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shipwebsource.mywebsource.IncomingPackagesFragment;
import com.shipwebsource.mywebsource.PackageHistoryFragment;
import com.shipwebsource.mywebsource.PreAlertFragment;

import java.util.HashMap;
import java.util.Map;


//Extending FragmentStatePagerAdapter
public class MenuViewPager extends FragmentPagerAdapter
{
    private int NUM_ITEMS = 3;
    private Context context;
    private Map<Integer, String> fragmentTags;
    private FragmentManager fragmentManager;
    public MenuViewPager(FragmentManager fm, Context c)
    {
        super(fm);
        fragmentManager = fm;
        context = c;
        fragmentTags= new HashMap<Integer, String>();
    }



    // Returns the fragment to show for the current page
    @Override
    public Fragment getItem(int position)
    {
        // Returning the current tabs
        switch (position)
        {
            case 0:
                PackageHistoryFragment tab1 = new PackageHistoryFragment();
                return tab1;
            case 1:
                IncomingPackagesFragment tab2 = new IncomingPackagesFragment();
                return tab2;
            case 2:
                PreAlertFragment tab3 = new PreAlertFragment();
                return tab3;

            default:
                return new PackageHistoryFragment();

        }

    }

    @Override
    public int getCount()
    {
        return NUM_ITEMS;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof Fragment)
        {
            // Record the fragment tag here
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            fragmentTags.put(position, tag);
        }
        return obj;
    }

    public Fragment getFragment(int position)
    {
        String tag = fragmentTags.get(position);
        if (tag == null)
        {
            return null;

        }
        return fragmentManager.findFragmentByTag(tag);
    }

    //    //integer to count number of tabs
//    int tabCount;
//
//    //Constructor to the class
//    public CustomerListPager(FragmentManager fm, int tabCount)
//    {
//        super(fm);
//        //Initializing tab count
//        this.tabCount= tabCount;
//    }
//
//    //Overriding method getItem
//    @Override
//    public Fragment getItem(int position)
//    {
//        //Returning the current tabs
//        switch (position)
//        {
//            case 0:
//                DefaultCustomersFragment tab1 = new DefaultCustomersFragment();
//                return tab1;
//            case 1:
//                HoldCustomersFragment tab2 = new HoldCustomersFragment();
//                return tab2;
//            case 2:
//                CancelledCustomersFragment tab3 = new CancelledCustomersFragment();
//                return tab3;
//            default:
//                return new DefaultCustomersFragment();
//        }
//    }
//
//    //Overriden method getCount to get the number of tabs
//    @Override
//    public int getCount()
//    {
//        return tabCount;
//    }


}