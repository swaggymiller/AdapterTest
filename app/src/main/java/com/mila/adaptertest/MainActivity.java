package com.mila.adaptertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener {
    private ListView lv;
    private ArrayAdapter<String>arr_Adapter;
    private SimpleAdapter sim_Adapter;
    private List<Map<String,Object>>dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv= findViewById(R.id.listView);

        String[] arr = {"1", "2", "3", "4"};
        String[] arr1 = {"2","3","4","5"};
        //新建适配器
        //ArrayAdapter(this为上下文联系，simple_list_item_1为所需要穿插的布局文件，arr为数据源)
        //SimpleAdapter()
        /*context :上下文
          data: 数据源 （list<?extends Map<String ,?>> data）一个Map所组成的list的集合
          每一个map对应list view的列表的每一行
          map中键值对所指定的键必须包含所有from中所指定的键
          resource对应列表项布局文件的id
          from对应map中的键名
          to：绑定数据视图的id，与from成对应关系
         */
        //通过适配器联系数据源和布局文件
        //适配器加载数据源
        arr_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2,arr);
        //sim_Adapter = new SimpleAdapter(this, data, resource, from, to);
        dataList = new ArrayList<Map<String,Object>>();
        sim_Adapter = new SimpleAdapter(this, getData(),R.layout.item,new String[] {"imageview","tv","tv1"},new int[] {R.id.imageView,R.id.tv,R.id.tv1});
        //视图加载listview适配器
        //lv.setAdapter(arr_Adapter);
        lv.setAdapter(sim_Adapter);
        lv.setOnItemClickListener(this);
        lv.setOnScrollListener(this);
    }

    private List<Map<String,Object>> getData(){
        for(int i=0;i<20;i++){
            Map<String,Object>map = new HashMap<String,Object>();
            map.put("imageview",R.drawable.ic_launcher_background);
            map.put("tv","列表号："+i);
            map.put("tv1","hello*"+(i+1));
            dataList.add(map);
        }
        return dataList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text  = lv.getItemAtPosition(position)+"";
        if(position == 2){
            turn(view);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Toast.makeText(this,"hhhhhh",Toast.LENGTH_SHORT).show();
        switch(scrollState){
            case SCROLL_STATE_FLING:
                Log.i("Main","离开屏幕前划了一下");
                Map<String,Object>map = new HashMap<String,Object>();
                map.put("imageview",R.drawable.ic_launcher_background);
                map.put("tv","列表号：");
                map.put("tv1","hello*");
                dataList.add(map);
                sim_Adapter.notifyDataSetChanged();   //下拉刷新，通知UI线程去刷新
                break;
            case SCROLL_STATE_IDLE:
                Log.i("Main","停止滑动");
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i("hhh","未离开屏幕，仍在滑动");
            break;
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public  void turn(View view){
        Intent intent = new Intent(MainActivity.this,CalenderTest.class);
        startActivity(intent);
    }
}


