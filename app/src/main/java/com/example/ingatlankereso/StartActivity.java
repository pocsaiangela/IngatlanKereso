package com.example.ingatlankereso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    private static final String LOG_TAG = StartActivity.class.getName();

    private boolean isLocationPermissionGranted = false;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);

        //login and registry button animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.registry);
        loginButton.startAnimation(animation);
        registerButton.startAnimation(animation);

        //location permission
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    android.Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                isLocationPermissionGranted = true;
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                isLocationPermissionGranted = true;
                            } else {
                                // No location access granted.
                            }
                        }
                );

        locationPermissionRequest.launch(new String[] {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }
    public void registry(View view) {
        if(isLocationPermissionGranted){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(StartActivity.this, "Nincsen engedély", Toast.LENGTH_LONG).show();
        }
    }

    public void login(View view) {
        if(isLocationPermissionGranted){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(StartActivity.this, "Nincsen engedély", Toast.LENGTH_LONG).show();
        }

    }
}
