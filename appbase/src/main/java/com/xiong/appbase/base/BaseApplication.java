/**
 *
 */
package com.xiong.appbase.base;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.xiong.appbase.custom.JbREfreshFooter;
import com.xiong.appbase.custom.JbRefreshHeader;
import com.xiong.appbase.utils.DLog;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


//保存了一些全局可用的方法,含对activity和dialog的相关操作
//public class BaseApplication extends Application {
public class BaseApplication extends MultiDexApplication {

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new JbRefreshHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new JbREfreshFooter(context);
            }
        });
    }

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
//        Utils.init(this);
        UMConfigure.init(mContext, "5ad83f418f4a9d598d0000aa", "jinbang",
                UMConfigure.DEVICE_TYPE_PHONE, "");
        //bugly
        // 获取当前包名
        String packageName = mContext.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(mContext);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        //数据库
        LitePal.initialize(this);
        // 初始化Bugly
//        CrashReport.initCrashReport(mContext, Config.BUGLY_ID, false, strategy);
//        CrashReport.testJavaCrash();

//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1106685804", "otZiNgl4wbjCWsmB");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
