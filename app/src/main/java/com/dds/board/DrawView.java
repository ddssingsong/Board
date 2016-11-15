package com.dds.board;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dds on 2016/11/14 0014.
 * 涂鸦画板
 */

public class DrawView extends View {

    private Bitmap cacheBitmap;//一张画布

    private Canvas cacheCanvas;//创建画家

    public Paint mPaint;//一支画笔

    private Path mPath;//画图的路径

    private float prex, prey;

    private int view_width, view_height;//屏幕的宽度和高度

    //初始化的工作放到构造方法中，为了节省内存，每次调用invalide都需要调用onDraw方法
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPath = new Path();
        cacheCanvas = new Canvas();

        //获取屏幕的宽度和高度
        view_width = context.getResources().getDisplayMetrics().widthPixels;
        view_height = context.getResources().getDisplayMetrics().heightPixels;

        //建立图片缓冲区用来保存图像
        cacheBitmap = Bitmap.createBitmap(view_width, view_height, Bitmap.Config.ARGB_8888);
        cacheCanvas.setBitmap(cacheBitmap);
        cacheCanvas.drawColor(Color.WHITE);//首先将画布的颜色设置为白色

        mPaint.setColor(Color.BLACK);//设置画笔的颜色为黑色
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为填充
        mPaint.setStrokeWidth(1);//设置画笔的宽度为1
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //直接将cacheBitmap画到Canvas上
        canvas.drawBitmap(cacheBitmap, 0, 0, mPaint);
    }


    //绘制路径，需要重写onTouchEvent()方法


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //1.获取起始位置
        //2.绘制路径
        //3.手抬起来的时候reset

        //获取触摸的位置
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //绘图的起始点
                mPath.moveTo(x, y);
                prex = x;
                prey = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //记录滑动的距离
                float dx = Math.abs(x - prex);
                float dy = Math.abs(y - prey);
                if (dx > 5 || dy > 5) {
                    //将之前的路径换成现在的
                    mPath.quadTo(prex,prey,(x+prex)/2,(y+prey)/2);
                    prex = x;
                    prey = y;
                    cacheCanvas.drawPath(mPath, mPaint);
                }
                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起的时候将Path重置
                mPath.reset();
                break;
        }
        invalidate();
        //将事件消费掉
        return true;
    }

    //将图片保存到存储卡中
    public void saveBitmap() throws Exception {
        //获取到存储位置
        //讲Bitmap压缩并存到存储中
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = new SimpleDateFormat("yyMMddhhmmss", Locale.getDefault()).format(new Date
                (System.currentTimeMillis()));
        //创建文件
        File file = new File(sdPath + File.separator + filename + "png");
        file.createNewFile();
        FileOutputStream os = new FileOutputStream(file);
        cacheBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);

        //关闭流
        os.flush();
        os.close();
        Toast.makeText(getContext(), "图片已经保存到" + sdPath + File.separator + filename + "png", Toast.LENGTH_SHORT).show();


    }
}
