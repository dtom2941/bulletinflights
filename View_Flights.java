package com.example.bulletinflights;
// figure out how to take JSON Object and turn into
import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import android.widget.BaseAdapter;
import org.apache.http.client.HttpClient;
import java.util.Map;
import android.view.ViewGroup;
import org.apache.commons.*;
import android.view.LayoutInflater;


public class View_Flights extends ListActivity {
    // took the web data grabbing stuff from:
    // http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
    //JSONParser jParser = new JSONParser();;

    public class MyAdapter extends BaseAdapter{
        private final ArrayList mData;

        public MyAdapter(ArrayList<HashMap<String, String>> map) {
            mData = new ArrayList();
            mData.addAll(map);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Map.Entry<String, String> getItem(int position) {
            return (Map.Entry) mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO implement you own logic with ID
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View result;

            if (convertView == null) {
                result = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view__flights, parent, false);
            } else {
                result = convertView;
            }

            Map.Entry<String, String> item = getItem(position);

            // TODO replace findViewById by ViewHolder
            ((TextView) result.findViewById(android.R.id.text1)).setText(item.getKey());
            ((TextView) result.findViewById(android.R.id.text2)).setText(item.getValue());

            return result;
        }
    }
    JSONArray flight = null;

    ArrayList<HashMap<String, String>> flightsList;

    private static String url_all_flights = "http://bulletinflights.com/db_get_flights.php";

    private static final String TAG_ID = "id";
    private static final String TAG_time = "time";
    private static final String TAG_date = "date";
    private static final String TAG_starting_location = "starting_location";
    private static final String TAG_ending_location = "ending_location";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__flights);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        flightsList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        JSONArray flights = null;

        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_all_flights, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {

                // flight found
                // Getting Array of Flights
                flights = json.getJSONArray(TAG_ID);

                // looping through All Products
                for (int i = 0; i < flights.length(); i++) {
                    JSONObject c = flights.getJSONObject(i);

                    // Storing each json item in variable
                    String date = c.getString(TAG_date);
                    String time = c.getString(TAG_time);
                    String startingLocation = c.getString(TAG_starting_location);
                    String endingLocation = c.getString(TAG_ending_location);
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_date, date);
                    map.put(TAG_time, time);
                    map.put(TAG_starting_location, startingLocation);
                    map.put(TAG_ending_location, endingLocation);
                    // adding HashList to ArrayList
                    flightsList.add(map);
                }
                MyAdapter flightAdapter = new MyAdapter(flightsList);
                lv.setAdapter(flightAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public static JSONObject getJSONfromURL(String url){
        //initialize
        InputStream is = null;
        String result = "";
        JSONObject jArray = null;
        //http post
        try{
            HttpClient httpclient = new HttpClient();
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }
        //try parse the string to a JSON object
        try{
            jArray = new JSONObject(result);
        }
        catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
        }
        return jArray;
    }
}

/*
public class JSONParser {

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result = new StringBuilder();
    URL urlObj;
    JSONObject jObj = null;
    StringBuilder sbParams;
    String paramsString;

    public JSONObject makeHttpRequest(String url, String method,
                                      List<com.vrei.meniu.NameValuePair> params) {

        sbParams = new StringBuilder();
        int i = 0;
        for (int key : ((Object) params).keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("GET")) {
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);

                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setConnectTimeout(15000);

                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(result.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON Object
        return jObj;
    }
}
*/