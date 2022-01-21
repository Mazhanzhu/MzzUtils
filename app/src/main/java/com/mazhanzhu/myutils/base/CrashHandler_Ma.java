package com.mazhanzhu.myutils.base;

import android.content.Context;
import android.os.SystemClock;

import com.mazhanzhu.utils.Log_Ma;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局捕获异常
 * 当程序发生Uncaught异常的时候,有该类来接管程序,并记录错误日志
 */
public class CrashHandler_Ma {
    public static String TAG = "MyCrash";
    // 用来存储设备信息和异常信息
    private static Map<String, String> map = new HashMap<>();

    //使用volatile关键字保其可见性
    volatile private static CrashHandler_Ma instance = null;

    private CrashHandler_Ma() {
    }

    public static CrashHandler_Ma getInstance() {
        try {
            if (instance != null) {//懒汉式

            } else {
                //创建实例之前可能会有一些准备性的耗时工作
                Thread.sleep(300);
                synchronized (CrashHandler_Ma.class) {
                    if (instance == null) {//二次检查
                        instance = new CrashHandler_Ma();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public void init(Context context) {
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                try {
                    Log_Ma.e(TAG, "uncaughtException: " + ex);
                    if (ex != null) {
                        // 使用Toast来显示异常信息
                        /*new Thread() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                Toast.makeText(context, "APP运行缓慢，自动调整中。", Toast.LENGTH_LONG).show();
                                Looper.loop();
                            }
                        }.start();*/

//                        saveCrashInfoFile(ex, context);

                        SystemClock.sleep(1000);
//                        restartApp();
                    }
                } catch (Exception e) {
                    SystemClock.sleep(1000);
                    // 退出程序
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            }
        });
    }
}
