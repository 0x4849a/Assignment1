# bpharris-FuelTrack
Assignment1
Video can be found here : https://archive.org/details/20160201011841
Or
https://archive.org/download/20160201011841/2016-02-01-0118-41.flv

The app is designed so that a user can add any number of fuel logs at one time by clicking "Add a Fuel Log".
The app will only return back to the main activity where the fuel logs are shown if the user clicks the back button on their device.

On the other hand, if a user wishes to edit a pre-existing fuel log, they can do this by clicking on a fuel log inside of the MainActivity's ListView, and once they edit a pre-existing fuel log, they will automatically return back to the Main Activity, which is by design.

Assignment Specs:

You are to design and implement a simple, attractive, and easy-to-use fuel consumption tracking app, which maintains a basic log of fuel used for a particular car. A log entry shows the following information:

    date (entered, yyyy-mm-dd format, e.g., 2016-01-18)
    station (entered, textual, e.g., Costco)
    odometer reading (entered in km, numeric to 1 decimal place)
    fuel grade (entered, textual, e.g., regular)
    fuel amount (entered in L, numeric to 3 decimal places)
    fuel unit cost (entered in cents per L, numeric to 1 decimal place)
    fuel cost (computed, in dollars, to 2 decimal places)

The app allows the user to:

    view the log entries (newest addition appearing last)
    add a new entry (appends to the log)
    select and edit an existing entry
    cancel adding or editing an entry
    see the total overall fuel cost (computed, in dollars, to 2 decimal places), in the view of the log entries

Naturally, the computed values must be updated dynamically if values in the entries change.

The app must assist the user in proper data entry.

The app must be persistent; exiting and stopping the app should not lose data.