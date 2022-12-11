package com.example.myapplication4java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Settings extends AppCompatActivity {

    protected static final int UPDATE_UI = 0;

    TimeZoneAdaptor timeZoneAdaptor = null;
    List<TimeZoneData> timezonelist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        timezonelist = new ArrayList<TimeZoneData>();
        timeZoneAdaptor = new TimeZoneAdaptor(this, R.layout.timezoneview, timezonelist);
        ListView lv = (ListView) findViewById(R.id.timeZoneList);
        lv.setAdapter(timeZoneAdaptor);
    }

    @Override
    protected void onStart(){
        super.onStart();

        String[] listItems = TimeZone.getAvailableIDs();
        TimeZone timezone = null;
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        Date now = new Date();

        for(int index=0; index<listItems.length; ++index)
        {
            timezone = TimeZone.getTimeZone(listItems[index]);
            format.setTimeZone(timezone);
            timezonelist.add(new TimeZoneData(getDisplayName(listItems[index]), format.format(now)));
            timezone = null;
        }
    }

    private String getDisplayName(String timezonename)
    {
        String displayname = timezonename;
        int sep = timezonename.indexOf('/');
        if(-1 != sep)
        {
            displayname = timezonename.substring(0,sep) + ", " + timezonename.substring(sep + 1);
            displayname = displayname.replace("_", "");
        }
        return displayname;
    }

    public class TimeZoneAdaptor extends ArrayAdapter<TimeZoneData> {

        List<TimeZoneData> objects = null;

        public  TimeZoneAdaptor(Context context, int textViewResourceId, List<TimeZoneData> objects){
            super(context, textViewResourceId, objects);
            this.objects = objects;
        }

        @Override
        public int getCount(){
            return ((null != objects) ? objects.size() : 0);
        }

        @Override
        public TimeZoneData getItem(int position){
            return ((null != objects) ? objects.get(position) : null);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View view = convertView;

            if(null == view){
                LayoutInflater vi = (LayoutInflater) Settings.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.timezoneview, null);
            }
            TimeZoneData data = objects.get(position);
            if(null != data){
                TextView textName = (TextView) view.findViewById(R.id.timezone_name);
                TextView textTime = (TextView) view.findViewById(R.id.timezone_time);
                textName.setText(data.name);
                textTime.setText(data.time);
            }
            return  view;
        }
    }

}