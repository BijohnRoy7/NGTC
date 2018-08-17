package invenz.roy.ngtc.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import invenz.roy.ngtc.R;
import invenz.roy.ngtc.adapters.FetchCustomAdapter;
import invenz.roy.ngtc.adapters.RecViewCustomAdapter;
import invenz.roy.ngtc.models.FetchItem;
import invenz.roy.ngtc.models.Item;
import invenz.roy.ngtc.models.MyItem;
import invenz.roy.ngtc.utils.MySqliteDbHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class WallpapersFragment extends Fragment {

    private static final String TAG = "ROY" ;
    private RecyclerView recyclerView;
    private List<FetchItem> itemList ;
    private FetchCustomAdapter customAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private MySqliteDbHelper mySqliteDbHelper;
    private SwipeRefreshLayout swipeRefreshLayout;

    //private ImageView imageView;

    public WallpapersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallpapers, container, false);


        recyclerView = view.findViewById(R.id.idRecView_wallpaperFrag);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);



        /*#####                   Swipe to refresh the fragment                      #####*/
        swipeRefreshLayout = view.findViewById(R.id.idSwipe_wallpaperFrag);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshMyFragment(true);
            }
        });




        /*#####                 using sqlite db to fetch all data                     #######*/
        mySqliteDbHelper = new MySqliteDbHelper(getContext());
        List<MyItem> myItemList = mySqliteDbHelper.fetcheAllItems();

        itemList = new ArrayList<>();

        Iterator<MyItem> it = myItemList.iterator();

        while (it.hasNext()){
            MyItem item = it.next();

            byte[] imgByte = item.getImageByte();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            //Toast.makeText(getContext(), ""+bitmap, Toast.LENGTH_SHORT).show();

            FetchItem fetchItem = new FetchItem(item.getId(), item.getItemName(), bitmap);
            itemList.add(fetchItem);

        }

        //if (itemList.size()!=0){
            customAdapter = new FetchCustomAdapter(getContext(), itemList);
            customAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(customAdapter);
        //}




        //imageView = view.findViewById(R.id.idImg);
/*
        byte[] imgByte = myItemList.get(0).getImageByte();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        Toast.makeText(getContext(), ""+bitmap, Toast.LENGTH_SHORT).show();*/
        //imageView.setImageBitmap(bitmap);

        return view;
    }



    /*####             Refresh the fragment when tab is changed                    ###*/
    public void refreshMyFragment(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }


}
