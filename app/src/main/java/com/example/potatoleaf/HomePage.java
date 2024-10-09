package com.example.potatoleaf;

import static android.app.ProgressDialog.show;
import static android.widget.Toast.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private CardView R_cardview, D_cardview, M_cardview, U_cardview,S_cardview,P_cardview,G_cardview,c_cardview;

    DrawerLayout drawerLayout;
   public ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    MaterialToolbar toolbar;
    private WebView webView;

    SharedPreferences sharedPreferences ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        webView = findViewById(R.id.webViews);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/index.html");
        c_cardview=findViewById(R.id.report);
        R_cardview = findViewById(R.id.LeavesId);
        D_cardview = findViewById(R.id.detectId);
        M_cardview = findViewById(R.id.mapId);
       // U_cardview = findViewById(R.id.uploadId);
        P_cardview = findViewById(R.id.paginationId);
        S_cardview = findViewById(R.id.sass_id);
        drawerLayout = findViewById(R.id.drawerid);
        navigationView = findViewById(R.id.navview);
        toolbar = findViewById(R.id.toolID);
        G_cardview=findViewById(R.id.newww);
         c_cardview.setOnClickListener(this);
        G_cardview.setOnClickListener(this);
        R_cardview.setOnClickListener(this);
        D_cardview.setOnClickListener(this);
        M_cardview.setOnClickListener(this);
       // U_cardview.setOnClickListener(this);
        P_cardview.setOnClickListener(this);
        S_cardview.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = this.getSharedPreferences("pass", Context.MODE_PRIVATE);


       setSupportActionBar(toolbar);

       View headerView = navigationView.getHeaderView(0);

       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
       drawerLayout.addDrawerListener(toggle);
       toggle.syncState();

       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               int id = item.getItemId();



                   if(id==R.id.navi_Ratings){
                   Intent intent = new Intent(getApplicationContext(),Rate_us.class);
                   startActivity(intent);


                   }   if(id==R.id.navi_video) {
                   Intent intent = new Intent(getApplicationContext(), Video.class);
                   startActivity(intent);
               }  if(id==R.id.navi_profile) {
                   Intent intent = new Intent(getApplicationContext(), profile.class);
                   startActivity(intent);

              }

                   if(id==R.id.navi_logout) {
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putBoolean("savepass", false);
                   editor.apply();

                   mAuth.signOut();
                   startActivity(new Intent(getApplicationContext(), MainActivity.class));
                   finish();
                   Toast.makeText(HomePage.this,"Sign Out", Toast.LENGTH_SHORT).show();
               }


                   return false;
               }

       });


         toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


    }









    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.LeavesId) {
            Intent intent = new Intent(HomePage.this, Review_activity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Review is loading", LENGTH_LONG).show();


        } else if (v.getId() == R.id.detectId) {
            Intent intent = new Intent(HomePage.this, Detect_activity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.mapId) {
            Intent intent = new Intent(HomePage.this, Map_Activity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.paginationId) {
            Intent intent = new Intent(HomePage.this, Pagination.class);
            startActivity(intent);


        }
        else if (v.getId() == R.id.sass_id) {
            Intent intent = new Intent(HomePage.this, Sass_Applicaion.class);
            startActivity(intent);

        }
        else if (v.getId() == R.id.newww) {
            Intent intent = new Intent(HomePage.this, graphql.class);
            startActivity(intent);

        }
        else if (v.getId() == R.id.report) {
            Intent intent = new Intent(HomePage.this, crystal_report.class);
            startActivity(intent);

        }

    }
}



