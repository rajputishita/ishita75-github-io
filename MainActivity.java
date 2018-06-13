package com.home.task_algo;

import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    JSONObject response, profile_pic_data, profile_pic_url;
    GPSTracker gps;
    TextView user_name,user_email,lat,lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("userProfile");
        Log.w("Jsondata", jsondata);
        user_name = findViewById(R.id.UserName);
        ImageView user_picture = findViewById(R.id.profilePic);
        user_email = findViewById(R.id.email);
        lat = findViewById(R.id.Latitude);
        lon = findViewById(R.id.longi);
        gps = new GPSTracker(MainActivity.this);
        if (gps.canGetLocation()) {

            String latitutes = Double.toString(gps.getLatitude());
            String longitutes = Double.toString(gps.getLongitude());
            lat.setText(latitutes);
            lon.setText(longitutes);
        } else {

            gps.showSettingsAlert();
        }


        try {
            response = new JSONObject(jsondata);
            user_email.setText(response.get("email").toString());
            user_name.setText(response.get("name").toString());

            profile_pic_data = new JSONObject(response.get("picture").toString());
            profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
            Picasso.with(this).load(profile_pic_url.getString("url"))
                    .into(user_picture);

        } catch (Exception e) {
            e.printStackTrace();
        }


        ImageView logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                finish();
                System.exit(1);
            }
        });
    }

}
