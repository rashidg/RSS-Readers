package network.rembo.rss;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.TextView;
import java.net.URL;
import android.view.View;
import android.net.Uri;
import android.content.Intent;
import org.w3c.dom.Text;

public class NewsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        TextView tv = (TextView) findViewById(R.id.textView);
        Application app = (Application) getIntent().getSerializableExtra("app11");
        tv.setText(app.getDescription());
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setText(app.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.openLink) {
            Application app = (Application) getIntent().getSerializableExtra("app11");
            Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(app.getLink()));
            startActivity(browserIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
