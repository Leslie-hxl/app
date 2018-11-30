package com.cn.android_testtwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import CarFragment.DaoSessionManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpLineActivity extends AppCompatActivity {
    private Button dl;
    private Button zc;
    private String appkey;
    private EditText user;
    private EditText password;
    private MyUserDao myUserDao;
    private StringBuilder YuanUrl;
    private StringBuilder YuanUrls;
    private SimpleDraweeView images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_line);
        initView();
        submit();

        /**
         * Rxjava解析数据获取token::
         */
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://www.apiopen.top/")
                .build();

        MyTokens myTokens = retrofit.create(MyTokens.class);
        final Observable<tokenBean> token = myTokens.getToken();

        token.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<tokenBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(tokenBean tokenBean) {
                        if (tokenBean != null) {
                            String msg = tokenBean.getMsg();
                            String appId = tokenBean.getData().getAppId();
                            appkey = tokenBean.getData().getAppkey();
                            Toast.makeText(UpLineActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名密码：
                String SongName = user.getText().toString().trim();
                String SongPassword = password.getText().toString().trim();

                //拼接注册url:
                StringBuilder stringBuilder = new StringBuilder("https://www.apiopen.top/createUser?key=");
                StringBuilder YuanUrl1 = stringBuilder.append(appkey);
                StringBuilder YuanUrl2 = YuanUrl1.append("&phone=");
                StringBuilder YuanUrl3 = YuanUrl2.append(SongName);
                StringBuilder YuanUrl4 = YuanUrl3.append("&passwd=");
                YuanUrl = YuanUrl4.append(SongPassword);
                Log.i("YuanUrl", YuanUrl.toString());

                //拼接登陆url:
                StringBuilder stringBuilder1 = new StringBuilder("https://www.apiopen.top/login?key=");
                StringBuilder YuanUrls1 = stringBuilder1.append(appkey);
                StringBuilder YuanUrls2 = YuanUrls1.append("&phone=");
                StringBuilder YuanUrls3 = YuanUrls2.append(SongName);
                StringBuilder YuanUrls4 = YuanUrls3.append("&passwd=");
                YuanUrls = YuanUrls4.append(SongPassword);
                Log.i("YuanUrls", YuanUrls.toString());
            }
        });

        /**
         * 注册用户：(拼接字符串)
         * https://www.apiopen.top/createUser?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123654
         */
        zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpUtils
                        .get()
                        .url(YuanUrl.toString())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if (response!=null){
                                    //json解析：
                                    Gson gson = new Gson();
                                    Myzhuces myzhuces = gson.fromJson(response, Myzhuces.class);
                                    Toast.makeText(UpLineActivity.this, myzhuces.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        /**
         * 获取Song这张表的操作类SongDao
         */
        DaoSession session = DaoSessionManager.getInstace().getDaoSession(getApplicationContext());
        myUserDao = session.getMyUserDao();

        /**
         * 登陆用户：
         * 1.拼接字符串:
         * 2.登录后将token和密码使用MD5加密保存到GreenDAO数据库:
         * https://www.apiopen.top/login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456
         */
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //json解析：
                OkHttpUtils
                    .get()
                    .url(YuanUrls.toString())
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (response!=null){
                                //获取用户名密码：
                                String SongName = user.getText().toString().trim();
                                String SongPassword = password.getText().toString().trim();

                                //json解析：
                                Gson gson1 = new Gson();
                                Mydenglu mydenglu = gson1.fromJson(response, Mydenglu.class);
                                String passwd = mydenglu.getData().getPasswd();
                                String phone = mydenglu.getData().getPhone();
                                Log.i("mydenglu", passwd+""+phone);

                                //判断是否匹配：
                                if (passwd.equals(SongPassword) && phone.equals(SongName)) {
                                    Toast.makeText(UpLineActivity.this, mydenglu.getMsg(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UpLineActivity.this,MainActivity.class));
                                    Log.i("mydengluS", "MO");
                                }

                                //创建用户对象类：
                                MyUser myUser = new MyUser();

                                //MD5加密：
                                String md5Str = getMD5Str(SongPassword);
                                String md5Str1 = getMD5Str(appkey);
                                Log.i("MD5",md5Str);
                                Log.i("MD5",md5Str1);
                                //Rsa非对称加密：
                                try {
                                    String s2 = new String(Base64.encode(SongPassword.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
                                    Log.i("b64",s2);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                                //把appk,密码添加到数据库：
                                myUser.setSongName(md5Str);

                                //增加:
                                myUserDao.insert(myUser);

                                //查寻：
                                List<MyUser> list = myUserDao.queryBuilder().where(MyUserDao.Properties.SongName.eq(md5Str))
                                        .list();
                                Log.i("green",list.toString());

                                new AlertDialog.Builder(UpLineActivity.this)
                                        .setMessage(list!=null && list.size() > 0 ? list.get(0).getSongName(): "data null")
                                        .setPositiveButton("ok",null)
                                        .create().show();

                                Toast.makeText(UpLineActivity.this, "用户信息添加成功", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
            }
        });
    }

    /**
     * MD5加密：
     *
     * @param str
     * @return
     */
    private String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] digest = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            if (Integer.toHexString(0xFF & digest[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & digest[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & digest[i]));
            }
        }
        //16位加密，从第9位到25位
        return md5StrBuff.substring(8, 24).toString().toUpperCase();
    }

    private void initView() {
        dl = (Button) findViewById(R.id.dl);
        zc = (Button) findViewById(R.id.zc);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        images = (SimpleDraweeView) findViewById(R.id.images);
    }

    private void submit() {
        // validate
        String userString = user.getText().toString().trim();
        if (TextUtils.isEmpty(userString)) {
            Toast.makeText(this, "userString不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "passwordString不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
