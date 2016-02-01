package com.example.brad.fueltracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by brad on 29/01/16.
 */
/* This activity is used to add fuel logs. It interacts with FileIO to do so. */
public class AddFuelActivity extends ActionBarActivity implements OnClickListener {
    /* This index is passed throguh an inttent whereby we determine if a listView index
        was clicked on or if instead "Add a Fuel Log" was clicked on. */
    int listViewIndex;

    /* Variables that are to be entered by the user. */
    private String date;
    private String station;
    private String odometer;
    private String fuelGrade;
    private String fuelAmount;
    private String unitCost;
    private String fuelCost;

    // A TAG that is used for debugging purposes.
    private String TAG = "AddFuelActivityTAG";
    // Local Views that are to be interacted with.
    private EditText editTextDate;
    private EditText editTextStation;
    private EditText editTextOdometer;
    private EditText editTextFuelGrade;
    private EditText editTextFuelAmount;
    private EditText editTextUnitCost;

    private TextView fuel_cost_label;
    private TextView fuel_cost_amount_label;

    private Button clear_all_label;
    private Button done_label;
    private Button cancel_label;

    // myFlogs is gotten from fio and is interacted with to access individual fuel logs.
    private ArrayList<FLog> myFlogs;
    private FileIO fio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfuel);

        // Set up the views in memory.
        fuel_cost_label = (TextView) findViewById((R.id.fuel_cost_label));
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextStation = (EditText) findViewById(R.id.editTextStation);
        editTextOdometer = (EditText) findViewById(R.id.editTextOdometer);
        editTextFuelGrade = (EditText) findViewById(R.id.editTextFuelGrade);
        editTextFuelAmount = (EditText) findViewById(R.id.editTextFuelAmount);
        editTextUnitCost = (EditText) findViewById(R.id.editTextUnitCost);
        fuel_cost_amount_label = (TextView) findViewById(R.id.fuel_cost_amount_label);

        clear_all_label = (Button) findViewById(R.id.clear_all_label);
        clear_all_label.setOnClickListener(this);

        done_label = (Button) findViewById(R.id.done_label);
        done_label.setOnClickListener(this);

        cancel_label = (Button) findViewById(R.id.cancel_label);
        cancel_label.setOnClickListener(this);

    }


    @Override
    protected void onStart() {
        Log.d(TAG, "Called the onStart method for "+TAG);
        super.onStart();
        // Create anew FileIO object and then load from file and load up the flogsList(myFlogs).
        fio = new FileIO(this);
        fio.loadFromFile();
        myFlogs = fio.getFLogsList();

        //If we get -1 from "pos" then it means that we were told to "Add a Fuel Log"
        //If we dont get -1 from "pos" then it means that we were told to "Edit a Fuel Log"
        listViewIndex = getIntent().getIntExtra("pos", -1);
        if (listViewIndex != -1)
        {
            changeActionBar();
            populateFields(listViewIndex);
        }
        // Hide fuel cost since we were told to add a fuel log
        else
        {
            hideFuelCost();
        }


        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        Log.d(TAG, "Called the onResume method for "+TAG);
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        Log.d(TAG, "Called the onPause method for "+TAG);
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        Log.d(TAG, "Called the onStop method for "+TAG);
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG, "Called the onDestroy method for "+TAG);
        super.onDestroy();
        // The activity is about to be destroyed.
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            //Cancel was called, so just go back to the previous activity without making any changes.
            case R.id.cancel_label:
                Log.d(TAG, "Going back to main without making changes.");
                Toast z = Toast.makeText(this, "Cancelling Creation of Current Log and Going Back", Toast.LENGTH_SHORT);
                z.show();
                resetViews();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.done_label:
                //Extract the text
                Log.d(TAG, "Clicked Done");

                date = textGrabber(editTextDate);
                Log.d(TAG, "Grabbed Date " + date);

                station = textGrabber(editTextStation);
                Log.d(TAG, "Grabbed Station " + station);

                odometer = textGrabber(editTextOdometer);
                Log.d(TAG, "Grabbed Odometer " + odometer);

                fuelGrade = textGrabber(editTextFuelGrade);
                Log.d(TAG, "Grabbed Fuel Grade " + fuelGrade);

                fuelAmount = textGrabber(editTextFuelAmount);
                Log.d(TAG, "Grabbed Fuel Amount " + fuelAmount);

                unitCost = textGrabber(editTextUnitCost);
                Log.d(TAG, "Grabbed Unit Cost " + unitCost);

                fuelCost = fuelCostAmount();
                //fuelCost = textGrabber(editTextFuelCost);
                //Log.d(TAG, "Grabbed FuelCost " + fuelCost);

                if (date == null || !validateDate(date) || station == null || odometer == null || fuelGrade == null || fuelAmount == null || unitCost == null)
                {
                    Log.d(TAG, "Displaying Toast for invalid qna input and breaking");
                    Toast t = Toast.makeText(this, "Please fill out all values properly and don't leave any blank.", Toast.LENGTH_SHORT);
                    t.show();
                    break;
                }

                Log.d(TAG, "Attempting to insert into storage");
                // Note that if listViewIndex is -1 then it means that we were told to add a new fuel log(not edit one)
                // The idea is that we will not go back after pressing done for "Add a Fuel Log" but we will for "Edit a Fuel Log".
                // So in this situation, the user presses done and can keep on adding more logs as they wish.
                if (listViewIndex == -1)
                {
                    //Create a new fuel log object.
                    FLog currentLog = new FLog(date, station, odometer, fuelGrade, fuelAmount, unitCost, fuelCost);
                    Log.d(TAG,"Adding Flog");
                    //Add the fuel log object to the fuel log list.
                    fio.addFlog(currentLog);

                    Log.d(TAG,"Calling Save in File");
                    fio.saveInFile();
                    Log.d(TAG, "Displaying Toast for successful insertion");
                    Toast t = Toast.makeText(this, "The fuel log was successfully created with a fuel cost of $" +fuelCostAmount(), Toast.LENGTH_SHORT);
                    Toast k = Toast.makeText(this, "Staying here until the back button is pressed on the phone.", Toast.LENGTH_SHORT);
                    t.show();
                    resetViews();
                    k.show();
                }
                else
                {
                    //Since we are just editing a fuel log, we will go back to the main activity after pressing done.
                    setResult(RESULT_OK);
                    //Change the field values.
                    changeFlog(date, station, odometer, fuelGrade, fuelAmount, unitCost, fuelCost);
                    Log.d(TAG,"Calling Save in File");
                    //Save the file after changing the values.
                    fio.saveInFile();
                    Log.d(TAG, "Displaying Toast for successful insertion");
                    Toast t = Toast.makeText(this, "The fuel log was successfully edited with a fuel cost of $" +fuelCostAmount(), Toast.LENGTH_SHORT);
                    t.show();
                    resetViews();
                    Intent intent2 = new Intent(AddFuelActivity.this, MainActivity.class);
                    startActivity(intent2);
                }

                break;

            case R.id.clear_all_label:
                //Since clear all is clicked on, we will need to clear all of the text in the views.
                Log.d(TAG, "Clear All Button Clicked");
                resetViews();
                break;
        }
    }

    //Generic method used to grab text from an EditText field
    //If the text is empty, we return null to indicate this.
    private String textGrabber(EditText field)
    {
        String stringToReturn = field.getText().toString();
        if (stringToReturn.length() == 0)
        {
            return null;
        }
        return stringToReturn;
    }

    //Generic method to clear the text by setting it to blank text.
    private void clear_text(EditText field)
    {
        field.setText("");
    }

    //Reset the views by calling clear_text on them all.
    private void resetViews()
    {
        clear_text(editTextDate);
        clear_text(editTextStation);
        clear_text(editTextOdometer);
        clear_text(editTextFuelGrade);
        clear_text(editTextFuelAmount);
        clear_text(editTextUnitCost);
        //clear_text(editTextFuelCost);
    }

    //Get the total fuel cost amount in dollars by doing fuel amount * unit cost / 100
    private String fuelCostAmount()
    {
        float fuelAm = Float.valueOf(fuelAmount);
        float unitCo = Float.valueOf(unitCost);
        float fuelCos = (fuelAm*unitCo)/100;

        return Float.toString(fuelCos);
    }

    //Populate the fields (incase we are editing a fuel log)
    private void populateFields(int listViewIndex)
    {
        editTextDate.setText(myFlogs.get(listViewIndex).getOriginalDate());
        editTextStation.setText(myFlogs.get(listViewIndex).getStationEntered());
        editTextOdometer.setText(Float.toString(myFlogs.get(listViewIndex).getOdometerEntered()));
        editTextFuelGrade.setText(myFlogs.get(listViewIndex).getFuelGradeEntered());
        editTextFuelAmount.setText(Float.toString(myFlogs.get(listViewIndex).getFuelAmountEntered()));
        editTextUnitCost.setText(Float.toString(myFlogs.get(listViewIndex).getUnitCostEntered()));
        fuel_cost_amount_label.setText(Float.toString(myFlogs.get(listViewIndex).getFuelCostEntered()));
    }

    //Instead of creating a new fuel log object, we will simply change the field values in case of
    //Edit a fuel log.
    private void changeFlog(String date, String station, String odometer, String fuelGrade, String fuelAmount, String unitCost, String fuelCost)
    {
        Log.d(TAG, "Request to changeFlog with listViewIndex as "+listViewIndex+"Date as "+date+" station as "+station+" odometer as "+odometer+" fuelGrade as "+fuelGrade+" fuelAmount as "+fuelAmount+" unitCost as "+unitCost+" fuelCost as "+fuelCost);
        myFlogs.get(listViewIndex).setOriginalDate(date);
        myFlogs.get(listViewIndex).setOdometerEntered(Float.parseFloat(odometer));
        myFlogs.get(listViewIndex).setFuelGradeEntered(fuelGrade);
        myFlogs.get(listViewIndex).setFuelAmountEntered(Float.parseFloat(fuelAmount));
        myFlogs.get(listViewIndex).setUnitCostEntered(Float.parseFloat(unitCost));
        myFlogs.get(listViewIndex).setFuelCostEntered(Float.parseFloat(fuelCost));
    }

    //Hide the fuel cost in case of add a fuel log by setting to label to empty text.
    private void hideFuelCost()
    {
        fuel_cost_label.setText("");
    }

    //Change the action bar title to Edit a Fuel Log incase of Edit.
    private void changeActionBar()
    {
        getSupportActionBar().setTitle("Edit a Fuel Log");
    }

    //Count the number of "-" in the date string.
    public int charCounter(String stringToTest)
    {
        int charCount = 0;
        for( int i=0; i< stringToTest.length(); i++ ) {
            if( stringToTest.charAt(i) == '-' ) {
                charCount++;
            }
        }
        return charCount;
    }

    //Adapted from http://stackoverflow.com/questions/12715246/how-to-check-if-a-character-in-a-string-is-a-digit-or-letter
    //Validate the Date -- Make sure that it's in the YYYY-MM-DD format.
    public boolean validateDate(String stringToTest) {
        if (stringToTest.length() != 10) {
            return false;
        }

        if (stringToTest.charAt(4) != '-' || stringToTest.charAt(7) != '-')
        {
            return false;
        }

        if (Character.isDigit(stringToTest.charAt(0)) && Character.isDigit(stringToTest.charAt(1))
        && Character.isDigit(stringToTest.charAt(2)) && Character.isDigit(stringToTest.charAt(3))
        && Character.isDigit(stringToTest.charAt(5)) && Character.isDigit(stringToTest.charAt(6))
        && Character.isDigit(stringToTest.charAt(8)) && Character.isDigit(stringToTest.charAt(9))){
            return true;
        }
        return false;
    }


}

