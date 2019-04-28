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
    private RequestQueue requestQueue;
    private String jsonString;
    private String url;
    private String arrivalTime;
    private String towards;
    private String lineColor;
    private String shortString = "";
    private boolean causedError = false;
    JsonParser parser;
    JsonObject result;
    JsonObject ctatt;
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
