package com.example.mst.bprecorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View view){
        switch (view.getId()) {
            case R.id.button:
                EditText et = (EditText)findViewById(R.id.etMaxBP);
                String maxBP = et.getText().toString();
                et = (EditText)findViewById(R.id.etMinBP);
                String minBP = et.getText().toString();
                et = (EditText)findViewById(R.id.etPulse);
                String pulse = et.getText().toString();
                et = (EditText)findViewById(R.id.etComment);
                String comment = et.getText().toString();
                //Toast.makeText(MainActivity.this, maxBP + "/" + minBP + "/" + pulse + " " + comment, Toast.LENGTH_LONG).show();

                // 送信したいパラメーター
                Map<String, String> params = new HashMap<String, String>();
                params.put("maxBP", maxBP);
                params.put("minBP", minBP);
                params.put("pulse", pulse);
                params.put("comment", comment);
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                params.put("dateBP", df.format(date));

                String url = "http://bloodpressure20170509114035.azurewebsites.net/api/values/";
                JSONObject jso = new JSONObject(params);
                JsonObjectRequest jsObjectRequest = new JsonObjectRequest(
                    //Request.Method.GET,
                    Request.Method.POST,
                    url,
                    jso,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d(TAG, response.toString(2));
                                int errCode = response.getInt("errorCode");
                                String resMessage = response.getString("message");
                                int resID = response.getInt("receiveId");
                                if (errCode == 0){
                                    Toast.makeText(MainActivity.this, "送信完了(｀・ω・´)ゞ", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this, resMessage, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "error JsonException \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                            Toast.makeText(MainActivity.this, "error VolleyError \n" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                );

                com.example.mst.bprecorder.MySingleton.getInstance(this).addToRequestQueue(jsObjectRequest);
                break;
        }
    }
}

