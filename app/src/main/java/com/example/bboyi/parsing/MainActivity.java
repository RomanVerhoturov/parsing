package com.example.bboyi.parsing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
//    private String url = "http://www.yudiz.com/blog/";
//    private ArrayList<String> mAuthorNameList = new ArrayList<>();
//    private ArrayList<String> mBlogUploadDateList = new ArrayList<>();
//    private ArrayList<String> mBlogTitleList = new ArrayList<>();
    private String url = "http://www.istu.edu/news/";
    private ArrayList<NewsModel> newsModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Description().execute();

    }

    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String  newsLink;
                // Connect to the web site
                Document mBlogDocument = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements newsRows = mBlogDocument.select("div.newslist-item");
                // Locate the content attribute
                int mElementSize = newsRows.size();

                for (int i = 0; i < mElementSize; i++) {
                    String date = newsRows.get(i).getElementsByClass("news-date").first().text();
                    String cat = newsRows.get(i).getElementsByClass("new-razdel-item").first().text();
                    Element tagA = newsRows.get(i).getElementsByTag("a").first();
                    newsLink = Constants.URL_ISTU + tagA.attr("href");

                   newsModels.add(new NewsModel(cat, NewsModel.COMMON, date,
                           tagA.text(), newsLink));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

            RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.act_recyclerview);

//            DataAdapter mDataAdapter = new DataAdapter(MainActivity.this, mBlogTitleList, mAuthorNameList, mBlogUploadDateList);
            NewsAdapter mNewsAdapter = new NewsAdapter(MainActivity.this, newsModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mNewsAdapter);

            mProgressDialog.dismiss();
        }
    }
}