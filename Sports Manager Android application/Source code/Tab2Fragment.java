package com.cs442.sairamkannan.sportsmgr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Tab2Fragment extends Fragment {
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "Login_Credentials1";
    ImageView i1,i2,i3,i4,i5,i6;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mFragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        View rootView = inflater.inflate(R.layout.recreational_layout, container, false);


        i1 = (ImageView)rootView.findViewById(R.id.rm1);
        i2 = (ImageView)rootView.findViewById(R.id.rm2);
        i3 = (ImageView)rootView.findViewById(R.id.rm3);
        i4 = (ImageView)rootView.findViewById(R.id.rm4);
        i5 = (ImageView)rootView.findViewById(R.id.rm5);
        i6 = (ImageView)rootView.findViewById(R.id.rm6);


        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Yoga");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo2()).commit();
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Pilates");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo2()).commit();

            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Swimming");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo2()).commit();

            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Zumba");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo2()).commit();
            }
        });
        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Kickboxing");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo2()).commit();
            }
        });
        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("sport","Cardio");
                editor.commit();
                mFragmentTransaction.replace(R.id.containerView, new Sportinfo2()).commit();
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
