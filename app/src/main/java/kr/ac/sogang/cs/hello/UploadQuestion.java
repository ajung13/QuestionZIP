package kr.ac.sogang.cs.hello;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_question);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void onUpload(View v){
        String writedata = "";

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

        for(int i=0; i<10; i++){
            if(cb[i].isChecked()){
                boolean result = phpTest(i);     //start from 0
                if(result)
                    Toast.makeText(getApplication(), "upload completed: "+(i+19), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplication(), "upload failed: "+(i+19), Toast.LENGTH_SHORT).show();
            }
        }

        finish();

/*        boolean flag=false;
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
        }*/
    }

    public boolean phpTest(final int qnum){
        String urladdr = "http://questionzip.dothome.co.kr/UploadR.php";
        try{
            String postData = "qnum=" + qnum;
            URL url = new URL(urladdr);
            int responsecode;
            int redirectedCount = 0;

            while(redirectedCount <= 2){
                HttpURLConnection conn2 = (HttpURLConnection)url.openConnection();
                conn2.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn2.setRequestProperty("Connection", "close");
                conn2.setRequestMethod("POST");
                conn2.setConnectTimeout(10000);
                conn2.setDoOutput(true);
                conn2.setDoInput(true);
                conn2.setUseCaches(false);
                conn2.connect();
//                responsecode = conn2.getResponseCode();
                responsecode = HttpURLConnection.HTTP_OK;
                if(responsecode==HttpURLConnection.HTTP_OK){
                    OutputStream os = conn2.getOutputStream();
                    os.write(postData.getBytes("EUC-KR"));
                    os.flush();
                    os.close();
                    conn2.disconnect();
                    return true;
                }
                else if(responsecode==HttpURLConnection.HTTP_MOVED_PERM ||
                        responsecode==HttpURLConnection.HTTP_MOVED_TEMP){
                    String redirectURL = conn2.getHeaderField("Location");
                    url = new URL(redirectURL);
                }
                redirectedCount++;
            }
        } catch(Exception e){
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Log.e("phpTest", exceptionAsString);
            return false;
        }
        return false;
    }


}
