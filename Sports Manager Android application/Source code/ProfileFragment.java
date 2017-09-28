package com.cs442.sairamkannan.sportsmgr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

public class ProfileFragment extends Fragment{
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ImageView i;
    String u_gender=null;
    private Uri outputFileUri;
    TextView t1;

    private View.OnClickListener mOriginalListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentManager = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        URI image = null;


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profile");
        setHasOptionsMenu(true);
        String MyPREFERENCES = "Login_Credentials1";
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String u_email = sharedpreferences.getString("email", null);
        String photo = sharedpreferences.getString("photo", null);


        String u_name=null;


        Cursor cs;
        DataHandler db = new DataHandler(this.getActivity());
        db.open();
        cs = db.returnPlayerName(u_email);
        if(cs!=null)
        {
            cs.moveToFirst();
            u_name = cs.getString(0);
            //Toast.makeText(MainActivity.this,"got data"+cs.getString(0),Toast.LENGTH_SHORT).show();
        }

        Cursor cs1;
        DataHandler db1 = new DataHandler(this.getActivity());
        db1.open();
        cs1 = db.returnPlayerGender(u_email);
        if(cs1!=null)
        {
            cs1.moveToFirst();
            u_gender = cs1.getString(0);
            //Toast.makeText(MainActivity.this,"got data"+cs.getString(0),Toast.LENGTH_SHORT).show();
        }

       View myInflatedView = inflater.inflate(R.layout.profile_layout, container,false);//use your layout by finding it
        TextView t = (TextView) myInflatedView.findViewById(R.id.user_name);//find the textview in the layout selected
        t.setText(u_name);
        t1 = (TextView)myInflatedView.findViewById(R.id.myteams);
        i = (ImageView)myInflatedView.findViewById(R.id.user_image);

        t1.setText("Bangalore Titans \nDolphins\nKings");
        t1.setMovementMethod(new ScrollingMovementMethod());

        Cursor cs2;
        DataHandler db2 = new DataHandler(this.getActivity());
        db2.open();
        cs2 = db2.returnPlayerStats(u_email);
        cs2.moveToFirst();
        String arr[] = new String[cs2.getCount()];
        String str = "";
        String arr1[] = new String[cs2.getCount()];
        int x=0;
        while (!cs2.isAfterLast()) {

            arr[x] = cs2.getString(0);
            x++;
            // System.out.println(sport_name);
            cs2.moveToNext();
        }

        for(int i=0;i<arr.length;i++)
        {
            if(!arr[i].equals("Yoga")&&!arr[i].equals("Zumba")&&!arr[i].equals("Swimming")&&!arr[i].equals("Pilates")&&!arr[i].equals("Kickboxing")&&!arr[i].equals("Cardio"))
            str += arr[i]+"\n";
          //  System.out.println("My registered team names" +str);
        }


        t1.setText(str);

         if(u_gender.equals("Male"))
            i.setImageResource(R.drawable.male);
        else
        i.setImageResource(R.drawable.user10);

        i.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               intent.setType("image/*");
               Intent camIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(Intent.EXTRA_INTENT, camIntent);
                startActivityForResult(intent, 007);

            }
        });
          //return inflater.inflate(R.layout.profile_layout,null);
        return myInflatedView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.edit1profile:
                //Toast.makeText((AppCompatActivity)getActivity(),"success",Toast.LENGTH_SHORT).show();
                mFragmentTransaction.replace(R.id.containerView, new SettingsFragment()).commit();break;
            case R.id.save1profile:
                mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();break;
            case R.id.campusprofile:
                mFragmentTransaction.replace(R.id.containerView, new CampusFragment()).commit();break;
            default: return super.onOptionsItemSelected(item);

        }return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        Uri selectedImageUri;
            selectedImageUri = data == null ? null : data.getData();
        if(selectedImageUri!=null)
        {
            i.setImageURI(selectedImageUri);
            String MyPREFERENCES = "Login_Credentials1";
            SharedPreferences sharedpreferences1 = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences1.edit();
            editor.putString("photo", selectedImageUri.toString());
            editor.commit();
        }
        else if(u_gender.equals("Male"))
            i.setImageResource(R.drawable.male);
        else
            i.setImageResource(R.drawable.user10);

    }
}
