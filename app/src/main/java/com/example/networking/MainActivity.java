package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Mountain> items;
    private ArrayAdapter<Mountain> adapter;
    private Mountain[] mountains;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");

        items = new ArrayList<>();
        adapter = new ArrayAdapter(this, R.layout.listview_item, items);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /*
               Mountain mountain = items.get(i);

               String message = "The mountain " + mountain.getName(); +
                        "is located in " + mountain.getLocation();
                        "and is " + mountain.getSize(); + " meters high. ";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show(); */
            }
        });



        }
  


@SuppressLint("StaticFieldLeak")
private class JsonTask extends AsyncTask<String, String, String> {

    private HttpURLConnection connection = null;
    private BufferedReader reader = null;

    protected String doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null && !isCancelled()) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String json) {
        Log.d("AsyncTask", json);
        Gson gson = new Gson();
        ListView listView = findViewById(R.id.list_view);
        mountains = gson.fromJson(json, Mountain[].class);
        adapter = new ArrayAdapter<Mountain>(MainActivity.this,R.layout.listview_item, R.id.item,mountains);
        listView.setAdapter(adapter);

        for (int i = 0; i < mountains.length; i++) {
            Log.d("MainActivity ==>", "Hittade ett berg: "+mountains[i]);
        }
    }
}

}
