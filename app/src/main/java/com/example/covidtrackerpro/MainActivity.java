package com.example.covidtrackerpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    TextView countryName, totalCases;
    CircleImageView flag;
    Button button;
    EditText editText;
    RequestQueue requestQueue;
    String cName, tCases, flagUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryName = (TextView)findViewById(R.id.countryName);
        totalCases= (TextView)findViewById(R.id.totalCases);
        button= (Button) findViewById(R.id.button);
        editText= (EditText) findViewById(R.id.eT);
        flag = (CircleImageView) findViewById(R.id.countryImg);

        requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Input box can't be empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    jsonParse();
                }
            }
        });
    }

    private void jsonParse() {
        String url = "https://corona.lmao.ninja/v2/countries/" + editText.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    cName = response.getString("country");
                    JSONObject flagObject = response.getJSONObject("countryInfo");
                    flagUri = flagObject.getString("flag");
                    tCases = response.getString("cases");

                } catch (Exception e) {

                }

                countryName.setText(cName);
                totalCases.setText("Number of Cases : " + tCases);
                Glide.with(getApplicationContext()).load(flagUri).into(flag);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
