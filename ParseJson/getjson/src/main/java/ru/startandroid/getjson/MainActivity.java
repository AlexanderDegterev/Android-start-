package ru.startandroid.getjson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//http://json2java.azurewebsites.net/
public class MainActivity extends AppCompatActivity {

    public static String LOG_TAG = "my_log";
    public String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };
    private ArrayAdapter<Friend> arrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new ParseTask().execute();

         // находим список
//        listView = (ListView) findViewById(R.id.lvMain);

         // создаем адаптер для перечисления имен
//        arrayAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, names);

         // присваиваем адаптер списку
//        listView.setAdapter(arrayAdapter);

    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected String doInBackground(Void... params) {
            // obtain data from an external resource
            Log.d(LOG_TAG, "---");
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
            Log.d(LOG_TAG, "\nStroka JSON(lenght): "+ strJson.length());

//            JSONArray jsonarray = new JSONArray(strJson);
//            for (int i = 0; i < jsonarray.length(); i++) {
//                JSONObject jsonobject = jsonarray.getJSONObject(i);
//                String name = jsonobject.getString("name");
//                String url = jsonobject.getString("city");

            // Begin
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            RootObject rootObject = gson.fromJson(strJson, RootObject.class);
            Log.i("my_log", "\ndata: " + rootObject.getData() + "\nFriend: " + rootObject.getFriends().get(1).getName());
            Log.d(LOG_TAG, "\ngetFriends() : " + rootObject.getFriends());


            ArrayList<String> nameArray = new ArrayList<>();
            ArrayList<Integer> idArray = new ArrayList<>();
            ArrayList<String> cityArray = new ArrayList<>();
//            nameArray.add(rootObject.getFriends().get(0).getName());
//            nameArray.add(rootObject.getFriends().get(1).getName());
             //..Friend friend = new Friend();//('int id' ,string name,string city);
                     //int id, string name, string city, Contacts contacts);


            // !!!! ?????? iterator foreach
            for (Friend friend: rootObject.getFriends()) {
                nameArray.add(friend.getName());
                idArray.add(friend.getId());
                cityArray.add(friend.getCity());
            }

            // вот так не верно !
//            JSONArray jsonarray = new JSONArray(rootObject.getFriends());
            for (int i = 0; i < rootObject.getFriends().size(); i++) {
//                JSONObject jsonobject = jsonarray.getJSONObject(i);
//                String name = jsonobject.getString("name");
//                Log.d(LOG_TAG, "\nname : " + jsonarray.length());
            }

//            Log.d(LOG_TAG, "isEmpty: " + rootObject.getFriends().toString());

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


            // массив имен атрибутов, из которых будут читаться данные
            String[] from = { ATTRIBUTE_NAME_NAME, ATTRIBUTE_NAME_ID,
                    ATTRIBUTE_NAME_CITY };
            // массив ID View-компонентов, в которые будут вставлять данные
            int[] to = { R.id.tvName, R.id.tvId, R.id.tvCity };

            // создаем адаптер
            SimpleAdapter sAdapter = new SimpleAdapter(getClass(), data, R.layout.item, from, to);

            // определяем список и присваиваем ему адаптер
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(sAdapter);


            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



            // Create adapter Урок 54
            arrayAdapter = new ArrayAdapter<Friend>(MainActivity.this,
                    android.R.layout.simple_list_item_1, rootObject.getFriends());
//            listView.setAdapter(arrayAdapter);

            //Выводим
            LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);

            LayoutInflater ltInflater = getLayoutInflater();

            for (int i = 0; i < rootObject.getFriends().size(); i++) {
                Log.d("myLogs", "i = " + i);
                View item = ltInflater.inflate(R.layout.item, linLayout, false);
                TextView tvName = (TextView) item.findViewById(R.id.tvName);
                tvName.setText(rootObject.getFriends().get(i).getName());
                TextView tvId = (TextView) item.findViewById(R.id.tvId);
                tvId.setText("id: " + rootObject.getFriends().get(i).getId());
                TextView tvCity = (TextView) item.findViewById(R.id.tvCity);
                tvCity.setText("City: " + rootObject.getFriends().get(i).getCity());
                item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
//                item.setBackgroundColor( colors[i % 2]);
                item.setBackgroundColor(545);
                linLayout.addView(item);
            }

//            Log.d(LOG_TAG, "namesFromJson[0]: " + rootObject.getFriends().get(0).getName());

//            Log.d(LOG_TAG, "имя: " + rootObject.getFriends().get(1).getName());
//            Log.d(LOG_TAG, "phone: " + rootObject.getFriends().get(1).getContacts().getMobile());
//            Log.d(LOG_TAG, "email: " + rootObject.getFriends().get(1).getContacts().getEmail());
//            Log.d(LOG_TAG, "skype: " + rootObject.getFriends().get(1).getContacts().getSkype());



        }
    }
}
