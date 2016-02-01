package com.example.brad.fueltracker;

import android.view.View.OnClickListener;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/* MainActivity displays the fuel logs in a listView by using the ideas that
   lonelyTwitter used to display its tweet logs.
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {
    /* fio is an object that will be used to interact with the FileIO class, which
       is just used to loadFromFile, saveToFile, and to gain access to the fuelLogs.
     */
    private FileIO fio;
    // flogs gets the fuel logs from fio.
    private ArrayList<FLog> flogs;
    // Adapter is used to interact with the fuel logs as shown in the listView
    private ArrayAdapter<FLog> adapter;
    // The TAG is used for debugging purposes using Log.d
    private String TAG = "MainActivityTAG";

    // Variables for the views.
    private ListView oldFlogList;
    private Button addFuelLogButton;
    private TextView totalLogsCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load addFuelLogButton into memory.
        addFuelLogButton = (Button) findViewById(R.id.addFuelLogButton);
        // Set a listener for addFuelLogButton
        addFuelLogButton.setOnClickListener(this);

        // Load views into memory.
        oldFlogList = (ListView) findViewById(R.id.oldFlogList);
        totalLogsCost = (TextView) findViewById(R.id.totalLogsCost);
        Log.d(TAG,"Loaded Views in onCreate");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // If they click the addFuelLogButton, then launch the fuel log activity and log it.
            case R.id.addFuelLogButton:
                Log.d(TAG, "Starting the Add Fuel Log Intent");
                launchAddFuelLogActivity();
                break;

        }
    }

    public void launchAddFuelLogActivity()
    {
        /* Launch the Add Fuel Log Activity and set "pos" to -1 so that we know that
           the user has clicked to add a new fuel log(rather than edit a fuel log,
           which has a pos of 0..N-1 where N-1 is the final index of the listView.
         */
        Log.d(TAG, "launchAddFuelLogActivity Called -- Now launching the activity.");
        Intent fuelIntent = new Intent(this, AddFuelActivity.class);
        fuelIntent.putExtra("pos", -1);
        this.startActivity(fuelIntent);
        Log.d(TAG, "Launched Activity Fuel Intent");
    }

    @Override
    protected void onStart() {
        Log.d(TAG + "Cycle", "Called the onStart method for " + TAG);
        super.onStart();

        //  Make a new fio object (pass in this as the Context)
        fio = new FileIO(this);
        // Load from file so we can set up the fuel logs in the listView.
        fio.loadFromFile();

        // Set up the adapter with the listView.
        adapter = new ArrayAdapter<FLog>(this, R.layout.list_item, fio.getFLogsList());
        oldFlogList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // Get the total sum of all fuel logs and put it into the textView totalLogsCost.
        double fuelSum = fio.flogSum();
        String tempString = "Total Cost (All Logs):";
        tempString+= " $"+Double.toString(fuelSum);
        totalLogsCost.setText(tempString);

        //This is an anonymous inner class that is used for creating the listener of the listView.
        //Adapted from here http://www.ezzylearning.com/tutorial/handling-android-listview-onitemclick-event
        oldFlogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            /* If someone clicks on an item in the listView, then pass in the relevant index position
               of the listView item and then start the Activity */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddFuelActivity.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        Log.d(TAG + "Cycle", "Called the onResume method for " + TAG);
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        Log.d(TAG+"Cycle", "Called the onPause method for "+TAG);
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        Log.d(TAG + "Cycle", "Called the onStop method for " + TAG);
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG + "Cycle", "Called the onDestroy method for " + TAG);
        super.onDestroy();
        // The activity is about to be destroyed.
    }
}
