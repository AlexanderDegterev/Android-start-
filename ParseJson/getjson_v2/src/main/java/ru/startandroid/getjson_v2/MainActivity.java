package ru.startandroid.getjson_v2;

import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public static String LOG_TAG = "my_log";
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new ParseTask().execute();

        // имена атрибутов для Map
        final String ATTRIBUTE_NAME_NAME = "text";
        final String ATTRIBUTE_NAME_ID = "id";
        final String ATTRIBUTE_NAME_CITY = "city";

//        ArrayList<Map<String, Object>> data2 = null ;

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_NAME, ATTRIBUTE_NAME_ID,
                ATTRIBUTE_NAME_CITY };
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.tvName, R.id.tvId, R.id.tvCity };

//        public interface AsyncResponse {
//            void processValue(ArrayList<Map<String, Object>>) {
//                Log.d(LOG_TAG, "processValue");
//            }
//        }
//        ParseTask PS = new ParseTask();
//        Log.d(LOG_TAG, "ParseTask: " + data2.get(0));


        // создаем адаптер
        SimpleAdapter sAdapter = new SimpleAdapter(this, data , R.layout.item, from, to);

        // определяем список и присваиваем ему адаптер
        listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(sAdapter);
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        public ArrayList<Map<String, Object>> data2 = null;

        @Override
        protected String doInBackground(Void... params) {
            // obtain data from an external resource
            try {
                URL url = new URL("http://androiddocs.ru/api/friends.json");
                //URL url = new URL("http://calapi.inadiutorium.cz/api/v0/en/calendars/default/today");

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
            Log.d(LOG_TAG, "Stroka JSON: " + strJson);
            Log.d(LOG_TAG, "\nStroka JSON(lenght): " + strJson.length());

            // Begin
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            RootObject rootObject = gson.fromJson(strJson, RootObject.class);
            Log.i("my_log", "\ndata: " + rootObject.getData() + "\nFriend: " + rootObject.getFriends().get(1).getName());
            Log.d(LOG_TAG, "\ngetFriends() : " + rootObject.getFriends());

            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

            // имена атрибутов для Map
            final String ATTRIBUTE_NAME_NAME = "text";
            final String ATTRIBUTE_NAME_ID = "id";
            final String ATTRIBUTE_NAME_CITY = "city";

            // упаковываем данные в понятную для адаптера структуру
            ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(rootObject.getFriends().size());
            Map<String, Object> m;
            for (int i = 0; i < rootObject.getFriends().size(); i++) {
                m = new HashMap<String, Object>();
                m.put(ATTRIBUTE_NAME_NAME, rootObject.getFriends().get(i).getName());
                m.put(ATTRIBUTE_NAME_ID, rootObject.getFriends().get(i).getId().toString());
                m.put(ATTRIBUTE_NAME_CITY, rootObject.getFriends().get(i).getCity());
                data.add(m);
            }

            data2 = data;

//            // массив имен атрибутов, из которых будут читаться данные
//            String[] from = { ATTRIBUTE_NAME_NAME, ATTRIBUTE_NAME_ID,
//                    ATTRIBUTE_NAME_CITY };
//            // массив ID View-компонентов, в которые будут вставлять данные
//            int[] to = { R.id.tvName, R.id.tvId, R.id.tvCity };
//
//            // создаем адаптер
//            SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);
//
//            // определяем список и присваиваем ему адаптер
//            listView = (ListView) findViewById(R.id.listView);
//            listView.setAdapter(sAdapter);

            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        }
    }
}
