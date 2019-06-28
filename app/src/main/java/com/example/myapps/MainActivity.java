package com.example.myapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements BaseViewInterface,HomeInterface.View{

    public ArrayList<ArticlesModel> articlesArrayList;
    ArticlesModel articles;
    //LinearLayout listdata,listload;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;

    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articles = new ArticlesModel();
//        listdata= findViewById(R.id.listdata);
//        listload= findViewById(R.id.listload);
        recyclerView =  findViewById(R.id.Rview);
        //new GetArticles().execute();

        presenter = new HomePresenter(this, this,this);
        presenter.getArticles();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(MainActivity.this, search.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setArticlesList(List<ArticlesModel> list) {
        adapter = new RecyclerAdapter(this, list);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                recyclerView.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVisibility(VISIBLE);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setArticles(String title, String description, String publishedAt, String content, String urlToImage) {
        ArticlesModel articlesModel = new ArticlesModel();
        articlesModel.getTitle();

    }

    @Override
    public void noInternetError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void showError(String errorMessage) {

    }

//        @SuppressLint("StaticFieldLeak")
//        public class GetArticles extends AsyncTask<Void, Void, JSONObject> {
//
//
//            @Override
//            protected void onPreExecute() {
//
//            }
//
//            @Override
//            protected JSONObject doInBackground(Void... params) {
//                JSONObject jsonObject;
//
//                try {
//                    String url = "https://newsapi.org/v2/everything?q=bitcoin&from=2019-04-03&sortBy=publishedAt&apiKey=916380a1e50f4301b709fc1b2c2d934a";
//                    System.out.println("url ku " + url);
//                    DefaultHttpClient httpClient = new DefaultHttpClient();
//                    HttpGet httpGet = new HttpGet(url);
//                    HttpResponse httpResponse = httpClient.execute(httpGet);
//                    HttpEntity httpEntity = httpResponse.getEntity();
//                    InputStream inputStream = httpEntity.getContent();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(
//                            inputStream, "iso-8859-1"
//                    ), 8);
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        stringBuilder.append(line).append("\n");
//                    }
//                    inputStream.close();
//                    String json = stringBuilder.toString();
//                    System.out.println("json nya " + json);
//                    jsonObject = new JSONObject(json);
//                } catch (Exception e) {
//                    System.out.println("json error : " + e.toString());
//                    jsonObject = null;
//                }
//                return jsonObject;
//            }
//
//            @Override
//            protected void onPostExecute(JSONObject jsonObject) {
//                if (jsonObject == null) {
//
//                } else if (jsonObject != null) {
//                    try {
//                        listload.setVisibility(View.GONE);
//                        listdata.setVisibility(View.VISIBLE);
//                        JSONArray Hasiljson = jsonObject.getJSONArray("ArticlesModel");
//                        articlesArrayList = new ArrayList<>();
//                        for (int i = 0; i < Hasiljson.length(); i++) {
//                            ArticlesModel = new ArticlesModel();
//                            //String urlku = "";
//                            String gambar = Hasiljson.getJSONObject(i).getString("urlToImage");
//                            ArticlesModel.setTitle(Hasiljson.getJSONObject(i).getString("title"));
//                            ArticlesModel.setUrl(Hasiljson.getJSONObject(i).getString("url"));
//                            ArticlesModel.setGambar(gambar);
//                            ArticlesModel.setDescription(Hasiljson.getJSONObject(i).getString("description"));
//                            articlesArrayList.add(ArticlesModel);
//                            Log.d("test", "onPostExecute: " + ArticlesModel.getTitle());
//                        }
//                        adapter = new RecyclerAdapter(getApplicationContext(), articlesArrayList);
//                        recyclerView.setAdapter(adapter);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
    }
