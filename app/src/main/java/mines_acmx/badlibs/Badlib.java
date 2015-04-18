package mines_acmx.badlibs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;

import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.content.res.AssetManager;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
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
        rewriteLists();
        initiateLists();
        setContentView(R.layout.activity_badlib);
    }

    @Override
    protected void onStop() {
        super.onStop();
        rewriteLists();
    }

    private void rewriteLists(){
        try {
            AssetManager am = getAssets();
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("words.txt", Context.MODE_PRIVATE));
            for(String s: names)
                out.write(s + "\n");
            out.write(":noun\n");
            out.write("dog\n");
            //for(String s : nouns)
                //out.write(s + "\n");
            out.write(":verb\n");
            for(String s : verbs)
                out.write(s + "\n");
            out.write(":adverb\n");
            for(String s : adverbs)
                out.write(s + "\n");
            out.write(":adjective\n");
            for(String s : adjectives)
                out.write(s + "\n");
            out.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void ensureDictionary(){
        try {
            FileInputStream fis = openFileInput("words.txt");
            return;
        }catch(FileNotFoundException e){
        }
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("words.txt");
            BufferedReader fromAssets = new BufferedReader(new InputStreamReader(is));
            PrintWriter toInternal = new PrintWriter("words.txt");
            while(fromAssets.ready()){
                toInternal.println(fromAssets.readLine());
            }
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void initiateLists(){
        nouns = new ArrayList<String>();
        verbs = new ArrayList<String>();
        adverbs = new ArrayList<String>();
        adjectives = new ArrayList<String>();
        names = new ArrayList<String>();
        BufferedReader br;

        try {
            AssetManager am = getAssets();
            InputStream is = am.open("words.txt");
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            line = br.readLine();
            ArrayList<String> currentList = names;
            while (br.ready()) {
                line = br.readLine();
                if(line.length() == 0)  // empty string read
                    continue;   // do nothing with it
                if(line.charAt(0) == ':') {
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
                    currentList.add(line);
                }
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // loadContactNames();
    }

    private void loadContactNames() {
        // for each contact in the contact list, add it to names ArrayList
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '" + ("1") + "'";
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        Cursor cursor = managedQuery(uri, projection, selection, selectionArgs, sortOrder);

        while(cursor.moveToNext()) {
            String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            if(!displayName.contains("@"))
                names.add(displayName);
        }
    }

    private String generateBadlib() {
        String toReturn = names.get(rand.nextInt(names.size())) + " ";
        toReturn += adverbs.get(rand.nextInt(adverbs.size())) + " ";
        toReturn += verbs.get(rand.nextInt(verbs.size())) + " ";
        toReturn += names.get(rand.nextInt(names.size())) + "'s ";
        toReturn += adjectives.get(rand.nextInt(adjectives.size())) + " ";
        toReturn += nouns.get(rand.nextInt(nouns.size())) + " ";
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
