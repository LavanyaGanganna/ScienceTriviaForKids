package my.awesome.lavanya.sciencetriviaforkids;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class Anatomy extends AppCompatActivity {
    private static final String TAG = Anatomy.class.getSimpleName();
    TextView textView1;
    TextView questtext;
    Button button0,button1,button2,button3;
    String[] answers=new String[4];
    LinearLayout linearLayout;
    QuizObject qobject;
    List<QuizObject> quizObjectList;
    boolean succeed=true;
    Button playagain;
    int questions=0;
    int answered=0;
    String correctanswer;
    TextView timertext;
    int score;
    MediaPlayer mediaPlayer;
    int listsize;
    int m=0;
    int n=0;
    DBHandler db = new DBHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anatomy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorblack));
        TextView mytitle = (TextView) toolbar.getChildAt(0);
        mytitle.setTextSize(30);
        getSupportActionBar().setTitle("Anatomy Quiz");
        textView1 = (TextView) findViewById(R.id.questionsans);
        questtext= (TextView) findViewById(R.id.questtext);
        button0 = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button2);
        button2 = (Button) findViewById(R.id.button3);
        button3 = (Button) findViewById(R.id.button4);
        button0.setOnClickListener(celebchosenlistener);
        button1.setOnClickListener(celebchosenlistener);
        button2.setOnClickListener(celebchosenlistener);
        button3.setOnClickListener(celebchosenlistener);
        playagain= (Button) findViewById(R.id.playagain);

        quizObjectList=db.getcelebrity(m);
        listsize=quizObjectList.size();
        //   Log.d(TAG,"the list size" + quizObjectList.size());
        while(!(listsize==25)){
            quizObjectList=db.getcelebrity(m);
            listsize=quizObjectList.size();
        }
        CreateNewQuestion();
    }
    private void CreateNewQuestion() {

        button0.setBackgroundResource(R.drawable.button_corners);
        button1.setBackgroundResource(R.drawable.button_corners);
        button2.setBackgroundResource(R.drawable.button_corners);
        button3.setBackgroundResource(R.drawable.button_corners);
        // qobject= db.getcelebrity(m);
        qobject=quizObjectList.get(n);
        // qobject= db.getcelebrity(m);
        correctanswer=qobject.getCorrect();
        questtext.setText(qobject.getQuestion());
        questions++;

        if (timertext != null) {
            timertext.setText(questions + "/"+listsize);
        }
        answers[0]=qobject.getChoicea();
        answers[1]=qobject.getChoiceb();
        answers[2]=qobject.getChoicec();
        answers[3]=qobject.getChoiced();

        button0.setText(answers[0]);
        button1.setText(answers[1]);
        button2.setText(answers[2]);
        button3.setText(answers[3]);

    }

    View.OnClickListener celebchosenlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button b=(Button) view;
            String buttontext=b.getText().toString();
            button0.setEnabled(false);
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            if (buttontext.equals(correctanswer)) {
                score = score + 1;
             //   view.setBackgroundColor(getResources().getColor(R.color.colorgreen));
                view.setBackgroundResource(R.drawable.green_corners);
                playmusic();
                answered++;
            } else {
                //view.setBackgroundColor(getResources().getColor(R.color.colorred));
                view.setBackgroundResource(R.drawable.red_corners);
                playerrormusic();

                if(button0.getText().equals(correctanswer)){
                   // button0.setBackgroundColor(getResources().getColor(R.color.colorgreen));
                    button0.setBackgroundResource(R.drawable.green_corners);
                }
                else if(button1.getText().equals(correctanswer)){
                  //  button1.setBackgroundColor(getResources().getColor(R.color.colorgreen));
                    button1.setBackgroundResource(R.drawable.green_corners);
                }else if (button2.getText().equals(correctanswer)) {
                    //button2.setBackgroundColor(getResources().getColor(R.color.colorgreen));
                    button2.setBackgroundResource(R.drawable.green_corners);
                }else if (button3.getText().equals(correctanswer)) {
                    //button3.setBackgroundColor(getResources().getColor(R.color.colorgreen));
                    button3.setBackgroundResource(R.drawable.green_corners);
                }

            }

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    button0.setEnabled(true);
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);

                    if(questions == listsize && answered >=15){

                        TextView textView = (TextView) findViewById(R.id.winnermsg);
                        textView.setText("\t\tCongratulations!! \n  \t You Completed \n\t\t \t  This Section ");
                        textView1.setText("Your Score: " + score);
                        linearLayout = (LinearLayout) findViewById(R.id.playagainlayout);
                            playagain.setText("Play Next Quiz");
                        linearLayout.setVisibility(View.VISIBLE);
                        playcheermusic();
                        button3.setEnabled(false);
                        button2.setEnabled(false);
                        button1.setEnabled(false);
                        button0.setEnabled(false);

                    }
                    else if(questions ==listsize && answered <15){
                        TextView textView = (TextView) findViewById(R.id.winnermsg);
                        textView.setText(" \t\t You Failed, need \n \t atleast 15 Correct ");
                        textView1.setText("Your Score: " + score );
                        linearLayout = (LinearLayout) findViewById(R.id.playagainlayout);
                        playagain.setText("Play Again");
                        linearLayout.setVisibility(View.VISIBLE);
                        succeed=false;
                        button3.setEnabled(false);
                        button2.setEnabled(false);
                        button1.setEnabled(false);
                        button0.setEnabled(false);


                    }
                    else if(n<25){
                        n++;
                        CreateNewQuestion();

                    }

                }

            }, 1000);

        }
    };


    private void playmusic() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.error);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.release();

            };
        });

    }

    private void playerrormusic() {
        MediaPlayer mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.beep);
        mediaPlayer1.start();
        mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer1) {
                mediaPlayer1.stop();
                mediaPlayer1.release();

            };
        });
    }
    private void playcheermusic() {
        MediaPlayer mp=  MediaPlayer.create(getApplicationContext(), R.raw.cheer);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();

            };
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem timerItem = menu.findItem(R.id.break_timer);
        timertext = (TextView) MenuItemCompat.getActionView(timerItem);
        timertext.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        timertext.setPadding(10, 30, 20, 10); //Or something like that...
        timertext.setText(questions+"/"+listsize);
        timertext.setTextColor(getResources().getColor(R.color.colorblack));
        return true;
    }
    public void playagain(View view) {
        // timeup = false;
        button3.setEnabled(true);
        button2.setEnabled(true);
        button1.setEnabled(true);
        button0.setEnabled(true);
        linearLayout.setVisibility(View.INVISIBLE);
        score = 0;
        answered = 0;
        questions=0;
        // chosenvalues.clear();
        if(succeed) {
            Log.d(TAG," inside succed");
            quizObjectList.clear();
            Intent intent = new Intent(this,Astronomy.class);
            startActivity(intent);
            finish();
        }
        else{
            if (Build.VERSION.SDK_INT >= 11) {
                recreate();
            } else {
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
