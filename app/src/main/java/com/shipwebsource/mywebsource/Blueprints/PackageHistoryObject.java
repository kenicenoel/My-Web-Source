package com.shipwebsource.mywebsource.Blueprints;


public class PackageHistoryObject
{


    String packageNumber;
    String description;
    String shipper;
    String price;
    String status;

    public PackageHistoryObject()
    {

    }

    public PackageHistoryObject(String packageNumber, String description, String shipper, String price,String status)
    {


        this.packageNumber = packageNumber;
        this.description = description;
        this.shipper = shipper;
        this.price = price;
        this.status = status;
    }


    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShipper()
    {
        return shipper;
    }

    public void setShipper(String shipper)
    {
        this.shipper = shipper;
    }


    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }


}
