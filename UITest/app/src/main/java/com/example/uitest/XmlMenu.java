package com.example.uitest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class XmlMenu extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_menu);
        textView = (TextView) findViewById(R.id.test_text);//从xml_menu中获得文本test_text
    }

    //创建选项菜单，菜单内容在menu文件下的menu_settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    //菜单响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.font_10:
                textView.setTextSize(10*2);
                break;
            case R.id.font_16:
                textView.setTextSize(16*2);
                break;
            case R.id.font_20:
                textView.setTextSize(20*2);
                break;
            case R.id.normal:
                Toast.makeText(XmlMenu.this, "普通菜单项", Toast.LENGTH_SHORT).show();
                break;
            case R.id.color_red:
                textView.setTextColor(Color.RED);
                break;
            case R.id.color_black:
                textView.setTextColor(Color.BLACK);
                break;
        }
        return true;
    }



}
