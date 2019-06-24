//package com.zff.easylog;
//
//import android.os.Environment;
//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
//import android.text.TextUtils;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.FilenameFilter;
//import java.io.IOException;
//import java.lang.ref.WeakReference;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// *
// * @author Byk
// * @date 2016/10/21
// * <p>
// * Log File Mgr
// */
//public class LogFileMan {
//
//    private static final String ROOT_DIR = "gnw/greenbox/HAPPY_LOG";
//    private static final String LOG_SUFFIX = ".log";
//    private static final String LOG_SPACE = "    ";
//    private static final int TIMEOUT_DAYS = 7;
//
//    private static boolean sWriteEnable = true;
//    private static final Pattern pattern = Pattern.compile("(\\d{4}-\\d{1,2}-\\d{1,2})\\.*");
//    private static final LogFileMan sInstance = new LogFileMan();
//
//    private ThreadPoolExecutor mExecutor;
//    private BlockingQueue<Runnable> mBlockQueue;
//
//    private LogFileMan() {
//        initExecutor();
//    }
//
//    public static LogFileMan getInstance() {
////        if (sInstance == null) {
////            // TODO 优化项: 日志用的地方太多,加锁排斥容易导致ANR
////            synchronized (LogFileMan.class) {
////                if (sInstance == null) {
////                    sInstance = new LogFileMan();
////                }
////            }
////        }
//        return sInstance;
//    }
//
//    public void writeToSDCard(@NonNull String tag, String msg) {
//        try {
//            if (!sWriteEnable) {
//                // Write Disable
//                return;
//            }
//
//            final File dir = getInstance().getRootDir();
//            if (dir == null || !dir.exists()) {
//                // SD Not Exist
//                return;
//            }
//
//            String logName = tag.trim()
//                    .replaceAll(":", "")
//                    .replaceAll("：", "");
//            if (TextUtils.isEmpty(logName)) {
//                // Name Not Allowed
//                return;
//            }
//
//            final String today = getNowDate();
//
//            final StringBuilder sName = new StringBuilder(today).append(File.separator)
//                    .append(logName)
//                    .append(LOG_SUFFIX);
//            final StringBuilder sMsg = new StringBuilder(getNowTiime()).append(LOG_SPACE)
//                    .append(msg);
//
//            getInstance().getExecutor()
//                    .execute(new MyRunnable(dir, sName, sMsg, today,this));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static class MyRunnable implements Runnable {
//        private File dir;
//        private StringBuilder sName;
//        private StringBuilder sMsg;
//        private String today;
//        private WeakReference<LogFileMan> wrf;
//
//        public MyRunnable(File dir, StringBuilder sName, StringBuilder sMsg, String today,LogFileMan logFileMan) {
//            this.dir = dir;
//            this.sName = sName;
//            this.sMsg = sMsg;
//            this.today = today;
//            this.wrf = new WeakReference<>(logFileMan);
//        }
//
//        @Override
//        public void run() {
//            synchronized (LogFileMan.class) {
//                write(dir, sName.toString(), sMsg.toString());
//                // 写日志是写当天的，删除是删除超过7天的文件。写完再删除过时的文件，不影响
//                wrf.get().cleanLogDir(dir, today);
//            }
//        }
//    }
//
//    private static void write(File root, String fileName, String content) {
//        FileWriter writer = null;
//        BufferedWriter bufWriter = null;
//        try {
//            boolean allowed = true;
//            File file = new File(root, fileName);
//            if (!file.exists()) {
//                if (file.getParentFile()
//                        .mkdirs()) {
//                    allowed = file.createNewFile();
//                }
//            }
//            if (allowed) {
//                writer = new FileWriter(file, true);
//                bufWriter = new BufferedWriter(writer);
//                bufWriter.write(content);
//                bufWriter.write("\n");
//                bufWriter.newLine();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (bufWriter != null) {
//                    bufWriter.close();
//                }
//                if(writer != null){
//                    writer.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void cleanLogDir(File root, final String today) {
//        if (root != null && root.exists()) {
//            File[] files = root.listFiles(new MyFilenameFilter());
//            if (files != null) {
//                int len = files.length;
//                if (len > 0) {
//                    String tmpDate;
//                    File tmpFile;
//                    for (File file1 : files) {
//                        tmpFile = file1;
//                        tmpDate = getDate(tmpFile.getName());
//                        if (!computeTwoDaysWithInSpecified(tmpDate, today, TIMEOUT_DAYS)) {
//                            // 如果存储的文件名的时间戳和今天的间隔在7天之外，则删除文件夹
//                            if (delete(file1)) {
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//
//    private static class MyFilenameFilter implements FilenameFilter {
//
//        @Override
//        public boolean accept(File dir, String name) {
//            return Pattern.matches("\\d{4}-\\d{1,2}-\\d{1,2}", name);
//        }
//    }
//
//    private static boolean delete(File file) {
//        if (!file.exists()) {
//            return false;
//        }
//
//        if (file.isFile()) {
//            return file.delete();
//        }
//
//        File[] childFiles = file.listFiles();
//        if (childFiles == null || childFiles.length == 0) {
//            return file.delete();
//        }
//
//        for (File theFile : childFiles) {
//            delete(theFile);
//        }
//        return file.delete();
//    }
//
//    public ThreadPoolExecutor getExecutor() {
//        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated() || mExecutor.isTerminating()) {
//            initExecutor();
//        }
//        return mExecutor;
//    }
//
//    private void initExecutor() {
//        mBlockQueue = new LinkedBlockingQueue<>();
//        mExecutor = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 30, TimeUnit.SECONDS, mBlockQueue,
//                new ThreadFactoryBuilder()
//                        .setNameFormat("LogMan-Pool-%d")
//                        .build());
//    }
//
//    private File getRootDir() {
//        try {
//            // 华为 TIT-AL00抽风报错: Attempt to invoke interface method 'android.os.storage.StorageVolume[]
//            // android.os.storage.IMountService.getVolumeList()' on a null object reference
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                File file = new File(Environment.getExternalStorageDirectory(), ROOT_DIR);
//                if (!file.exists()) {
//                    if (file.mkdirs()) {
//                        return file;
//                    }
//                } else {
//                    return file;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Get All External Dirs Include Inner SDCard Dir
//        File[] sdCardDirs = ContextCompat.getExternalFilesDirs(AppSprite.INSTANCE, null);
//        if (sdCardDirs != null) {
//            for (File dir : sdCardDirs) {
//                File file = new File(dir, ROOT_DIR);
//                if (!file.exists()) {
//                    if (file.mkdirs()) {
//                        return file;
//                    }
//                } else {
//                    return file;
//                }
//            }
//        }
//
//        return null;
//    }
//
//    private File directoryCache;
//
//    public File getRootDir() {
//        if (directoryCache == null) {
//            directoryCache = getRootDir(true);
//        }
//
//        return directoryCache;
//    }
//
//    /**
//     * 通过给定的字符串获取对应的日期
//     */
//    public static String getDate(String content) {
//
//        Matcher matcher = pattern.matcher(content);
//        if (matcher.find()) {
//            return matcher.group(1);
//        }
//        return null;
//    }
//
//    /**
//     * 格式化成为 年 月 日
//     */
//    private static SimpleDateFormat format_date_ymd = new SimpleDateFormat("yyyy-MM-dd");
//    private String getNowDate() {
//        return format_date_ymd.format(Calendar.getInstance().getTime());
//    }
//
//    /**
//     * 格式化成为 时 分 秒
//     */
//    private static SimpleDateFormat format_date_hms = new SimpleDateFormat("HH:mm:ss");
//    private String getNowTiime() {
//        return format_date_ymd.format(Calendar.getInstance().getTime());
//    }
//
//    /**
//     * 计算两个时间间隔是否在指定天数内
//     *
//     * @param endStr      结束时间
//     * @param startStr    开始时间
//     * @param intervalDay 开始时间与结束时间指定的间隔 *@return 如果开始时间与结束时间的日期间隔之差小于或者intervalDay
//     */
//    private boolean computeTwoDaysWithInSpecified(String startStr, String endStr, int intervalDay) {
//        boolean isPositive = intervalDay == Math.abs(intervalDay);
//        Date startDate = getYMDByString(startStr);
//        Date endDate = getYMDByString(endStr);
//        if (startDate == null || endDate == null) {
//            // 日期格式错误，判断不在范围内
//            return false;
//        }
//        long timeLong = endDate.getTime() - startDate.getTime();
//        int dayInterval = (int) (timeLong / 1000 / 60 / 60 / 24);
//        if (isPositive) {
//            return dayInterval >= 0 && dayInterval <= intervalDay;
//        } else {
//            return dayInterval >= intervalDay && dayInterval <= 0;
//        }
//    }
//
//    /**
//     * 通过指定字符串获取 年 月 日
//     * */
//    private Date getYMDByString(String date){
//        try {
//            return format_date_ymd.parse(date);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}