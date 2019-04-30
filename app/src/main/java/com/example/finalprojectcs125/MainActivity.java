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
                if (station.equals("18th")) {
                    currentStation = "40830";
                } else if (station.equals("35th/Archer")) {
                    currentStation = "40120";
                } else if (station.equals("35-Bronzeville-IIT")) {
                    currentStation = "41120";
                } else if (station.equals("43rd")) {
                    currentStation = "41270";
                } else if (station.equals("47th Green Line")) {
                    currentStation = "41080";
                } else if (station.equals("47th Red Line")) {
                    currentStation = "41230";
                } else if (station.equals("51st")) {
                    currentStation = "40130";
                } else if (station.equals("54th/Cermak")) {
                    currentStation = "40580";
                } else if (station.equals("63rd")) {
                    currentStation = "40910";
                } else if (station.equals("69th")) {
                    currentStation = "40990";
                } else if (station.equals("79th")) {
                    currentStation = "40240";
                } else if (station.equals("87th")) {
                    currentStation = "41430";
                } else if (station.equals("95th/Dan Ryan")) {
                    currentStation = "40450";
                } else if (station.equals("Adams/Wabash")) {
                    currentStation = "40680";
                } else if (station.equals("Addison Blue Line")) {
                    currentStation = "41240";
                } else if (station.equals("Addison Brown Line")) {
                    currentStation = "41440";
                } else if (station.equals("Addison Red Line")) {
                    currentStation = "41420";
                } else if (station.equals("Argyle")) {
                    currentStation = "41200";
                } else if (station.equals("Armitage")) {
                    currentStation = "40660";
                } else if (station.equals("Ashland Green and Pink Lines")) {
                    currentStation = "40170";
                } else if (station.equals("Ashland Orange Line")) {
                    currentStation = "41060";
                } else if (station.equals("Austin Blue Line")) {
                    currentStation = "40010";
                } else if (station.equals("Austin Green Line")) {
                    currentStation = "41260";
                } else if (station.equals("Belmont Red Brown Purple Lines")) {
                    currentStation="41320";
                } else if (station.equals("Belmont Blue Line")) {
                    currentStation="40060";
                } else if (station.equals("Berwyn")) {
                    currentStation = "40340";
                } else if (station.equals("Bryn Mawr")) {
                    currentStation = "41380";
                } else if (station.equals("California")) {
                    currentStation = "40570";
                } else if (station.equals("Central")) {
                    currentStation = "40280";
                } else if (station.equals("Central Park")) {
                    currentStation = "40780";
                } else if (station.equals("Cermak-Chinatown")) {
                    currentStation = "41000";
                } else if (station.equals("Cermak-McCormick Place")) {
                    currentStation = "41690";
                } else if (station.equals("Chicago")) {
                    currentStation = "41410";
                } else if (station.equals("Cicero")) {
                    currentStation = "40970";
                } else if (station.equals("Clark/Division")) {
                    currentStation = "40630";
                } else if (station.equals("Clark/Lake")) {
                    currentStation = "40380";
                } else if (station.equals("Clinton")) {
                    currentStation = "40430";
                } else if (station.equals("Conservatory")) {
                    currentStation = "41670";
                } else if (station.equals("Cottage Grove")) {
                    currentStation = "40720";
                } else if (station.equals("Cumberland")) {
                    currentStation = "40230";
                } else if (station.equals("Damen/Milwaukee")) {
                    currentStation = "40590";
                } else if (station.equals("Damen")) {
                    currentStation = "40090";
                } else if (station.equals("Davis")) {
                    currentStation = "40050";
                } else if (station.equals("Dempster")) {
                    currentStation = "40690";
                } else if (station.equals("Dempster-Skokie")) {
                    currentStation = "40140";
                } else if (station.equals("Diversey")) {
                    currentStation = "40530";
                } else if (station.equals("Division")) {
                    currentStation = "40320";
                } else if (station.equals("ForestPark")) {
                    currentStation = "40390";
                } else if (station.equals("Foster")) {
                    currentStation = "40520";
                } else if (station.equals("Francisco")) {
                    currentStation = "40870";
                } else if (station.equals("Fullerton")) {
                    currentStation = "41220";
                } else if (station.equals("Garfield")) {
                    currentStation = "40510";
                } else if (station.equals("Grand/Milwaukee")) {
                    currentStation = "40490";
                } else if (station.equals("Grand/State")) {
                    currentStation = "40330";
                } else if (station.equals("Granville")) {
                    currentStation = "40760";
                } else if (station.equals("Halsted/63rd")) {
                    currentStation = "40940";
                } else if (station.equals("Halsted")) {
                    currentStation = "41130";
                } else if (station.equals("Harlem")) {
                    currentStation = "40980";
                } else if (station.equals("Harold W. Library")) {
                    currentStation = "40850";
                } else if (station.equals("Harrison")) {
                    currentStation = "41490";
                } else if (station.equals("Howard")) {
                    currentStation = "40900";
                } else if (station.equals("Illinois Medical District")) {
                    currentStation = "40810";
                } else if (station.equals("Indiana")) {
                    currentStation = "40300";
                } else if (station.equals("Irving Park")) {
                    currentStation = "40550";
                } else if (station.equals("Jackson/Dearborn")) {
                    currentStation = "40070";
                } else if (station.equals("Jackson/State")) {
                    currentStation = "40560";
                } else if (station.equals("Jarvis")) {
                    currentStation = "41190";
                } else if (station.equals("Jefferson Park")) {
                    currentStation = "41280";
                } else if (station.equals("Kedzie")) {
                    currentStation = "41180";
                } else if (station.equals("Kedzie-Horman")) {
                    currentStation = "40250";
                } else if (station.equals("Kimball")) {
                    currentStation = "41290";
                } else if (station.equals("King Drive")) {
                    currentStation = "41140";
                } else if (station.equals("Kostner")) {
                    currentStation = "40600";
                } else if (station.equals("Lake")) {
                    currentStation = "41660";
                } else if (station.equals("Laramie")) {
                    currentStation = "40700";
                } else if (station.equals("LaSalle")) {
                    currentStation = "41340";
                } else if (station.equals("LaSalle/Van Buren")) {
                    currentStation = "40160";
                } else if (station.equals("Lawrence")) {
                    currentStation = "40770";
                } else if (station.equals("Linden")) {
                    currentStation = "41050";
                } else if (station.equals("Logan Square")) {
                    currentStation = "41020";
                } else if (station.equals("Loyola")) {
                    currentStation = "41300";
                } else if (station.equals("Main")) {
                    currentStation = "40270";
                } else if (station.equals("Merchandise Mart")) {
                    currentStation = "40460";
                } else if (station.equals("Midway")) {
                    currentStation = "40930";
                } else if (station.equals("Monroe")) {
                    currentStation = "40790";
                } else if (station.equals("Montrose")) {
                    currentStation = "41330";
                } else if (station.equals("Morgan")) {
                    currentStation = "41510";
                } else if (station.equals("Morse")) {
                    currentStation = "40100";
                } else if (station.equals("North/Clybourn")) {
                    currentStation = "40650";
                } else if (station.equals("Noyes")) {
                    currentStation = "40400";
                } else if (station.equals("Oak Park")) {
                    currentStation = "40180";
                } else if (station.equals("Oakton-Skokie")) {
                    currentStation = "41680";
                } else if (station.equals("O hare")) {
                    currentStation = "40890";
                } else if (station.equals("Pualina")) {
                    currentStation = "41310";
                } else if (station.equals("Polk")) {
                    currentStation = "41030";
                } else if (station.equals("Pulaski")) {
                    currentStation = "40920";
                } else if (station.equals("Quincy/Wells")) {
                    currentStation = "40040";
                } else if (station.equals("Racine")) {
                    currentStation = "40470";
                } else if (station.equals("Ridgeland")) {
                    currentStation = "40610";
                } else if (station.equals("Rockwell")) {
                    currentStation = "41010";
                } else if (station.equals("Roosevelt")) {
                    currentStation = "41400";
                } else if (station.equals("Rosemont")) {
                    currentStation = "40820";
                } else if (station.equals("Sedgwick")) {
                    currentStation = "40800";
                } else if (station.equals("Sheridan")) {
                    currentStation = "40080";
                } else if (station.equals("South Blvd")) {
                    currentStation = "40840";
                } else if (station.equals("Southport")) {
                    currentStation = "40360";
                } else if (station.equals("Sox-35th")) {
                    currentStation = "40190";
                } else if (station.equals("State/Lake")) {
                    currentStation = "40260";
                } else if (station.equals("Thorndale")) {
                    currentStation = "40880";
                } else if (station.equals("UIC-Halsted")) {
                    currentStation = "40350";
                } else if (station.equals("Washington")) {
                    currentStation = "40370";
                } else if (station.equals("Washington/Wabash")) {
                    currentStation = "41700";
                } else if (station.equals("Washington/Wells")) {
                    currentStation = "40730";
                } else if (station.equals("Wellington")) {
                    currentStation = "41210";
                } else if (station.equals("Western")) {
                    currentStation = "40220";
                } else if (station.equals("Wilson")) {
                    currentStation = "40540";
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
                if (!(ctatt.has("eta"))) {
                    shortString = "No trains arriving soon.";
                    return;
                }
                eta = ctatt.get("eta").getAsJsonArray();
                for (int i = 0; i < eta.size(); i++) {
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
