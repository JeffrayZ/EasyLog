package com.zff.easylog.main;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * @ProjectName: EasyLogConfig
 * @Package: com.zff.easylog.main
 * @ClassName: EasyLogConfig
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2019/6/18 10:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/18 10:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public final class EasyLogConfig {
    final Context mContext;
    /**
     * 是否允许在release包中使用
     */
    final boolean mUseInRelease;
    /**
     * log文件夹名称
     */
    final String logDirName;

    public EasyLogConfig(Builder builder) {
        mContext = builder.mContext;
        mUseInRelease = builder.mUseInRelease;
        logDirName = builder.logDirName;
    }

//    public void init() {
//        creatLocalDir(builder.logDirName, builder.mContext);
//    }




//    private void writeToFile1(String msg) {
//        String localDirPaht = Environment.getExternalStorageDirectory()
//                .getAbsolutePath() + File.separator + localPath;
//        File file = new File(localDirPaht + File.separator + getNowDate() + TAG + ".log");
//        // 是否允许写入
//        boolean isAllowToWrite = false;
//        if(file.exists()){
//            isAllowToWrite = true;
//        } else {
//            try {
//                isAllowToWrite = file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if(isAllowToWrite){
//            BufferedWriter bufferedWriter = null;
//            FileWriter fileWriter = null;
//            try {
//                fileWriter = new FileWriter(file,true);
//                bufferedWriter = new BufferedWriter(fileWriter);
//                bufferedWriter.write(getNowDateAndTime());
//                bufferedWriter.newLine();
//                bufferedWriter.write(msg);
//                bufferedWriter.newLine();
//                bufferedWriter.newLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if(bufferedWriter != null){
//                    try {
//                        bufferedWriter.flush();
//                        bufferedWriter.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                if(fileWriter != null){
//                    try {
//                        fileWriter.flush();
//                        fileWriter.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }

    public static final class Builder {
        private Context mContext;
        /**
         * 是否允许在release包中使用
         */
        private boolean mUseInRelease;
        /**
         * log文件夹名称
         */
        private String logDirName;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setCanUserInRelease(boolean mUseInRelease) {
            Log.e("EasyLogConfig", "设置是否允许在release包中使用》》》" + mUseInRelease);
            this.mUseInRelease = mUseInRelease;
            return this;
        }

        public Builder setLocalDirPath(String logDirName) {
            Log.e("EasyLogConfig", "设置文件夹名称》》》" + logDirName);
            this.logDirName = logDirName;
            return this;
        }

        public EasyLogConfig build() {
            return new EasyLogConfig(this);
        }
    }
}
