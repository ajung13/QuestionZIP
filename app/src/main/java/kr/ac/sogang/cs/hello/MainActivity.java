package kr.ac.sogang.cs.hello;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String[] navItems = {"내 정보", "오답노트", "사용방법", "Contact"};
    private ListView lvNavList;
    private FrameLayout flContainer;
    private DrawerLayout dlDrawer;
    private Button btn;
    final Window win = getWindow();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNavList = (ListView) findViewById(R.id.lv_activity_main_nav_list);
        flContainer = (FrameLayout) findViewById(R.id.fl_activity_main_container);
        btn = (Button) findViewById(R.id.slide_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlDrawer.openDrawer(lvNavList);
            }
        });

        dlDrawer = (DrawerLayout) findViewById(R.id.dl_activity_main_drawer);
        lvNavList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            switch (position) {
                case 0:
                    Intent intent = new Intent(getApplicationContext(), myinfoActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(), "오답노트(예정)", Toast.LENGTH_LONG).show();
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "사용방법(예정)", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "개발자 contact (예정)", Toast.LENGTH_LONG).show();
                    break;
            }
            dlDrawer.closeDrawer(lvNavList);
        }
    }

    @Override
    public void onBackPressed(){
        if(dlDrawer.isDrawerOpen(lvNavList))
            dlDrawer.closeDrawer(lvNavList);
        else
            super.onBackPressed();
    }

    public void onButton1Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), Recommend.class);
        startActivity(intent);
    }
    public void onButton2Clicked(View v){
        Intent intent = new Intent(getApplicationContext(), UploadQuestion.class);
        startActivity(intent);
    }
}