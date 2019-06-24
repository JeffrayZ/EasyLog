package com.zff.easylog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zff.easylog.main.EasyLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EasyLog.getInstance().writeToFile(TAG, "Main创建成功");

       ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
       ses.scheduleAtFixedRate(new Runnable() {
           @Override
           public void run() {
               EasyLog.getInstance().writeToFile(TAG,"5555     " + getNowDateAndTime());
           }
       },0,1000, TimeUnit.MILLISECONDS);

        ScheduledExecutorService ses1 = Executors.newScheduledThreadPool(3);
        ses1.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                EasyLog.getInstance().writeToFile(TAG,"4444     " + getNowDateAndTime());
            }
        },0,1000, TimeUnit.MILLISECONDS);

        ScheduledExecutorService ses2 = Executors.newScheduledThreadPool(3);
        ses2.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                EasyLog.getInstance().writeToFile(TAG,"3333     " + getNowDateAndTime());
            }
        },0,1000, TimeUnit.MILLISECONDS);

        ScheduledExecutorService ses3 = Executors.newScheduledThreadPool(3);
        ses3.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                EasyLog.getInstance().writeToFile(TAG,"2222     " + getNowDateAndTime(),true,Log.ERROR);
            }
        },0,1000, TimeUnit.MILLISECONDS);

        ScheduledExecutorService ses4 = Executors.newScheduledThreadPool(3);
        ses4.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                EasyLog.getInstance().writeToFile(TAG,"1111     " + getNowDateAndTime(),true, Log.ERROR);
            }
        },0,1000, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyLog.getInstance().writeToFile(TAG, "Main onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyLog.getInstance().writeToFile(TAG, "Main onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        EasyLog.getInstance().writeToFile(TAG, "Main onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        EasyLog.getInstance().writeToFile(TAG, "Main onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        EasyLog.getInstance().writeToFile(TAG, "Main onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyLog.getInstance().writeToFile(TAG, "Main被销毁");
    }

    /**
     * 格式化成为 年 月 日 时分秒
     */
    private static SimpleDateFormat format_date_ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String getNowDateAndTime() {
        return format_date_ymdhms.format(Calendar.getInstance().getTime());
    }
}
