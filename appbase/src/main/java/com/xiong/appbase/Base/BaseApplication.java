/**
 *
 */
package com.xiong.appbase.Base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;
import com.xiong.appbase.Base.utils.DLog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


//保存了一些全局可用的方法,含对activity和dialog的相关操作
public class BaseApplication extends Application {

    //activity的栈
    public final Stack<Activity> mActivityStack = new Stack<Activity>();
    //传值时参数的map
    private final HashMap<String, Object> mActivityParamsMap = new HashMap<String, Object>();
    private static BaseApplication mAppInstance;
    // 用于存放倒计时时间
    public static Map<String, Long> timeMap;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppInstance = this;
        mContext = getApplicationContext();
        Utils.init(mAppInstance);

    }

    //单例
    public static BaseApplication getInstance() {
        return mAppInstance;
    }

    public static Context getAppContext() {
        return mContext;
    }

    public BaseApplication() {
        DLog.i("ElfApplication", "ElfApplication");
    }

    //放入传值的参数
    public void setInternalActivityParam(String key, Object object) {
        mActivityParamsMap.put(key, object);
    }

    /**
     * @param key
     * @return
     */
    //传值移除参数
    public Object receiveInternalActivityParam(String key) {
        return mActivityParamsMap.remove(key);
    }

    /**
     * 移除堆栈里某个特定的Activity，不销毁
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 在Activity堆栈里最顶部增加一个Activity
     */
    public void pushActivity(Activity activity) {
        mActivityStack.add(activity);
    }

    /**
     * 清除销毁获取堆栈里某个特定的Activity
     */
    private void finishActivity(Activity activity) {
        if (activity != null) {
            DLog.d(getClass().getSimpleName(), "finish:" + getClass().getSimpleName());
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 获取堆栈里某个特定的Activity
     */
    public Activity getSpecialActivity(Class<?> cls) {
        Activity act = null;
        for (int i = mActivityStack.size() - 1; i >= 0; i--) {
            Activity act_ = mActivityStack.get(i);
            if (act_ == null)
                continue;
            if (cls.equals(act_.getClass())) {
                act = act_;
                break;
            }
        }
        return act;
    }

    /**
     * 清除销毁获取堆栈里某个特定的Activity
     */
    public void finishSpecialActivity(Class<?> cls) {
        for (int i = mActivityStack.size() - 1; i >= 0; i--) {
            Activity act = mActivityStack.get(i);
            if (act == null)
                continue;
            if (cls.equals(act.getClass())) {
                finishActivity(act);
            }
        }
    }

    /**
     * 获取堆栈里 最顶部的Activity
     */
    public Activity getTopActivity() {
        Activity activity = null;
        if (!mActivityStack.empty())
            activity = mActivityStack.lastElement();
        return activity;
    }


    /**
     * 清除销毁堆栈里所有的Activity，除了特定的某个Activity
     */
    public void finishAllActivityExceptOne(Class<?> cls) {
        for (int i = mActivityStack.size() - 1; i >= 0; i--) {
            Activity act = mActivityStack.get(i);
            if (act == null)
                continue;
            if (!cls.equals(act.getClass())) {
                finishActivity(act);
            }
        }
    }

    /**
     * 清除销毁堆栈里所有的Activity，退出程序
     */
    public void finishApplication() {
        while (!mActivityStack.empty()) {
            Activity activity = getTopActivity();
            finishActivity(activity);
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
