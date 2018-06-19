package com.example.bboyi.parsing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsActivity extends AppCompatActivity {
TextView tit, them, dat, lin;
String url;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

//        content news-content

        Intent i=getIntent();

        final String title=i.getExtras().getString("title");
        final String theme=i.getExtras().getString("theme");
        final String date=i.getExtras().getString("date");
        final String pos=i.getExtras().getString("link");

        tit = (TextView) findViewById(R.id.title);
        them = (TextView) findViewById(R.id.theme);
        dat = (TextView) findViewById(R.id.date);
        lin = (TextView) findViewById(R.id.link);

        tit.setText(title);
        them.setText(theme);
        dat.setText(date);
        lin.setText(pos);



       url = pos;
       new Description().execute();

    }

    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(NewsActivity.this);
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
                System.out.println("url");
                Document newsDoc = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements newsRows = newsDoc.select("div.content.news-content");
                // Locate the content attribute
                int mElementSize = newsRows.size();

                for (int i = 0; i < mElementSize; i++) {
                    String header1 = newsRows.select("h1").text();
//                    String date = newsRows.get(i).getElementsByClass("news-date").first().text();
//                    String cat = newsRows.get(i).getElementsByClass("new-razdel-item").first().text();
//                    Element tagA = newsRows.get(i).getElementsByTag("a").first();
//                    newsLink = Constants.URL_ISTU + tagA.attr("href");
//
//                    newsModels.add(new NewsModel(cat, NewsModel.COMMON, date,
//                            tagA.text(), newsLink));
                    System.out.println(header1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

//            RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.act_recyclerview);

//            DataAdapter mDataAdapter = new DataAdapter(MainActivity.this, mBlogTitleList, mAuthorNameList, mBlogUploadDateList);
//            NewsAdapter mNewsAdapter = new NewsAdapter(NewsActivity.this, newsModels);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//            mRecyclerView.setLayoutManager(mLayoutManager);
//            mRecyclerView.setAdapter(mNewsAdapter);

            mProgressDialog.dismiss();
        }
    }

}
