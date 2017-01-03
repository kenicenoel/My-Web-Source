package com.shipwebsource.mywebsource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class PreAlertFragment extends Fragment
{
    private final String TAG = PreAlertFragment.class.getSimpleName();
    private View view;
    private RecyclerView recyclerViewPreAlertHistory;
    private ArrayList<String> preAlerts;
    private RelativeLayout preAlertHistoryLayout;
    private Button createPreAlert;

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
        createPreAlert = (Button) view.findViewById(R.id.button_CreatePreAlert);

        createPreAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showCreatePreAlertDialog();
            }
        });

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

    private void showCreatePreAlertDialog()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        PreAlertDialogFragment preAlertDialogFragment = PreAlertDialogFragment.newInstance();
        preAlertDialogFragment.show(fm, TAG);

    }



    @Override
    public void onResume()
    {
        super.onResume();
    }
}
