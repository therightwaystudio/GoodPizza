package com.example.romanovsky_m.testproject.Activities;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.LocationManager;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.romanovsky_m.testproject.Database.DaoSession;
import com.example.romanovsky_m.testproject.Database.OfferDB;
import com.example.romanovsky_m.testproject.Fragments.BuyFragment;
import com.example.romanovsky_m.testproject.Fragments.CategoriesListFragment;
import com.example.romanovsky_m.testproject.Fragments.ContactFragment;
import com.example.romanovsky_m.testproject.Fragments.LoadingFragment;
import com.example.romanovsky_m.testproject.Fragments.MyMapFragment;
import com.example.romanovsky_m.testproject.Fragments.OffersListFragment;
import com.example.romanovsky_m.testproject.Models.Offer;
import com.example.romanovsky_m.testproject.Models.OffersSingleton;

import com.example.romanovsky_m.testproject.R;
import com.example.romanovsky_m.testproject.Utils.GoodPizzaApp;

import java.util.ArrayList;
import java.util.List;


public class GoodPizzaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final int REQUEST_FINE_LOCATION=0;
    private static final String DIALOG_CONTACT = "DialogContact";
    private static final String DIALOG_LOADING = "DialogLoading";
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private static final String KEY_FAB_VISIBLE = "fab_visible";

    public static boolean LOADED_FLAG = false;

    private NavigationView mNavigationView;
    private MenuItem mPreviousItem = null;
    private CoordinatorLayout mCoordinatorLayout;

    private boolean fabVisible=true;

    public boolean isOnline(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_project);

        loadPermissions(Manifest.permission.ACCESS_FINE_LOCATION,REQUEST_FINE_LOCATION);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        if(!isGpsOnline(getBaseContext()) && !isOnline(getBaseContext())){
            Snackbar.make(mCoordinatorLayout, "Нет доступа к интернету и геоданным", Snackbar.LENGTH_LONG).show();
        } else
        if(!isOnline(getBaseContext())){
            Snackbar.make(mCoordinatorLayout, "Нет доступа к сети. Пожалуйста, включите wi-fi или мобильный интернет.", Snackbar.LENGTH_LONG).show();
        } else
        if(!isGpsOnline(getBaseContext())){
            Snackbar.make(mCoordinatorLayout, "Не включена передача геоданных. Пожалуйста, включите.", Snackbar.LENGTH_LONG).show();
        }
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if(!LOADED_FLAG) {
            LoadingFragment loadingFragment = new LoadingFragment();
            loadingFragment.show(fragmentManager, DIALOG_LOADING);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PackageManager packageManager = getPackageManager();
        boolean telephonySupported = packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
        boolean gsmSupported = packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_GSM);
        boolean cdmaSupported = packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_CDMA);
        final boolean isMobile = (telephonySupported || gsmSupported || cdmaSupported);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(!isMobile) fab.setImageResource(R.drawable.letter_icon);
        if(savedInstanceState != null){
            fabVisible = savedInstanceState.getBoolean(KEY_FAB_VISIBLE);
            if(fabVisible){
                fab.show();
            } else {
                fab.hide();
            }
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMobile){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+375292959954"));
                    startActivity(intent);
                }
                else {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"therightwaystudio@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Вопрос");
                    emailIntent.setType("text/plain");
                    startActivity(emailIntent);
               }
            }
        });

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(fragmentManager.getBackStackEntryCount()!=0){
                    fab.hide();
                    fabVisible = false;

                    if(mPreviousItem != null) {
                        mPreviousItem.setCheckable(true);
                        mPreviousItem.setChecked(true);
                    }
                }
                else{
                    fab.show();
                    fabVisible=true;

                    if(mPreviousItem != null) {
                        mPreviousItem.setCheckable(false);
                        mPreviousItem.setChecked(false);
                    }
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount()==0){
                finishAffinity();
            }else{
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(mPreviousItem != null){
            mPreviousItem.setCheckable(false);
            mPreviousItem.setChecked(false);
        }

        if(id == R.id.home_menu){
            fragment = new CategoriesListFragment();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
        else if(id == R.id.menu_buy){
            Menu menu = mNavigationView.getMenu();
            MenuItem menuItem = menu.findItem(R.id.nav_basket);
            menuItem.setCheckable(true);
            menuItem.setChecked(true);
            mPreviousItem = menuItem;

            fragment = new BuyFragment();
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_noodles) {
            fragment = OffersListFragment.newInstance(3);

        } else if (id == R.id.nav_desserts) {
            fragment = OffersListFragment.newInstance(10);

        } else if (id == R.id.nav_drinks) {
            fragment = OffersListFragment.newInstance(9);

        } else if (id == R.id.nav_supplements) {
            fragment = OffersListFragment.newInstance(23);

        } else if (id == R.id.nav_party_maker) {
            fragment = OffersListFragment.newInstance(25);

        } else if (id == R.id.nav_pizza) {
            fragment = OffersListFragment.newInstance(1);

        } else if (id == R.id.nav_rolls) {
            fragment = OffersListFragment.newInstance(18);

        } else if (id == R.id.nav_salads) {
            fragment = OffersListFragment.newInstance(7);

        } else if (id == R.id.nav_sets) {
            fragment = OffersListFragment.newInstance(2);

        } else if (id == R.id.nav_soups) {
            fragment = OffersListFragment.newInstance(6);

        } else if (id == R.id.nav_sushi) {
            fragment = OffersListFragment.newInstance(5);

        } else if (id == R.id.nav_teploe) {
            fragment = OffersListFragment.newInstance(8);

        } else if (id == R.id.nav_snacks) {
            fragment = OffersListFragment.newInstance(20);

        } else if (id == R.id.nav_basket) {
            fragment = new BuyFragment();

        } else  if (id == R.id.nav_location) {
            fragment = new MyMapFragment();

        } else  if (id == R.id.nav_contact) {
            ContactFragment dialog = new ContactFragment();
            dialog.show(fragmentManager, DIALOG_CONTACT);
            item.setCheckable(false);
            item.setChecked(false);

        } else if(id == R.id.nav_popular_offers){
            Intent intent = new Intent(this,PopularOffersActivity.class);
            startActivity(intent);
            item.setCheckable(false);
            item.setChecked(false);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


        if(!(id == R.id.nav_contact)){
            if(mPreviousItem != null) {
                mPreviousItem.setCheckable(false);
                mPreviousItem.setChecked(false);
            }
            item.setCheckable(true);
            item.setChecked(true);
            mPreviousItem = item;
            fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadPermissions(String perm,int requestCode) {
        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                ActivityCompat.requestPermissions(this, new String[]{perm},requestCode);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(LOADED_FLAG) {
            updateSubtitle();
        }
    }


    public boolean isGpsOnline(Context context){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void updateSubtitle(){
        double price = OffersSingleton.get(getBaseContext()).getOffers().getPrice();
        if (price != 0.0) {
            getSupportActionBar().setSubtitle("Сумма: " + price + "руб");
        }
    }

    public void upgradeDB(){
        List<OfferDB> offersListDB;
        OfferDB offerDB;

        Long ID;
        String NAME;
        String URL;
        Double PRICE;
        String DESCRIPTION;
        String PICTURE_URL;
        Integer NUMBER;
        Long CATEGORY_ID;

        offersListDB = new ArrayList<>();
        for(Offer offer : OffersSingleton.get(getBaseContext()).getOffers().mOfferList){
            ID = (long)offer.getId();
            NAME = offer.getName();
            URL = offer.getUrl();
            PRICE = offer.getPrice();
            DESCRIPTION = offer.getDescription();
            PICTURE_URL = offer.getPictureUrl();
            NUMBER = offer.getNumber();
            CATEGORY_ID = (long)offer.getCategoryId();


            offerDB = new OfferDB(ID,NAME,URL,PRICE,DESCRIPTION,PICTURE_URL,NUMBER,CATEGORY_ID);
            offersListDB.add(offerDB);
        }

        getAppDaoSession().getOfferDBDao().updateInTx(offersListDB);
    }

    public void onStop() {
        super.onStop();
        if(LOADED_FLAG) {
            upgradeDB();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_FAB_VISIBLE, fabVisible);
    }

    private DaoSession getAppDaoSession(){
        return ((GoodPizzaApp) getApplication()).getDaoSession();
    }

    public Menu getSingleActivityMenu(){
        return  mNavigationView.getMenu();
    }

    public void setPreviousItem(MenuItem previousItem) {
        this.mPreviousItem = previousItem;
    }

}
