package mines_acmx.badlibs;

import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.content.res.AssetManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.util.Random;
import java.util.ArrayList;
import java.io.InputStreamReader;

// The Main Activity for Badlibs project
public class Badlib extends ActionBarActivity {

    private ArrayList<String> nouns;
    private ArrayList<String> verbs;
    private ArrayList<String> names;
    private ArrayList<String> adjectives;
    private ArrayList<String> adverbs;
    private static Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initiateLists();
        setContentView(R.layout.activity_badlib);
    }

    private void initiateLists(){
        //nouns = new ArrayList<String>(Arrays.asList("turtle", "hat", "pig", "Mason"));
        nouns = new ArrayList<String>();
        verbs = new ArrayList<String>();
        adverbs = new ArrayList<String>();
        adjectives = new ArrayList<String>();
        names = new ArrayList<String>();


        try {
            AssetManager am = getAssets();
            InputStream is = am.open("words.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            line = br.readLine();
            ArrayList<String> currentList = names;
            while (br.ready()) {
                line = br.readLine();
                if(line.length() > 0 && line.charAt(0) == ':') {
                    switch (line) {
                        case ":noun":
                            currentList = nouns;
                            break;
                        case ":adjective":
                            currentList = adjectives;
                            break;
                        case ":adverb":
                            currentList = adverbs;
                            break;
                        case ":verb":
                            currentList = verbs;
                            break;
                    }
                }else{
                    if(!line.isEmpty())
                        currentList.add(line);
                }
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String generateBadlib() {
        String toReturn = names.get(rand.nextInt(names.size())) + "\n";
        toReturn += adverbs.get(rand.nextInt(adverbs.size())) + "\n";
        toReturn += verbs.get(rand.nextInt(verbs.size())) + "\n";
        toReturn += names.get(rand.nextInt(names.size())) + "'s\n";
        toReturn += adjectives.get(rand.nextInt(adjectives.size())) + "\n";
        toReturn += nouns.get(rand.nextInt(nouns.size())) + "\n";
        
        return toReturn;
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
