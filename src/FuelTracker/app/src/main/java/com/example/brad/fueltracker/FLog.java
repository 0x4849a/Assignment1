package com.example.brad.fueltracker;

import java.util.Date;

/**
 * Created by brad on 29/01/16.
 */
/* FLog is equivalent to Tweet in lonelyTwitter app. It stores all of the relevant data
   for each and every fuel log -- providing getters and setters for each fuel log field.

   We would implement Comparable incase we'd want to sort by date in the future, but I haven't implemented
   this feature completely because it's unnecessary as per the assignment specs.
 */
public class FLog  {
    // There's three different types of dates stored, which is somewhat ineffecient
    private Date dateStored;
    private String originalDate;
    private String dateEntered;

    // The rest of the types of data stored as per the fuel logs.
    private String stationEntered;
    private String fuelGradeEntered;
    private float unitCostEntered;
    private float fuelAmountEntered;
    private float fuelCostEntered;
    private float odometerEntered;

    public FLog(String date, String station, String odometer, String fuelGrade, String fuelAmount, String unitCost, String fuelCost)
    {
        // When they pass in a date, call parseDate and store the three different types of dates.
        parseDate(date);
        // Store the rest of the types of data.
        stationEntered = station;
        odometerEntered = Float.parseFloat(odometer);
        fuelGradeEntered = fuelGrade;
        fuelAmountEntered = Float.parseFloat(fuelAmount);
        unitCostEntered = Float.parseFloat(unitCost);
        fuelCostEntered = Float.parseFloat(fuelCost);
    }

    private void parseDate(String date)
    {
        /* Every single date needs to be stored in YYYY-MM-DD format or else it will break,
           so this needs to be validated ahead of time. */
        String [] parts = date.split("-");
        // originalDate is the original YYYY-MM-DD format -- it's used for Edit Cards
        originalDate = date;
        /* dateStored is the *actual* Date format of the date. Storing this could be useful if
           we ever wanted to sort the fuel logs by the date, so that's why I did it. */
        dateStored = new Date(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        /* I store the Month/Day normally, but for the year, I manually input it(parts[0]) because dateStored
           seems to be giving inaccurate years when I try to parse the strings.. */
        dateEntered = dateStored.toString().substring(0,10) + " "+parts[0];
    }

    /* This is what I would use to potentially sort fuel logs by date, but this feature was never
       implemented. */
    public int compareTo(FLog logA) {
        return this.getDateEntered().compareTo(logA.getDateEntered());
    }

    /* toString is necessary for the listView to parse each of its entries to String format.
   I tried to use the assigned specifications as per the number of decimal places by using the
   String.format("%.xf") where X is the number of decimal places to round to.
 */
    @Override
    public String toString() {
        return dateEntered + " , "+ stationEntered+ " , " + String.format("%.1f", odometerEntered) + "Km , " + fuelGradeEntered
                + " , " + String.format("%.3f",fuelAmountEntered) + "L , " + String.format("%.1f", unitCostEntered) + " Cents/L , $" + String.format("%.2f", fuelCostEntered);
    }

    /* Set up getters and setters for all of the fuel log fields */
    public void setDateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
    }

    public String getDateEntered()
    {
        return this.dateEntered;
    }

    public String getStationEntered() {
        return stationEntered;
    }

    public void setStationEntered(String stationEntered) {
        this.stationEntered = stationEntered;
    }

    public String getFuelGradeEntered() {
        return fuelGradeEntered;
    }

    public void setFuelGradeEntered(String fuelGradeEntered) {
        this.fuelGradeEntered = fuelGradeEntered;
    }

    public float getUnitCostEntered() {
        return unitCostEntered;
    }

    public void setUnitCostEntered(float unitCostEntered) {
        this.unitCostEntered = unitCostEntered;
    }

    public float getFuelAmountEntered() {
        return fuelAmountEntered;
    }

    public void setFuelAmountEntered(float fuelAmountEntered) {
        this.fuelAmountEntered = fuelAmountEntered;
    }

    public float getFuelCostEntered() {
        return fuelCostEntered;
    }

    public void setFuelCostEntered(float fuelCostEntered) {
        this.fuelCostEntered = fuelCostEntered;
    }

    public float getOdometerEntered() {
        return odometerEntered;
    }

    public void setOdometerEntered(float odometerEntered) {
        this.odometerEntered = odometerEntered;
    }

    public String getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(String originalDate) {
        parseDate(originalDate);
        //this.originalDate = originalDate;
    }
}
