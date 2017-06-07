package kr.ac.sogang.cs.hello;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import Jama.Matrix;

public class Recommend extends AppCompatActivity {
    int questionNum = 0;
    TextView[] q = new TextView[5];
    ScrollView scrl;
    Handler mHandler;
    public int mainTime = 0;
    Matrix mR;
//    TextView timer = (TextView) findViewById(R.id.q_timer);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        q[0] = (TextView) findViewById(R.id.question11);
        q[1] = (TextView) findViewById(R.id.question12);
        q[2] = (TextView) findViewById(R.id.question13);
        q[3] = (TextView) findViewById(R.id.question14);
        scrl = (ScrollView) findViewById(R.id.verScroll);


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

        phptest();


 /*       try{
            StringBuffer data = new StringBuffer();
            FileInputStream fis = openFileInput("wrongquestion.txt");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));
            String line = buffer.readLine();
            while(line!=null){
                data.append(line + " 모의고사의 ");
                line = buffer.readLine();
                data.append(line+"\n");
                line = buffer.readLine();
            }
            data.append("을 기준으로 문제를 추천하겠습니다.");
            q[0].setText(data);
            buffer.close();
        }catch(Exception e){
            e.printStackTrace();
            q[0].setText("아직 틀린 문제를 업로드 하지 않았습니다.\n임의로 문제를 추천할까요?");
        }*/


    }

    public void phptest(){
        String test = "http://163.239.78.130/SelectR.php";
        URLConnector task = new URLConnector(test);
        task.start();
        try{
            task.join();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String result = task.getResult();
        ParseJSON(result);
        ParseJSON(result);
        return;
    }


    public void ParseJSON(String target){
        try{
            JSONObject json = new JSONObject(target);
            JSONArray arr = json.getJSONArray("result");
            for(int i=0; i<arr.length(); i++){
                JSONObject json2 = arr.getJSONObject(i);
                for(int j=0; j<10; j++){
                    String tmp = json2.get("q"+j).toString();
                    mR.set(i, j, Double.parseDouble(tmp));
                }
            }
            String tmp = strung(mR);
            System.out.println(tmp);
        }catch(Exception e){
            e.printStackTrace();
        }
        return;
    }


    public static String strung(Matrix m) {
        StringBuffer sb = new StringBuffer();
        for (int r = 0; r < m.getRowDimension(); ++ r) {
            for (int c = 0; c < m.getColumnDimension(); ++c)
                sb.append(m.get(r, c)).append("\t");
            sb.append("\n");
        }
        return sb.toString();
    }

    public void onBackButton(View v){
        Toast.makeText(getApplicationContext(), "Back to menu", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onRecommendPrev(View v){
        if(questionNum!=0){
            q[questionNum].setVisibility(View.INVISIBLE);
            questionNum--;
            scrl.smoothScrollTo(0,0);
            q[questionNum].setVisibility(View.VISIBLE);
        }
        return;
    }

    public void onRecommendNext(View v){
        if(questionNum!=3){
            q[questionNum].setVisibility(View.INVISIBLE);
            questionNum++;
            scrl.smoothScrollTo(0,0);
            q[questionNum].setVisibility(View.VISIBLE);
        }
        return;
    }
}
