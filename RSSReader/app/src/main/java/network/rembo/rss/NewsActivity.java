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

public class NewsActivity extends Activity {

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

    public void openLink(View V){
        Application app = (Application) getIntent().getSerializableExtra("app11");
        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(app.getLink()));
        startActivity(browserIntent);
    }
}
