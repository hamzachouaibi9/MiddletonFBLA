package com.middleton.middletonfbla;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.middleton.middletonfbla.Fragments.AboutUsFragment;
import com.middleton.middletonfbla.Fragments.CalendarFragment;
import com.middleton.middletonfbla.Fragments.ContactFragment;
import com.middleton.middletonfbla.Fragments.EventsFragment;
import com.middleton.middletonfbla.Fragments.GalleryFragment;
import com.middleton.middletonfbla.Fragments.HomeFragment;
import com.middleton.middletonfbla.Fragments.QAFragment;
import com.middleton.middletonfbla.Fragments.SocialMediaFragment;
import com.middleton.middletonfbla.Fragments.WebsiteFragment;
import com.middleton.middletonfbla.Register.RegisterActivity;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    FirebaseAuth auth;
    CollectionReference database;
    ImageView imageView;
    TextView email, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance().collection("User_Information");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        View view = navView.getHeaderView(0);

        imageView = (ImageView) view.findViewById(R.id.accountImage);
        email = (TextView) view.findViewById(R.id.accountEmail);
        name = (TextView) view.findViewById(R.id.accountName);

        email.setText(auth.getCurrentUser().getEmail());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
            navView.setCheckedItem(R.id.nav_home);
        }

        database.document(auth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()){
                    String Name = documentSnapshot.get("name").toString().trim();
                    name.setText(Name);
                    if (documentSnapshot.get("imageLink") != null) {
                        Picasso.get().load(documentSnapshot.get("imageLink").toString().trim()).into(imageView);
                    }else{
                        Picasso.get().load(R.drawable.logo_blue).into(imageView);
                    }
                }else{
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_QA){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new QAFragment()).commit();
        }else if(item.getItemId() == R.id.action_contactUs){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ContactFragment()).commit();
        }else if(item.getItemId() == R.id.action_logout){
            auth.signOut();
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                break;
            case R.id.nav_calendar:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new CalendarFragment()).commit();
                break;
            case R.id.nav_websites:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new WebsiteFragment()).commit();
                break;
            case R.id.nav_socialMedia:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SocialMediaFragment()).commit();
                break;
            case R.id.nav_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new EventsFragment()).commit();
                break;
            case R.id.nav_gallery:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new GalleryFragment()).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new AboutUsFragment()).commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

}
