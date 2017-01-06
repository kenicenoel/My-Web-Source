package com.shipwebsource.mywebsource;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shipwebsource.mywebsource.Adaptors.GenericListStringRecyclerViewAdaptor;
import com.shipwebsource.mywebsource.Blueprints.PackageObject;

import java.util.ArrayList;


public class MainMenuFragment extends Fragment
{
    private final String TAG = MainMenuFragment.class.getSimpleName();
    private GenericListStringRecyclerViewAdaptor adaptor;
    private RecyclerView recyclerViewPackageHistory;
    private RecyclerView recyclerViewIncomingPackages;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<PackageObject> history;
    private ArrayList<PackageObject> incoming;
    private RelativeLayout incomingPackagesRelativeLayout;

    private TextView footerCallUs;
    private TextView footerRequestShuttle;
    private TextView footerLoveUs;
    private TextView footerGetHelp;

    private TextView tagPreAlert;
    private TextView tagCustomsRates;
    private TextView tagPackageEstimator;
    private TextView loggedInUser;


    private static final String WEBSOURCE = "tel:18682854932";
    private View view;


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

        history = new ArrayList<>();
        incoming = new ArrayList<>();

        recyclerViewPackageHistory.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());

//        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
//        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
//        recyclerViewPackageHistory.addItemDecoration(dividerItemDecoration);

        recyclerViewPackageHistory.setLayoutManager(linearLayoutManager);
        adaptor = new GenericListStringRecyclerViewAdaptor(history);
        generateDummyData();
        recyclerViewPackageHistory.setAdapter(adaptor);

        if (incoming.isEmpty())
        {
            incomingPackagesRelativeLayout = (RelativeLayout) view.findViewById(R.id.layout_emptyView_incomingPackages);
            incomingPackagesRelativeLayout.setVisibility(View.VISIBLE);
            recyclerViewIncomingPackages.setVisibility(View.INVISIBLE);
        }

        else
        {
            incomingPackagesRelativeLayout.setVisibility(View.GONE);
            recyclerViewIncomingPackages.setVisibility(View.VISIBLE);
        }

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


}
