package com.example.myapps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class search extends AppCompatActivity {

    public ArrayList<articles> articlesArrayList;
    articles articles;
    String article;
    EditText ed_search;
    RecyclerView recyclerView;
    Toolbar toolbar_back_button;
    recyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ed_search = (EditText) findViewById(R.id.ed_search);
        articles = new articles();
        toolbar_back_button = (Toolbar) findViewById(R.id.back_button);
        recyclerView = (RecyclerView) findViewById(R.id.recyle);
        recyclerView.setLayoutManager(new LinearLayoutManager(search.this));

        setSupportActionBar(toolbar_back_button);
        toolbar_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back_button = new Intent(search.this, MainActivity.class);
                startActivity(back_button);
                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(true);

        if (ed_search.getText().toString() == "") {
            ed_search.setError("Please Insert Your book");
        }
        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            //coding search di sini
            article = ed_search.getText().toString();
            new SearchProcess(article).execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    public class SearchProcess extends AsyncTask<Void, Void, JSONObject> {
        String article;
        public SearchProcess(String article){
            this.article = article;
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject jsonObject;

            try {
                //article = ed_search.getText().toString().replaceAll(" ", "%20");
                String url = "https://newsapi.org/v2/everything?q="+article+"&apiKey=916380a1e50f4301b709fc1b2c2d934a";
                System.out.println(url);
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
                jsonObject = new JSONObject(json);
            } catch (Exception e) {
                jsonObject = null;
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            Log.d("hasil json ", "onPostExecute: " + jsonObject.toString());
            if (jsonObject != null) {
                try {
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
            } else {
            }
        }
    }
}
