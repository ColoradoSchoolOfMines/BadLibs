package mines_acmx.badlibs;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

// The Main Activity for Badlibs project
public class Badlib extends ActionBarActivity {

    private ArrayList<String> noun;
    private ArrayList<String> name;
    private ArrayList<String> adjective;
    private ArrayList<String> verb;
    private ArrayList<String> adverb;
    private static Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initiateLists();
        setContentView(R.layout.activity_badlib);
    }

    public void initiateLists(){
        noun = new ArrayList<String>(Arrays.asList("turtle", "hat", "pig", "Mason"));

    }

    public String generateBadlib() {
        return noun.get(rand.nextInt(noun.size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_badlib, menu);

        Button button = (Button) findViewById(R.id.generate);
        button.setOnClickListener(new OnClickListener() {
           @Override
            public void onClick(View view) {
               TextView textView = (TextView) findViewById(R.id.textView);
               String badlib = generateBadlib();
               textView.setText(badlib);
           }
        });

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
}
