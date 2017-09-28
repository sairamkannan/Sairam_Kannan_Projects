package com.cs442.sairamkannan.sportsmgr;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Tab1Fragment extends Fragment{

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "Login_Credentials1";
    ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mFragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



        View rootView = inflater.inflate(R.layout.fitness_layout, container, false);
        i1 = (ImageView)rootView.findViewById(R.id.im1);
        i2 = (ImageView)rootView.findViewById(R.id.im2);
        i3 = (ImageView)rootView.findViewById(R.id.im3);
        i4 = (ImageView)rootView.findViewById(R.id.im4);
        i5 = (ImageView)rootView.findViewById(R.id.im5);
        i6 = (ImageView)rootView.findViewById(R.id.im6);
        i7 = (ImageView)rootView.findViewById(R.id.im7);
        i8 = (ImageView)rootView.findViewById(R.id.im8);
        i9 = (ImageView)rootView.findViewById(R.id.im9);
        i10 = (ImageView)rootView.findViewById(R.id.im10);
        i11 = (ImageView)rootView.findViewById(R.id.im11);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Baseball");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Cricket");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();

            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Soccer");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();

            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Football");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();
            }
        });
        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Tabletennis");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();
            }
        });
        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Bowling");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();
            }
        });
        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Billiards");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();
            }
        });
        i8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Basketball");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();
            }
        });
        i9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Chess");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();
            }
        });
        i10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Badminton");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();
            }
        });
        i11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Racquetball");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();
            }
        });

        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser == true)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recreational");
        }
    }


}
