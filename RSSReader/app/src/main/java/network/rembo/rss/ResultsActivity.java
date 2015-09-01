package network.rembo.rss;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import java.util.ArrayList;
import java.lang.Integer;


public class ResultsActivity extends ActionBarActivity {

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ArrayList<Application> foundApps = (ArrayList<Application>) getIntent().getSerializableExtra("apps");
        Log.d("foundApps.size", Integer.toString(foundApps.size()));
        ArrayAdapter<Application> adapter = new ArrayAdapter<Application>(ResultsActivity.this, R.layout.list_items, foundApps);
        lv = (ListView) findViewById(R.id.foundApps);
        lv.setAdapter(adapter);

        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //arg1.setBackgroundColor(Color.LTGRAY);
                Application current_app = (Application) lv.getItemAtPosition(position);
                Intent i = new Intent(lv.getContext(), NewsActivity.class);
                i.putExtra("app11", current_app);
                startActivity(i);
            }
        });
    }

}
