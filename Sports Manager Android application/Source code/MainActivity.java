package com.cs442.sairamkannan.sportsmgr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    String u_gender=null;
    FragmentTransaction mFragmentTransaction;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tv1 = (TextView)findViewById(R.id.welcome_user);
        String MyPREFERENCES = "Login_Credentials1";
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String u_email = sharedpreferences.getString("email", null);

        String u_name=null;


        Cursor cs=null;
        DataHandler db = new DataHandler(MainActivity.this);
        db.open();
        cs = db.returnPlayerName(u_email);
        if(cs!=null)
        {
            cs.moveToFirst();
            u_name = cs.getString(0);
            //Toast.makeText(MainActivity.this,"got data"+cs.getString(0),Toast.LENGTH_SHORT).show();
        }

        Cursor cs1;
        DataHandler db1 = new DataHandler(MainActivity.this);
        db1.open();
        cs1 = db.returnPlayerGender(u_email);
        if(cs1!=null)
        {
            cs1.moveToFirst();
            u_gender = cs1.getString(0);
            //Toast.makeText(MainActivity.this,"got data"+cs.getString(0),Toast.LENGTH_SHORT).show();
        }




        //String u_password = sharedpreferences.getString("password",null);

        NavigationView navigationView = (NavigationView) findViewById(R.id.shitstuff);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.welcome_user);
        ImageView i = (ImageView)hView.findViewById(R.id.imageView);
        nav_user.setText("Hi, "+u_name);


        if(u_gender.equals("Male"))
            i.setImageResource(R.drawable.male);
        else
            i.setImageResource(R.drawable.user10);


        Context context = getBaseContext();
        Window window = getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(context,R.color.black));


        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();



                if (menuItem.getItemId() == R.id.nav_home) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_gym) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new CampusFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_settings) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new SettingsFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_profile) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new ProfileFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_logout) {
                    String MyPREFERENCES = "Login_Credentials1";
                    SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("email","");
                    editor.putString("password","");
                    editor.commit();
                    Intent inte = new Intent(MainActivity.this,Login1.class);
                    startActivity(inte);
                }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

         toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.addDrawerListener(mDrawerToggle);//DrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    @Override
    public void onBackPressed()
    {

    }


}