package ru.startandroid.androidstartjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //http://androiddocs.ru/parsing-json-poluchaem-i-razbiraem-json-s-vneshnego-resursa/

    public static String LOG_TAG = "my_log";

    Button searchButton;
    EditText searchText;
    EditText searchUrl;
    ListView lists;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new ParseTask().execute();

        // find View-elements
        searchButton = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);
        searchUrl = (EditText) findViewById(R.id.searchUrl);
        lists = (ListView) findViewById(R.id.lists);
        tv = (TextView) findViewById(R.id.tv);

        String req = ((EditText) searchText).getText().toString();
        String lng = String.valueOf(req.length());
        tv.setText(lng);
//            //we will check whether something is entered into a text box
        if (req.length() > 0) {
            Toast.makeText(MainActivity.this, "Более 0", Toast.LENGTH_LONG).show();
        }

        // assign a handler to a button OK
        searchButton.setOnClickListener(oclButton);
//
    } // CLOSE onCreate

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            // obtain data from an external resource
            try {
                //URL url = new URL("http://androiddocs.ru/api/friends.json");
                URL url = new URL("http://calapi.inadiutorium.cz/api/v0/en/calendars/default/today");
                searchUrl.setText(url.getHost()+url.getPath());

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

            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            // remove entirely the received json-line
            Log.d(LOG_TAG, strJson);

            JSONObject dataJsonObj = null;
            String secondName = "";

            try {
                dataJsonObj = new JSONObject(strJson);
                JSONArray friends = dataJsonObj.getJSONArray("friends");

                // 1. достаем инфо о втором друге - индекс 1
                JSONObject secondFriend = friends.getJSONObject(1);
                secondName = secondFriend.getString("name");
                Log.d(LOG_TAG, "Второе имя: " + secondName);

                // 2. перебираем и выводим контакты каждого друга
                for (int i = 0; i < friends.length(); i++) {
                    JSONObject friend = friends.getJSONObject(i);

                    JSONObject contacts = friend.getJSONObject("contacts");

                    String phone = contacts.getString("mobile");
                    String email = contacts.getString("email");
                    String skype = contacts.getString("skype");

                    Log.d(LOG_TAG, "phone: " + phone);
                    Log.d(LOG_TAG, "email: " + email);
                    Log.d(LOG_TAG, "skype: " + skype);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    View.OnClickListener oclButton = new View.OnClickListener() {

        //@Override ключевое слово, которое позволяет в дочернем классе заново создать реализацию метода родительского класса.
        @Override
        public void onClick(View v) {
            String req = ((EditText) searchText).getText().toString();
            String lng = String.valueOf(req.length());
            Toast.makeText(MainActivity.this, lng, Toast.LENGTH_LONG).show();
            tv.setText(lng);
//            //проверим введено ли что-нибудь в текстовое поле
            if (req.length() > 0) {
                Toast.makeText(MainActivity.this, "Более 0", Toast.LENGTH_LONG).show();

            } else {
//                //создадим всплывающее окно с предупреждением, если ничего не ввели.
                Toast.makeText(MainActivity.this, "НЕ введены данные", Toast.LENGTH_LONG).show();
            }
//
        }
    };  //CLOSE View.OnClickListener oclButton
}

