package network.rembo.rss;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ListView;
import java.util.Calendar;
import java.util.ArrayList;


public class SearchActivity extends Activity {

    int getMonthNum(String str){
        //Log.d("getMonthNum ====> \"", str + "\"");
        if (str.equals("Jan")) return 1;
        if (str.equals("Feb")) return 2;
        if (str.equals("Mar")) return 3;
        if (str.equals("Apr")) return 4;
        if (str.equals("May")) return 5;
        if (str.equals("Jun")) return 6;
        if (str.equals("Jul")) return 7;
        if (str.equals("Aug")) return 8;
        if (str.equals("Sep")) return 9;
        if (str.equals("Oct")) return 10;
        if (str.equals("Nov")) return 11;
        if (str.equals("Dec")) return 12;
        return -1;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Calendar c = Calendar.getInstance();
        DatePicker picker = (DatePicker) findViewById(R.id.datePicker);
        picker.init(
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                new OnDateChangedListener(){
                    @Override
                    public void onDateChanged(DatePicker view, int chosenYear, int chosenMonth,int chosenDay) {
                        ArrayList<Application> listApps = (ArrayList<Application>) getIntent().getSerializableExtra("apps");
                        Log.d("listApps.size() = ", Integer.toString(listApps.size()));
                        ArrayList<Application> chosenApps = new ArrayList<>();
                        chosenMonth++;
                        for (int i = 0; i < listApps.size(); i++){
                            Application cur = (Application) listApps.get(i);
                            int day = Integer.parseInt(cur.getPubDate().substring(5,7));
                            int month = getMonthNum(cur.getPubDate().substring(8, 11));
                            int year = Integer.parseInt(cur.getPubDate().substring(12, 16));
                            if (day == chosenDay && month == chosenMonth && year == chosenYear)
                                chosenApps.add(cur);
                        }
                        Log.d("ASDFASDAM", "here");
                        Intent i = new Intent(getApplicationContext(), ResultsActivity.class);
                        i.putExtra("apps", chosenApps);
                        startActivity(i);
                    }
                });
    }

}
