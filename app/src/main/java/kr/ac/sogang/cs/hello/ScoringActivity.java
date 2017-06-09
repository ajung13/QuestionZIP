package kr.ac.sogang.cs.hello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ScoringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);

        //print the answer of user
        Intent intent = getIntent();
        for(int i=0; i<5; i++){
            int tmp = intent.getIntExtra("a"+(i+1), -1);
            TextView tv = (TextView)findViewById(R.id.score11);
            switch(i){
                case 0: tv = (TextView)findViewById(R.id.score11);
                    break;
                case 1: tv = (TextView)findViewById(R.id.score21);
                    break;
                case 2: tv = (TextView)findViewById(R.id.score31);
                    break;
                case 3: tv = (TextView)findViewById(R.id.score41);
                    break;
                case 4: tv = (TextView)findViewById(R.id.score51);
                    break;
                default: break;
            }
            tv.setText(String.valueOf(tmp));
        }

        for(int i=0; i<5; i++){
            int tmp = intent.getIntExtra("ra"+(i+1), -1);
            TextView tv = (TextView)findViewById(R.id.score12);
            switch(i){
                case 0: tv = (TextView)findViewById(R.id.score12);
                    break;
                case 1: tv = (TextView)findViewById(R.id.score22);
                    break;
                case 2: tv = (TextView)findViewById(R.id.score32);
                    break;
                case 3: tv = (TextView)findViewById(R.id.score42);
                    break;
                case 4: tv = (TextView)findViewById(R.id.score52);
                    break;
                default: break;
            }
            tv.setText(String.valueOf(tmp));
        }

    }

}
