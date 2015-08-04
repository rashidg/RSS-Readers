package network.rembo.rss;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.content.Intent;

import java.io.Serializable;

public class LangActivity extends Activity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang);
    }

    public void aze_button(View v){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("link", "http://socar.az/socar/az/feed");
        startActivity(i);
    }
    public void brit_button(View v){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("link", "http://socar.az/socar/en/feed");
        startActivity(i);
    }

}
