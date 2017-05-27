package kr.ac.sogang.cs.hello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Recommend extends AppCompatActivity {
    int questionNum = 0;
    TextView[] q = new TextView[5];
    ScrollView scrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        q[0] = (TextView) findViewById(R.id.question11);
        q[1] = (TextView) findViewById(R.id.question12);
        q[2] = (TextView) findViewById(R.id.question13);
        q[3] = (TextView) findViewById(R.id.question14);
        scrl = (ScrollView) findViewById(R.id.verScroll);


        try{
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
        }

        String temp = "1. 다음 글에서 필자가 주장하는 바로 가장 적절한 것은?\n" +
                "One of the most difficult things many successful people do is\n" +
                "to challenge their own beliefs. Convictions that may have once\n" +
                "been true and useful may change. Friedrich Nietzsche said it\n" +
                "well when he said, “It’s not simply a question of having the\n" +
                "courage of one’s convictions, but at times having the courage to\n" +
                "attack one’s convictions.” That’s how you grow. That’s how you\n" +
                "mature. That’s how you develop. Look at Tolstoy himself, a\n" +
                "great example of a man who was willing to grow because he\n" +
                "realized that he had to attack, at times, his own convictions.\n" +
                "Socrates said it well when he said, “The unexamined life is not\n" +
                "worth living.” But we need to add that the examined life is\n" +
                "painful, risky, full of vulnerability. And, yet, to revitalize public\n" +
                "conversation, we have to ensure that self-criticism and\n" +
                "self-correction are accented in our individual lives, as well as\n" +
                "in our society and world.\n" +
                "① 성장을 위해 자신의 신념에 도전하라.\n" +
                "② 성공을 위해 역경을 밑거름으로 삼으라.\n" +
                "③ 타인의 비판에 대해 수용적 태도를 가지라.\n" +
                "④ 타인을 비판하기 전에 자신을 먼저 돌아보라.\n" +
                "⑤ 자신의 신념을 실행에 옮기는 용기를 키우라.\n";
        q[1].setText(temp);

        temp = "2. 다음 글의 요지로 가장 적절한 것은?\n" +
                "Why do we need to routinely have the oil changed in our\n" +
                "automobiles? Why do we need to see our dentist twice a year?\n" +
                "The simple answer to these questions is preventative\n" +
                "maintenance. How many times have you heard of stories where\n" +
                "people ignored the warning signs and adverse situations\n" +
                "seemed to present themselves overnight? A friend of mine\n" +
                "knew there was a nail in one of his front tires, but there didn’t\n" +
                "seem to be any obvious damage to the tire. He chose to ignore\n" +
                "the nail until he found himself on the side of the highway with a\n" +
                "flat tire. He later told me that before he experienced the\n" +
                "embarrassment of having a flat, he “planned on getting it fixed\n" +
                "when he had the time”. If he would have only taken a few\n" +
                "minutes to get the nail removed, he most likely would not have\n" +
                "received a flat tire on that particular day.\n" +
                "① 문제 발생을 막기 위해 사전 예방이 필요하다.\n② 안전 장비 착용을 의무화하는 것이 중요하다.\n" +
                "③ 사고 발생 시 침착한 대응이 바람직하다.\n ④ 어려운 일은 여럿이 함께 해결하는 것이 좋다.\n" +
                "⑤ 안전사고 예방에 대한 철저한 교육이 요구된다.";
        q[2].setText(temp);

        temp = "3. 다음 글의 주제로 가장 적절한 것은?\n" +
                "One of the reasons for difficulty in achieving one’s optimal\n" +
                "weight is poor nutrient timing. When you eat is almost as\n" +
                "important as what you eat, because the same nutrients have\n" +
                "different effects on the body when consumed at different times.\n" +
                "The body’s energy needs change throughout the day. It’s\n" +
                "important to concentrate your food intake during those times\n" +
                "when your body’s energy needs are greatest and not to\n" +
                "consume more calories than your body needs to meet its\n" +
                "immediate energy needs at any time. When you consume\n" +
                "calories at times of peak energy need, most of them are used to\n" +
                "fuel your muscles and nervous system, to synthesize muscle\n" +
                "tissue, and to replenish muscle fuel stores. When you consume\n" +
                "more calories than you need at any time, those excess calories\n" +
                "will be stored as body fat. * replenish: 다시 채우다\n" +
                "① the effects of nutrient timing on psychological states\n" +
                "② the roles of essential nutrients to improve your health\n" +
                "③ the correlation between slow eating and calorie intake\n" +
                "④ the benefits of maintaining optimal weight for your health\n" +
                "⑤ the importance of nutrient timing to reach optimal weight\n";
        q[3].setText(temp);


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
    }

    public void onRecommendNext(View v){
        if(questionNum!=3){
            q[questionNum].setVisibility(View.INVISIBLE);
            questionNum++;
            scrl.smoothScrollTo(0,0);
            q[questionNum].setVisibility(View.VISIBLE);
        }
    }
}
