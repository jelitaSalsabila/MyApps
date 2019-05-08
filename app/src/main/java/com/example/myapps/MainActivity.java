package com.example.myapps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<articles> articlesArrayList;
    articles articles;
    Menu menu;
    LinearLayout listdata,listload;
    RecyclerView recyclerView;
    recyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articles = new articles();
        listdata=(LinearLayout) findViewById(R.id.listdata);
        listload=(LinearLayout) findViewById(R.id.listload);
        recyclerView = (RecyclerView) findViewById(R.id.Rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        new GetArticles().execute();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(MainActivity.this, search.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
        @SuppressLint("StaticFieldLeak")
        public class GetArticles extends AsyncTask<Void, Void, JSONObject> {


            @Override
            protected void onPreExecute() {

            }

            @Override
            protected JSONObject doInBackground(Void... params) {
                JSONObject jsonObject;

                try {
                    String url = "https://newsapi.org/v2/everything?q=bitcoin&from=2019-04-03&sortBy=publishedAt&apiKey=916380a1e50f4301b709fc1b2c2d934a";
                    System.out.println("url ku " + url);
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    InputStream inputStream = httpEntity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            inputStream, "iso-8859-1"
                    ), 8);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    inputStream.close();
                    String json = stringBuilder.toString();
                    System.out.println("json nya " + json);
                    jsonObject = new JSONObject(json);
                } catch (Exception e) {
                    System.out.println("json error : " + e.toString());
                    jsonObject = null;
                }
                return jsonObject;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                if (jsonObject == null) {

                } else if (jsonObject != null) {
                    try {
                        listload.setVisibility(View.GONE);
                        listdata.setVisibility(View.VISIBLE);
                        JSONArray Hasiljson = jsonObject.getJSONArray("articles");
                        articlesArrayList = new ArrayList<>();
                        for (int i = 0; i < Hasiljson.length(); i++) {
                            articles = new articles();
                            //String urlku = "";
                            String gambar = Hasiljson.getJSONObject(i).getString("urlToImage");
                            articles.setTitle(Hasiljson.getJSONObject(i).getString("title"));
                            articles.setUrl(Hasiljson.getJSONObject(i).getString("url"));
                            articles.setGambar(gambar);
                            articles.setDescription(Hasiljson.getJSONObject(i).getString("description"));
                            articlesArrayList.add(articles);
                            Log.d("test", "onPostExecute: " + articles.getTitle());
                        }
                        adapter = new recyclerAdapter(getApplicationContext(), articlesArrayList);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
