package com.xiong.appbase.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

//自定义异常处理器,报异常时会把异常信息写进文本保存
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/CrashLog/";
    private static final String FILE_NAME = "crash ";
    private static final String FILE_NAME_SUFFIX = ".txt";
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler mInstance;

    private CrashHandler(Context context) {
        if (mContext == null) {
            mContext = context.getApplicationContext();
        }
        //系统默认的异常处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static CrashHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CrashHandler(context);
        }
        return mInstance;
    }

    //当程序有未捕获的异常,系统将自动调用uncaughtException方法,ex参数即为该异常
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        dumpException2Card(e);
        e.printStackTrace();
        //如果系统提供了默认的异常处理器,就交给系统去处理,否则就杀掉自己
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpException2Card(Throwable e) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(mContext, "SD卡不可用", Toast.LENGTH_LONG).show();
            return;
        }
        File folder = new File(PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String time = getCurrentDate();
        File file = new File(PATH + FILE_NAME + time + FILE_NAME_SUFFIX);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            e.printStackTrace(pw);
            pw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        //APP版本号
        pw.print("App Version: ");
        pw.print(info.versionName);
        pw.print("_");
        pw.println(info.versionCode);
        //Android版本
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        //CPU架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    private String getCurrentDate() {
        //获取当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis());
    }
}
