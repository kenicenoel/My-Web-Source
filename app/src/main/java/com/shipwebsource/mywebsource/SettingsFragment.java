package com.shipwebsource.mywebsource;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Helpers.SettingsBuddy;


public class SettingsFragment extends Fragment
{


    private View view;
    private TextView loggedInUser;
    private SettingsBuddy settingsBuddy;
    private CheckBox allowNotifications;
    private Button saveSettings;
    public SettingsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view =  inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
       loggedInUser = (TextView)view.findViewById(R.id.textview_loggedInUser);
        settingsBuddy = SettingsBuddy.getInstance(getContext());
        allowNotifications = (CheckBox) view.findViewById(R.id.checkbox_AllowGeneralNotifications);
        saveSettings = (Button) view.findViewById(R.id.button_SaveSettings);
        String notificationsEnabled = settingsBuddy.getData("GeneralNotificationsEnabled");
        if (notificationsEnabled.equals("true"))
        {
            allowNotifications.setChecked(true);

        }

        else
        {
            allowNotifications.setChecked(false);
        }



        String name = settingsBuddy.getData("Name");
        String accountNumber = settingsBuddy.getData("AccountNumber");

        loggedInUser.append(name + " ("+accountNumber+")");

        loggedInUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getContext(), loggedInUser);

                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_user, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        switch (item.getTitle().toString())
                        {
                            case "Logout":
                                logoutUser();
                                break;
                        }
                        return true;
                    }


                });

                popup.show();//showing popup menu
            }
        });

        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (allowNotifications.isChecked())
                {
                    settingsBuddy.saveData("GeneralNotificationsEnabled", "true");
                }

                else
                {
                    settingsBuddy.saveData("GeneralNotificationsEnabled", "false");
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.masterSinglePane, new MainMenuFragment()).commit();
            }
        });


    }



    // Logs out the current user
    public void logoutUser()
    {

        settingsBuddy.clearData();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }




}
