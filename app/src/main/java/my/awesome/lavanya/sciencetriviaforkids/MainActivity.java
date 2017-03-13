package my.awesome.lavanya.sciencetriviaforkids;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button button0,button1,button3;
    String DB_PATH = "/data/data/my.awesome.lavanya.sciencetriviaforkids/databases/sciencequiz.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorblack));
        TextView mytitle = (TextView) toolbar.getChildAt(0);
        mytitle.setTextSize(30);
        getSupportActionBar().setTitle("Science Trivia For Kids");
       button0= (Button) findViewById(R.id.button0);
        button1= (Button) findViewById(R.id.button1);
        button3= (Button) findViewById(R.id.button3);
        long size=new File(DB_PATH).length();

       while(size <15360){
            //Log.d(TAG,"the filesize"+ size);
            Databaseopener databaseopener=new Databaseopener(MainActivity.this,null,null,null,1);
            try {
                databaseopener.copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            size=new File(DB_PATH).length();

        }

        if(size==15360) {
            // Log.d(TAG,"i am inside if");
            button0.setEnabled(true);
            button0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Choicescreen.class);
                    startActivity(intent);
                }
            });
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (isNetworkAvailable()) {

        }
        else{
            Toast.makeText(this, "Check Network Connection", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
