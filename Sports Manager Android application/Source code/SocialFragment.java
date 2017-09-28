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

import java.util.ArrayList;
import java.util.List;


public class SocialFragment extends Fragment {

    ListView lv;
    View rootView;
    String u_email=null;
    String setsport=null;
    String sport_name=null;
    String t_name=null;
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "Login_Credentials1";
    private List<myTeamInfo> myTeams = new ArrayList<myTeamInfo>();
    String sport_type=null;
       @Nullable
       @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
       {
           rootView = inflater.inflate(R.layout.social_layout, container, false);
           sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
           u_email= sharedpreferences.getString("email", null);
           lv = (ListView)rootView.findViewById(R.id.mylist);



           populateHotelList();
           populateListView();
           return rootView;
       }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser == true)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Activities");
        }
    }


    private void populateHotelList()
    {

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
        final ListView lv = (ListView)rootView.findViewById(R.id.mylist);

        //list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, strArr));
        lv.setAdapter(adapter);
        //registerClickCallBack();
    }


    public class MyListAdapter extends ArrayAdapter<myTeamInfo>
    {
        public MyListAdapter() {
            super((AppCompatActivity)getActivity(), R.layout.activities_custom_list, myTeams);
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent)
        {
            View itemView = convertView;

            if (itemView == null) {
                itemView = ((AppCompatActivity)getActivity()).getLayoutInflater().inflate(R.layout.activities_custom_list, parent, false);
            }
            //find item to work with
            myTeamInfo currentTeam = myTeams.get(position);

            TextView t1 = (TextView) itemView.findViewById(R.id.textView);
            t1.setText(currentTeam.getTeam_name());

            TextView t2 = (TextView) itemView.findViewById(R.id.textView3);
            setsport = currentTeam.getSport_name();

            if(setsport.equals("Baseball")||setsport.equals("Cricket")||setsport.equals("Soccer")||setsport.equals("Football")||setsport.equals("Tabletennis")||setsport.equals("Bowling")||setsport.equals("Billiards")||setsport.equals("Basketball")||setsport.equals("Chess")||setsport.equals("Badminton")||setsport.equals("Racquetball"))
            {
                sport_type="Recreational Sports";
            }
            else
            {
                sport_type="Fitness";
            }
            t2.setText(sport_type+" -> "+setsport);

            return itemView;
            //return super.getView(position,convertView,parent);
        }
    }
}
