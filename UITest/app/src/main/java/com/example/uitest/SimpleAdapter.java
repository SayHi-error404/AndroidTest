package com.example.uitest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAdapter extends AppCompatActivity {

    //动物名称
    private String[] animal={"Lion","Tiger","Monkey","Dog","Cat","Elephant"};
    //动物图片
    private int[] animalImages= new int[]
            {R.drawable.lion,R.drawable.tiger,R.drawable.monkey,
                    R.drawable.dog,R.drawable.cat,R.drawable.elephant};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_adapter);

        //创建一个List集合，元素是Map
        List<Map<String,Object>> listItems = new ArrayList<>();
        for (int i=0;i<animal.length;i++)
        {
            Map<String,Object> listItem = new HashMap<>();
            listItem.put("animal",animal[i]);
            listItem.put("image",animalImages[i]+"");
            listItems.add(listItem);
        }

        //创建一个SimpleAdapter
        android.widget.SimpleAdapter simpleAdapter=new android.widget.SimpleAdapter(
                this,//当前上下文
                listItems,//list集合，列表项的数据集
                R.layout.simple_adapter_list,//界面布局，提供列表项的布局
                new String[] {"animal","image"},//指定Map<String,?>中的String Key
                new int[] {R.id.textView,R.id.imageView});//指定要填充的组件（如：将animal填入textView中。textView在simple_adapter_list）

        //获得ListView
        ListView simpleListView=(ListView)findViewById(R.id.simpleListView);
        //为获得ListView设置Adapter
        simpleListView.setAdapter(simpleAdapter);

        //设置点击后出现Toast提示
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),animal[i],Toast.LENGTH_LONG).show();
            }
        });
    }
}

