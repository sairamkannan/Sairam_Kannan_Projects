package com.cs442.sairamkannan.sportsmgr;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Signup extends AppCompatActivity implements View.OnClickListener{
    TextView link,input1;
    EditText et_dob,et_name,et_email,et_pass,et_phone;
    Calendar myCalendar;
    Button b_signup;
    int month,day,year,age,s_month,s_day,s_year,res_mon,res_day,res_year;
    RadioButton male,female;
    RadioGroup rg;
    DataHandler db;
    DatePickerDialog.OnDateSetListener date;
    private Pattern pattern;
    private Matcher matcher;
    String tmp1,tmp2,tmp3,tmp4,tmp5,tmp6,tmp7,tmp8,tmp9,tmp10,tmp11;
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%_]).{6,10})";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        link = (TextView)findViewById(R.id.link_login);
        et_dob = (EditText)findViewById(R.id.input_dob);
        et_name = (EditText)findViewById(R.id.input_name);
        et_phone = (EditText)findViewById(R.id.input_phone);
        et_pass = (EditText)findViewById(R.id.input_password);
        et_email = (EditText)findViewById(R.id.input_email);
        input1 = (TextView)findViewById(R.id.input);
        b_signup = (Button)findViewById(R.id.btn_signup);
        rg = (RadioGroup)findViewById(R.id.gender_group);
        pattern = Pattern.compile(PASSWORD_PATTERN);


        String first = "<font color='#00000'>Sports</font>";
        String next = "<font color='#000000'> Manager</font>";
        input1.setText(Html.fromHtml(first + next));

        link.setOnClickListener(this);
        et_dob.setOnClickListener(this);
        b_signup.setOnClickListener(this);



        //Calendar Popup on Edit Text Click
          myCalendar = Calendar.getInstance();

         date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Calendar age = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                Calendar minAdultage = new GregorianCalendar();
                minAdultage.add(Calendar.YEAR, -18);
                if(minAdultage.before(age))
                {
                    et_dob.setError("User Should Be 18 Years of Age");
                    Toast.makeText(Signup.this, "User should be atleast 18 years old",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    updateLabel();
                }

            }

        };

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.link_login:
                Intent i = new Intent(Signup.this,Login1.class);
                startActivity(i);
                break;
            case R.id.input_dob:
                new DatePickerDialog(Signup.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.btn_signup:

                month = myCalendar.get(Calendar.MONTH);
                month++;
                day = myCalendar.get(Calendar.DAY_OF_MONTH);
                year = myCalendar.get(Calendar.YEAR);

                Date d = new Date();
                s_month = d.getMonth()+1;
                s_year = d.getYear()+1900;
                s_day = d.getDate();

                res_year = s_year - year;



                Boolean check = validate(et_pass.getText().toString());
                String ans = getSelection();// gets null if no checkbox selected
                //validating if any of the fields are empty
                if(TextUtils.isEmpty(et_dob.getText()) || TextUtils.isEmpty(et_name.getText()) || TextUtils.isEmpty(et_phone.getText()) || TextUtils.isEmpty(et_email.getText()) || TextUtils.isEmpty(et_pass.getText()) || ans==null)
                {
                    Toast.makeText(Signup.this,"Please Enter All Fields",Toast.LENGTH_SHORT).show();
                }
                else if(et_phone.getText().length()<10 && !isValidEmail(et_email.getText().toString().trim()) && !validate(et_pass.getText().toString().trim()))
                {
                    Toast.makeText(Signup.this,"Phone number should be 10 digits",Toast.LENGTH_SHORT).show();
                    et_phone.setError("10 digits needed");
                    et_email.setError("Invalid Email");
                    et_pass.setError("Password must contain \nOne UpperCase \nOne Lowercase \nOne Digit \nOne Special Character");
                }

                else if(!isValidEmail(et_email.getText().toString().trim()) && !validate(et_pass.getText().toString().trim()))
                {
                    et_email.setError("Invalid Email");
                    et_pass.setError("Password must contain \nOne UpperCase \nOne Lowercase \nOne Digit \nOne Special Character");
                }

                else if(!validate(et_pass.getText().toString().trim()))
                {
                    et_pass.setError("Password must contain \nOne UpperCase \nOne Lowercase \nOne Digit \nOne Special Character");
                }

                else if(et_phone.getText().length()<10)
                {
                    et_phone.setError("10 digits needed");
                }

                else if(!isValidEmail(et_email.getText().toString().trim()))
                {
                    et_email.setError("Invalid Email");
                }
                else if(year>s_year)
                {
                    et_dob.setError("Enter a valid Date");
                    Toast.makeText(Signup.this,"Enter valid year",Toast.LENGTH_SHORT).show();
                }

                else if(res_year<18)
                {
                    et_dob.setError("You're not old Enough");
                    Toast.makeText(Signup.this,"not old enough",Toast.LENGTH_SHORT).show();
                }

                else if(year == s_year && month>s_month)
                {
                    et_dob.setError("Enter a valid Date");
                    Toast.makeText(Signup.this,"invalid month",Toast.LENGTH_SHORT).show();
                }

                else if(year == s_year && month == s_month && day > s_day)
                {
                    et_dob.setError("Enter a valid Date");
                    Toast.makeText(Signup.this,"invalid day",Toast.LENGTH_SHORT).show();
                }

                else
                {

                    tmp1 = et_name.getText().toString();
                    tmp2 = et_email.getText().toString();
                    tmp3 = et_pass.getText().toString();
                    tmp4 = et_phone.getText().toString();
                    tmp5 = et_dob.getText().toString();
                    tmp6 = ans;
                    tmp7 = "0";
                    tmp8 = "2";
                    //tmp9 = "0";
                    //tmp10 = "0";
                    //tmp11 = "0";



                    //inserting data into the database from register page
                    db = new DataHandler(Signup.this);
                    db.open();
                    try
                    {
                        long id = db.insertPlayerData(tmp1,tmp2,tmp3,tmp5,tmp4,tmp6,tmp7,tmp8);
                        Toast.makeText(getBaseContext(), "Registration Successful", Toast.LENGTH_LONG).show();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println(tmp2);

                                Properties props = new Properties();
                                props.put("mail.smtp.host", "smtp.gmail.com");
                                props.put("mail.smtp.socketFactory.port", "465");
                                props.put("mail.smtp.socketFactory.class",
                                        "javax.net.ssl.SSLSocketFactory");
                                props.put("mail.smtp.auth", "true");
                                props.put("mail.smtp.port", "465");

                                Session session = Session.getDefaultInstance(props,
                                        new Authenticator() {
                                            protected PasswordAuthentication getPasswordAuthentication() {
                                                return new PasswordAuthentication("sportsmanagercontact@gmail.com","iitsports");//change your password accordingly
                                            }
                                        });

                                //compose message
                                try {
                                    System.out.println("inside try");
                                    System.out.println(tmp2);

                                    MimeMessage message = new MimeMessage(session);
                                    message.setFrom(new InternetAddress("sportsmanagercontact@gmail.com"));//change accordingly
                                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(tmp2));
                                    message.setSubject("Welcome "  + tmp1);
                                    message.setText("You have successfully registered to the Sports Manager Application.");

                                    //send message
                                    Transport.send(message);

                                    //System.out.println("message sent successfully");

                                } catch (MessagingException e) {throw new RuntimeException(e);}

                            }
                        }).start();


                        Intent i1 = new Intent(Signup.this, Login1.class);
                        startActivity(i1);//goes to login page if all are filled
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(Signup.this,"Email already exists",Toast.LENGTH_SHORT).show();
                    }

                    //db.close();

                }
                break;

        }
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_dob.setText(sdf.format(myCalendar.getTime()));
    }

    private String getSelection(){

        male = (RadioButton)findViewById(R.id.check_male);
        female = (RadioButton)findViewById(R.id.check_female);
        if (male.isChecked())
        {
            return male.getText().toString();
        }
        if (female.isChecked())
        {
            return female.getText().toString();
        }


        return null;

    }

    public boolean validate(final String password){

        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void onBackPressed()
    {

    }

}
