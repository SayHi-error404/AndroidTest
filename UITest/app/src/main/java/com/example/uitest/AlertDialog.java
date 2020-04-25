package com.example.uitest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AlertDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        //获得按钮
        Button dialog_button = (Button)findViewById(R.id.dialog_button);
        //监听按钮
        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();//点击按钮后执行方法
            }
        });
    }

    public void createDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        // LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化；
        // findViewById()是找xml布局文件下的具体widget控件(如Button、TextView等)。
        builder.setView(inflater.inflate(R.layout.custom_dialog, null));//加载布局
        builder.setTitle("ANDROID APP");
        builder.setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // 登录
                        Toast.makeText(getApplicationContext(),"登录",Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),"取消",Toast.LENGTH_LONG).show();
                    }
                });
        builder.create();//创建对话框
        builder.show();//显示对话框
    }

}
