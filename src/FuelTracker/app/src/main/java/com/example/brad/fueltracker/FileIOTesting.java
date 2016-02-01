package com.example.brad.fueltracker;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/* FileIO is the interface used for loading from and saving to a gson/json file.
   as well as the interface for accessing the fuellogs.
   It is used by MainActivity as well as AddFuelActivity.
   These methods are **only** used for testing purposes and are not called by any activity.*/

class FileIOTesting extends ActionBarActivity {
    //private ArrayAdapter<FLog> adapter;
    private ArrayList<FLog> flogs;
    private static final String FILENAME = "file.sav";

    //Context context;

    //Because we don't actually extend an activity -- we need to have a context to work with gson.


    //loadFromFile is basically copy-pasted from lonelyTwitter app
    public void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<FLog>>() {}.getType();
            flogs = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            flogs = new ArrayList<FLog>();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }

    //saveInFile is basically copy-pasted from lonelyTwitter app
    public void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(flogs, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    // Public interface to add a fuel log.
    public void addFlog(ArrayList<FLog> flogs, FLog flog)
    {
        flogs.add(flog);
    }

    // Public interface to remove a fuel log -- not used.
    public void removeFlog(ArrayList<FLog> flogs, FLog flog)
    {
        flogs.remove(flog);
    }

    // Public interface to get the flogs list.
    public ArrayList<FLog> getFLogsList()
    {
        return flogs;
    }

    // Public interface to sum up the total fuel logs cost.
    public double flogSum(ArrayList<FLog> flogs)
    {
        float fuelSum = 0;
        for (int i=0; i < flogs.size(); i++)
        {
            fuelSum += flogs.get(i).getFuelCostEntered();
        }
        return Math.round(fuelSum*100.0)/100.0;
    }
}

