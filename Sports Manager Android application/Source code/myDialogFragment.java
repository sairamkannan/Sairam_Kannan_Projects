package com.cs442.sairamkannan.sportsmgr;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class myDialogFragment extends Fragment {
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SharedPreferences sharedpreferences;
    String MyPREFERENCES = "Login_Credentials1";
    View rootView;
    String u_name=null;
    String u_email=null;
    String title=null;
    EditText e1,e2,e3;
    Button b1,b2;
    int stat;
    String max_players;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        rootView = inflater.inflate(R.layout.extra, container, false);
        e1 = (EditText)rootView.findViewById(R.id.editText3);
        e2 = (EditText)rootView.findViewById(R.id.editText4);
        e3 = (EditText)rootView.findViewById(R.id.editText2);
        b1 = (Button)rootView.findViewById(R.id.button_dialog);
        b2 = (Button)rootView.findViewById(R.id.button_dialog_cancel);

        setHasOptionsMenu(true);

        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        title= sharedpreferences.getString("sport", null);
        u_email= sharedpreferences.getString("email", null);

        if(title.equals("Cricket")||title.equals("Baseball")||title.equals("Soccer")||title.equals("Football"))
        {
            max_players="11";
        }
        else if(title.equals("Bowling")||title.equals("Tabletennis")||title.equals("Billiards")||title.equals("Racquetball")||title.equals("Badminton"))
        {
            max_players="2";
        }
        else if(title.equals("Chess"))
        {
            max_players="1";
        }
        else if(title.equals("Basketball"))
        {
            max_players="5";
        }
        else
        {
            max_players = "100";
        }

        Cursor cs=null;
        DataHandler db = new DataHandler((AppCompatActivity)getActivity());
        db.open();
        cs = db.returnPlayerName(u_email);
        if(cs!=null)
        {
            cs.moveToFirst();
            u_name = cs.getString(0);
            //Toast.makeText(MainActivity.this,"got data"+cs.getString(0),Toast.LENGTH_SHORT).show();
        }

        e1.setText(title);
        e2.setText("(C) " + u_name);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFragmentTransaction.replace(R.id.containerView, new Sportinfo()).commit();
            }
        });

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(TextUtils.isEmpty(e3.getText()))
                {
                    e3.setError("Enter a Team Name");
                }
                else if(TextUtils.isDigitsOnly(e3.getText()))
                {
                    e3.setError("Team Should Have characters");
                }
                else
                {
                    DataHandler db1 = new DataHandler((AppCompatActivity)getActivity());
                    db1.open();

                    try {
                        long id1 = db1.insertPlayerStats(u_email, title, e3.getText().toString(), "1");
                        stat = 0;
                    }
                    catch (Exception e)
                    {
                        stat = 1;
                        Toast.makeText((AppCompatActivity)getActivity(),"You are already part of a team",Toast.LENGTH_SHORT).show();
                        mFragmentTransaction.replace(R.id.containerView, new UpdatesFragment()).commit();
                    }
                    if(stat==0)
                    {
                        try{
                            long id = db1.insertTeamData(e3.getText().toString(),title,u_name,max_players);
                            Toast.makeText((AppCompatActivity)getActivity(),"Team Created",Toast.LENGTH_SHORT).show();
                            mFragmentTransaction.replace(R.id.containerView, new UpdatesFragment()).commit();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText((AppCompatActivity)getActivity(),"Team Name exists Choose Different Name",Toast.LENGTH_SHORT).show();
                        }
                    }

                }

            }
        });


        return rootView;
    }
}
