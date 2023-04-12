package com.example.assignmentapp;

import android.content.Intent;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class ToolbarHelper {
    public static void setUpToolbar(AppCompatActivity activity) {
        DrawerLayout drawerLayout = activity.findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = activity.findViewById(R.id.NavigationView);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_account) {
                // Handle the account action
            } else if (id == R.id.nav_settings) {
                // Handle the settings action
            } else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(activity, Login.class);
                activity.startActivity(intent);
                activity.finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    public static void setUpNavigationDrawer(AppCompatActivity activity) {
        DrawerLayout drawerLayout = activity.findViewById(R.id.my_drawer_layout);
        NavigationView navigationView = activity.findViewById(R.id.NavigationView);
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        setUpDrawerToggle(activity, drawerLayout, toolbar);
        setUpNavigationView(activity, navigationView, drawerLayout);
    }

    private static void setUpDrawerToggle(AppCompatActivity activity, DrawerLayout drawerLayout, Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private static void setUpNavigationView(AppCompatActivity activity, NavigationView navigationView, DrawerLayout drawerLayout) {
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_account) {
                // Handle the account action
            } else if (id == R.id.nav_settings) {
                // Handle the settings action
            } else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(activity, Login.class);
                activity.startActivity(intent);
                activity.finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}


