package com.codinghomies.topdeals;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codinghomies.topdeals.Adapter.ItemsAdapter;
import com.codinghomies.topdeals.Model.Items;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity implements ItemsAdapter.OnListItemClick, SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerViewItems;
    private FirebaseFirestore firebaseFirestore;

    private ItemsAdapter itemRecyclerAdapter;

    public static final String URL_PATH = null;
    static final Integer INTERNET_REQUEST_CODE = 0;

    ImageButton settingImageBtn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        //changing status bar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            ((Window) window).addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_200));
        }

        askForInternetPermission();

        Toolbar screen_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(screen_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        firebaseFirestore= FirebaseFirestore.getInstance();
        recyclerViewItems = (RecyclerView) findViewById(R.id.item_recycler_view);

        swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(MainActivity.this);

        settingImageBtn = findViewById(R.id.main_screen_top_toolbar_settings);
        settingImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_setting = new Intent(getApplicationContext(), SettingsScreen.class);
                startActivity(intent_setting);
            }
        });
        
        //verify internet connection on the loading time of app
        if (internetConnected(getApplicationContext()))
        {
           //method for fetching data
           loadData();
       }
        else
        {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    //manual defined and override methods starts from here
    public void loadData()  //method for recycler view and query of fire store and pagination
    {
        //Query
        Query query=firebaseFirestore.collection("Products");

        PagedList.Config config= new PagedList.Config.Builder()
                .setInitialLoadSizeHint(25)
                .setPageSize(25).build();

        //Recycler
        FirestorePagingOptions<Items> recyclerOptions= new FirestorePagingOptions.Builder<Items>()
                .setLifecycleOwner(this)
                .setQuery(query, config,  new SnapshotParser<Items>() {
                    @NonNull
                    @Override
                    public Items parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        Items items = snapshot.toObject(Items.class);
                        items.setItem_id(snapshot.getId());
                        return items;
                    }
                })
                .build();

        itemRecyclerAdapter = new ItemsAdapter(recyclerOptions, this);  //check

        //View Holder
        recyclerViewItems.setHasFixedSize(true);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItems.setAdapter(itemRecyclerAdapter);
    }

    //for opening url in chrome browser within app
    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

        String url_item = documentSnapshot.getString("url");

        //chrome custom tabs
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.purple_500));
        builder.addDefaultShareMenuItem();
        builder.setShowTitle(true);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url_item));
    }

    //for swipe to refresh
    @Override
    public void onRefresh() {
        itemRecyclerAdapter.notifyDataSetChanged();
        loadData();
        swipeRefreshLayout.setRefreshing(false);
    }

    //this method is called in Android 6.0 & above versions to get run time permission from user
    private void askForInternetPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.INTERNET)) {
                //This is called if user has denied the permission before. In this case I am just asking the permission again
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET}, INTERNET_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET}, INTERNET_REQUEST_CODE);
            }
        }
    }

    //method for internet connection
    private boolean internetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}