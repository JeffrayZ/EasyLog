//package com.zff.easylog;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//
///**
// * Created by Byk on 2016/10/20.
// * <p>
// * Log Printf
// */
//public class LogMan {
//
//    private static boolean sLogEnable = true;
//    private static boolean sFileLogEnable = false;
//
//    private static String mClassName = LogMan.class.getName();
//    private static ArrayList<String> mMethods = new ArrayList<>();
//
//    static {
//        Method[] ms = LogMan.class.getDeclaredMethods();
//        for (Method m : ms) {
//            mMethods.add(m.getName());
//        }
//    }
//
//    public static void setEnable(boolean logEnable) {
//        sLogEnable = logEnable;
//    }
//
//    public static boolean issFileLogEnable() {
//        return sFileLogEnable;
//    }
//
//    public static void setsFileLogEnable(boolean sFileLogEnable) {
//        LogMan.sFileLogEnable = sFileLogEnable;
//    }
//
//    public static void writeToSdCard(String tag, String msg) {
//        writeToSdCard(tag, msg, true);
//    }
//
//    public static void writeToSdCard(String tag, String msg, boolean printLogcat) {
//        if (BuildConfig.DEBUG || issFileLogEnable()) {
//            writeToSdCard(tag, msg, printLogcat, false);
//        }
//    }
//
//    public static void writeToSdCard(String tag, String msg, boolean printLogcat, boolean withLine) {
//        if (sLogEnable) {
//            if (TextUtils.isEmpty(msg)) {
//                msg = "null";
//            }
//
//            if (printLogcat) {
//                i(tag, msg, withLine);
//            }
//
//            if (sFileLogEnable) {
//                try {
//                    LogFileMan.writeToSDCard(tag, msg);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    /**
//     * 写入设备到本地，BASE64加密
//     *
//     * @param
//     * @param msg
//     * @return
//     */
//    private static boolean writeBase42StringSd(String tag, String msg) {
//        try {
//            String deviceFile = Coder.encryptBASE64(msg.getBytes());
//            //FileUtil.writeStringToSdCard(deviceFile, encryptDeviceId);
//            LogFileMan.writeToSDCard(tag, deviceFile);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public static void d(String tag, String msg) {
//        d(tag, msg, true);
//    }
//
//    public static void d(String tag, String msg, boolean withLine) {
//        if (sLogEnable) {
//            Log.d(tag, withLine ? getMsgWithLineNumber(msg) : msg);
//        }
//    }
//
//    public static void e(String tag, String msg) {
//        e(tag, msg, true);
//    }
//
//    public static void e(String tag, String msg, boolean withLine) {
//        if (sLogEnable) {
//            Log.e(tag, withLine ? getMsgWithLineNumber(msg) : msg);
//        }
//    }
//
//    public static void i(String tag, String msg) {
//        i(tag, msg, true);
//    }
//
//    public static void i(String tag, String msg, boolean withLine) {
//        if (sLogEnable) {
//            Log.i(tag, withLine ? getMsgWithLineNumber(msg) : msg);
//        }
//    }
//
//    public static void w(String tag, String msg) {
//        w(tag, msg, true);
//    }
//
//    public static void w(String tag, String msg, boolean withLine) {
//        if (sLogEnable) {
//            Log.w(tag, withLine ? getMsgWithLineNumber(msg) : msg);
//        }
//    }
//
//    public static void v(String tag, String msg) {
//        v(tag, msg, true);
//    }
//
//    public static void v(String tag, String msg, boolean withLine) {
//        if (sLogEnable) {
//            Log.v(tag, withLine ? getMsgWithLineNumber(msg) : msg);
//        }
//    }
//
//    public static void d(String msg) {
//        d(msg, true);
//    }
//
//    public static void d(String msg, boolean withLine) {
//        if (sLogEnable) {
//            String[] content = getMsgAndTagWithLineNumber(msg);
//            Log.d(content[0], withLine ? content[1] : msg);
//        }
//    }
//
//    public static void e(String msg) {
//        e(msg, true);
//    }
//
//    public static void e(String msg, boolean withLine) {
//        if (sLogEnable) {
//            String[] content = getMsgAndTagWithLineNumber(msg);
//            Log.e(content[0], withLine ? content[1] : msg);
//        }
//    }
//
//    public static void i(String msg) {
//        i(msg, true);
//    }
//
//    public static void i(String msg, boolean withLine) {
//        if (sLogEnable) {
//            String[] content = getMsgAndTagWithLineNumber(msg);
//            Log.i(content[0], withLine ? content[1] : msg);
//        }
//    }
//
//    public static void i() {
//        if (sLogEnable) {
//            String[] content = getMsgAndTagWithLineNumber("");
//            Log.i(content[0], content[1]);
//        }
//    }
//
//    public static void w(String msg) {
//        w(msg, true);
//    }
//
//    public static void w(String msg, boolean withLine) {
//        if (sLogEnable) {
//            String[] content = getMsgAndTagWithLineNumber(msg);
//            Log.w(content[0], withLine ? content[1] : msg);
//        }
//    }
//
//    public static void v(String msg) {
//        v(msg, true);
//    }
//
//    public static void v(String msg, boolean withLine) {
//        if (sLogEnable) {
//            String[] content = getMsgAndTagWithLineNumber(msg);
//            Log.v(content[0], withLine ? content[1] : msg);
//        }
//    }
//
//    public static String getMsgWithLineNumber(String msg) {
//        try {
//            StackTraceElement[] e = (new Throwable()).getStackTrace();
//            for (StackTraceElement st : e) {
//                if (!mClassName.equals(st.getClassName()) && !mMethods.contains(st.getMethodName())) {
//                    int b = st.getClassName()
//                            .lastIndexOf(".") + 1;
//                    String TAG = st.getClassName()
//                            .substring(b);
//                    return TAG + "->" + st.getMethodName() + "():" + st.getLineNumber() + "->" + msg;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return msg;
//    }
//
//    public static String[] getMsgAndTagWithLineNumber(String msg) {
//        try {
//            StackTraceElement[] e = (new Throwable()).getStackTrace();
//            for (StackTraceElement st : e) {
//                if (!mClassName.equals(st.getClassName()) && !mMethods.contains(st.getMethodName())) {
//                    int b = st.getClassName()
//                            .lastIndexOf(".") + 1;
//                    String TAG = st.getClassName()
//                            .substring(b);
//                    String message = st.getMethodName() + "():" + st.getLineNumber() + "->" + msg;
//                    return new String[]{TAG, message};
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new String[]{"universal tag", msg};
//    }
//
//    /**
//     * 打印调用堆栈
//     *
//     * @param tag
//     * @param msg
//     * @param printf
//     */
//    public static void wtf(String tag, String msg, boolean printf) {
//        StackTraceElement st[] = Thread.currentThread().getStackTrace();
//        writeToSdCard(tag, msg, printf);
//        for (int i = 0; i < st.length; i++) {
//            writeToSdCard(tag, i + ":" + st[i], printf);
//        }
//    }
//}
