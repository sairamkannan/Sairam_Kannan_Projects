package com.cs442.sairamkannan.sportsmgr;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PlayersListFragment extends Fragment {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "Login_Credentials1";
    String title=null;
    View rootView;
    String cap=null;
    ListView lv;
    String t_name=null;
    String u_name=null;
    String u_sport=null;
    String u_email=null;
    String team_name=null;
    int check = 0;
    Button b1;
    private List<PlayerNames> myTeams = new ArrayList<PlayerNames>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mFragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        title= sharedpreferences.getString("team", null);
        u_email= sharedpreferences.getString("email", null);
        u_sport = sharedpreferences.getString("sport", null);
        Cursor cs=null;
        DataHandler db = new DataHandler((AppCompatActivity)getActivity());
        db.open();
        cs = db.returnPlayerStats(u_email);
        cs.moveToFirst();
        //System.out.println(cs.getCount());

        while (!cs.isAfterLast()) {
            team_name = cs.getString(0);
            if(team_name.equals(title))
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
        rootView = inflater.inflate(R.layout.playerslist, container, false);
        lv = (ListView)rootView.findViewById(R.id.playerslist);
        b1 = (Button)rootView.findViewById(R.id.playersjoin);

        if(check == 1)
        {
            b1.setText("Leave Team");
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check == 1)
                {
                    DataHandler db1 = new DataHandler((AppCompatActivity)getActivity());
                    db1.open();
                    db1.removePlayerStats(u_email, u_sport);
                    Toast.makeText((AppCompatActivity) getActivity(), "You Have Been Removed From this team", Toast.LENGTH_SHORT).show();
                    b1.setText("Join Team");
                    check=0;
                    mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
                }
                else
                {
                    Cursor cs,cs1;

                    DataHandler db1 = new DataHandler((AppCompatActivity)getActivity());
                    db1.open();
                    cs = db1.returnPlayerCount(title, u_sport);
                    cs1 = db1.returnPlayerCount1(u_email,u_sport);


                    if(cs.getCount()>0)
                    {
                        if(cs1.getCount()>0)
                        {
                            cs1.moveToFirst();
                            String sp1 = cs1.getString(0);
                            Toast.makeText((AppCompatActivity)getActivity(),"You are already Part of "+sp1,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            long id1 = db1.insertPlayerStats(u_email, u_sport,title, "0");
                            Toast.makeText((AppCompatActivity)getActivity(),"Joined Successfully",Toast.LENGTH_SHORT).show();
                            b1.setText("Leave Team");
                            check=1;
                            mFragmentTransaction.replace(R.id.containerView, new PlayersListFragment()).commit();
                        }


                    }
                    else
                    {
                        if(cs1.getCount()>0)
                        {
                            cs1.moveToFirst();
                            String sp1 = cs1.getString(0);
                            Toast.makeText((AppCompatActivity)getActivity(),"You are already Part of "+sp1,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            long id1 = db1.insertPlayerStats(u_email, u_sport,title, "1");
                            Toast.makeText((AppCompatActivity)getActivity(),"Joined Successfully",Toast.LENGTH_SHORT).show();
                            b1.setText("Leave Team");
                            check=1;
                            mFragmentTransaction.replace(R.id.containerView, new PlayersListFragment()).commit();
                        }

                    }


                }
            }
        });

        setHasOptionsMenu(true);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);

        populateHotelList();
        populateListView();
       // registerClickCallBack();

        return rootView;
    }

    private void populateHotelList()
    {
        Cursor cs=null;
        DataHandler db = new DataHandler((AppCompatActivity)getActivity());
        db.open();
        cs = db.returnPlayers(title);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            t_name = cs.getString(0);
            cap = cs.getString(1);
            if(cap.equals("1"))
            {
                myTeams.add(new PlayerNames(t_name+"(C)"));
            }
           else
            {
                myTeams.add(new PlayerNames(t_name));
            }
            cs.moveToNext();
        }
    }

    private void populateListView()
    {
        final ArrayAdapter<PlayerNames> adapter = new MyListAdapter();
        final ListView lv = (ListView)rootView.findViewById(R.id.playerslist);

        //list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, strArr));
        lv.setAdapter(adapter);
        //registerClickCallBack();
    }

    public class MyListAdapter extends ArrayAdapter<PlayerNames>
    {
        public MyListAdapter() {
            super((AppCompatActivity) getActivity(), R.layout.custom_listview, myTeams);
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent)
        {
            View itemView = convertView;

            if (itemView == null) {
                itemView = ((AppCompatActivity)getActivity()).getLayoutInflater().inflate(R.layout.custom_listview, parent, false);
            }
            //find item to work with
            PlayerNames currentTeam = myTeams.get(position);

            TextView t1 = (TextView) itemView.findViewById(R.id.list_text);
            t1.setTextSize(20);

                t1.setText(currentTeam.getPlayer_name());



            return itemView;
            //return super.getView(position,convertView,parent);
        }
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
                mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
        }return super.onOptionsItemSelected(item);
    }


}
