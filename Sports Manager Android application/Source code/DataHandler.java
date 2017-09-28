package com.cs442.sairamkannan.sportsmgr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataHandler {

    //Player profile attributes
    public static final String name = "name";
    public static final String email = "email";
    public static final String password = "password";
    public static final String dob = "dob";
    public static final String phone = "phone";
    public static final String gender = "gender";
    public static final String role = "role";
    public static final String notify = "notify";
    //public static final String wins = "wins";
   // public static final String lost = "lost";
    //public static final String ties = "ties";

    public static final String TABLE_NAME_PROFILE = "player_profile";
    public static final String TABLE_NAME_SPORT = "sport_profile";
    public static final String TABLE_NAME_TEAM = "team_profile";
    public static final String TABLE_PLAYER_STATS = "player_stats";

    //Team profile attributes
    /* Fill details for team here*/
    public static final String t_name="t_name";
    public static final String t_cap="t_cap";
    public static final String t_max="t_max";
    public static final String t_sport="t_sport";


    //Sport profile attributes
    /* Fill details for sport here*/
    public static final String s_name="s_name";
    public static final String s_max_teams="s_max_teams";

    //Player Stats Attributes
    public static final String p_email = "p_email";
    public static final String p_sport = "p_sport";
    public static final String p_team = "p_team";
    public static final String p_captain = "p_captain";

    // Schedule Table Attributes

    public static final String sc_fixtures="sc_fixtures";
    public static final String sc_date="sc_date";
    public static final String sc_time="sc_time";


    public static final String DATA_BASE_NAME = "sports_finale";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CREATE_PROFILE = "create table player_profile(name text not null,email text primary key,password text not null,phone text not null,dob text not null,gender text not null,role text not null,notify text not null);";
    public static final String TABLE_CREATE_SPORT = "create table sport_profile(s_name text primary key,s_max_teams text DEFAULT '16');";
    public static final String TABLE_CREATE_TEAM = "create table team_profile(t_name text,t_sport text,t_cap text,t_max text, PRIMARY KEY(t_name,t_sport));";
    public static final String TABLE_CREATE_PLAYER_STATS = "create table player_stats(p_email text not null,p_sport text not null,p_team text not null,p_captain text not null, PRIMARY KEY(p_email,p_sport));";
    public static final String TABLE_CREATE_SCHEDULE = "create table schedule(sc_fixtures text not null,sc_date text not null,sc_time text not null);";

    DatabaseHelper dbhelper;
    Context ctx;
    SQLiteDatabase db;
    public DataHandler(Context ctx)
    {
        this.ctx = ctx;
        dbhelper = new DatabaseHelper(ctx);

    }
    public static class DatabaseHelper extends SQLiteOpenHelper
    {

        public DatabaseHelper(Context ctx)
        {
            super(ctx, DATA_BASE_NAME, null, DATABASE_VERSION);


        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL(TABLE_CREATE_PROFILE);
                db.execSQL(TABLE_CREATE_SPORT);
                db.execSQL(TABLE_CREATE_TEAM);
                db.execSQL(TABLE_CREATE_PLAYER_STATS);
                db.execSQL(TABLE_CREATE_SCHEDULE);
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Baseball');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Cricket');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Soccer');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Football');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Tabletennis');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Bowling');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Billiards');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Basketball');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Chess');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Badminton');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Racquetball');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Yoga');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Pilates');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Swimming');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Zumba');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Kickboxing');");
                db.execSQL("INSERT INTO sport_profile (s_name) VALUES ('Cardio');");

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS player_profile");

        }
    }
    public DataHandler open()
    {
        db = dbhelper.getWritableDatabase();

        return this;
    }

    public void close()
    {
        dbhelper.close();
    }

    //insert schedule data

    public long insertScheduleData(String s_fixtures,String s_date,String s_time)
    {
        ContentValues content = new ContentValues();
        content.put(sc_fixtures,s_fixtures);
        content.put(sc_date,s_date);
        content.put(sc_time,s_time);
        return db.insertOrThrow(TABLE_CREATE_SCHEDULE, null, content);
    }
    public Cursor returnScheduleData(String sport)
    {
        Cursor cs;
        String query = "SELECT t_name FROM "+TABLE_NAME_TEAM+" WHERE t_sport ='"+sport+"'";
        cs = db.rawQuery(query,null);
        return cs;
    }

   // public long insertData(String name,String email)
    public long insertPlayerData(String pl_name,String pl_email,String pl_password,String pl_dob,String pl_phone,String pl_gender,String pl_role,String pl_notify)
    {

        ContentValues content = new ContentValues();
        content.put(name,pl_name);
        content.put(email, pl_email);
        content.put(password, pl_password);
        content.put(dob,pl_dob);
        content.put(phone,pl_phone);
        content.put(gender,pl_gender);
        content.put(role,pl_role);
        content.put(notify,pl_notify);
        //content.put(wins,pl_wins);
        //content.put(lost,pl_lost);
       // content.put(ties,pl_ties);

        return db.insertOrThrow(TABLE_NAME_PROFILE, null, content);
    }

    public long insertTeamData(String name,String sport,String captain,String max_teams)
    {
        ContentValues content = new ContentValues();
        content.put(t_name,name);
        content.put(t_sport,sport);
        content.put(t_cap,captain);
        content.put(t_max,max_teams);
        return db.insertOrThrow(TABLE_NAME_TEAM, null, content);
    }

    public long insertPlayerStats(String email,String sport,String team,String captain)
    {
        ContentValues content = new ContentValues();
        content.put(p_email,email);
        content.put(p_sport,sport);
        content.put(p_team,team);
        content.put(p_captain,captain);
        return db.insertOrThrow(TABLE_PLAYER_STATS, null, content);
    }

    public long updatePlayerData(String pl_password,String pl_phone,String pl_email)
    {
        ContentValues content = new ContentValues();
        content.put(phone,pl_phone);
        content.put(password, pl_password);

        return db.update(TABLE_NAME_PROFILE, content, "email='" + pl_email + "'", null);


    }

    public Cursor returnTeamData(String sport)
    {
        Cursor cs;
        String query = "SELECT t_name FROM "+TABLE_NAME_TEAM+" WHERE t_sport ='"+sport+"'";
        cs = db.rawQuery(query,null);
        return cs;
    }

   public Cursor returnPlayerData()
   {
        //return db.query(TABLE_NAME,new String[]{name,veg}, null, null, null, null, null);
       Cursor cs;
       String query = "SELECT * FROM "+TABLE_NAME_PROFILE;
        //cs = db.query(TABLE_NAME,new String[] {name,veg},null,null,null,null,null);
       cs = db.rawQuery(query,null);

   return cs;
   }

    public Cursor returnPlayerData1(String s)
    {
        //return db.query(TABLE_NAME,new String[]{name,veg}, null, null, null, null, null);
        Cursor cs;
        String query = "SELECT * FROM "+TABLE_NAME_PROFILE+" WHERE email ='"+s+"'";
        //cs = db.query(TABLE_NAME,new String[] {name,veg},null,null,null,null,null);
        cs = db.rawQuery(query,null);
        return cs;
    }

    public Cursor returnPlayerName(String s)
    {   Cursor cs;
        String query = "SELECT name FROM "+TABLE_NAME_PROFILE+" WHERE email ='"+s+"'";
        cs = db.rawQuery(query,null);
        return cs;
    }

    public Cursor returnPlayerGender(String s)
    {   Cursor cs;
        String query = "SELECT gender FROM "+TABLE_NAME_PROFILE+" WHERE email ='"+s+"'";
        cs = db.rawQuery(query,null);
        return cs;
    }

    public Cursor returnSportData()
    {
        Cursor cs;
        String query = "SELECT * FROM "+TABLE_NAME_SPORT;
        cs = db.rawQuery(query,null);
        return cs;
    }

    public Cursor returnPlayerStats(String email)
    {
        Cursor cs;
        String query = "SELECT p_team,p_sport FROM "+TABLE_PLAYER_STATS+" WHERE p_email ='"+email+"'";
        cs = db.rawQuery(query,null);
        return cs;
    }

    public Cursor returnPlayerStats1(String email)
    {
        Cursor cs;
        String query = "SELECT p_sport FROM "+TABLE_PLAYER_STATS+" WHERE p_email ='"+email+"'";
        cs = db.rawQuery(query,null);
        return cs;
    }

    public Cursor returnPlayers(String team) {
        Cursor cs;
        String query = "SELECT p_email,p_captain FROM " + TABLE_PLAYER_STATS + " WHERE p_team ='" + team + "'";
        cs = db.rawQuery(query, null);
        return cs;
    }
    public void removePlayerStats(String email,String sport)
    {
        Cursor cs;
        String query = "DELETE FROM "+TABLE_PLAYER_STATS+" p_email ='"+email+"'"+" and p_sport ='"+sport+"'";
         db.delete(TABLE_PLAYER_STATS,"p_email='"+email+"'"+" and p_sport='"+sport+"'",null);

    }

    public Cursor returnPlayerCount(String team,String sport)
    {
        Cursor cs;
        String query = "SELECT p_email FROM "+TABLE_PLAYER_STATS+" WHERE p_team ='"+team+"' and p_sport ='"+sport+"';";
        cs = db.rawQuery(query,null);
        return cs;
    }


    public Cursor returnPlayerCount1(String email,String sport)
    {
        Cursor cs;
        String query = "SELECT p_team FROM "+TABLE_PLAYER_STATS+" WHERE p_email ='"+email+"' and p_sport ='"+sport+"';";
        cs = db.rawQuery(query,null);
        return cs;
    }
}
