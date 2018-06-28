package ru.startandroid.getjson;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//http://json2java.azurewebsites.net/
public class MainActivity extends AppCompatActivity {

    public static String LOG_TAG = "my_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new ParseTask().execute();
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";


        @Override
        protected String doInBackground(Void... params) {
            // obtain data from an external resource
            Log.d(LOG_TAG, "class PArseTAsk");
            try {
                URL url = new URL("http://androiddocs.ru/api/friends.json");
                //URL url = new URL("http://calapi.inadiutorium.cz/api/v0/en/calendars/default/today");
                //searchUrl.setText(url.getHost()+url.getPath());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
                Log.d(LOG_TAG, resultJson);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }


        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            // remove entirely the received json-line
            Log.d(LOG_TAG, "Stroka JSON: "+ strJson);

            // Begin
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            RootObject jsonFriend = gson.fromJson(strJson, RootObject.class);
            Log.i("my_log", "NAME: " + jsonFriend.getData() + "\nCITY: " + jsonFriend.getFriends());
//                Log.d(LOG_TAG, "Friend.class: " + object);
            //End


//            JSONObject dataJsonObj = null;
//            String secondName = "";
//            String secondName2 = "";
//
//            try {
//                dataJsonObj = new JSONObject(strJson);
//                JSONArray friends = dataJsonObj.getJSONArray("friends");
//
//                // 1. достаем инфо о втором друге - индекс 1
//                JSONObject secondFriend = friends.getJSONObject(1);
//                secondName = secondFriend.getString("name");
//                Log.d(LOG_TAG, "Второе имя: " + secondName);
//
//                // 2. перебираем и выводим контакты каждого друга
//                for (int i = 0; i < friends.length(); i++) {
//                    JSONObject friend = friends.getJSONObject(i);
//
//                    JSONObject dbfriends1 = friends.getJSONObject(i);
//                    secondName2 = dbfriends1.getString("name");
//                    Log.d(LOG_TAG, "имя: " + secondName2);
//
//                    JSONObject contacts = friend.getJSONObject("contacts");
//
//                    String phone = contacts.getString("mobile");
//                    String email = contacts.getString("email");
//                    String skype = contacts.getString("skype");
//
//                    Log.d(LOG_TAG, "phone: " + phone);
//                    Log.d(LOG_TAG, "email: " + email);
//                    Log.d(LOG_TAG, "skype: " + skype);
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    }
}
