package network.rembo.rss;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class StorageActivity extends ActionBarActivity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        printApps();
    }

    @Override
    public void onResume(){
        super.onResume();
        printApps();

    }

    private void printApps(){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<Application> foundApps = dbHandler.getData();

        ArrayAdapter<Application> adapter = new ArrayAdapter<Application>(this, R.layout.list_items, foundApps);
        lv = (ListView) findViewById(R.id.StoredApps);
        lv.setAdapter(adapter);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                arg1.setBackgroundColor(Color.LTGRAY);
                Application current_app = (Application) lv.getItemAtPosition(position);
                Intent i = new Intent(lv.getContext(), StoredNewsActivity.class);
                i.putExtra("app11", current_app);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_storage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.emptyDB) {
            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            dbHandler.emptyDB();
            printApps();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
