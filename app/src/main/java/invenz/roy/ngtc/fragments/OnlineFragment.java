package invenz.roy.ngtc.fragments;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import invenz.roy.ngtc.R;
import invenz.roy.ngtc.adapters.RecViewCustomAdapter;
import invenz.roy.ngtc.models.Item;
import invenz.roy.ngtc.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineFragment extends Fragment {

    private static final String TAG = "ROY" ;
    private RecyclerView recyclerView;
    private List<Item> itemList = new ArrayList<>();
    private RecViewCustomAdapter customAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvTapToRefresh;

    public OnlineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_online, container, false);


        tvTapToRefresh = view.findViewById(R.id.idTap_online);
        recyclerView = view.findViewById(R.id.idRecView_onlineFrag);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);


        ConnectivityManager manager =(ConnectivityManager) getContext()
                .getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                //we have WIFI

                Toast.makeText(getContext(), "Wifi", Toast.LENGTH_SHORT).show();
                getItemsFromOnline();
            }
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                //we have cellular data

                Toast.makeText(getContext(), "Cellular", Toast.LENGTH_SHORT).show();
                getItemsFromOnline();
            }
        } else{
            //we have no connection :(

            tvTapToRefresh.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            //Toast.makeText(getContext(), "Yes", Toast.LENGTH_SHORT).show();

            tvTapToRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshMyFragment(true);
                }
            });

        }

        return view;
    }



    /*####                                 get values                   ####*/
    private void getItemsFromOnline(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(JsonArrayRequest.Method.POST, Constants.GET_INFO_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        itemList.clear();
                        for (int i =0; i<response.length(); i++){

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("image_name");
                                String imageTitle = jsonObject.getString("image_title");

                                Item item = new Item(id, name, imageTitle);
                                itemList.add(item);

                                customAdapter = new RecViewCustomAdapter(getContext(), itemList);
                                //customAdapter.notifyDataSetChanged();
                                recyclerView.setAdapter(customAdapter);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse1(OnlineFragment): "+error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }


    /*####             Refresh the fragment when tab is changed                    ###*/
    public void refreshMyFragment(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }




}
