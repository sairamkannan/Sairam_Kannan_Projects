package com.cs442.sairamkannan.sportsmgr;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsFragment extends Fragment {
    private Pattern pattern;
    private Matcher matcher;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{6,10})";
    DataHandler db1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        pattern = Pattern.compile(PASSWORD_PATTERN);

        String MyPREFERENCES = "Login_Credentials1";
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String u_email = sharedpreferences.getString("email", null);
        String u_name=null,u_phone=null,u_dob=null,u_gender=null,u_notify=null,u_password=null;

        Cursor cs;
        DataHandler db = new DataHandler(this.getActivity());
        db1 = new DataHandler(this.getActivity());
        db.open();
        cs = db.returnPlayerData1(u_email);
        if(cs.moveToFirst())
        {
            u_name = cs.getString(0);
            u_password=cs.getString(2);
            u_dob=cs.getString(4);
            u_phone=cs.getString(3);
            u_gender=cs.getString(5);
            u_notify=cs.getString(7);

        }while(cs.moveToNext());

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Settings");
        setHasOptionsMenu(true);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.drawable.set);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Activities");
        //((MainActivity) getActivity()).getSupportActionBar().setTitle("Your title");
        View myInflatedView = inflater.inflate(R.layout.settings_layout, container,false);

        EditText e_name = (EditText)myInflatedView.findViewById(R.id.edtName);
        final EditText e_phone = (EditText)myInflatedView.findViewById(R.id.edtPhone);
        EditText e_dob = (EditText)myInflatedView.findViewById(R.id.edtDob);
        EditText e_email = (EditText)myInflatedView.findViewById(R.id.edtEmail);
        EditText s_gender = (EditText)myInflatedView.findViewById(R.id.spnGender);
        EditText e_notify = (EditText)myInflatedView.findViewById(R.id.edtNotify);
        final EditText e_password = (EditText)myInflatedView.findViewById(R.id.edtPassword);
        Button e_change = (Button)myInflatedView.findViewById(R.id.btnChange);


        ImageView i = (ImageView)myInflatedView.findViewById(R.id.imgProfile1);

        if(u_gender.equals("Male"))
            i.setImageResource(R.drawable.male);
        else
            i.setImageResource(R.drawable.user10);

        e_name.setText(u_name);
        e_phone.setText(u_phone);
        e_dob.setText(u_dob);
        e_email.setText(u_email);
        e_notify.setText(u_notify+" Days");
        e_password.setText(u_password);
        s_gender.setText(u_gender);

        e_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(e_phone.getText())||TextUtils.isEmpty(e_password.getText()))
                {
                    //Toast.makeText((AppCompatActivity)getActivity(),"Please Enter a valid Password and Phone Number",Toast.LENGTH_SHORT).show();
                    e_password.setError("Required");
                    e_phone.setError("Required");
                }
                else if(e_phone.getText().length()<10 && !validate(e_password.getText().toString().trim()))
                {
                    e_password.setError("Password must contain \nOne UpperCase \nOne Lowercase \nOne Digit \nOne Special Character");
                    e_phone.setError("Phone number must be 10 digits long");
                }
                else if(e_phone.getText().length()<10)
                {
                    e_phone.setError("Phone number must be 10 digits long");
                }

                else if(!validate(e_password.getText().toString().trim()))
                {
                    e_password.setError("Password must contain \nOne UpperCase \nOne Lowercase \nOne Digit \nOne Special Character");
                }
                else
                {

                    db1.open();
                    long id = db1.updatePlayerData(e_password.getText().toString(),e_phone.getText().toString(),u_email);
                    Toast.makeText((AppCompatActivity)getActivity(), "Changes Successful", Toast.LENGTH_LONG).show();
                }
            }
        });

        return myInflatedView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.settings_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.saveprofile:
                mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();
                //Toast.makeText((AppCompatActivity)getActivity(),"success",Toast.LENGTH_SHORT).show();
            default: return super.onOptionsItemSelected(item);

        }
    }
    public boolean validate(final String password){

        matcher = pattern.matcher(password);
        return matcher.matches();

    }
}
