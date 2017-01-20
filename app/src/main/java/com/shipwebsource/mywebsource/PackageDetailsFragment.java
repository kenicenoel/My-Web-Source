package com.shipwebsource.mywebsource;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PackageDetailsFragment extends Fragment
{

    private static final String TAG = PackageDetailsFragment.class.getSimpleName();
    private static final String API_KEY = "wsMobileApp";
    private View view;
    private String packageNumber;
    private String shipper;
    private String cost;
    private String description;

    private final String URL = "http://noel.netau.net/webservice/get_package_details.php";
    private ProgressWheel wheel;
    private RequestQueue requestQueue;

    private TextView packageNumberTextView;
    private TextView shipperTextView;
    private TextView costTextView;
    private TextView descriptionTextView;

    public PackageDetailsFragment()
    {
        // Required empty public constructor
    }

    public static PackageDetailsFragment newInstance(String packageNumber)
    {
        Bundle args = new Bundle();

        PackageDetailsFragment fragment = new PackageDetailsFragment();
        args.putString("PackageNumber", packageNumber);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_package_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume()
    {
        super.onResume();

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //Check if the fragment was created from the new instance method and if so, get any arguments passed
        if (getArguments() != null)
        {
            packageNumber = getArguments().getString("PackageNumber");
            wheel =(ProgressWheel) view.findViewById(R.id.progress_wheel);
            requestQueue = Volley.newRequestQueue(getContext());
            shipperTextView = (TextView) view.findViewById(R.id.textview_packageDetails_Shipper);
            costTextView = (TextView) view.findViewById(R.id.textview_packageDetails_Cost);
            descriptionTextView = (TextView) view.findViewById(R.id.textview_packageDetails_Description);
            packageNumberTextView = (TextView) view.findViewById(R.id.textview_packageDetails_packageNumber);




            // Get the package history
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
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
                            packageNumberTextView.setText(packageNumber);
                            shipperTextView.setText(jsonObject.getString("shipper"));
                            descriptionTextView.setText(jsonObject.getString("description"));
                            costTextView.setText("$"+jsonObject.getString("cost"));
                            Log.d(TAG, response);

                        }


                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Log.d(TAG, error.getMessage());

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("packageNumber", packageNumber);
                    hashMap.put("key", API_KEY);
                    return hashMap;
                }
            };

            requestQueue.add(request);

        }



    }
}
