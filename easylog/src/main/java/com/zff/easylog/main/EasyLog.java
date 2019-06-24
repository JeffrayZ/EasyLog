package com.zff.easylog.main;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.zff.easylog.util.EasyLogUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @ProjectName: EasyLog
 * @Package: com.zff.easylog.main
 * @ClassName: EasyLog
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2019/6/19 11:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 11:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EasyLog {
    private static final String TAG = "EasyLog";
    private static volatile EasyLog singleton;
    private EasyLogConfig config;
    private File rootDir;
    private File curLogDir;
    private File curLogFile;
    /**
     * log根目录地址
     */
    private String logDirPath;

    private EasyLog() {
    }

    public static EasyLog getInstance() {
        if (singleton == null) {
            synchronized (EasyLog.class) {
                if (singleton == null) {
                    singleton = new EasyLog();
                }
            }
        }
        return singleton;
    }

    public void init(EasyLogConfig config) {
        this.config = config;
        logDirPath = getLogDirPath();
    }

    private String getLogDirPath() {
        String PATH_LOGCAT;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.e(TAG, "即将在外部存储创建文件夹");
            // 优先保存到SD卡中
            PATH_LOGCAT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + config.logDirName;
        } else {
            Log.e(TAG, "即将在内部存储创建文件夹");
            // 如果SD卡不存在，就保存到本应用的目录下
            PATH_LOGCAT = config.mContext.getFilesDir().getAbsolutePath() + File.separator + config.logDirName;
        }
        return PATH_LOGCAT;
    }

    /***
     * 根据路径创建文件夹的方法
     */
    private File creatRootDir(String parentDirPath) {
        File file = new File(parentDirPath);
        if (!file.exists()) {
            boolean isSuccess = file.mkdirs();
            Log.e(TAG, isSuccess ? "根目录创建成功" : "根目录创建失败");
        } else {
            Log.e(TAG, "根目录已存在，无需重复创建");
        }
        return file;
    }

    /***
     * 根据路径创建文件夹的方法
     */
    private File creatLogDir(String parentDirPath, String childDirName) {
        File file = new File(parentDirPath, childDirName);
        if (!file.exists()) {
            boolean isSuccess = file.mkdirs();
            Log.e(TAG, isSuccess ? childDirName + "文件夹创建成功" : childDirName + "文件夹创建失败");
        } else {
            Log.e(TAG, childDirName + "文件夹已存在，无需重复创建");
        }
        return file;
    }

    /***
     * 根据路径创建文件的方法
     */
    private File creatFile(String path, String fileName) {
        File file = new File(path, fileName);
        if (!file.exists()) {
            try {
                boolean isSuccess = file.createNewFile();
                Log.e(TAG, isSuccess ? fileName + "文件创建成功" : fileName + "文件创建失败");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, fileName + "文件已存在，无需重复创建");
        }

        return file;
    }

    /**
     * @param logFileName 日志文件的名称
     * @param msg         写入的log
     * @author Jeffray
     * @date 2019年6月21日 15:30:40
     * @desc 写日志
     */
    public void writeToFile(String logFileName, String msg) {
        this.writeToFile(logFileName,msg,false);
    }

    /**
     * @param logFileName 日志文件的名称
     * @param msg         写入的log
     * @param showLog     是否在控制台展示
     * @author Jeffray
     * @date 2019年6月21日 15:30:55
     * @desc 写日志
     */
    public void writeToFile(String logFileName, String msg, boolean showLog) {
        this.writeToFile(logFileName,msg,showLog,-1);
    }

    /**
     * @param logFileName 日志文件的名称
     * @param msg         写入的log
     * @param showLog     是否在控制台展示
     * @param level       日志等级
     * @author Jeffray
     * @date 2019/6/21 15:21
     * @desc 写日志
     */
    public void writeToFile(String logFileName, String msg, boolean showLog, int level) {
        if (showLog) {
            switch (level) {
                case Log.DEBUG:
                    Log.d(TAG, msg);
                    break;
                case Log.ERROR:
                    Log.e(TAG, msg);
                    break;
                case Log.INFO:
                    Log.i(TAG, msg);
                    break;
                case Log.VERBOSE:
                    Log.v(TAG, msg);
                    break;
                case Log.WARN:
                    Log.w(TAG, msg);
                    break;
                default:
                    Log.v(TAG, msg);
                    break;
            }
        }
        rootDir = creatRootDir(logDirPath);
        curLogDir = creatLogDir(rootDir.getPath(), logFileName);
        curLogFile = creatFile(curLogDir.getPath(), EasyLogUtil.getNowDate() + "_" + logFileName + ".log");

        if (rootDir != null && rootDir.exists() && curLogDir != null && curLogDir.exists()
                && curLogFile != null && curLogFile.exists()) {
            Log.e(TAG, curLogFile.getName() + "可以写入log日志");
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                fileWriter = new FileWriter(curLogFile, true);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(EasyLogUtil.getNowDateAndTime());
                bufferedWriter.newLine();
                bufferedWriter.write(msg);
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Log.e(TAG, "当前状态不可写入log日志");
        }
    }
}
