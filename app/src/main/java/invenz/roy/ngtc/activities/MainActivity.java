package invenz.roy.ngtc.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import hotchemi.android.rate.AppRate;
import invenz.roy.ngtc.R;
import invenz.roy.ngtc.adapters.ViewPageCustomAdapter;
import invenz.roy.ngtc.fragments.ContactUsFragment;
import invenz.roy.ngtc.fragments.OnlineFragment;
import invenz.roy.ngtc.fragments.WallpapersFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "ROY" ;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.nav_drawer_color));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setElevation(0);


        /*###                         Changing the color of nav drawer icon                        ####*/
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.nav_drawer_color));


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*###                 my works start here                     ####*/


        tabLayout =findViewById(R.id.idTabLayout);
        viewPager = findViewById(R.id.idViewPager);
        frameLayout = findViewById(R.id.idFrameLayout);

        /*###                     Sliding codes                      ###*/
        setUpFragmentToViewpager(viewPager); //my method

        tabLayout.setupWithViewPager(viewPager);
        setUpMyTablayout(); //my method



        /*###                 Preventing taking screenshot                 ###*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        /*###                    Rate app codes                                ####*/
        AppRate.with(this)
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);

    }




    /*###                 my method to set fragment into viewpager                        ###*/
    private void setUpFragmentToViewpager(ViewPager viewPager) {
        ViewPageCustomAdapter viewPageCustomAdapter = new ViewPageCustomAdapter(getSupportFragmentManager());
        viewPageCustomAdapter.addFragmentToList(new WallpapersFragment());
        viewPageCustomAdapter.addFragmentToList(new OnlineFragment());

        viewPager.setAdapter(viewPageCustomAdapter);

    }


    /*#######                     my method to set the tab position              ##########*/
    private void setUpMyTablayout() {

        tabLayout.getTabAt(0).setText("Wallpaper");
        tabLayout.getTabAt(1).setText("Online");

    }



    /*###                              When Back Pressed                          ####*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else{

            /*#####                    Checking if frameLayout is visible                         ####*/
            if (frameLayout.getVisibility() == View.VISIBLE) {
                viewPager.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);

            }else {
                //super.onBackPressed();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Leaving")
                        .setMessage("Do you really want to exit?")
                        .setIcon(R.drawable.ic_warning_red_24dp)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_share) {

            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "National");
                String sAux = "\nLet me recommend you this application\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=invenz.example.bijohn.fifawc2018 \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));

            } catch(Exception e) {
                //e.toString();
                e.getStackTrace();
                Log.d(TAG, "onNavigationItemSelected(MainAct): "+e.getMessage());
            }

        } else if (id == R.id.nav_contact_us) {
            fragment = new ContactUsFragment();
            setupFragment(fragment);

        } else if (id == R.id.nav_exit) {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Leaving")
                    .setMessage("Do you really want to exit?")
                    .setIcon(R.drawable.ic_warning_red_24dp)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /*###                      ###*/
    public void setupFragment(Fragment upFragment) {

        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.idFrameLayout, upFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

}
