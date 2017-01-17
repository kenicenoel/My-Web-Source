package com.shipwebsource.mywebsource;

/**
 * Created by Software Developer on 1/18/2016.
 */


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shipwebsource.mywebsource.Helpers.SettingsBuddy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText u;
    private EditText p;
    private TextView k;
    private TextView e;

    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;

    private RequestQueue requestQueue;
    private static final String URL = "http://noel.netau.net/webservice/login.php";

    private StringRequest request;
    private String fullName = "";
    private String message = "";
    private String country = "";


    public final String DEFAULT = "N/A"; // A default constant for use with sharedPreferences
    public boolean isLoggedIn = false;
    private Button loginButton;
    private SettingsBuddy settings;

    private String accountnumber;
    private String password;
    private String wsKey;


    private final String PREFIX_DOMINICA = "DOM";
    private final String PREFIX_GRENADA = "GRE";
    private final String PREFIX_GUYANA = "GUY";
    private final String PREFIX_JAMAICA = "JCA";
    private final String PREFIX_ST_LUCIA = "BSL";
    private final String PREFIX_TRINIDAD = "WEB";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setVisibility(View.INVISIBLE);
        settings = SettingsBuddy.getInstance(getApplicationContext());

        // Find the username, password and key fields
        u = (EditText) findViewById(R.id.accountNumberField);
        p = (EditText) findViewById(R.id.passwordField);
        k = (TextView) findViewById(R.id.wsMobileApp);
        e = (TextView) findViewById(R.id.loginMessage);


        requestQueue = Volley.newRequestQueue(this);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Get the text from the accountnumber, password and key fields
               accountnumber = u.getText().toString();
                password = p.getText().toString();
                wsKey = k.getText().toString();


                Log.d(TAG, "String Request Starts");
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d(TAG, "JSON Object Response");
                        try
                        {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("success").equals("true"))
                            {
                                Log.d(TAG, "Login data is valid");
                                // Set the full name and the message returned from the Json Response
                                fullName = jsonObject.getString("firstname")+" " +jsonObject.getString("lastname");
                                message = jsonObject.getString("message");
                                country = jsonObject.getString("country");

                                // Store the accountnumber, password, full name and key to enable automatic login each time
                                settings.saveData("AccountNumber", accountnumber);
                                settings.saveData("Password", password);
                                settings.saveData("Name", fullName);
                                settings.saveData("Country", country);
                                settings.saveData("Key", wsKey);

//                                findCountryBasedOnAccountPrefix(accountnumber);


                                Log.d(TAG, fullName + " " + message);

                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                                snackbar.show();

                                Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();

                            }

                            else if(jsonObject.getString("success").equals("false"))
                            {
                                message = jsonObject.getString("message");
                                e.setText(message);
                                e.setVisibility(View.VISIBLE);


                            }




                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Something weird happened. Here's the error: "+ error.getMessage()+".", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("accountNumber", accountnumber);
                        hashMap.put("password", password);
                        hashMap.put("key", wsKey);
                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });

        // Check if the device is currently running an android version less than 6.0
        if (Build.VERSION.SDK_INT < 23)
        {
            // If android version is less than 6, it is not necessary to check for permissions
            continueLoginProcedure();
        }

        // since android version is at least 6 or higher, check if permissions are already granted
        else
        {
            // if permissions are already granted continue setup logic and if not request permissions
            if (checkAndRequestPermissions())
            {
                //If you have already permitted the permission
                continueLoginProcedure();

            }

        }

    }


    private boolean checkAndRequestPermissions()
    {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);


        int storagePermissionRead = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int storagePermissionWrite = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int  locationPermissionCoarse  = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        int  locationPermissionFine  = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);




        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermissionRead != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (storagePermissionWrite != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (locationPermissionCoarse != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (locationPermissionFine != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,

                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_ACCOUNTS:

                Map<String, Integer> perms = new HashMap<String, Integer>();

                // Initialize
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);



                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                {
                    perms.put(permissions[i], grantResults[i]);
                }

                // Check if all permissions are granted
                if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    // All Permissions Granted
                    continueLoginProcedure();
                }

                else
                {
                    // You did not accept the request can not use the functionality
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Permission needed");
                    builder.setMessage("To provide you with the best possible experience, we need permission to read and write to external storage, access your location and access the camera. You cannot continue if these" +
                            "permissions are not granted.");
                    builder.setPositiveButton("Okay",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    checkAndRequestPermissions();
                                    dialog.dismiss();
                                }
                            });

                    builder.setNegativeButton("Ummm.. no",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
                break;
        }
    }



    private void continueLoginProcedure()
    {
        //  Check if the user has logged in before and if so, redirect them to the main menu
        isLoggedIn = isUserCredentialsStored();


        if(isLoggedIn)
        {
            Intent intent= new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        else
        {
            loginButton.setVisibility(View.VISIBLE);

        }

    }


    // Checks if the user credentials are stored
    public boolean isUserCredentialsStored()
    {
        String accountnumber = settings.getData("AccountNumber");
        String pass = settings.getData("Password");

        // Return true or false (simplified version)
        if (!accountnumber.equals(DEFAULT) || !pass.equals(DEFAULT))
        {
            return true;
        }

        return false;

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
                settings.saveData("Country", country );
                break;

            case PREFIX_GRENADA:
                Snackbar gd = Snackbar.make(findViewById(android.R.id.content), "Web Source Grenada", Snackbar.LENGTH_LONG);
                gd.show();
                country = "Grenada";
                settings.saveData("Country", country );
                break;

            case PREFIX_GUYANA:
                Snackbar gy = Snackbar.make(findViewById(android.R.id.content), "Web Source Guyana", Snackbar.LENGTH_LONG);
                gy.show();
                country = "Guyana";
                settings.saveData("Country", country );
                break;

            case PREFIX_JAMAICA:
                Snackbar jm = Snackbar.make(findViewById(android.R.id.content), "Web Source Jamaica", Snackbar.LENGTH_LONG);
                jm.show();
                country = "Jamaica";
                settings.saveData("Country", country );
                break;

            case PREFIX_ST_LUCIA:
                Snackbar lc = Snackbar.make(findViewById(android.R.id.content), "ShopBox St Lucia", Snackbar.LENGTH_LONG);
                lc.show();
                country = "St Lucia";
                settings.saveData("Country", country );
                break;

            case PREFIX_TRINIDAD:
                Snackbar tt = Snackbar.make(findViewById(android.R.id.content), "Web Source Trinidad", Snackbar.LENGTH_LONG);
                tt.show();
                country = "Trinidad";
                settings.saveData("Country", country );
                break;

            default:
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Web Source Trinidad", Snackbar.LENGTH_LONG);
                snackbar.show();
                country = "Trinidad";
                settings.saveData("Country", country );
                break;

        }
    }















}
