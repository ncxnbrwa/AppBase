package com.xiong.appbase.utils;

import android.util.Log;

import com.xiong.appbase.BuildConfig;

/**
 * 日志信息输出�?
 * <p>该类可自动或手动配置不同等级日志在发布模式下是否允许输出�?
 * 并使用android.util.Log输出日志内容</p>
 *
 */
public final class DLog {
//	private static boolean mbDebugMode = true;
	private static boolean mbDebugMode = BuildConfig.mbDebugMode;
	private static boolean mbLogDInRelease = false;
	private static boolean mbLogVInRelease = false;
	private static boolean mbLogIInRelease = true;
	private static boolean mbLogWInRelease = true;
	private static boolean mbLogEInRelease = true;
	public static void setInDebugMode(boolean bDebug) {
		mbDebugMode = bDebug;
	}
	public static void setLogDInRelease(boolean bLog) {
		mbLogDInRelease = bLog;
	}
	public static void setLogVInRelease(boolean bLog) {
		mbLogVInRelease = bLog;
	}
	public static void setLogIInRelease(boolean bLog) {
		mbLogIInRelease = bLog;
	}
	public static void setLogWInRelease(boolean bLog) {
		mbLogWInRelease = bLog;
	}
	public static void setLogEInRelease(boolean bLog) {
		mbLogEInRelease = bLog;
	}
	public static boolean isInDebugMode() {
		return mbDebugMode;
	}
	public static int d(String tag, String msg) {
		//判断再决定是否输出该信息
		if(mbDebugMode || mbLogDInRelease) {
			return Log.d(tag, msg);
		}
		return 0;
	}
	
	public static int d(String tag, int msg) {
		if(mbDebugMode || mbLogDInRelease) {
			return Log.d(tag, ""+msg);
		}
		return 0;
	}
	
	public static int d(String tag, String msg, Throwable tr) {
		if(mbDebugMode || mbLogDInRelease) {
			return Log.d(tag, msg, tr);
		}
		return 0;
	}
	public static int i(String tag, String msg) {
		if(mbDebugMode || mbLogIInRelease) {
			return Log.i(tag, msg);
		}
		return 0;
	}
	
	public static int i(String tag, int msg) {
		if(mbDebugMode || mbLogIInRelease) {
			return Log.i(tag, Integer.toString(msg));
		}
		return 0;
	}
	public static int i(String tag, String msg, Throwable tr) {
		if(mbDebugMode || mbLogIInRelease) {
			return Log.i(tag, msg, tr);
		}
		return 0;
	}
	public static int v(String tag, String msg) {
		if(mbDebugMode || mbLogVInRelease) {
			return Log.v(tag, msg);
		}
		return 0;
	}
	
	public static int v(String tag, int msg) {
		if(mbDebugMode || mbLogVInRelease) {
			return Log.v(tag, Integer.toString(msg));
		}
		return 0;
	}
	public static int v(String tag, String msg, Throwable tr) {
		if(mbDebugMode || mbLogVInRelease) {
			return Log.v(tag, msg, tr);
		}
		return 0;
	}
	public static int w(String tag, String msg) {
		if(mbDebugMode || mbLogWInRelease) {
			return Log.w(tag, msg);
		}
		return 0;
	}
	
	public static int w(String tag, int msg) {
		if(mbDebugMode || mbLogWInRelease) {
			return Log.w(tag, Integer.toString(msg));
		}
		return 0;
	}
	
	public static int w(String tag, String msg, Throwable tr) {
		if(mbDebugMode || mbLogWInRelease) {
			return Log.w(tag, msg, tr);
		}
		return 0;
	}
	public static int e(String tag, String msg) {
		if(mbDebugMode || mbLogEInRelease) {
			return Log.e(tag, msg);
		}
		return 0;
	}
	
	public static int e(String tag, int msg) {
		if(mbDebugMode || mbLogEInRelease) {
			return Log.e(tag, Integer.toString(msg));
		}
		return 0;
	}
	public static int e(String tag, String msg, Throwable tr) {
		if(mbDebugMode || mbLogEInRelease) {
			return Log.e(tag, msg, tr);
		}
		return 0;
	}
}
