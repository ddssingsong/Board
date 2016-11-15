package com.dds.board;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    // 处理菜单事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawView drawView = (DrawView) findViewById(R.id.drawView);
        switch (item.getItemId()) {
            // 设置id为menu_exit的菜单子项所要执行的方法。
            case R.id.menu1_shub1:
                drawView.mPaint.setStrokeWidth(1);
                break;
            case R.id.menu1_shub2:
                drawView.mPaint.setStrokeWidth(5);
                break;
            case R.id.menu1_shub3:
                drawView.mPaint.setStrokeWidth(10);
                break;
            case R.id.menu1_shub4:
                drawView.mPaint.setStrokeWidth(50);
                break;
            case R.id.menu2:
                drawView.mPaint.setColor(Color.BLACK);
                break;
            case R.id.menu3:
                drawView.mPaint.setColor(Color.WHITE);
                break;
            case R.id.menu4:
                try {
                    drawView.saveBitmap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.menu5:
                System.exit(0);// 结束程序
                break;
        }
        return true;
    }
}
