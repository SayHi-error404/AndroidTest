package com.example.uitest;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Set;

public class ActionMode extends ListActivity {

    private String[] data = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine","Ten"};

    private SelectionAdapter selectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_mode);

        //创建一个SelectionAdapter
        selectionAdapter = new SelectionAdapter(
            this,
            R.layout.action_mode_list,
            R.id.textView1,
            data);

        //设置Adapter
        setListAdapter(selectionAdapter);
        //设置选择模式，CHOICE_MODE_MULTIPLE_MODAL模式用户必须通过长按任意一个列表项，进入多选模式，否则不能进行多选
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        //实现setMultiChoiceModeListener接口的5个方法
        getListView().setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            //被选中的数量
            private int nr = 0;

            //条目选中状态
            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position,
                                                  long id, boolean checked) {
                // TODO Auto-generated method stub
                if (checked) {
                    nr++;//选中项+1
                    //Toast.makeText(getApplicationContext(),"+1",Toast.LENGTH_LONG).show();
                    selectionAdapter.setNewSelection(position, checked);
                } else {
                    nr--;//选中项-1
                    //Toast.makeText(getApplicationContext(),"-1",Toast.LENGTH_LONG).show();
                    selectionAdapter.removeSelection(position);
                }
                mode.setTitle(nr + " selected");

            }

            //点击
            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                // TODO Auto-generated method stub
                switch (item.getItemId()) {

                    case R.id.item_delete://右上角删除键
                        nr = 0;
                        selectionAdapter.clearSelection();
                        mode.finish();
                }
                return false;
            }

            //创建选项菜单，菜单内容在menu文件下的contextual_menu
            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                nr = 0;//初始为0
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.contextual_menu, menu);
                return true;
            }

            //删除
            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                // TODO Auto-generated method stub
                //没有选中项，清空selectionAdapter的内容
                selectionAdapter.clearSelection();
            }


            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }

        });


    }

    //自定义Adapter绑定ListView
    private class SelectionAdapter extends ArrayAdapter<String> {

        private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

        public SelectionAdapter(Context context, int resource,
                                int textViewResourceId, String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        public void setNewSelection(int position, boolean value) {
            mSelection.put(position, value);
            notifyDataSetChanged();
        }

        public boolean isPositionChecked(int position) {
            Boolean result = mSelection.get(position);
            return result == null ? false : result;
        }

        public Set<Integer> getCurrentCheckedPosition() {
            return mSelection.keySet();
        }

        public void removeSelection(int position) {
            mSelection.remove(position);
            notifyDataSetChanged();
        }

        public void clearSelection() {
            mSelection = new HashMap<Integer, Boolean>();
            notifyDataSetChanged();
        }

        //Adapter的显示
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);//let the adapter handle setting up the row views
            v.setBackgroundColor(getResources().getColor(android.R.color.background_light)); //default color，背景颜色

            if (mSelection.get(position) != null) {
                v.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));// this is a selected position so make it red，选中的颜色
            }
            return v;
        }
    }
}

