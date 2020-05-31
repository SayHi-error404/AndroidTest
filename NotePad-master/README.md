# NotePad
This is an AndroidStudio rebuild of google SDK sample NotePad

# 期中实验拓展要求
 1. NoteList中显示条目增加时间戳显示
 2. 添加笔记查询功能（根据标题查询）

# 实现过程与结果
## NoteList中显示条目增加时间戳显示
为了实现这个拓展功能，首先修改布局文件，对**noteslist_item.xml**文件添加布局和一个新的**textview**用于存放时间，代码如下：
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/textView1"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:gravity="center_vertical"
        android:paddingLeft="5dip"
        android:singleLine="true"
        android:textAppearance="?android:attr/textAppearanceLarge"
        android:textColor="#000000"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/textView2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:paddingLeft="5dip"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/textView1" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
接下来将数据传进前端，打开**NotesList**文件，找到`PROJECTION`,对其进行修改，添加文本的修改时间`COLUMN_NAME_MODIFICATION_DATE`，若前端需显示的文本创建时间那也可添加创建时间`COLUMN_NAME_CREATE_DATE`：
```java
/**
* The columns needed by the cursor adapter
*/
private static final String[] PROJECTION = new String[] {
		NotePad.Notes._ID, // 0
		NotePad.Notes.COLUMN_NAME_TITLE, // 1
		NotePad.Notes.COLUMN_NAME_CREATE_DATE,
		NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
};
```
找到`onCreate`方法中的`dataColumns`和`viewIDs`进行修改，dataColumns中写注入数据的列名，viewIDs则是注入数据目标的组件：
```java
// The names of the cursor columns to display in the view, initialized to the title column
String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE ,NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;

// The view IDs that will display the cursor columns, initialized to the TextView in
// noteslist_item.xml
int[] viewIDs = { R.id.textView1, R.id.textView2 };

SimpleCursorAdapter adapter
        = new SimpleCursorAdapter(
        this,                             // The Context for the ListView
        R.layout.noteslist_item,          // Points to the XML for a list item
        cursor,                           // The cursor to get items from
        dataColumns,
        viewIDs
);
```
这样时间戳的显示就完成了，但是出现了一个问题，在源代码中，获得的是当前时间的时间戳，如1590576894896	
![img1](
https://github.com/SayHi-error404/AndroidTest/blob/master/NotePad-master/ScreenShots/img1.png )	

该时间为格林威治时间1970年01月01日00时00分00秒起至当下的总秒数，但是在本次实验拓展，希望实现类似于2020-05-29 05:31:13这样的时间，可是经过多次修改和寻找，最终还是不知道怎样在查询时修改显示的时间数据，于是改为**在添加时改变添加时间的格式**和**在修改时改变修改时间的格式**。

首先打开**NotePadProvider**文件找到`insert`方法，找到该方法中的`now`常量，对其进行修改：
```java
Date date = new Date();
SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
String now = sdf.format(date);
```
接着打开**NoteEditor**文件，找到`updateNote`方法，找到该方法中的`now`常量，对其进行修改（同上）：
```java
Date date = new Date();
SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
String now = sdf.format(date);
```
**实现效果截图：**	

<img src="https://github.com/SayHi-error404/AndroidTest/blob/master/NotePad-master/ScreenShots/img2.png" width="500" height="750" alt="img2"/>

## 添加笔记查询功能（根据标题查询）
首先在主界面为搜索添加一个菜单按钮，打开**list_options_menu**文件，为其添加一个search的`item`：
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">
    <!--  This is our one standard application action (creating a new note). -->

    <item android:id="@+id/menu_search"
        android:title="@string/menu_search"
        android:icon="@drawable/ic_menu_search"
        android:alphabeticShortcut='s'
        android:showAsAction="always" />

    <item android:id="@+id/menu_add"
          android:icon="@drawable/ic_menu_compose"
          android:title="@string/menu_add"
          android:alphabeticShortcut='a'
        android:showAsAction="always" />

    <!--  If there is currently data in the clipboard, this adds a PASTE menu item to the menu
          so that the user can paste in the data.. -->
    <item android:id="@+id/menu_paste"
          android:icon="@drawable/ic_menu_compose"
          android:title="@string/menu_paste"
          android:alphabeticShortcut='p' />
</menu>
```
注：此处新增的`item`中的`android:icon`可以使用安卓自带的图片
```xml
android:icon="@android:drawable/ic_menu_search"
或
android:icon="@android:drawable/ic_search_category_default"
```
也可使用从网上下载的图片，在我的代码中使用的是网上下载的图片，将图片名称修改`ic_menu_search`，并放入**drawable**文件下，图片可从阿里矢量图标库[https://www.iconfont.cn/](https://www.iconfont.cn/)处下载。	
**实现效果截图：**

<img src="https://github.com/SayHi-error404/AndroidTest/blob/master/NotePad-master/ScreenShots/img3.png" alt="img3"/>

添加完了按钮，接下来新建一个Activity，取名为**NoteSearch**，新建的同时Android Studio会自动生成一个名为activity_note_search的布局文件，此处为了规范文件我修改为其名字为**note_search**（可改可不改）。首先先实现布局文件，给其添加`constraintlayout`布局以及`SearchView`搜索框组件和`ListView`列表组件，如下：
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".NoteSearch">

    <SearchView
        android:id="@+id/search_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:iconifiedByDefault="false"
        android:hint="@string/search_hint"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <ListView
        android:id="@id/android:list"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/search_view" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
接下来来到**NoteList**找到它的`onOptionsItemSelected`方法，在`switch`中添加一个`case`，让使用者在点击搜索后打开NoteSearch搜索界面：
```java
case R.id.menu_search:
	Intent intent = new Intent(NotesList.this, NoteSearch.class);
	startActivity(intent);
	return true;
```
**实现效果截图：**

<img src="https://github.com/SayHi-error404/AndroidTest/blob/master/NotePad-master/ScreenShots/img4.png" width="500" height="750" alt="img4"/>

最终对**NoteSearch**文件进行修改，完成查询功能，此处模仿**NoteList**文件：
```java
package com.example.android.notepad;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

public class NoteSearch extends ListActivity implements SearchView.OnQueryTextListener {
    // For logging and debugging
    private static final String TAG = "NoteSearch";

    /**
     * The columns needed by the cursor adapter
     */
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_CREATE_DATE,
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_search);
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(NotePad.Notes.CONTENT_URI);
        }
        //获得组件
        SearchView searchview = (SearchView)findViewById(R.id.search_view);
        //注册监听
        //searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener(){});
        //上面这样写在SimpleCursorAdapter中的context不能写this，未解决，改为本类实现OnQueryTextListener接口
        searchview.setOnQueryTextListener(NoteSearch.this);

    }

    //查询文本提交，此处直接显示list，不进行提交
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //文本改变，在list中显示查询结果
    @Override
    public boolean onQueryTextChange(String newText) {
        //模糊查询
        /* Performs a managed query. The Activity handles closing and requerying the cursor
         * when needed.
         *
         * Please see the introductory note about performing provider operations on the UI thread.
         */
        String selection = NotePad.Notes.COLUMN_NAME_TITLE + " Like ? ";
        String[] selectionArgs = { "%"+newText+"%" };
        //Log.e(TAG,"search "+selection+newText);
        Cursor cursor = managedQuery(
                getIntent().getData(),            // Use the default content URI for the provider.
                PROJECTION,                       // Return the note ID and title for each note.
                selection,                        // where语句（where like ?）
                selectionArgs,                    // 输入的内容
                NotePad.Notes.DEFAULT_SORT_ORDER  // Use the default sort order.
        );

        // The names of the cursor columns to display in the view, initialized to the title column
        String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE ,NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;

        // The view IDs that will display the cursor columns, initialized to the TextView in
        // noteslist_item.xml
        int[] viewIDs = { R.id.textView1, R.id.textView2 };

        SimpleCursorAdapter adapter
                = new SimpleCursorAdapter(
                this,                    // The Context for the ListView
                R.layout.noteslist_item,          // Points to the XML for a list item
                cursor,                           // The cursor to get items from
                dataColumns,
                viewIDs
        );

        // Sets the ListView's adapter to be the cursor adapter that was just created.
        setListAdapter(adapter);
        return true;
    }


    //和NotesList同
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        // Constructs a new URI from the incoming URI and the row ID
        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);

        // Gets the action from the incoming Intent
        String action = getIntent().getAction();

        // Handles requests for note data
        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {

            // Sets the result to return to the component that called this Activity. The
            // result contains the new URI
            setResult(RESULT_OK, new Intent().setData(uri));
        } else {

            // Sends out an Intent to start an Activity that can handle ACTION_EDIT. The
            // Intent's data is the note ID URI. The effect is to call NoteEdit.
            startActivity(new Intent(Intent.ACTION_EDIT, uri));
        }
    }
}
```
大体上和**NoteList**相同，保留`onListItemClick`对文本选择的反应，继承了**ListActivity**，实现**SearchView**的`OnQueryTextListener`监听接口，实现模糊查询，在此处原本是打算使用`searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener(){});`这种方式注册监听的，但是在实现这个接口的过程中`SimpleCursorAdapter `下的**context无法使用this**，不知是何原因，尚未解决。因此查询了百度和询问他人，最终改为实现这个接口，重写它的方法后在`OnCreate`中启动监听。

**实验效果截图：**

<img src="https://github.com/SayHi-error404/AndroidTest/blob/master/NotePad-master/ScreenShots/img5.png" width="500" height="750" alt="img5"/>

至此，期中实验结束。
