package com.cn.android_testtwo;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * 初始化OkHttpUtils:
 */
public class MyNews extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster master;
    private DaoSession session;
    private MyUserDao myUserDao;
    //静态单例
    public static MyNews instances;
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;

        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(1000L, TimeUnit.MILLISECONDS)
                .readTimeout(1000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);

        Fresco.initialize(this);

        instances=this;
//        setDatabase();
    }
    public static MyNews getInstances(){
        return instances;
    }

    /**
     * 设置greenDao:
     * 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
     * 能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
     * 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
     * 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
     * 此处sport-db表示数据库名称 可以任意填写
     */
    private void setDatabase(){
        //实现创建:
        mHelper = new DaoMaster.DevOpenHelper(this, "android.db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        master = new DaoMaster(db);
        session= master.newSession();
        myUserDao = session.getMyUserDao();
    }
    public SQLiteDatabase getDatabase() {
        return db;
    }

    public DaoSession getDaoSession() {
        return session;
    }

    public MyUserDao getUserDao() {
        return myUserDao;
    }
}

