package com.shipwebsource.mywebsource;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shipwebsource.mywebsource.Adaptors.FeaturedCardsRecyclerviewAdaptor;
import com.shipwebsource.mywebsource.Adaptors.PackageListRecyclerViewAdaptor;
import com.shipwebsource.mywebsource.Blueprints.FeaturedCard;
import com.shipwebsource.mywebsource.Blueprints.PackageObject;
import com.shipwebsource.mywebsource.Helpers.SettingsBuddy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainMenuFragment extends Fragment implements PackageListRecyclerViewAdaptor.ClickListener
{
    private final String TAG = MainMenuFragment.class.getSimpleName();
    private PackageListRecyclerViewAdaptor packageHistoryAdaptor;
    private PackageListRecyclerViewAdaptor incomingPackagesAdaptor;
    private FeaturedCardsRecyclerviewAdaptor cardsRecyclerviewAdaptor;
    private RecyclerView recyclerViewPackageHistory;
    private RecyclerView recyclerViewIncomingPackages;
    private RecyclerView recyclerViewFeaturedCards;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<PackageObject> history;
    private ArrayList<PackageObject> incoming;
    private ArrayList<FeaturedCard> featuredCards;
    private RelativeLayout packageHistorylayout;
    private RelativeLayout incomingPackagesRelativeLayout;

    private TextView footerCallUs;
    private TextView footerRequestShuttle;
    private TextView footerLoveUs;
    private TextView footerGetHelp;

    private TextView tagPreAlert;
    private TextView tagCustomsRates;
    private TextView tagPackageEstimator;
    private TextView loggedInUser;

    private GridLayoutManager gridLayoutManager;

    private final String URL = "http://noel.netau.net/webservice/get_package_history.php";
    private final String URL_INCOMING = "http://noel.netau.net/webservice/get_incoming_packages.php";


    private static final String WEBSOURCE = "tel:18682854932";
    private View view;
    private RequestQueue requestQueue;
    private LinearLayoutManager linearLayoutManagerB;
    private int currentPosition;
    private int previousPosition;


    public MainMenuFragment()
    {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        return  view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        footerCallUs = (TextView) view.findViewById(R.id.footer_callUs);
        footerRequestShuttle = (TextView) view.findViewById(R.id.footer_shuttle);
        footerGetHelp = (TextView) view.findViewById(R.id.footer_help);
        footerLoveUs = (TextView)view.findViewById(R.id.footer_loveUs);





        tagPreAlert = (TextView) view.findViewById(R.id.tag_pre_alerts);
        tagCustomsRates = (TextView) view.findViewById(R.id.tag_customs_rates);
        tagPackageEstimator = (TextView) view.findViewById(R.id.tag_package_estimator);

        recyclerViewPackageHistory = (RecyclerView) view.findViewById(R.id.recyclerview_packageHistory);
        recyclerViewIncomingPackages = (RecyclerView) view.findViewById(R.id.recyclerview_incomingPackages);
        recyclerViewFeaturedCards = (RecyclerView) view.findViewById(R.id.recyleriew_FeaturedCards);

        packageHistorylayout = (RelativeLayout) view.findViewById(R.id.layout_emptyView_packageHistory);
        incomingPackagesRelativeLayout = (RelativeLayout) view.findViewById(R.id.layout_emptyView_incomingPackages);


        history = new ArrayList<>();
        incoming = new ArrayList<>();
        featuredCards = new ArrayList<>();

        recyclerViewPackageHistory.setHasFixedSize(true);
        recyclerViewIncomingPackages.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManagerB = new LinearLayoutManager(getContext());



        recyclerViewPackageHistory.setLayoutManager(linearLayoutManager);
        recyclerViewIncomingPackages.setLayoutManager(linearLayoutManagerB);
        recyclerViewFeaturedCards.setLayoutManager(new GridLayoutManager(getContext(), 3));

        packageHistoryAdaptor = new PackageListRecyclerViewAdaptor(history);
        incomingPackagesAdaptor = new PackageListRecyclerViewAdaptor(incoming);
        cardsRecyclerviewAdaptor = new FeaturedCardsRecyclerviewAdaptor(featuredCards);
        packageHistoryAdaptor.setClickListener(this);
        incomingPackagesAdaptor.setClickListener(this);

        Drawable a = getResources().getDrawable(R.drawable.bg_ad_always_a_deal);
        Drawable b = getResources().getDrawable(R.drawable.bg_ad_websource_love);
        Drawable c = getResources().getDrawable(R.drawable.bg_ad_just_shop);

        FeaturedCard featuredCard = new FeaturedCard(c);
        FeaturedCard featuredCard2 = new FeaturedCard(b);
        FeaturedCard featuredCard3 = new FeaturedCard(a);

        featuredCards.add(featuredCard);
        featuredCards.add(featuredCard2);
        featuredCards.add(featuredCard3);

        recyclerViewPackageHistory.setAdapter(packageHistoryAdaptor);
        recyclerViewFeaturedCards.setAdapter(cardsRecyclerviewAdaptor);
        recyclerViewIncomingPackages.setAdapter(incomingPackagesAdaptor);


//        generateDummyData();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                getPackageHistory();
            }
        })
        {

        }.start();


        new Thread(new Runnable() {
            @Override
            public void run()
            {
                getIncomingPackages();
            }
        }).start();


        footerCallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

        footerRequestShuttle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestShuttleFragment requestShuttleFragment = new RequestShuttleFragment();

                FragmentTransaction fragmentTransaction = setupFragmentTransactionWithSlideAnimations();
                fragmentTransaction.replace(R.id.masterSinglePane, requestShuttleFragment, "Request Shuttle").addToBackStack(TAG);
                fragmentTransaction.commit();



            }
        });

        footerGetHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetHelpFragment getHelpFragment = new GetHelpFragment();

                FragmentTransaction fragmentTransaction = setupFragmentTransactionWithSlideAnimations();
                fragmentTransaction.replace(R.id.masterSinglePane, getHelpFragment, "Get Help").addToBackStack(TAG);
                fragmentTransaction.commit();



            }
        });

        tagPreAlert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PreAlertFragment preAlertFragment = new PreAlertFragment();
                FragmentTransaction fragmentTransaction = setupFragmentTransactionWithSlideAnimations();
                fragmentTransaction.replace(R.id.masterSinglePane, preAlertFragment, "Manage Pre-alerts").addToBackStack(TAG);
                fragmentTransaction.commit();

            }
        });


        tagPackageEstimator.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PackageEstimatorFragment packageEstimatorFragment = new PackageEstimatorFragment();
                FragmentTransaction fragmentTransaction = setupFragmentTransactionWithSlideAnimations();
                fragmentTransaction.replace(R.id.masterSinglePane, packageEstimatorFragment, "Package Estimator").addToBackStack(TAG);
                fragmentTransaction.commit();
            }
        });


        tagCustomsRates.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CustomsRatesFragment customsRatesFragment = new CustomsRatesFragment();
                FragmentTransaction fragmentTransaction = setupFragmentTransactionWithSlideAnimations();
                fragmentTransaction.replace(R.id.masterSinglePane, customsRatesFragment, "Customs Rates").addToBackStack(TAG);
                fragmentTransaction.commit();
            }
        });

    }

    public void call()
    {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse(WEBSOURCE));
        startActivity(callIntent);
    }

    private void generateDummyData()
    {

        PackageObject object = new PackageObject("HAWB0000000029510012", "Kitchen Utensils", "Amazon", "100.50", "Delivered");
        history.add(object);

        PackageObject object2 = new PackageObject("HAWB0000000029510013", "Apparel", "EBAY", "80.30", "Delivered");
        history.add(object2);

        PackageObject object3 = new PackageObject("HAWB0000000029510014", "Toys", "USPS", "250.99", "Delivered");
        history.add(object3);

        PackageObject object4 = new PackageObject("HAWB0000000029510015", "Video Game", "FedEx", "41.75", "Delivered");
        history.add(object4);

        PackageObject object5 = new PackageObject("HAWB0000000029510016", "Laptop/ TV stick", "DHL", "104.50", "Delivered");
        history.add(object5);

//        PackageObject object6 = new PackageObject("HAWB0000000029510017", "Cell Parts", "Canada Post", "53.17", "Delivered");
//        history.add(object6);
//
//        PackageObject object7 = new PackageObject("HAWB0000000029510018", "Book/ Home Accessories", "UPS", "1018", "Delivered");
//        history.add(object7);







    }

    public FragmentTransaction setupFragmentTransactionWithSlideAnimations()
    {
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.setCustomAnimations(R.anim.slide_left_enter,
                R.anim.slide_left_exit,
                R.anim.slide_right_enter,
                R.anim.slide_right_exit);

        return t;

    }


    private void getPackageHistory()
    {

            SettingsBuddy buddy = SettingsBuddy.getInstance(getContext());
            final String accountNumber = buddy.getData("AccountNumber");
            requestQueue = Volley.newRequestQueue(getContext());


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
                            JSONArray packageHistory = jsonObject.getJSONArray("package_history");
                            for (int i = 0; i < packageHistory.length(); i++)
                            {
                                JSONObject packages = packageHistory.getJSONObject(i);
                                String packageNumber = packages.getString("packageNumber");
                                String desc = packages.getString("description");
                                String shipper = packages.getString("shipper");
                                String status = packages.getString("status");
                                String cost = packages.getString("cost");
                                String timestamp = packages.getString("timestamp");

                                PackageObject obj = new PackageObject( packageNumber, desc, shipper, cost, status);
                                history.add(obj);

                            }
                            packageHistoryAdaptor.notifyDataSetChanged();
                            if (history.isEmpty())
                            {
                                packageHistorylayout.setVisibility(View.VISIBLE);
                                recyclerViewPackageHistory.setVisibility(View.INVISIBLE);
                            }

                            else
                            {
                                packageHistorylayout.setVisibility(View.GONE);
                                recyclerViewPackageHistory.setVisibility(View.VISIBLE);
                            }


                            Log.d(TAG, response);


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
                        hashMap.put("accountNumber", accountNumber);
                        return hashMap;
                    }
                };

                requestQueue.add(request);




    }







    private void getIncomingPackages()
    {

        SettingsBuddy buddy = SettingsBuddy.getInstance(getContext());
        final String accountNumber = buddy.getData("AccountNumber");
        requestQueue = Volley.newRequestQueue(getContext());



                // Incoming packages
                StringRequest request = new StringRequest(Request.Method.POST, URL_INCOMING, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d(TAG, "JSON Object Response");
                        try
                        {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray packageHistory = jsonObject.getJSONArray("incoming_packages");
                            for (int i = 0; i < packageHistory.length(); i++)
                            {
                                JSONObject packages = packageHistory.getJSONObject(i);
                                String packageNumber = packages.getString("packageNumber");
                                String desc = packages.getString("description");
                                String shipper = packages.getString("shipper");
                                String status = packages.getString("status");
                                String cost = packages.getString("cost");
                                String timestamp = packages.getString("timestamp");

                                PackageObject obj = new PackageObject( packageNumber, desc, shipper, cost, status);
                                incoming.add(obj);

                            }
                            incomingPackagesAdaptor.notifyDataSetChanged();
                            if (incoming.isEmpty())
                            {
                                incomingPackagesRelativeLayout.setVisibility(View.VISIBLE);
                                recyclerViewIncomingPackages.setVisibility(View.INVISIBLE);
                            }

                            else
                            {
                                incomingPackagesRelativeLayout.setVisibility(View.GONE);
                                recyclerViewIncomingPackages.setVisibility(View.VISIBLE);
                            }

                            Log.d(TAG, response);




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
                        hashMap.put("accountNumber", accountNumber);
                        return hashMap;
                    }
                };

                requestQueue.add(request);


            }


    @Override
    public void onItemClick(int position, int previousPostion, View v)
    {
        TextView pNum = (TextView) v.findViewById(R.id.layout_packageNumber);

        String packageNumber = pNum.getText().toString();
        currentPosition = position;
        previousPosition = previousPostion;

        PackageDetailsFragment fragment = PackageDetailsFragment.newInstance(packageNumber);
        FragmentTransaction ft = setupFragmentTransactionWithSlideAnimations();
        ft.replace(R.id.masterSinglePane, fragment, "PackageDetailsFragment").addToBackStack(TAG);
        ft.commit();

    }



}
