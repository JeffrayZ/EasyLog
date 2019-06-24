package com.zff.easylog;

import android.app.Application;

/**
 * @ProjectName: EasyLog
 * @Package: com.zff.easylog
 * @ClassName: MainApplication
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2019/6/19 10:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 10:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MainApplication extends Application {
    private MainApplication() {}

    private static class SingletonInstance {
        private static final MainApplication INSTANCE = new MainApplication();
    }

    public static MainApplication getInstance() {
        return SingletonInstance.INSTANCE;
    }

}
