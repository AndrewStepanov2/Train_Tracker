package com.example.finalprojectcs125;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainActivity extends AppCompatActivity {

    /** RequestQueue for API calls. */
    private RequestQueue requestQueue;

    /** String to store the results of the API request. */
    private String jsonString;

    /** url to use in API call. */
    private String url;

    /** Arrival time for train. */
    private String arrivalTime;

    /** Where the train is headed. */
    private String towards;

    /** Name of the train line. */
    private String lineColor;

    /** String to record and display the parsed data from jsonString. */
    private String shortString = "";

    /** Causes the app to print the error message if the API call causes an error. */
    private boolean causedError = false;

    /** Json parser. */
    JsonParser parser;

    /** Json object to store the results. */
    JsonObject result;

    /** Starting Json object that contains all of the data. */
    JsonObject ctatt;

    /** Json array with all of the train data in it. */
    JsonArray eta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView text = findViewById(R.id.textView);
        requestQueue = Volley.newRequestQueue(this);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String API_KEY = "d37555ccc09141848543ab21e287b560";
                String currentStation = "";
                String station = parent.getItemAtPosition(position).toString();
                if (station.equals("Belmont")) {
                    currentStation="41320";
                }
                if (station.equals("Clark/Lake")) {
                    currentStation = "40380";
                }
                if (station.equals("18th")) {
                    currentStation = "40830";
                }
                if (station.equals("35th/Archer")) {
                    currentStation = "40120";
                }
                if (station.equals("35-Bronzeville-IIT")) {
                    currentStation = "41120";
                }
                if (station.equals("43rd")) {
                    currentStation = "41270";
                }
                if (station.equals("47th")) {
                    currentStation = "41080";
                }
                if (station.equals("51st")) {
                    currentStation = "40130";
                }
                if (station.equals("54th/Cermak")) {
                    currentStation = "40580";
                }
                if (station.equals("63rd")) {
                    currentStation = "40910";
                }
                if (station.equals("69th")) {
                    currentStation = "40990";
                }
                if (station.equals("79th")) {
                    currentStation = "40240";
                }
                if (station.equals("87th")) {
                    currentStation = "41430";
                }
                if (station.equals("95th/Dan Ryan")) {
                    currentStation = "40450";
                }
                if (station.equals("Adams/Wabash")) {
                    currentStation = "40680";
                }
                if (station.equals("Addison")) {
                    currentStation = "41240";
                }
                if (station.equals("Argyle")) {
                    currentStation = "41200";
                }
                if (station.equals("Armitage")) {
                    currentStation = "40660";
                }
                if (station.equals("Ashland")) {
                    currentStation = "40170";
                }
                if (station.equals("Austin")) {
                    currentStation = "41260";
                }
                if (station.equals("Berwyn")) {
                    currentStation = "40340";
                }
                if (station.equals("Bryn Mawr")) {
                    currentStation = "41380";
                }
                if (station.equals("California")) {
                    currentStation = "40570";
                }
                if (station.equals("Central")) {
                    currentStation = "40280";
                }
                url = "http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?key=" + API_KEY + "&mapid=" + currentStation + "&outputType=JSON&max=3";
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        ((Button) findViewById(R.id.button)).setOnClickListener((v) -> {
            process(requestQueue, url);
            text.setText(shortString);
            shortString = "";
        });
    }

    /**
     * Function to make the API call.
     *
     * @param requestQueue RequestQueue to make the API call
     * @param url url to use in API call
     */
    public void process(RequestQueue requestQueue, String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                jsonString = response;
                parser = new JsonParser();
                result = parser.parse(jsonString).getAsJsonObject();
                ctatt = result.get("ctatt").getAsJsonObject();
                eta = ctatt.get("eta").getAsJsonArray();
                for (int i = 0; i < 3; i++) {
                    JsonObject etaArray = eta.get(i).getAsJsonObject();
                    arrivalTime = etaArray.getAsJsonPrimitive("arrT").
                            getAsString();
                    towards = etaArray.getAsJsonPrimitive("destNm").getAsString();
                    lineColor = etaArray.getAsJsonPrimitive("rt").getAsString();
                    if (lineColor.equals("G")) {
                        lineColor = "Green Line";
                    }
                    if (lineColor.equals("Red")) {
                        lineColor = "Red Line";
                    }
                    if (lineColor.equals("Blue")) {
                        lineColor = "Blue Line";
                    }
                    if (lineColor.equals("Brn")) {
                        lineColor = "Brown Line";
                    }
                    if (lineColor.equals("Org")) {
                        lineColor = "Orange Line";
                    }
                    if (lineColor.equals("P")) {
                        lineColor = "Purple line";
                    }
                    if (lineColor.equals("Pink")) {
                        lineColor = "Pink Line";
                    }
                    if (lineColor.equals("Y")) {
                        lineColor = "Yellow Line";
                    }
                    shortString = shortString + "Headed To: " + towards + "\n Route: " + lineColor +
                            "\n Arrival Time: " + arrivalTime.substring(11, 19) + "\n \n";
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonString = error.getMessage();
                causedError = true;
            }
        });
        requestQueue.add(stringRequest);
        if (causedError) {
            shortString = jsonString;
            causedError = false;
            return;
        }
    }
}
