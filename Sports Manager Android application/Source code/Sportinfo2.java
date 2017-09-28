package com.cs442.sairamkannan.sportsmgr;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Sportinfo2 extends Fragment {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "Login_Credentials1";
    Button b1;
    View rootView;
    String sport_name=null;
    String u_name=null;
    String u_email=null;
    String title = null;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    int check=0;
    TextView t1,t2,t3,t4,t5,t6,t7;
    ImageView i1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        title= sharedpreferences.getString("sport", null);
        u_email = sharedpreferences.getString("email", null);

        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fit_layout, container, false);
        t1 = (TextView)rootView.findViewById(R.id.lblWins);
        t2 = (TextView)rootView.findViewById(R.id.lblLosses);
        t3 = (TextView)rootView.findViewById(R.id.lblTies);
        t4 = (TextView)rootView.findViewById(R.id.tvWins);
        t5 = (TextView)rootView.findViewById(R.id.tvLosses);
        t6 = (TextView)rootView.findViewById(R.id.tvTies);
        t7 = (TextView)rootView.findViewById(R.id.sport1);
        b1 = (Button)rootView.findViewById(R.id.schedulebutton);
        i1 = (ImageView)rootView.findViewById(R.id.imview);

        t7.setText(title);

        Cursor cs=null;
        DataHandler db = new DataHandler((AppCompatActivity)getActivity());
        db.open();
        cs = db.returnPlayerStats1(u_email);
        cs.moveToFirst();
        //System.out.println(cs.getCount());

        while (!cs.isAfterLast()) {
            sport_name = cs.getString(0);
            if(sport_name.equals(title))
            {
                check = 1;
                break;
            }
            else
            {
                check = 0;
            }
           // System.out.println(sport_name);
            cs.moveToNext();
        }

        if(check == 1)
        {
            b1.setText("Remove From Schedule");
        }

        switch(title)
        {
            case "Yoga":
                i1.setImageResource(R.drawable.yoga);
                t1.setText("Mon");
                t2.setText("Wed");
                t3.setText("Fri");
                t4.setText("7am-8am");
                t5.setText("7am-8am");
                t6.setText("7am-8am");
                break;
            case "Pilates":
                t1.setText("Tue");
                t2.setText("Thu");
                t3.setText("Sat");
                t4.setText("6pm-7pm");
                t5.setText("6pm-7pm");
                t6.setText("6pm-7pm");
                i1.setImageResource(R.drawable.pilates);
                break;
            case "Swimming":
                t1.setText("Mon");
                t2.setText("Thu");
                t3.setText("Sat");
                t4.setText("2pm-3pm");
                t5.setText("2pm-3pm");
                t6.setText("2pm-3pm");
                i1.setImageResource(R.drawable.swimming);
                break;
            case "Zumba":
                t1.setText("Tue");
                t2.setText("Wed");
                t3.setText("Fri");
                t4.setText("5pm-6pm");
                t5.setText("6pm-7pm");
                t6.setText("7pm-8pm");
                i1.setImageResource(R.drawable.zumba);
                break;
            case "Kickboxing":
                t1.setText("Mon");
                t2.setText("Wed");
                t3.setText("Sat");
                t4.setText("4pm-5pm");
                t5.setText("4pm-5pm");
                t6.setText("4pm-5pm");
                i1.setImageResource(R.drawable.kickboxing);
                break;
            case "Cardio":
                t1.setText("Mon");
                t2.setText("Tue");
                t3.setText("Wed");
                t4.setText("7pm-8pm");
                t5.setText("7pm-8pm");
                t6.setText("7pm-8pm");
                i1.setImageResource(R.drawable.cardio);
                break;
            default:
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check==0)
                {
                    DataHandler db1 = new DataHandler((AppCompatActivity)getActivity());
                    db1.open();
                    try{
                        long id1 = db1.insertPlayerStats(u_email, title,title, "0");
                        Toast.makeText((AppCompatActivity)getActivity(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                        b1.setText("Remove From Schedule");
                        check=1;

                      /*  if(title.equals("Zumba"))
                        {
                            Calendar calendar = Calendar.getInstance();

                            Intent myIntent = new Intent((AppCompatActivity)getActivity(), notify.class);
                            //pendingIntent = PendingIntent.getBroadcast((AppCompatActivity)getActivity(), 0, myIntent, 0);
                            pendingIntent = PendingIntent.getService(getContext(),21,myIntent,0);
                            calendar.set(Calendar.HOUR_OF_DAY, 18);
                            calendar.set(Calendar.MINUTE, 30);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.AM_PM, Calendar.PM);

                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 12 * 60 * 60 * 1000, pendingIntent);
                            System.out.println("reached alarm");

                        }*/
                    }
                    catch (Exception e)
                    {
                        Toast.makeText((AppCompatActivity)getActivity(),"You are already Registered for this Sport",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    DataHandler db1 = new DataHandler((AppCompatActivity)getActivity());
                    db1.open();
                    Cursor cs1;
                    db1.removePlayerStats(u_email, title);
                    Toast.makeText((AppCompatActivity)getActivity(),"You Have Been Unregistered",Toast.LENGTH_SHORT).show();
                   // getActivity().stopService(new Intent(getContext(), notify.class));
                    b1.setText("Add to Schedule");
                    check=0;
                }


            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sport_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sport_profile_menu:
              //  Intent intent = new Intent(getActivity(), MainActivity.class);
             //   startActivity(intent);
                mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
        }return super.onOptionsItemSelected(item);
    }
    }
