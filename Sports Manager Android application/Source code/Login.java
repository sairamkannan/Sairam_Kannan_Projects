package com.cs442.sairamkannan.sportsmgr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

import com.facebook.FacebookSdk;
public class Login extends AppCompatActivity
{

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());



        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                /* Create an Intent that will start the Menu-Activity. */



                String MyPREFERENCES = "Login_Credentials1";
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                String u_email = sharedpreferences.getString("email",null);
                String u_password = sharedpreferences.getString("password",null);

                if(u_email!=null && u_password!=null)
                {
                    if(!u_email.equals("") && !u_password.equals(""))
                    {   //Toast.makeText(Login.this,"reachd if",Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(Login.this, MainActivity.class);
                        Login.this.startActivity(mainIntent);
                        Login.this.finish();
                    }
                    else
                    {
                        //Toast.makeText(Login.this, "reachd if-else", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(Login.this, Login1.class);
                        Login.this.startActivity(mainIntent);
                        Login.this.finish();
                    }
                }

                else
                {
                    //Toast.makeText(Login.this,"reachd else",Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(Login.this, Login1.class);
                    Login.this.startActivity(mainIntent);
                    Login.this.finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


}
