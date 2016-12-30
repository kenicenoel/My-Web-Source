package com.shipwebsource.mywebsource.Blueprints;


public class CustomsRatesObject
{


    String category;
    String duty;
    String vat;


    public CustomsRatesObject()
    {

    }

    public CustomsRatesObject(String category, String duty)
    {


        this.category = category;
        this.duty = duty;
        this.vat = "12.5";

    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getVat()
    {
        return vat;
    }

    public void setVat(String vat)
    {
        this.vat = vat;
    }



}
