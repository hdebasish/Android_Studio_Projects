package com.example.navigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id){

            case R.id.inbox:
                Toast.makeText(getApplicationContext(),"Inbox",Toast.LENGTH_SHORT).show();
                break;

            case R.id.starred:
                Toast.makeText(getApplicationContext(),"Starred",Toast.LENGTH_SHORT).show();
                break;

            case R.id.sent:
                Toast.makeText(getApplicationContext(),"Sent",Toast.LENGTH_SHORT).show();
                break;

            case R.id.drafts:
                Toast.makeText(getApplicationContext(),"Drafts",Toast.LENGTH_SHORT).show();
                break;

            case R.id.all_mail:
                Toast.makeText(getApplicationContext(),"All mail",Toast.LENGTH_SHORT).show();
                break;

            case R.id.trash:
                Toast.makeText(getApplicationContext(),"Trash",Toast.LENGTH_SHORT).show();
                break;

            case R.id.spam:
                Toast.makeText(getApplicationContext(),"Spam",Toast.LENGTH_SHORT).show();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }
}