package invenz.roy.ngtc.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import invenz.roy.ngtc.R;
import invenz.roy.ngtc.utils.Constants;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "ROY" ;
    private EditText etItemName, etBuyerName, etContactNo;
    private Spinner spQuantity, spCity;
    private TextView btOrderNow;

    private List<Integer> quantityList;
    private List<String> cityList;
    private ArrayAdapter quantityAdapter, cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        /*#####                getting info from RecViewCustomAdapter            ####*/
        final int itemId = getIntent().getExtras().getInt("item_id");
        String itemName = getIntent().getExtras().getString("item_name");
        Toast.makeText(this, "item Id: "+itemId, Toast.LENGTH_SHORT).show();


        /*#####                           Initializing                         #####*/
        etItemName = findViewById(R.id.idItemName_orderAct);
        etBuyerName = findViewById(R.id.idBuyerName_orderAct);
        etContactNo = findViewById(R.id.idContactNo_orderAct);

        spQuantity = findViewById(R.id.idQuantity_orderAct);
        spCity = findViewById(R.id.idCity_orderAct);
        btOrderNow = findViewById(R.id.idOrderNow_orderAct);

        etItemName.setText(itemName);

        /*###      putting values in quantity spinnaer     ###*/
        quantityList = new ArrayList<>();
        for (int i=1; i<30; i++){
            quantityList.add(i);
        }
        quantityAdapter = new ArrayAdapter(OrderActivity.this, android.R.layout.simple_list_item_1, quantityList);
        spQuantity.setAdapter(quantityAdapter);


        /*##        putting values in city spinner              ###*/
        cityList = new ArrayList<>();
        cityList.add("Chittagong");
        cityList.add("Dhaka");
        cityList.add("Comilla");
        cityList.add("Rajshahi");
        cityList.add("Rangpur");
        cityList.add("Sylhet");

        cityAdapter = new ArrayAdapter(OrderActivity.this, android.R.layout.simple_list_item_1, cityList);
        spCity.setAdapter(cityAdapter);



        /*####                   When order button clicked                          ###*/
        btOrderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                v.startAnimation(AnimationUtils.loadAnimation(OrderActivity.this, R.anim.image_click));

                /*#####          getting values from components                           ####*/
                final String sItemName, sBuyerName, sContactNo, sQuantity, sCity;
                sItemName = etItemName.getText().toString();
                sBuyerName = etBuyerName.getText().toString();
                sContactNo = etContactNo.getText().toString();
                sQuantity = spQuantity.getSelectedItem().toString();
                sCity = spCity.getSelectedItem().toString();

                if (sItemName.isEmpty() || sBuyerName.isEmpty() || sContactNo.isEmpty() || sQuantity.isEmpty() || sCity.isEmpty()){
                    //Toast.makeText(OrderActivity.this, "Please provide all informations", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v,"Please provide all informations", Snackbar.LENGTH_SHORT ).setAction("action", null).show();


                }else {
                    //Toast.makeText(OrderActivity.this, "OK", Toast.LENGTH_SHORT).show();

                    if (sContactNo.length()!=9){
                        //Toast.makeText(OrderActivity.this, "Please enter 9 Digit of your Contact Number", Toast.LENGTH_SHORT).show();
                        Snackbar.make(v,"Please enter 9 Digit of your Contact Number", Snackbar.LENGTH_SHORT ).setAction("action", null).show();

                    }else {

                        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.INSERT_ORDER_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(response);

                                            String serverResponse = jsonObject.getString("response");
                                            //Toast.makeText(OrderActivity.this, "" + serverResponse, Toast.LENGTH_SHORT).show();

                                            Snackbar.make(v,""+serverResponse, Snackbar.LENGTH_SHORT ).setAction("action", null).show();


                                            etBuyerName.setText("");
                                            etContactNo.setText("");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "onErrorResponse: " + error);
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> orderMap = new HashMap<>();
                                orderMap.put("buyer_name", sBuyerName);
                                orderMap.put("contact_no", "8801"+sContactNo);
                                orderMap.put("quantity", sQuantity);
                                orderMap.put("city", sCity);
                                orderMap.put("item_id", String.valueOf(itemId));

                                return orderMap;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(OrderActivity.this);
                        requestQueue.add(stringRequest);
                    }


                }

            }
        });





    }
}
