package invenz.roy.ngtc.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import invenz.roy.ngtc.R;
import invenz.roy.ngtc.utils.Constants;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "ROY" ;
    private BroadcastReceiver broadcastReceiver;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.KEY_ACCESS_TOKEN, null);

        storeTokenIntoServer(token);

        //Toast.makeText(getApplicationContext(), ""+token, Toast.LENGTH_SHORT).show();

        /*broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


            }
        };
        *//*###### Registering reciever #######*//*
        registerReceiver(broadcastReceiver, new IntentFilter(Constants.TOKEN_BROADCAST));*/


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 1000);

    }


    /*###                              storing Token Into Server                ####*/
    private void storeTokenIntoServer(final String token) {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.STORE_TOKEN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String serverResponse = jsonObject.getString("message");

                            Log.d(TAG, "onResponse(SplashActivity): "+serverResponse);
                            //Toast.makeText(SplashActivity.this, ""+serverResponse, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse(SplashActivity): "+error);
                error.getStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> tokenMap = new HashMap<>();
                tokenMap.put("token", token);
                return tokenMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SplashActivity.this);
        requestQueue.add(stringRequest);
    }
}
