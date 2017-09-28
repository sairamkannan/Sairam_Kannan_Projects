package com.cs442.sairamkannan.sportsmgr;



import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PrimaryFragment extends Fragment {

    ListView lv;
    String u_email=null;
    String setsport=null;
    String sport_name=null;
    String reportDate,reportDate1;
    String t_name=null;
    String t_name1=null;
    String title=null;
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "Login_Credentials1";

    private List<myTeamInfo> myTeams = new ArrayList<myTeamInfo>();
    private List<myTeamInfo2> myTeams1 = new ArrayList<myTeamInfo2>();
    String sport_type=null;
    Calendar cal,cal1,cal2,cal3,cal4,cal5,cal6,cal7;
   // public static int counter=0;
    DateFormat dateFormat,dateFormat1;

    int month,day,year,age,s_month,s_day,s_year,res_mon,res_day,res_year;


    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Schedule");
        rootView = inflater.inflate(R.layout.primary_layout, container, false);
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        u_email= sharedpreferences.getString("email", null);
        title= sharedpreferences.getString("sport", null);
        lv = (ListView)rootView.findViewById(R.id.mylist1);
        dateFormat = new SimpleDateFormat("MM/dd/yyyy @ h:mm a");
        dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
        cal = Calendar.getInstance();






        cal7=Calendar.getInstance();
      //  cal.add(Calendar.DATE,counter++);
        reportDate = dateFormat.format(cal.getTime());


        populateHotelList();
        populateListView();
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser == true)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Schedule");
        }
    }
    private void populateHotelList()
    {
        myTeams.clear();
        Cursor cs=null;
        DataHandler db = new DataHandler((AppCompatActivity)getActivity());

        db.open();

        cs = db.returnPlayerStats(u_email);
        if(cs!=null)
        {
            cs.moveToFirst();
            while (!cs.isAfterLast()) {
                t_name = cs.getString(0);
                sport_name = cs.getString(1);
                myTeams.add(new myTeamInfo(t_name,sport_name));
                cs.moveToNext();
            }
        }

    }
    private void populateListView()
    {
        final ArrayAdapter<myTeamInfo> adapter = new MyListAdapter();
        final ListView lv = (ListView)rootView.findViewById(R.id.mylist1);

        //list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, strArr));
        lv.setAdapter(adapter);
        adapter.setNotifyOnChange(false);

        adapter.notifyDataSetChanged();
        //registerClickCallBack();
    }
    public class MyListAdapter extends ArrayAdapter<myTeamInfo>
    {

        public MyListAdapter() {
            super((AppCompatActivity)getActivity(), R.layout.activities_schedule_list, myTeams);
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = ((AppCompatActivity) getActivity()).getLayoutInflater().inflate(R.layout.activities_schedule_list, parent, false);
            }
            //find item to work with
            myTeamInfo currentTeam = myTeams.get(position);

            TextView t1 = (TextView) itemView.findViewById(R.id.textView);
            t1.setText(currentTeam.getTeam_name() +" vs Chicago Bears ");


           TextView t2 = (TextView) itemView.findViewById(R.id.textView3);
            setsport = currentTeam.getSport_name();
            t2.setText("Location: Stuart Field");
            // counter=counter+2;
           TextView t3 = (TextView) itemView.findViewById(R.id.textView4);
            t3.setText("Date&Time: " + reportDate);

            if (setsport.equals("Baseball") || setsport.equals("Cricket") || setsport.equals("Soccer") || setsport.equals("Football") || setsport.equals("Tabletennis") || setsport.equals("Bowling") || setsport.equals("Billiards") || setsport.equals("Basketball") || setsport.equals("Chess") || setsport.equals("Badminton") || setsport.equals("Racquetball")) {
                sport_type = "Recreational Sports";


            }
       /*     if (setsport.equals("Soccer"))
            {
            // Code for recreation sport scheduling.
               Cursor cs1 = null;
                Cursor cs2 = null;
                DataHandler db1 = new DataHandler((AppCompatActivity)getActivity());
                DataHandler db2 = new DataHandler((AppCompatActivity)getActivity());

                db1.open();
                db2.open();
                cs1 = db1.returnTeamData(title);
                cs2 = db2.returnPlayerStats(u_email);
                cs1.moveToFirst();
                cs2.moveToFirst();
                String str = "";
                String arr[] = new String[cs2.getCount()];
                int x=0;
                while (!cs2.isAfterLast()) {

                    arr[x] = cs2.getString(0);
                    x++;
                    // System.out.println(sport_name);
                    cs2.moveToNext();
                }
            //    for(int i=0;i<arr.length;i++)
              //  {
                //    if(!arr[i].equals("Yoga")&&!arr[i].equals("Zumba")&&!arr[i].equals("Swimming")&&!arr[i].equals("Pilates")&&!arr[i].equals("Kickboxing")&&!arr[i].equals("Cardio"))
                  //      str += arr[i]+"\n";
              //      System.out.println("My registered team names from primary fragment " +str);
             //   }
                while (!cs1.isAfterLast()) {
                    t_name1 = cs1.getString(0);
                    for(int i=0;i<arr.length;i++)
                    {
                        if(!arr[i].equals("Yoga")&&!arr[i].equals("Zumba")&&!arr[i].equals("Swimming")&&!arr[i].equals("Pilates")&&!arr[i].equals("Kickboxing")&&!arr[i].equals("Cardio")) {
                            str += arr[i] + "\n";
                            if (str.equals(t_name1) && cs1.getCount() >=1)
                            {
                                cs1.moveToFirst();
                                while(!str.equals(t_name1))
                                {
                                    t1.setText(str + " vs " + t_name1);
                                    cs1.moveToNext();
                                }
                            }
                        }

                       // System.out.println("My registered team names from primary fragment " +str);
                    }

                   // System.out.println("Teams registered in the same sport from primary fragment " + t_name1);

                    cs1.moveToNext();

                }


            } */
            else if(setsport.equals("Yoga"))
            {
                sport_type="Fitness";
                Calendar calendar = Calendar.getInstance();
                cal1=Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                int hour = calendar.get(Calendar.HOUR);
                switch (day) {
                    case Calendar.SUNDAY:
                        int counter=0;
                        cal1.add(Calendar.DATE,counter+1);
                        reportDate1=dateFormat1.format(cal1.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");
                        t3.setText("Next class is on " +reportDate1+ " from 7am-8am");
                        break;
                        // Current day is Sunday

                    case Calendar.MONDAY:
                        if((calendar.get(Calendar.AM_PM)==Calendar.AM && hour>=8) || calendar.get(Calendar.AM_PM)==Calendar.PM)
                        {
                            int countera=0;
                            cal1.add(Calendar.DATE,countera+2);
                            reportDate1=dateFormat1.format(cal1.getTime());
                           // t3.setText("Next class is on Wednesday from 7am-8am");
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Next class is on " +reportDate1+ " from 7am-8am");

                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 7am-8am");
                        }
                            break;
                        // Current day is Monday

                    case Calendar.TUESDAY:
                        int counterb=0;
                        cal1.add(Calendar.DATE,counterb+1);
                        reportDate1=dateFormat1.format(cal1.getTime());
                      //  t3.setText("Next class is on Wednesday from 7am-8am");
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");
                        t3.setText("Next class is on " +reportDate1+ " from 7am-8am");
                        break;
                    case Calendar.WEDNESDAY:
                        if((calendar.get(Calendar.AM_PM)==Calendar.AM && hour>=8) || calendar.get(Calendar.AM_PM)==Calendar.PM)
                        {
                            int counterf=0;
                            cal1.add(Calendar.DATE,counterf+2);
                            reportDate1=dateFormat1.format(cal1.getTime());
                           // t3.setText("Next class is on Friday from 7am-8am");
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Next class is on " +reportDate1+ " from 7am-8am");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 7am-8am");
                        }
                        break;
                    case Calendar.THURSDAY:
                        int counterc=0;
                        cal1.add(Calendar.DATE,counterc+1);
                        reportDate1=dateFormat1.format(cal1.getTime());
                       // t3.setText("Next class is on Friday from 7am-8am");
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");
                        t3.setText("Next class is on " +reportDate1+ " from 7am-8am");
                        break;
                    case Calendar.FRIDAY:
                        int counterd=0;
                        if((calendar.get(Calendar.AM_PM)==Calendar.AM && hour>=8) || calendar.get(Calendar.AM_PM)==Calendar.PM)
                        {
                            cal1.add(Calendar.DATE,counterd+3);
                            reportDate1=dateFormat1.format(cal1.getTime());
                         //   t3.setText("Next class is on Monday from 7am-8am");
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Next class is on " +reportDate1+ " from 7am-8am");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 7am-8am");

                        }
                        break;
                    case Calendar.SATURDAY:
                        int countere=0;
                        cal1.add(Calendar.DATE,countere+2);
                        reportDate1=dateFormat1.format(cal1.getTime());
                      //  t3.setText("Next class is on Monday from 7am-8am");
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");
                        t3.setText("Next class is on " +reportDate1+ " from 7am-8am");
                        break;
                        // etc.
                }


            }
            else if(setsport.equals("Pilates"))
            {
                sport_type="Fitness";
                Calendar calendar = Calendar.getInstance();
                cal2=Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                int hour = calendar.get(Calendar.HOUR);
                switch (day) {
                    case Calendar.SUNDAY:
                        int counter=0;
                        cal2.add(Calendar.DATE,counter+2);
                        reportDate1=dateFormat1.format(cal2.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 6pm-7pm");
                        //t3.setText("Next class is on Tuesday from 6pm-7pm");
                        // Current day is Sunday

                    case Calendar.MONDAY:
                        int countera=0;
                        cal2.add(Calendar.DATE,countera+1);
                        reportDate1=dateFormat1.format(cal2.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 6pm-7pm");

                       // t3.setText("Next class is on Tuesday from 6pm-7pm");
                        break;
                    // Current day is Monday

                    case Calendar.TUESDAY:
                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=7)
                        {
                            int counterb=0;
                            cal2.add(Calendar.DATE,counterb+2);
                            reportDate1=dateFormat1.format(cal2.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 6pm-7pm");
                           // t3.setText("Next class is on Thursday from 6pm-7pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 6pm-7pm");
                        }
                        break;
                    case Calendar.WEDNESDAY:
                        int counterc=0;
                        cal2.add(Calendar.DATE,counterc+1);
                        reportDate1=dateFormat1.format(cal2.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 6pm-7pm");

                       // t3.setText("Next class is on Thursday from 6pm-7pm");
                        break;
                    case Calendar.THURSDAY:
                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=7)
                        {
                            int counterd=0;
                            cal2.add(Calendar.DATE,counterd+2);

                            reportDate1=dateFormat1.format(cal2.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 6pm-7pm");
                           // t3.setText("Next class is on Saturday from 6pm-7pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 6pm-7pm");
                        }
                        break;

                    case Calendar.FRIDAY:
                        int countere=0;
                        cal2.add(Calendar.DATE,countere+3);
                        reportDate1=dateFormat1.format(cal2.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 6pm-7pm");

                       // t3.setText("Next class is on Saturday from 6pm-7pm");
                        break;
                    case Calendar.SATURDAY:
                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=7)
                        {
                            int counterf=0;
                            cal2.add(Calendar.DATE,counterf+3);
                            reportDate1=dateFormat1.format(cal2.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 6pm-7pm");
                           // t3.setText("Next class is on Tuesday from 6pm-7pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 6pm-7pm");
                        }
                        break;
                    // etc.
                }


            }
            else if(setsport.equals("Zumba"))
            {
                sport_type="Fitness";
                Calendar calendar = Calendar.getInstance();
                cal3=Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                int hour = calendar.get(Calendar.HOUR);
                switch (day) {
                    case Calendar.SUNDAY:
                        int counter=0;
                        cal3.add(Calendar.DATE,counter+2);
                        reportDate1=dateFormat1.format(cal3.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 5pm-6pm");
                       // t3.setText("Next class is on Tuesday from 5pm-6pm");
                        // Current day is Sunday

                    case Calendar.MONDAY:
                        int countera=0;
                        cal3.add(Calendar.DATE,countera+1);
                        reportDate1=dateFormat1.format(cal3.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 5pm-6pm");

                       // t3.setText("Next class is on Tuesday from 5pm-6pm");
                        break;
                    // Current day is Monday

                    case Calendar.TUESDAY:
                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=6)
                        {
                            int counterb=0;
                            cal3.add(Calendar.DATE,counterb+1);
                            reportDate1=dateFormat1.format(cal3.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Next class is on " +reportDate1+ " from 6pm-7pm");
                            //t3.setText("Next class is on Wednesday from 6pm-7pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 5pm-6pm");
                        }
                        break;
                    case Calendar.WEDNESDAY:

                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=7)
                        {
                            int counterc=0;
                            cal3.add(Calendar.DATE,counterc+2);
                            reportDate1=dateFormat1.format(cal3.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Next class is on " +reportDate1+ " from 7pm-8pm");
                          //  t3.setText("Next class is on Friday from 7pm-8pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 6pm-7pm");
                        }
                        break;
                    case Calendar.THURSDAY:
                        int counterd=0;
                        cal3.add(Calendar.DATE,counterd+1);
                        reportDate1=dateFormat1.format(cal3.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 7pm-8pm");
                      //  t3.setText("Next class is on Friday from 7pm-8pm");
                        break;

                    case Calendar.FRIDAY:

                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=8)
                        {
                            int countere=0;
                            cal3.add(Calendar.DATE,countere+4);
                            reportDate1=dateFormat1.format(cal3.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 5pm-6pm");
                            //t3.setText("Next class is on Tuesday from 5pm-6pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 7pm-8pm");
                        }
                        break;
                    case Calendar.SATURDAY:
                        int counterf=0;
                        cal3.add(Calendar.DATE,counterf+3);
                        reportDate1=dateFormat1.format(cal3.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 5pm-6pm");

                            //t3.setText("Next class is on Tuesday from 5pm-6pm");

                        break;
                    // etc.
                }


            }
            else if(setsport.equals("Swimming"))
            {
                sport_type="Fitness";
                Calendar calendar = Calendar.getInstance();
                cal4=Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                int hour = calendar.get(Calendar.HOUR);
                switch (day) {
                    case Calendar.SUNDAY:
                        int counter=0;
                        cal4.add(Calendar.DATE,counter+1);
                        reportDate1=dateFormat1.format(cal4.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 2pm-3pm");
                        //t3.setText("Next class is on Monday from 2pm-3pm");
                        // Current day is Sunday

                    case Calendar.MONDAY:

                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=3)
                        {
                            int countera=0;
                            cal4.add(Calendar.DATE,countera+3);
                            reportDate1=dateFormat1.format(cal4.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 2pm-3pm");
                            //t3.setText("Next class is on Thursday from 2pm-3pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 2pm-3pm");
                        }
                        break;
                    // Current day is Monday

                    case Calendar.TUESDAY:
                        int counterc=0;
                        cal4.add(Calendar.DATE,counterc+2);
                        reportDate1=dateFormat1.format(cal4.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 2pm-3pm");

                          //  t3.setText("Next class is on Thursday from 2pm-3pm");

                        break;
                    case Calendar.WEDNESDAY:
                        int counterd=0;
                        cal4.add(Calendar.DATE,counterd+1);
                        reportDate1=dateFormat1.format(cal4.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 2pm-3pm");

                      //  t3.setText("Next class is on Thursday from 2pm-3pm");
                        break;
                    case Calendar.THURSDAY:
                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=3)
                        {
                            int countere=0;
                            cal4.add(Calendar.DATE,countere+2);
                            reportDate1=dateFormat1.format(cal4.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 2pm-3pm");
                          //  t3.setText("Next class is on Saturday from 2pm-3pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 2pm-3pm");
                        }
                        break;

                    case Calendar.FRIDAY:
                        int counterf=0;
                        cal4.add(Calendar.DATE,counterf+1);
                        reportDate1=dateFormat1.format(cal4.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 2pm-3pm");

                       // t3.setText("Next class is on Saturday from 2pm-3pm");
                        break;
                    case Calendar.SATURDAY:
                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=3)
                        {
                            int counterg=0;
                            cal4.add(Calendar.DATE,counterg+2);
                            reportDate1=dateFormat1.format(cal4.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 2pm-3pm");
                           // t3.setText("Next class is on Monday from 2pm-3pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 2pm-3pm");
                        }
                        break;
                    // etc.
                }


            }
            else if(setsport.equals("Kickboxing"))
            {
                sport_type="Fitness";
                Calendar calendar = Calendar.getInstance();
                cal5=Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                int hour = calendar.get(Calendar.HOUR);
                switch (day) {
                    case Calendar.SUNDAY:
                        int counter=0;
                        cal5.add(Calendar.DATE,counter+1);
                        reportDate1=dateFormat1.format(cal5.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 4pm-5pm");
                      //  t3.setText("Next class is on Monday from 4pm-5pm");
                        // Current day is Sunday

                    case Calendar.MONDAY:

                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=5)
                        {
                            int countera=0;
                            cal5.add(Calendar.DATE,countera+2);
                            reportDate1=dateFormat1.format(cal5.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 4pm-5pm");
                           // t3.setText("Next class is on Wednesday from 4pm-5pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 4pm-5pm");
                        }
                        break;
                    // Current day is Monday

                    case Calendar.TUESDAY:
                        int counterb=0;
                        cal5.add(Calendar.DATE,counterb+1);
                        reportDate1=dateFormat1.format(cal5.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 4pm-5pm");

                      //  t3.setText("Next class is on Wednesday from 4pm-5pm");

                        break;
                    case Calendar.WEDNESDAY:

                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=5)
                        {
                            int counterc=0;
                            cal5.add(Calendar.DATE,counterc+3);
                            reportDate1=dateFormat1.format(cal5.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 4pm-5pm");
                         //   t3.setText("Next class is on Saturday from 4pm-5pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Class will be held today from 4pm-5pm");
                        }
                        break;
                    case Calendar.THURSDAY:
                        int counterd=0;
                        cal5.add(Calendar.DATE,counterd+2);
                        reportDate1=dateFormat1.format(cal5.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 4pm-5pm");
                      //  t3.setText("Next class is on Saturday from 4pm-5pm");
                        break;

                    case Calendar.FRIDAY:
                        int countere=0;
                        cal5.add(Calendar.DATE,countere+1);
                        reportDate1=dateFormat1.format(cal5.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 4pm-5pm");

                       // t3.setText("Next class is on Saturday from 4pm-5pm");
                        break;
                    case Calendar.SATURDAY:
                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=5)
                        {
                            int counterf=0;
                            cal5.add(Calendar.DATE,counterf+2);
                            reportDate1=dateFormat1.format(cal5.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 4pm-5pm");
                           // t3.setText("Next class is on Monday from 4pm-5pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 4pm-5pm");
                        }
                        break;
                    // etc.
                }


            }
            else if(setsport.equals("Cardio"))
            {
                sport_type="Fitness";
                Calendar calendar = Calendar.getInstance();
                cal6=Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                int hour = calendar.get(Calendar.HOUR);
                switch (day) {
                    case Calendar.SUNDAY:
                        int counter=0;
                        cal6.add(Calendar.DATE,counter+1);
                        reportDate1=dateFormat1.format(cal6.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");

                        t3.setText("Next class is on " +reportDate1+ " from 7pm-8pm");
                       // t3.setText("Next class is on Monday from 7pm-8pm");
                        // Current day is Sunday

                    case Calendar.MONDAY:

                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=8)
                        {
                            int countera=0;
                            cal6.add(Calendar.DATE,countera+1);
                            reportDate1=dateFormat1.format(cal6.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");

                            t3.setText("Next class is on " +reportDate1+ " from 7pm-8pm");
                          //  t3.setText("Next class is on Tuesday from 7pm-8pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 7pm-8pm");
                        }
                        break;
                    // Current day is Monday

                    case Calendar.TUESDAY:

                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=8)
                        {
                            int counterb=0;
                            cal6.add(Calendar.DATE,counterb+1);
                            reportDate1=dateFormat1.format(cal6.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Next class is on " +reportDate1+ " from 7pm-8pm");
                          //  t3.setText("Next class is on Wednesday from 7pm-8pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 7pm-8pm");
                        }

                        break;
                    case Calendar.WEDNESDAY:

                        if(calendar.get(Calendar.AM_PM)==Calendar.PM && hour>=8)
                        {
                            int counterc=0;
                            cal6.add(Calendar.DATE,counterc+5);
                            reportDate1=dateFormat1.format(cal6.getTime());
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Next class is on " +reportDate1+ " from 7pm-8pm");
                           // t3.setText("Next class is on Monday from 7pm-8pm");
                        }
                        else {
                            t1.setText(currentTeam.getTeam_name());
                            t2.setText("Location: Keating Hall");
                            t3.setText("Class will be held today from 7pm-8pm");
                        }
                        break;
                    case Calendar.THURSDAY:
                        int counterd=0;
                        cal6.add(Calendar.DATE,counterd+4);
                        reportDate1=dateFormat1.format(cal6.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");
                        t3.setText("Next class is on " +reportDate1+ " from 7pm-8pm");
                        //t3.setText("Next class is on Monday from 7pm-8pm");
                        break;

                    case Calendar.FRIDAY:
                        int countere=0;
                        cal6.add(Calendar.DATE,countere+3);
                        reportDate1=dateFormat1.format(cal6.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");
                        t3.setText("Next class is on " +reportDate1+ " from 7pm-8pm");

                      //  t3.setText("Next class is on Monday from 7pm-8pm");
                        break;
                    case Calendar.SATURDAY:
                        int counterf=0;
                        cal6.add(Calendar.DATE,counterf+2);
                        reportDate1=dateFormat1.format(cal6.getTime());
                        t1.setText(currentTeam.getTeam_name());
                        t2.setText("Location: Keating Hall");
                        t3.setText("Next class is on " +reportDate1+ " from 7pm-8pm");
                        //t3.setText("Next class is on Monday from 7pm-8pm");

                        break;
                    // etc.
                }


            }

           // t2.setText(sport_type+" -> "+setsport);

            return itemView;
            //return super.getView(position,convertView,parent);
        }
    }
}