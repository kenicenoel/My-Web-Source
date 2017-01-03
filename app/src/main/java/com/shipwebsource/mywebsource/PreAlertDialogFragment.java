package com.shipwebsource.mywebsource;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class PreAlertDialogFragment extends DialogFragment
{
    private EditText supplier, value, trackingNum;
    private TextView textViewSupplier, textViewCategory, textViewValue, textViewShipper, textViewTrackingNum;
    private Spinner category, shipper;
    private Button cancel, save;


    private View view;

    public PreAlertDialogFragment()
    {
        // Required empty public constructor
    }

    public static PreAlertDialogFragment newInstance()
    {
        PreAlertDialogFragment frag = new PreAlertDialogFragment();
//        Bundle args = new Bundle();
//        args.putString("title", title);
//        frag.setArguments(args);
        return frag;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_pre_alert_dialog_frament, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        supplier = (EditText) view.findViewById(R.id.et_supplier);
        value = (EditText) view.findViewById(R.id.et_value);
        shipper = (Spinner) view.findViewById(R.id.spinner_shipper);
        trackingNum = (EditText) view.findViewById(R.id.et_trackingNumber);

        textViewCategory = (TextView) view.findViewById(R.id.label_category);
        textViewShipper = (TextView) view.findViewById(R.id.label_shipper);
        textViewSupplier = (TextView) view.findViewById(R.id.label_supplier);
        textViewTrackingNum = (TextView) view.findViewById(R.id.label_trackingNumber);
        textViewValue = (TextView) view.findViewById(R.id.label_value);

        category = (Spinner) view.findViewById(R.id.spinner_category);

        save = (Button) view.findViewById(R.id.button_SavePreAlert);
        cancel = (Button) view.findViewById(R.id.button_CancelPreAlert);

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });


        // Show soft keyboard automatically and request focus to field
        supplier.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }




}
