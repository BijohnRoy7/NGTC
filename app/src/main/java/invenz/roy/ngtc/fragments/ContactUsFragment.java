package invenz.roy.ngtc.fragments;


import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import invenz.roy.ngtc.R;
import invenz.roy.ngtc.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {

    private static final String TAG = "ROY" ;
    private ImageView ivCopy1, ivCopy2;
    private TextView tvContactNumber1, tvContactNumber2, tvAddress;


    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        ivCopy1 = view.findViewById(R.id.idCopy1_contactUs);
        ivCopy2 = view.findViewById(R.id.idCopy2_contactUs);
        tvContactNumber1 = view.findViewById(R.id.idMblNo1_contactUs);
        tvContactNumber2 = view.findViewById(R.id.idMblNo2_contactUs);
        tvAddress = view.findViewById(R.id.idAdress_contactUs);

        tvContactNumber1.setClickable(true);
        tvContactNumber2.setClickable(true);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JsonArrayRequest.Method.POST, Constants.GET_CONTACT_US_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject serverResponse = response.getJSONObject(response.length() - 1);
                            String address = serverResponse.getString("address");
                            String contactNo1 = serverResponse.getString("contact_no1");
                            String contactNo2 = serverResponse.getString("contact_no2");

                            tvAddress.setText(address);
                            tvContactNumber1.setText(contactNo1);
                            tvContactNumber2.setText(contactNo2);

                            //Toast.makeText(getContext(), ""+serverResponse.getString("address"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse(ContactUsFragment) : "+error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);


        /*####                   Click to copy contact                       ###*/
        ivCopy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.image_click));

                ClipboardManager cm = (ClipboardManager)getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                cm.setText(tvContactNumber1.getText());
                //Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();

                Snackbar.make(view,"Copied", Snackbar.LENGTH_SHORT ).setAction("action", null).show();

            }
        });


        /*####                   Click to copy contact                       ###*/
        ivCopy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.image_click));

                ClipboardManager cm = (ClipboardManager)getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                cm.setText(tvContactNumber2.getText());
                //Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();

                Snackbar.make(view,"Copied", Snackbar.LENGTH_SHORT ).setAction("action", null).show();

            }
        });

        return view;
    }

}
