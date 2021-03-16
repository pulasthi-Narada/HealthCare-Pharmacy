package com.healthcarepharmacy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);


       // get id of whatsapp Floating Action Button
        FloatingActionButton fab = findViewById(R.id.Whatapp_floatingBtn);
       //  whatsapp Floating Action Button  OnClickListener. when button is press this method will run.

        SharedPreferences sharedPreff = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPreff.getInt("value",0);


        if(loginCode==0) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get whatsapp install or not as a boolen.
                Boolean installed = checkWhatappInstall("com.whatsapp");
                if(installed){
                    // if whatsapp app installed then open whatsapp using Intent.
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //set phone number
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+94767558358"));
                    startActivity(intent);
                }else {
                    //not install whatsapp then this message will show.
                    Snackbar.make(view, "Whatsapp not installed on your phone.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_Upload_Prescription,R.id.nav_Categories,R.id.nav_Search_Buy_Medicine,R.id.nav_my_address,R.id.nav_my_Prescription,R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

               // get selected id from navigation menu item
                int id= destination.getId();


                switch (id){
                    // if user select navigation Categories in menu then start  Categories Activity
                    case R.id.nav_logout:
                        Intent intent = new Intent(MainActivity.this, logoutActivity.class);
                           startActivity(intent);
                           finish();
                        break;


                }

            }
        });
    }

    private Boolean checkWhatappInstall(String url) {
        // check whatapp app install on phone
        PackageManager packageManager = getPackageManager();
        boolean appInstalled;
        try {
            packageManager.getPackageInfo(url,packageManager.GET_ACTIVITIES);
            appInstalled = true;
        }catch (PackageManager.NameNotFoundException e){
            appInstalled =false;

        }catch (Exception e) {
            Toast toast=Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT);
            toast.setMargin(50,50);toast.show();
            appInstalled =false;
        }
        return  appInstalled;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId= item.getItemId();



           if(itemId == R.id.action_cart){
           Intent intent = new Intent(MainActivity.this, CartActivity.class);
           startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreff = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPreff.getInt("value",0);


        if(loginCode==0) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreff = getSharedPreferences("login", MODE_PRIVATE);
        int loginCode = sharedPreff.getInt("value",0);


        if(loginCode==0) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}