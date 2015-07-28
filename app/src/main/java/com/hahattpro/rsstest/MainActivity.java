package com.hahattpro.rsstest;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hahattpro.meowdebughelper.CopyInputStream;
import com.hahattpro.meowdebughelper.CreateFile;
import com.hahattpro.meowdebughelper.Mailer;
import com.hahattpro.simplistic_rss.RssItem;
import com.hahattpro.simplistic_rss.RssReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    ListView listView;

    String testingEmail = "testing.ttpro1995@yahoo.com.vn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.mListView);

        new RssAsynTask().execute("http://rss.cnn.com/rss/edition.rss");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    InputStream getRSSxmlStream(String rssURL){
        String LOCAL_TAG = "getRSSxml";
        HttpURLConnection httpURLConnection = null;
        URL url = null;
        InputStream resultIS = null;
        try{
            url = new URL(rssURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            //write to inputstream
            resultIS = httpURLConnection.getInputStream();
            return resultIS;

        }catch (MalformedURLException e){
            Log.e(LOCAL_TAG,"MalformedURLException");
        }catch (IOException e){
            Log.e(LOCAL_TAG,"IOException");
        }
        return null;
    }

    private class Test1GetString extends AsyncTask<Void,Void,Void>{
        String LOCAL_TAG = Test1GetString.class.getSimpleName();
        @Override
        protected Void doInBackground(Void... params) {
            Log.i(LOCAL_TAG,"Start Test1GetRSSString");
            InputStream is = getRSSxmlStream("http://rss.cnn.com/rss/edition.rss");
            CopyInputStream copyInputStream = new CopyInputStream(is);
            String body = copyInputStream.getString();
            Log.i(LOCAL_TAG,"done get stream");

            CreateFile createFile = new CreateFile("test1_"+System.currentTimeMillis()+"xml",body,MainActivity.this);
            File file =createFile.getFile();
            Mailer meow = new Mailer(MainActivity.this);
            meow.SendMail(testingEmail,"test1_"+System.currentTimeMillis(),"meow",file);
            return null;
        }
    }

    private List<RssItem> useSimplistic_rss(String url){
        RssReader rssReader;
        List<RssItem> rssItems = null;

        try {
            rssReader = new RssReader(url);
            rssItems = rssReader.getItems();
        }catch (Exception e){e.printStackTrace();}
        return rssItems;
    }

    private class RssAsynTask extends AsyncTask<String,Void,List<RssItem>>
    {
        @Override
        protected List<RssItem> doInBackground(String... params) {

            return useSimplistic_rss(params[0]);
        }

        @Override
        protected void onPostExecute(final List<RssItem> rssItems) {
            super.onPostExecute(rssItems);
            MyCustomAdapter adapter = new MyCustomAdapter(MainActivity.this,R.layout.list_item, rssItems);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RssItem item = rssItems.get(position);
                    openWebPage(item.getLink());
                }
            });
        }

        private void openWebPage(String url) {
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url;
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

}
