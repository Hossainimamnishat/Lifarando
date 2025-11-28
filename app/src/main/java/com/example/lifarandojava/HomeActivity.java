package com.example.lifarandojava;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lifarandojava.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding; // Declare binding at the class level

    private CircularProgressIndicator circularProgressIndicator;
    private TextView progressTextView;
    private int i = 0;
    private Handler progressHandler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout and set the content view
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // *** FIX: Set the toolbar from the included layout as the action bar ***
        setSupportActionBar(binding.appBarHome.toolbar);

        // Setup the FloatingActionButton click listener
        binding.appBarHome.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show());

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Get the header view from the NavigationView to find its children
        View headerView = navigationView.getHeaderView(0);
        progressTextView = headerView.findViewById(R.id.progressText);
        circularProgressIndicator = headerView.findViewById(R.id.circularProgressIndicator);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);

        // This line will now work because setSupportActionBar() was called correctly
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Start the progress indicator update
        startProgressUpdate();
    }

    private final Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            if (i <= 100) {
                try {
                    String progressString = i + "%";
                    progressTextView.setText(progressString);
                    circularProgressIndicator.setProgress(i, true);
                    i++;
                    // Schedule the next run
                    progressHandler.postDelayed(this, 200);
                } catch (Exception e) {
                    Log.d("ProgressUpdateError", "Error updating progress: " + e.getMessage());
                }
            } else {
                // Stop the handler when progress reaches 100
                progressHandler.removeCallbacks(this);
            }
        }
    };

    private void startProgressUpdate() {
        // Start the initial run
        progressHandler.post(progressRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Prevent memory leaks by removing the handler callbacks when the activity is destroyed
        progressHandler.removeCallbacks(progressRunnable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}