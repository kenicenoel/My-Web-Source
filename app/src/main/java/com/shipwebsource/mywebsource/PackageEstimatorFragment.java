package com.shipwebsource.mywebsource;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class PackageEstimatorFragment extends Fragment
{
    private EditText packageValue;
    private Spinner packageWeight;
    private Spinner packageCategory;

    private TextView total;
    private TextView weightInKilo;
    private TextView packageValueInTTD;
    private View view;


    public PackageEstimatorFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_package_estimator, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        packageCategory = (Spinner) view.findViewById(R.id.spinner_itemCategory);
        packageWeight = (Spinner) view.findViewById(R.id.spinner_estimatorValue);
        packageValue = (EditText) view.findViewById(R.id.estimator_value);
        packageValueInTTD = (TextView) view.findViewById(R.id.textview_itemValueInTTD);
        weightInKilo = (TextView) view.findViewById(R.id.textview_itemWeightInKilo);

        packageValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (s.length() == 0)
                {
                    packageValue.setText(0);
                }
                double money = Double.parseDouble(packageValue.getText().toString()) * 7;
                packageValueInTTD.setText("$"+money+ " TTD");

            }
        });




        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.item_categories, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        packageCategory.setAdapter(adapter);

        packageCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String str = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterB = ArrayAdapter.createFromResource(getContext(), R.array.item_weight, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        packageWeight.setAdapter(adapterB);

        packageWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String str = (String) parent.getItemAtPosition(position);

                if (str.equals("") || str.equals("Choose one"))
                {
                    str = "0";
                }
                Double kg = Double.parseDouble(String.valueOf(str)) * 0.453592;
                weightInKilo.setText(kg+ " Kg");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
