package com.shipwebsource.mywebsource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class PreAlertFragment extends Fragment
{
    private View view;
    private RecyclerView recyclerViewPreAlertHistory;
    ArrayList<String> preAlerts;
    RelativeLayout preAlertHistoryLayout;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_prealert, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        recyclerViewPreAlertHistory = (RecyclerView) view.findViewById(R.id.recyclerview_preAlertHistory);
        preAlertHistoryLayout = (RelativeLayout) view.findViewById(R.id.layout_emptyView_preAlerts);
        preAlerts = new ArrayList<>();
        if (preAlerts.isEmpty())
        {
            preAlertHistoryLayout.setVisibility(View.VISIBLE);
            recyclerViewPreAlertHistory.setVisibility(View.INVISIBLE);
        }

        else
        {
            preAlertHistoryLayout.setVisibility(View.GONE);
            recyclerViewPreAlertHistory.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public void onResume()
    {
        super.onResume();
    }
}
