package network.rembo.rss;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class MainActivity extends Activity implements Serializable {

    Button btnParse;
    ListView listApps;
    String xmlData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnParse = (Button) findViewById(R.id.btnParse);
        listApps = (ListView) findViewById(R.id.listApps);



        listApps.setClickable(true);
        listApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Application current_app = (Application) listApps.getItemAtPosition(position);
                Log.d("asd", "dsa");
                Intent i = new Intent(listApps.getContext(), NewsActivity.class);
                i.putExtra("app11", current_app);
                startActivity(i);
            }
        });

        btnParse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ParseApplications parse = new ParseApplications(xmlData);
                boolean operationStatus = true; //parse.process();
                if (operationStatus) {
                    ArrayList<Application> allApps = new ArrayList<Application>();// = parse.getApplications();
                    Application ap1 = new Application();
                    ap1.setPubDate("2015");
                    ap1.setDescription("asd");
                    ap1.setLink("google.com");
                    ap1.setTitle("OH YEAH");
                    allApps.add(ap1);
                    Log.d("ASDFGHJASDFGHASDFGHASDFGSDFGHDFG", new Integer(allApps.size()).toString());
                    ArrayAdapter<Application> adapter = new ArrayAdapter<Application>(MainActivity.this, R.layout.list_items, allApps);
/*
                    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, allApps) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                            TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                            text1.setText(allApps.get(position).getName());
                            text2.setText(persons.get(position).getAge());
                            return view;
                        }
                    };
*/
                    listApps.setVisibility(listApps.VISIBLE);
                    listApps.setAdapter(adapter);

                } else {
                    Log.d("MainActivity", "Error parsing file");
                }


            }
        });

        //new DownloadData().execute("http://socar.az/socar/az/feed");
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
        if (id == R.id.action_settings) {
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

