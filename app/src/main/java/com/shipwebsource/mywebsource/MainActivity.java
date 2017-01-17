package com.shipwebsource.mywebsource;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.shipwebsource.mywebsource.Helpers.SettingsBuddy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    private final String TAG = MainActivity.class.getSimpleName();
    private SettingsBuddy settingsBuddy;
    private TextView loggedInUser;
    private Toolbar toolbar;
    private ImageView countryLogo;
    private ImageView actionSettings;
    private String country;

    private static final String URL = "http://noel.netau.net/webservice/register_token.php";
    private RequestQueue requestQueue;



    private final String PREFIX_DOMINICA = "Dominica";
    private final String PREFIX_GRENADA = "Grenada";
    private final String PREFIX_GUYANA = "Guyana";
    private final String PREFIX_JAMAICA = "Jamaica";
    private final String PREFIX_ST_LUCIA = "St Lucia";
    private final String PREFIX_TRINIDAD = "Trinidad";
    private String token;
    private String accountNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsBuddy = SettingsBuddy.getInstance(getApplicationContext());

        // Check if the user allows sending generic notifications
        String notificationsEnabled = settingsBuddy.getData("GeneralNotificationsEnabled");
        if (notificationsEnabled.equals("true"))
        {
            FirebaseMessaging.getInstance().subscribeToTopic("General");
        }




        token = FirebaseInstanceId.getInstance().getToken();
       accountNumber = settingsBuddy.getData("AccountNumber");
        Log.d(TAG, "Token = "+token);
        if (token != null)
        {
            registerToken();
        }



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        countryLogo = (ImageView) toolbar.findViewById(R.id.countryLogo);

        country = settingsBuddy.getData("Country");
        Log.d(TAG, "User country is "+country);
        if (country != null)
        {
            setupCountryLogo(country);

        }



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

    private void setupCountryLogo(String country)
    {

        switch (country)
        {
            case PREFIX_DOMINICA:
                Snackbar dm = Snackbar.make(findViewById(android.R.id.content), "ShopBox Dominica", Snackbar.LENGTH_LONG);
                dm.show();

                new Thread(new Runnable()
                {
                    public void run() {

                        countryLogo.setImageResource(R.drawable.my_shopbox_dominica);
                    }
                }).start();
                break;

            case PREFIX_GRENADA:
                Snackbar gd = Snackbar.make(findViewById(android.R.id.content), "Web Source Grenada", Snackbar.LENGTH_LONG);
                gd.show();
                countryLogo.setImageResource(R.drawable.my_websource_horizontal_toolbar);
                break;

            case PREFIX_GUYANA:
                Snackbar gy = Snackbar.make(findViewById(android.R.id.content), "Web Source Guyana", Snackbar.LENGTH_LONG);
                gy.show();
                new Thread(new Runnable()
                {
                    public void run() {

                        countryLogo.setImageResource(R.drawable.my_websource_horizontal_toolbar);
                    }
                }).start();

                break;

            case PREFIX_JAMAICA:
                Snackbar jm = Snackbar.make(findViewById(android.R.id.content), "Web Source Jamaica", Snackbar.LENGTH_LONG);
                jm.show();
                new Thread(new Runnable()
                {
                    public void run() {

                        countryLogo.setImageResource(R.drawable.my_websource_horizontal_toolbar);
                    }
                }).start();

                break;

            case PREFIX_ST_LUCIA:
                Snackbar lc = Snackbar.make(findViewById(android.R.id.content), "ShopBox St Lucia", Snackbar.LENGTH_LONG);
                lc.show();
                new Thread(new Runnable()
                {
                    public void run() {

                        countryLogo.setImageResource(R.drawable.my_shopbox_st_lucia);
                    }
                }).start();

                break;

            case PREFIX_TRINIDAD:
                Snackbar tt = Snackbar.make(findViewById(android.R.id.content), "Web Source Trinidad", Snackbar.LENGTH_LONG);
                tt.show();
                new Thread(new Runnable()
                {
                    public void run() {

                        countryLogo.setImageResource(R.drawable.my_websource_horizontal_toolbar);
                    }
                }).start();

                break;

        }



    }



    private void findCountryBasedOnAccountPrefix(String accountnumber)
    {
        String prefix = accountnumber.substring(0, 3);
        String country = "";
        switch (prefix)
        {
            case PREFIX_DOMINICA:
                Snackbar dm = Snackbar.make(findViewById(android.R.id.content), "ShopBox Dominica", Snackbar.LENGTH_LONG);
                dm.show();
                country = "Dominica";
                settingsBuddy.saveData("Country", country );
                break;

            case PREFIX_GRENADA:
                Snackbar gd = Snackbar.make(findViewById(android.R.id.content), "Web Source Grenada", Snackbar.LENGTH_LONG);
                gd.show();
                country = "Grenada";
                settingsBuddy.saveData("Country", country );
                break;

            case PREFIX_GUYANA:
                Snackbar gy = Snackbar.make(findViewById(android.R.id.content), "Web Source Guyana", Snackbar.LENGTH_LONG);
                gy.show();
                country = "Guyana";
                settingsBuddy.saveData("Country", country );
                break;

            case PREFIX_JAMAICA:
                Snackbar jm = Snackbar.make(findViewById(android.R.id.content), "Web Source Jamaica", Snackbar.LENGTH_LONG);
                jm.show();
                country = "Jamaica";
                settingsBuddy.saveData("Country", country );
                break;

            case PREFIX_ST_LUCIA:
                Snackbar lc = Snackbar.make(findViewById(android.R.id.content), "ShopBox St Lucia", Snackbar.LENGTH_LONG);
                lc.show();
                country = "St Lucia";
                settingsBuddy.saveData("Country", country );
                break;

            case PREFIX_TRINIDAD:
                Snackbar tt = Snackbar.make(findViewById(android.R.id.content), "Web Source Trinidad", Snackbar.LENGTH_LONG);
                tt.show();
                country = "Trinidad";
                settingsBuddy.saveData("Country", country );
                break;



        }
    }



    private void registerToken()
    {
        SettingsBuddy buddy = SettingsBuddy.getInstance(this);

        Log.d(TAG, "Ready to register token.");
        requestQueue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "JSON Object Response");
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, response);

                    if (jsonObject.getString("success").equals("true")) {
                        Log.d(TAG, "Successully updated user token.");
                    } else if (jsonObject.getString("success").equals("false")) {
                        Log.d(TAG, "Failed to update user token.");


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("token", token);
                hashMap.put("accountNumber", accountNumber);

                Log.d(TAG, "Sending details: "+token+ " for account "+accountNumber);
                return hashMap;
            }
        };

        requestQueue.add(request);

    }



}
