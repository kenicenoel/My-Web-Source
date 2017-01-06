package com.shipwebsource.mywebsource;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;


public class PackageEstimatorFragment extends Fragment
{
    private final String TAG = PackageEstimatorFragment.class.getSimpleName();
    private EditText packageValueTextview;
    private Spinner packageWeightTextview;
    private Spinner packageCategorySpinner;

    private TextView totalEstimateTextview;
    private TextView weightInKiloTextview;
    private TextView packageValueInTTDTextview;
    private View view;

    private Button performCalculation;

    private double freight;
    private double packageValue;
    private double dutyRate;
    private int insurance;

    private final double EXCHANCE_RATE = 6.82;
    private final double VAT_RATE = 0.125;
    private final double VAT_ON_LOCAL_HANDLING = 0.10;
    private final double ONLINE_PURCHASE_TAX_RATE = 0.07;

    private boolean weightChanged = false;
    private boolean valueEntered = false;
    private boolean categoryChosen = false;
    private HashMap<String, Integer> categoriesAndDutyMap;
    private HashMap<String, Double> weightHashMap;

    private double cif;
    private double duty;
    private double vat;
    private double opt;
    private double fuel;


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

        packageCategorySpinner = (Spinner) view.findViewById(R.id.spinner_itemCategory);
        packageWeightTextview = (Spinner) view.findViewById(R.id.spinner_estimatorValue);
        packageValueTextview = (EditText) view.findViewById(R.id.estimator_value);
        packageValueInTTDTextview = (TextView) view.findViewById(R.id.textview_itemValueInTTD);
        weightInKiloTextview = (TextView) view.findViewById(R.id.textview_itemWeightInKilo);
        performCalculation = (Button) view.findViewById(R.id.button_performEstimation);
        totalEstimateTextview = (TextView) view.findViewById(R.id.textview_totalEstimate);

        // Initialize the HashMaps with data
        setupCategoryHashMap();
        setupWeightHashMap();


        performCalculation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (weightChanged && valueEntered && categoryChosen)
                {
                    calculateCharges();
                }

                else
                {
                    Toast.makeText(getContext(), "Sorry. But you need to chose a weight, category and enter a packageValue.", Toast.LENGTH_LONG).show();
                }

            }
        });

        packageValueTextview.addTextChangedListener(new TextWatcher() {
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
                    packageValueInTTDTextview.setText("$"+moneyInTTD+ " TTD");
                    packageValue = money;
                    valueEntered = true;

                    insurance = 0;

                    // If the double packageValue is less than or equal to 100 then insurance is always 1 USD
                    if (money <= 100)
                    {
                        insurance = 1;
                    }

                    else
                    {
                        double sum = 0;

                        for (int i = 1; i < s.length(); i++)
                        {
                            String temp = Character.toString(s.charAt(i));
                            sum+= Integer.parseInt(temp);

                        }

                        // All the remaining digits were zero
                        if (sum == 0)
                        {
                            insurance = (int) money / 100;
                        }

                        // At least one other digit was greater than zero
                        else
                        {
                            insurance = (int) (money /100) + 1;
                        }
                    }

                    Log.d(TAG, "Package Value: "+ packageValue + "Insurance: "+insurance);


                }




            }
        });




        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.item_categories, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        packageCategorySpinner.setAdapter(adapter);

        packageCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String str = (String) parent.getItemAtPosition(position);
                if (!str.equals("Choose one"))
                {
                    // Set the duty rate equal to the matching Key in the hashmap
                    double rate = categoriesAndDutyMap.get(str);
                    dutyRate = rate / 100;
                    Log.d(TAG, "Category: "+ str + "DutyRate: "+dutyRate);

                    categoryChosen = true;

                }
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
        packageWeightTextview.setAdapter(adapterB);

        packageWeightTextview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String str = (String) parent.getItemAtPosition(position);

                if (!str.equals("Choose one"))
                {
                    double weightFromString = Double.parseDouble(String.valueOf(str));
                    double kg = weightFromString * 0.453592;
                    BigDecimal kgAsBigDecimal = new BigDecimal(kg);
                    kg = roundValue(kgAsBigDecimal);
                    weightInKiloTextview.setText(kg+ " Kg");
                    freight = 0;
                    weightChanged = true;


                        if (weightFromString == 0.5)
                        {
                            freight = 4.12;
                        }


                        else if (weightFromString >= 16)
                        {
                            freight = weightFromString * 3.12;
                        }


                        else
                        {
                            freight = weightHashMap.get(str);
                        }

                    Log.d(TAG, "Weight: "+weightFromString + "Freight: "+freight);


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void calculateCharges()
    {
        cif = calulateCIF();
        duty = calculateDuty();
        vat = calculateVAT();
        opt = calculateOnlinePurchaseTax();
        fuel = calculateFuel();



        double total = 0;
        if (dutyRate == 0)
        {
            duty = 0;
            total =  (opt + duty + vat + freight + fuel + insurance + VAT_ON_LOCAL_HANDLING);
        }

        else
        {
            total =  (opt + duty + vat + freight + fuel + insurance + VAT_ON_LOCAL_HANDLING);
        }

        Log.d(TAG, "Freight: "+ freight+ " Duty: "+ duty + " VAT: "+vat + " OPT: "+opt + " Fuel: "+fuel + " INS: "+insurance + " VOLH: "+VAT_ON_LOCAL_HANDLING);

        double totalInTTD = total * 6.82;
        BigDecimal sum = new BigDecimal(totalInTTD);
        totalInTTD = roundValue(sum);
        totalEstimateTextview.setText("$"+ totalInTTD+ " TTD");


    }

    private double calculateFuel()
    {
        return 0.17 * freight;
    }


    private double calculateOnlinePurchaseTax()
    {
        return ONLINE_PURCHASE_TAX_RATE * cif;
    }

    private double calculateVAT()
    {
        return VAT_RATE * (cif + duty + opt);
    }

    private double calculateDuty()
    {

        return dutyRate * cif;
    }

    private double calulateCIF()
    {
        return freight + insurance + packageValue;

    }


    private void setupCategoryHashMap()
    {
        categoriesAndDutyMap = new HashMap<String, Integer>();

        categoriesAndDutyMap.put("Alarm (Motor Vehicle)", 25);
        categoriesAndDutyMap.put("Album", 20);
        categoriesAndDutyMap.put("Amplifier", 30);
        categoriesAndDutyMap.put("Apparel (All clothing)", 20);
        categoriesAndDutyMap.put("Appliances", 20);
        categoriesAndDutyMap.put("Baby Play Pen", 20);
        categoriesAndDutyMap.put("Bag", 20);
        categoriesAndDutyMap.put("Bed Linen", 20);
        categoriesAndDutyMap.put("Bicycle", 20);
        categoriesAndDutyMap.put("Blank DVDs &amp; CDs", 0);
        categoriesAndDutyMap.put("Bluetooth Headset", 0);
        categoriesAndDutyMap.put("Books", 0);
        categoriesAndDutyMap.put("Calculator", 0);
        categoriesAndDutyMap.put("Camera", 20);
        categoriesAndDutyMap.put("Camera Lens", 20);
        categoriesAndDutyMap.put("Candles", 20);
        categoriesAndDutyMap.put("Car Parts", 30);
        categoriesAndDutyMap.put("CD ROM (Software)", 0);
        categoriesAndDutyMap.put("CDs", 0);
        categoriesAndDutyMap.put("CDs (Music)", 0);
        categoriesAndDutyMap.put("Cell Phones &amp; Accessories", 20);
        categoriesAndDutyMap.put("Christmas Lights", 20);
        categoriesAndDutyMap.put("Christmas Tree (Artificial)", 20);
        categoriesAndDutyMap.put("Coffee", 40);
        categoriesAndDutyMap.put("Computer Parts", 0);
        categoriesAndDutyMap.put("Computer Systems", 0);
        categoriesAndDutyMap.put("Computer Tower", 0);
        categoriesAndDutyMap.put("Computer Tower Case", 0);
        categoriesAndDutyMap.put("Cosmetics", 20);
        categoriesAndDutyMap.put("Costume Jewellery", 30);
        categoriesAndDutyMap.put("Digital Cameras", 20);
        categoriesAndDutyMap.put("Diving Gear", 10);
        categoriesAndDutyMap.put("DVDs", 20);
        categoriesAndDutyMap.put("Electronics", 20);
        categoriesAndDutyMap.put("Envelopes", 20);
        categoriesAndDutyMap.put("Ethernet Hub", 0);
        categoriesAndDutyMap.put("Fax and Line Modems", 0);
        categoriesAndDutyMap.put("Food Supplies", 20);
        categoriesAndDutyMap.put("Furniture", 20);
        categoriesAndDutyMap.put("Game Accessories", 20);
        categoriesAndDutyMap.put("Hand Held Tools", 0);
        categoriesAndDutyMap.put("Head Lights (Motor Vehicle)", 25);
        categoriesAndDutyMap.put("Headphones", 20);
        categoriesAndDutyMap.put("Ink Cartridges", 0);
        categoriesAndDutyMap.put("iPad (without sim slot) / Tablet PCs", 0);
        categoriesAndDutyMap.put("iPad(with sim slot)", 20);
        categoriesAndDutyMap.put("Ipods / Musical Storage Devices", 20);
        categoriesAndDutyMap.put("Jewellery", 30);
        categoriesAndDutyMap.put("Kitchen Accessories", 20);
        categoriesAndDutyMap.put("Lab Ware", 0);
        categoriesAndDutyMap.put("Laptops", 0);
        categoriesAndDutyMap.put("Medical Apparel", 10);
        categoriesAndDutyMap.put("Monitor (Computer)", 0);
        categoriesAndDutyMap.put("Monitor (Display)", 20);
        categoriesAndDutyMap.put("Mother Boards", 0);
        categoriesAndDutyMap.put("Musical Equipment", 10);
        categoriesAndDutyMap.put("Pet Food", 20);
        categoriesAndDutyMap.put("Plumbing", 0);
        categoriesAndDutyMap.put("Pressure Washer", 20);
        categoriesAndDutyMap.put("Printer (3 in one)", 0);
        categoriesAndDutyMap.put("Printer (none 3-in-one)", 0);
        categoriesAndDutyMap.put("Printer Cartridge", 0);
        categoriesAndDutyMap.put("Processors", 0);
        categoriesAndDutyMap.put("Projector Screen", 20);
        categoriesAndDutyMap.put("Remote Control", 0);
        categoriesAndDutyMap.put("Router", 0);
        categoriesAndDutyMap.put("Safety Helmet", 0);
        categoriesAndDutyMap.put("Shoes (walking or casual)", 20);
        categoriesAndDutyMap.put("Speakers", 20);
        categoriesAndDutyMap.put("Sunglasses", 20);
        categoriesAndDutyMap.put("Toys", 20);
        categoriesAndDutyMap.put("Video Games", 20);
        categoriesAndDutyMap.put("Vitamins &amp; Food Supplements", 20);
        categoriesAndDutyMap.put("Watches", 30);
    }


    private void setupWeightHashMap()
    {
        weightHashMap = new HashMap<String, Double>();

        weightHashMap.put("1", 4.87);
        weightHashMap.put("2", 8.49);
        weightHashMap.put("3", 10.86);
        weightHashMap.put("4", 13.48);
        weightHashMap.put("5", 16.60);
        weightHashMap.put("6", 19.72);
        weightHashMap.put("7", 22.84);
        weightHashMap.put("8", 25.96);
        weightHashMap.put("9", 29.08);
        weightHashMap.put("10", 32.20);
        weightHashMap.put("11", 35.32);
        weightHashMap.put("12", 38.44);
        weightHashMap.put("13", 41.56);
        weightHashMap.put("14", 44.68);
        weightHashMap.put("15", 47.80);


    }

    private double roundValue(BigDecimal d)
    {
        BigDecimal rounded = d.setScale(2, RoundingMode.HALF_UP);
        String result = String.valueOf(rounded);
        return Double.parseDouble(result);
    }
}
