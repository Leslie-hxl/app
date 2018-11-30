package CarFragment;

import android.content.Context;

import com.cn.android_testtwo.DaoMaster;
import com.cn.android_testtwo.DaoSession;

public class DaoSessionManager {
    private final String DB_NAME = "android.db";
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private DaoSessionManager() {
    }

    public static DaoSessionManager mInstance = new DaoSessionManager();

    public static DaoSessionManager getInstace() {

        return mInstance;
    }

    public DaoMaster getDaoMaster(Context mContext) {

        DaoMaster.DevOpenHelper mHelper = new DaoMaster
                .DevOpenHelper(mContext, DB_NAME, null);
        daoMaster = new DaoMaster(mHelper.getWritableDatabase());
        return daoMaster;
    }

    public DaoSession getDaoSession(Context mContext) {

        if (daoSession == null) {

            if (daoMaster == null) {
                getDaoMaster(mContext);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}
