package com.cs442.sairamkannan.sportsmgr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class CampusFragment extends Fragment {
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Button b1,b2,b3;
    String s1,s2,s3;
    TextView t1,t2,t3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Campus & Gym Info");

        setHasOptionsMenu(true);
        View myInflatedView = inflater.inflate(R.layout.campus_layout, container,false);
        b1 = (Button)myInflatedView.findViewById(R.id.bog_button);
        b2 = (Button)myInflatedView.findViewById(R.id.keating_button);
        b3 = (Button)myInflatedView.findViewById(R.id.stuart_button);
        t1 = (TextView)myInflatedView.findViewById(R.id.bog_add);
        t2 = (TextView)myInflatedView.findViewById(R.id.keating_add);
        t3 = (TextView)myInflatedView.findViewById(R.id.stuart_add);
        s1 = t1.getText().toString();
        s2 = t2.getText().toString();
        s3 = t3.getText().toString();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr="+s1;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr="+s2;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr="+s3;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        return myInflatedView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.campusmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.campusprofile:
               // mFragmentTransaction.replace(R.id.containerView, new CampusFragment()).commit();
                return super.onOptionsItemSelected(item);

            case R.id.saveprofile:
                mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
                //Toast.makeText((AppCompatActivity)getActivity(),"success",Toast.LENGTH_SHORT).show();
            default: return super.onOptionsItemSelected(item);

        }
    }


}
