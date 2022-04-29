package com.connectedinteractive.attribution_sdk_demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.connectedinteractive.attribution_sdk_demo.utils.MockLocationUtils;

public class LocationTestActivity extends AppCompatActivity {

    private EditText editTextLatitude;
    private EditText editTextLongitude;
    private Button buttonSetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);

        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        buttonSetLocation = findViewById(R.id.buttonSetLocation);

        buttonSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonSetLocationClick();
            }
        });
    }

    private void onButtonSetLocationClick() {
        MockLocationUtils mockLocationUtils = new MockLocationUtils(this, true);
        mockLocationUtils.setLocation(Double.valueOf(editTextLatitude.getText().toString()), Double.valueOf(editTextLongitude.getText().toString()));
    }
}
