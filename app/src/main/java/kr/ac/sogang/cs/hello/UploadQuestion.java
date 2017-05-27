package kr.ac.sogang.cs.hello;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class UploadQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_question);
    }

    public void onUpload(View v){
        String writedata = "";

        final RadioGroup rg = (RadioGroup)findViewById(R.id.moigosa);
        int monthinput = rg.getCheckedRadioButtonId();
        // monthinput: Radio button that user clicked
        RadioButton rb = (RadioButton)findViewById(monthinput);
        // now we can print "rb.getText().toString()"
        writedata += rb.getText().toString();
        writedata += "\n";

        final CheckBox[] cb = new CheckBox[10];
        cb[0] = (CheckBox)findViewById(R.id.rq1);
        cb[1] = (CheckBox)findViewById(R.id.rq2);
        cb[2] = (CheckBox)findViewById(R.id.rq3);
        cb[3] = (CheckBox)findViewById(R.id.rq4);
        cb[4] = (CheckBox)findViewById(R.id.rq5);
        cb[5] = (CheckBox)findViewById(R.id.rq6);
        cb[6] = (CheckBox)findViewById(R.id.rq7);
        cb[7] = (CheckBox)findViewById(R.id.rq8);
        cb[8] = (CheckBox)findViewById(R.id.rq9);
        cb[9] = (CheckBox)findViewById(R.id.rq10);

        boolean flag=false;
        for(int i=0; i<10; i++){
            if(cb[i].isChecked()) {
                if(flag){
                    writedata += ", ";
                    flag = true;
                }
                writedata += cb[i].getText().toString();
                writedata += "번 ";
            }
        }
        writedata += "\n";

        try{
            FileOutputStream fos = openFileOutput("wrongquestion.txt", Context.MODE_APPEND);
            PrintWriter out = new PrintWriter(fos);
            out.print(writedata);
            out.close();
            Toast.makeText(getApplicationContext(), "업로드가 완료되었습니다!", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Upload Error", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void onBackButton(View v){
        Toast.makeText(getApplicationContext(), "Back to menu", Toast.LENGTH_LONG).show();
        finish();
    }

}
