package com.shipwebsource.mywebsource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Helpers.SettingsBuddy;

public class MainActivity extends AppCompatActivity
{
    private final String TAG = MainActivity.class.getSimpleName();
    private SettingsBuddy settingsBuddy;
    private TextView loggedInUser;






    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        settingsBuddy = SettingsBuddy.getInstance(getApplicationContext());
//        loggedInUser = (TextView) findViewById(R.id.textview_loggedInUser);
        String name = settingsBuddy.getData("Name");
        String accountNumber = settingsBuddy.getData("AccountNumber");

//        loggedInUser.setText("Hello, "+name);

        FragmentManager fragmentManager = getSupportFragmentManager();
        MainMenuFragment mainMenuFragment = new MainMenuFragment();

        FragmentTransaction fragmentTransaction = setupFragmentTransactionWithSlideAnimations();


        Fragment fragment = fragmentManager.findFragmentById(R.id.masterSinglePane);

            if (fragment == null)
            {
                fragmentTransaction.replace(R.id.masterSinglePane, mainMenuFragment, "MainMenuFragment").addToBackStack(TAG);
            }


        fragmentTransaction.commit();







    }



    public FragmentTransaction setupFragmentTransactionWithSlideAnimations()
    {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.setCustomAnimations(R.anim.slide_left_enter,
                R.anim.slide_left_exit,
                R.anim.slide_right_enter,
                R.anim.slide_right_exit);

        return t;

    }




    @Override
    protected void onResume()
    {
        super.onResume();

    }


}
