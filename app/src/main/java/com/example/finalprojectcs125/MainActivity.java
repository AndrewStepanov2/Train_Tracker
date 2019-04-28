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

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private String jsonString = null;

    private String url;

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
                if (station.equals("Clark/Lake")) {
                    currentStation = "40380";
                }
                if (station.equals("Belmont")) {
                    currentStation="41320";
                }
                url = "http://lapi.transitchicago.com/api/1.0/ttarrivals.aspx?key=" + API_KEY + "&mapid=" + currentStation + "&outputType=JSON&max=3";
                // get info from API request
                // display the data from API request
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        ((Button) findViewById(R.id.button)).setOnClickListener((v) -> {
            process(requestQueue, url);
            text.setText(jsonString);
        });
    }
    public void process(RequestQueue requestQueue, String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonString = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonString = error.getMessage();
            }
        });
        requestQueue.add(stringRequest);
        //parse jsonString
        //get arrival time
    }
}
