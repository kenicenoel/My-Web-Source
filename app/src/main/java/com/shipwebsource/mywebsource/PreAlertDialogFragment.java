package com.shipwebsource.mywebsource;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class PreAlertDialogFragment extends DialogFragment
{
    private static final double EXCHANCE_RATE = 6.82;
    private EditText supplier, value, trackingNum;
    private TextView textViewShipperMoreDetails, textViewValueInTTD;
    private Spinner category, shipper;
    private Button cancel, save;


    private View view;
    private double packageValue;
    private boolean valueEntered = false;
    private boolean categoryChosen;
    private String selectedCategory;
    private boolean shipperChosen;
    private String selectedShipper = "";

    private String trackingNumber;


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
        trackingNum = (EditText) view.findViewById(R.id.et_trackingNumber);

        shipper = (Spinner) view.findViewById(R.id.spinner_shipper);
        category = (Spinner) view.findViewById(R.id.spinner_category);


        textViewShipperMoreDetails = (TextView) view.findViewById(R.id.textview_spinner_shipper_moreDetails);
        textViewValueInTTD = (TextView) view.findViewById(R.id.textview_preAlert_itemValueInTTD);

        setUpSpinners();



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


        trackingNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (!s.toString().equals("Choose one"))
                {
                    trackingNumber = s.toString();

                }


            }
        });

        value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (!s.toString().equals(""))
                {
                    double money = Double.parseDouble(s.toString());
                    double moneyInTTD = money * EXCHANCE_RATE;
                    BigDecimal moneyInTTDAsBigDecimal = new BigDecimal(moneyInTTD);
                    moneyInTTD = roundValue(moneyInTTDAsBigDecimal);
                    textViewValueInTTD.setText("$"+moneyInTTD+ " TTD");
                    packageValue = money;
                    valueEntered = true;

                }

                else
                {
                    textViewValueInTTD.setText("$"+0+ " TTD");
                }




            }
        });



        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String str = (String) parent.getItemAtPosition(position);
                if (!str.equals("Choose one"))
                {
                    selectedCategory = str;
                    categoryChosen = true;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        shipper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String str = (String) parent.getItemAtPosition(position);
                if (!str.equals("Choose one"))
                {
                    selectedShipper = str;
                    if (selectedShipper.equals("FedEx") && trackingNumber.length() > 5)
                    {
                        switch (trackingNumber.length())
                        {
                            case 20:
                                textViewShipperMoreDetails.setText("Smart Post");
                                break;

//                            case 15:
//                                textViewShipperMoreDetails.setText("FedEx Ground (old)");
//                                break;

//                            case 12:
//                                textViewShipperMoreDetails.setText("FedEx Express (old)");
//                                break;

//                            case 14:
//                                textViewShipperMoreDetails.setText("Ground / Express (New)");
//                                break;

                            default:
                                textViewShipperMoreDetails.setText("");
                                break;
                        }


                    }
                    shipperChosen = true;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });























        // Show soft keyboard automatically and request focus to field
        supplier.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void setUpSpinners()
    {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterA = ArrayAdapter.createFromResource(getContext(), R.array.item_categories, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        category.setAdapter(adapterA);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterB = ArrayAdapter.createFromResource(getContext(), R.array.shippers, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        shipper.setAdapter(adapterB);
    }


    private double roundValue(BigDecimal d)
    {
        BigDecimal rounded = d.setScale(2, RoundingMode.HALF_UP);
        String result = String.valueOf(rounded);
        return Double.parseDouble(result);
    }


}
