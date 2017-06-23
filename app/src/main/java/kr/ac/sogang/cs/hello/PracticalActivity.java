package kr.ac.sogang.cs.hello;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PracticalActivity extends AppCompatActivity {
    int questionNum = 0;
    TextView[] q = new TextView[6];
    ImageView[] ans_image = new ImageView[5];
    int[] userAnswer = {-1, -1, -1, -1, -1};
    int[] questionAnswer = new int[5];
    ScrollView scrl;
    Handler mHandler;
    public int mainTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical);

        q[0] = (TextView) findViewById(R.id.question10);
        q[1] = (TextView) findViewById(R.id.question11);
        q[2] = (TextView) findViewById(R.id.question12);
        q[3] = (TextView) findViewById(R.id.question13);
        q[4] = (TextView) findViewById(R.id.question14);
        q[5] = (TextView) findViewById(R.id.question15);
        scrl = (ScrollView) findViewById(R.id.verScroll);
        ans_image[0] = (ImageView) findViewById(R.id.answer1);
        ans_image[1] = (ImageView) findViewById(R.id.answer2);
        ans_image[2] = (ImageView) findViewById(R.id.answer3);
        ans_image[3] = (ImageView) findViewById(R.id.answer4);
        ans_image[4] = (ImageView) findViewById(R.id.answer5);

        viewAnswer(-1);

        mHandler = new Handler(){
            public void handleMessage(Message msg){
                TextView timertest = (TextView)findViewById(R.id.q_timer);
                super.handleMessage(msg);
                int div = msg.what;
                int min = mainTime / 60;
                int sec = mainTime % 60;
                String strTime = String.format("%02d : %02d", min, sec);
                this.sendEmptyMessageDelayed(0,1000);
                timertest.setText(strTime);
                timertest.invalidate();
                mainTime++;
            }
        };
        mHandler.sendEmptyMessage(1);

//        recommend();
        viewQuestions();
    }

    public String connectionURL(String urladdr){
        HttpURLConnection conn;
        String line = "";
        String result = "";
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = new URL(urladdr);
            int redirectedCount = 0;
            while(redirectedCount <= 1) {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "Android");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setUseCaches(false);
                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(is));

                    while((line=in.readLine())!=null){
                        result = result.concat(line);
                    }

                    is.close();
                    in.close();
                    conn.disconnect();
                    break;
                } else if (conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP || conn.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM) {
                    String redirectURL = conn.getHeaderField("Location");
                    url = new URL(redirectURL);
                }
                redirectedCount++;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
/*    public void recommend(){
        String urladdr = "http://questionzip.dothome.co.kr/SelectR2.php";
        String result = "";

        result = connectionURL(urladdr);

        ParseJSON_R(result);
        q[0].setText("Parsing Completed");
        matrix_factorization();
        q[0].setText("Calculating matrix R Completed");
        sort_recommend();
    }*/

    public void viewQuestions(){
        q[0].setText("first");
        q[1].setText("second");
        q[2].setText("third");
        q[3].setText("fourth");
        q[4].setText("fifth");
        q[5].setText("sixth");
/*        if(index[0]!=-1){
            String urladdr = "http://questionzip.dothome.co.kr/SelectQ.php";
            String result = connectionURL(urladdr);
            ParseJSON_Q(result);
        }*/
    }

    public void onRecommendPrev(View v){
        if(questionNum!=0){
            q[questionNum].setVisibility(View.INVISIBLE);
            questionNum--;
            scrl.smoothScrollTo(0,0);
            q[questionNum].setVisibility(View.VISIBLE);
            if(questionNum!=0)
                viewAnswer(userAnswer[questionNum-1]-1);
        }
    }

    public void onRecommendNext(View v){
        if(questionNum!=5){
            q[questionNum].setVisibility(View.INVISIBLE);
            questionNum++;
            scrl.smoothScrollTo(0,0);
            q[questionNum].setVisibility(View.VISIBLE);
            viewAnswer(userAnswer[questionNum-1]-1);
        }
        else{
            Intent intent = new Intent(PracticalActivity.this, ScoringActivity.class);
            intent.putExtra("a1", userAnswer[0]);
            intent.putExtra("a2", userAnswer[1]);
            intent.putExtra("a3", userAnswer[2]);
            intent.putExtra("a4", userAnswer[3]);
            intent.putExtra("a5", userAnswer[4]);
            intent.putExtra("ra1", questionAnswer[0]);
            intent.putExtra("ra2", questionAnswer[1]);
            intent.putExtra("ra3", questionAnswer[2]);
            intent.putExtra("ra4", questionAnswer[3]);
            intent.putExtra("ra5", questionAnswer[4]);
            startActivity(intent);
            finish();
        }
    }

    public void ansSelected(View v){
        int selected = -1;

        switch(v.getId()){
            case R.id.answer1:
                selected = 1;     break;
            case R.id.answer2:
                selected = 2;     break;
            case R.id.answer3:
                selected = 3;     break;
            case R.id.answer4:
                selected = 4;     break;
            case R.id.answer5:
                selected = 5;     break;
            default:              break;
        }

        viewAnswer(selected-1);
        if(questionNum!=0)
            userAnswer[questionNum-1] = selected;
    }

    private void viewAnswer(int selected){
        for(int i=0; i<5; i++)
            ans_image[i].setAlpha(0);
        if(selected > -1)
            ans_image[selected].setAlpha(100);
    }
}
