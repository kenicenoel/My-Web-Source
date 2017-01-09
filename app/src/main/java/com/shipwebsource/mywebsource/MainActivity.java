package com.shipwebsource.mywebsource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Helpers.SettingsBuddy;

public class MainActivity extends AppCompatActivity
{
    private final String TAG = MainActivity.class.getSimpleName();
    private SettingsBuddy settingsBuddy;
    private TextView loggedInUser;
    private Toolbar toolbar;
    private ImageView myWebSourceLogo;
    private ImageView actionSettings;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        settingsBuddy = SettingsBuddy.getInstance(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        myWebSourceLogo = (ImageView) toolbar.findViewById(R.id.websource_logo);
        actionSettings = (ImageView) toolbar.findViewById(R.id.action_settings);

        actionSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = setupFragmentTransactionWithSlideAnimations();
                ft.replace(R.id.masterSinglePane, new SettingsFragment(), "SettingsFragment").addToBackStack(TAG);
                ft.commit();

            }
        });

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
