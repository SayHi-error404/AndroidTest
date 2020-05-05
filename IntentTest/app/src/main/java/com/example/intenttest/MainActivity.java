package com.example.intenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//获得布局
        final EditText editText = (EditText)findViewById(R.id.url);//获得编辑框
        Button button = (Button)findViewById(R.id.url_button);//获得按钮
        //对按钮进行监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = editText.getText().toString();//获得编辑框中的url链接
                Intent intent = new Intent(Intent.ACTION_VIEW);//设置ACTION_VIEW（显示指定数据）
                Uri uri = Uri.parse(data);//根据指定字符串解析出Uri对象
                intent.setData(uri); //设置Data属性
                //intent.setData(Uri.parse("https://"+url+"/"));//拼接url
                startActivity(intent);//启动intent
            }
        });
    }


}
