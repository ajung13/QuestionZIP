package kr.ac.sogang.cs.hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class wrongNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_note);
    }

    private void pdfprint(){
        //레이아웃은 res>layout>activity_wrong_note.xml 에서 확인
        //오답노트 activity에서 버튼을 누르면 이 함수를 실행함
    }
}
