package com.example.brad.fueltracker;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import java.util.ArrayList;

/* I performed some tests here.. However the large majority of tests were performed via the GUI
   It's also difficult for me to test objects from FileIO because it requires a context, so I had
   to create a FileIOTesting class that is used for testing purposes only.
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2 {

    public ApplicationTest() {
        super(AddFuelActivity.class);
    }

    public void testAddingFuelLog()
    {

       // testFlogs = fio.getFLogsList();
        //System.out.println(testFlogs.size());
        //System.out.println("The size is "+fio.flogSum());

        //String date, String station, String odometer, String fuelGrade, String fuelAmount, String unitCost, String fuelCost)
        FLog flog = new FLog("2016-01-18", "Costco", "2.5", "Regular", "40.234", "79.4","3194.5796");
        assertTrue(flog.getStationEntered() == "Costco");
        assertTrue(flog.getOriginalDate() == "2016-01-18");
        assertTrue(flog.getFuelGradeEntered() == "Regular");
        float tempFloat = Float.parseFloat("40.234");
        assertTrue(flog.getFuelAmountEntered() == tempFloat);
        float tempFloat2 = Float.parseFloat("79.4");
        assertTrue(flog.getUnitCostEntered() == tempFloat2);
        float tempFloat3 = Float.parseFloat("3194.5796");
        assertTrue(flog.getFuelCostEntered() == tempFloat3);

        flog.setOriginalDate("2015-01-10");
        assertTrue(flog.getOriginalDate() == "2015-01-10");
        flog.setFuelGradeEntered("Diesel");
        assertTrue(flog.getFuelGradeEntered() == "Diesel");
        flog.setOdometerEntered(Float.parseFloat("23.5"));
        assertTrue(flog.getOdometerEntered() == Float.parseFloat("23.5"));
        flog.setStationEntered("McDonalds");
        assertTrue(flog.getStationEntered() == "McDonalds");
        flog.setFuelAmountEntered(Float.parseFloat("40.50"));
        assertTrue(flog.getFuelAmountEntered() == Float.parseFloat("40.50"));
        flog.setUnitCostEntered(Float.parseFloat("22222"));
        assertTrue(flog.getUnitCostEntered() == Float.parseFloat("22222"));
        flog.setFuelCostEntered(Float.parseFloat("212"));
        assertTrue(flog.getFuelCostEntered() == Float.parseFloat("212"));
    }

    public void testFileIOMethods()
    {
        FileIOTesting fio = new FileIOTesting();
        ArrayList<FLog> testFlogs = new ArrayList<FLog>();

        FLog flog = new FLog("2016-01-18", "Costco", "2.5", "Regular", "40.234", "79.4","5");
        testFlogs.add(flog);
        FLog flog2 = new FLog("2016-01-18", "Costco", "2.5", "Regular", "40.234", "79.4","10");
        testFlogs.add(flog2);

        assertTrue(fio.flogSum(testFlogs) == Float.parseFloat("15"));
        fio.addFlog(testFlogs, new FLog("2016-01-18", "Costco", "2.5", "Regular", "40.234", "79.4", "6"));
        assertTrue(fio.flogSum(testFlogs) == Float.parseFloat("21"));
        fio.removeFlog(testFlogs, flog);
        assertTrue(fio.flogSum(testFlogs) == Float.parseFloat("16"));
    }

    public void testAddFuelActivityMethods()
    {
        AddFuelActivity fuelAct = new AddFuelActivity();
        assertTrue(fuelAct.validateDate("2010-10-10"));
        assertFalse(fuelAct.validateDate("2010-10.10"));
        assertFalse(fuelAct.validateDate("2010.10.10"));
        assertFalse(fuelAct.validateDate("2010.10-10"));
        assertFalse(fuelAct.validateDate("20100.10.10"));
        assertFalse(fuelAct.validateDate("20100-10-10"));
        assertFalse(fuelAct.validateDate("2010-100-10"));
        assertFalse(fuelAct.validateDate("2010-10-100"));
        assertFalse(fuelAct.validateDate("210-10-100"));
        assertFalse(fuelAct.validateDate("210-10-10"));
        assertFalse(fuelAct.validateDate("2010-1-1"));
        assertFalse(fuelAct.validateDate("2010-10-1"));
        assertFalse(fuelAct.validateDate("2010-1-10"));

        assertTrue(fuelAct.charCounter("2010-10-10") == 2);
        assertTrue(fuelAct.charCounter("2010-10.10") == 1);
        assertTrue(fuelAct.charCounter("2010.10.10") == 0);
    }

}