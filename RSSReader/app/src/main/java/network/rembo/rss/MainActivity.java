package network.rembo.rss;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.widget.AdapterView;
import java.io.Serializable;
import android.content.Intent;
import java.lang.String;
import java.lang.Integer;
import android.graphics.Color;

public class MainActivity extends ActionBarActivity implements Serializable {

    Button btnParse;
    ListView listApps;
    String xmlData = null;
    ArrayList<Application> allApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnParse = (Button) findViewById(R.id.btnParse);
        listApps = (ListView) findViewById(R.id.listApps);

        String lnk = getIntent().getSerializableExtra("link").toString();
        new DownloadData().execute(lnk);

        listApps.setClickable(true);
        listApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                arg1.setBackgroundColor(Color.LTGRAY);
                Application current_app = (Application) listApps.getItemAtPosition(position);
                Intent i = new Intent(listApps.getContext(), NewsActivity.class);
                i.putExtra("app11", current_app);
                startActivity(i);
            }
        });

        btnParse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ParseApplications parse = new ParseApplications(xmlData);
                boolean operationStatus = parse.process();
                if (operationStatus) {
                    allApps = parse.getApplications(); // = new ArrayList<Application>();
                    ArrayAdapter<Application> adapter = new ArrayAdapter<Application>(MainActivity.this, R.layout.list_items, allApps);


                    /*ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_2, allApps) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            Log.d("getView:===> ", Integer.toString(position));
                            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                            text1.setText(allApps.get(position).getTitle());
                            text2.setText(allApps.get(position).getPubDate());
                            return view;
                        }
                    };*/


                    listApps.setVisibility(listApps.VISIBLE);
                    listApps.setAdapter(adapter);

                } else {
                    Log.d("MainActivity", "Error parsing file");
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("Couldn't get the feed.\nNo internet connection.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.search) {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            i.putExtra("apps", allApps);
            startActivity(i);
            return true;
        }
        else if (id == R.id.refresh){
            btnParse = (Button) findViewById(R.id.btnParse);
            btnParse.performClick();
            return true;
        }
        else if (id == R.id.memory){
            Intent i = new Intent(this, StorageActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class DownloadData extends AsyncTask<String,Void, String>{

        String myXmlData;

        protected String doInBackground(String... urls) {
            try {
                myXmlData = downloadXML(urls[0]);

            } catch(IOException e) {
                return "Unable to download XML file.";
            }

            return "";
        }

        protected void onPostExecute(String result) {
            Log.d("OnPostExecute", myXmlData);
            xmlData = myXmlData;
            Button btn = (Button) findViewById(R.id.btnParse);
            btn.performClick();
        }

        private String downloadXML(String theUrl) throws IOException {
            int BUFFER_SIZE=2000;
            InputStream is=null;

            String xmlContents = "";

            try {
                URL url = new URL(theUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                int response = conn.getResponseCode();
                Log.d("DownloadXML", "The response returned is: " + response);
                is = conn.getInputStream();

                InputStreamReader isr = new InputStreamReader(is);
                int charRead;
                char[] inputBuffer = new char[BUFFER_SIZE];
                try {
                    while((charRead = isr.read(inputBuffer))>0)
                    {
                        String readString = String.copyValueOf(inputBuffer, 0, charRead);
                        xmlContents += readString;
                        inputBuffer = new char[BUFFER_SIZE];
                    }

                    return xmlContents;

                } catch(IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } finally {
                if(is != null)
                    is.close();
            }

        }
    }

}
