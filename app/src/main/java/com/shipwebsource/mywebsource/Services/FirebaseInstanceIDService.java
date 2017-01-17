package com.shipwebsource.mywebsource.Services;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.shipwebsource.mywebsource.Helpers.SettingsBuddy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class FirebaseInstanceIDService extends FirebaseInstanceIdService
{

    private static final String TAG = FirebaseInstanceIdService.class.getSimpleName();
    private static final String URL = "http://noel.netau.net/webservice/register_token.php";
    private RequestQueue requestQueue;

    @Override
    public void onTokenRefresh()
    {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token Refreshed");
//        registerToken(token);

    }

    private void registerToken(final String token)
    {
        SettingsBuddy buddy = SettingsBuddy.getInstance(this);
        final String accountNumber = buddy.getData("AccountNumber");
        Log.d(TAG, "Ready to register token.");

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "JSON Object Response");
                try
                {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("success").equals("true"))
                    {
                        Log.d(TAG, "Successully updated user token.");
                    }
                    else if (jsonObject.getString("success").equals("false"))
                    {
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
                return hashMap;
            }
        };

        requestQueue.add(request);

    }


}
