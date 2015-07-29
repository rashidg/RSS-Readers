package network.rembo.rss;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.TextView;


public class NewsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        TextView tv = (TextView) findViewById(R.id.textView);
        Application app = (Application) getIntent().getSerializableExtra("app11");
        tv.setText(app.getDescription());
    }

}
