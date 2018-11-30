package com.cn.android_testtwo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车选择的物品存储类：
 */
public class CarStorage {
    private Context context;
    private static CarStorage instance;//购物车实例对象：
    public static final String JSON_CART = "json_cart";
    private SparseArray<goodsBean> sparseArray;//存储商品创建一个优于hashmap的集合


    /**
     * 第二步：构造方法+创建存储集合;
     * @param context
     */
    private CarStorage(Context context){
        this.context=context;
        sparseArray=new SparseArray<>(100);
        listToSparse();
    }

    /**
     * 第一步：单例模式(懒汉式)：第一次调用的时候，实例购物车：
     * @return
     */
    public static CarStorage getInstance(){
        if (instance==null){
            instance=new CarStorage(MyNews.getContext());
        }
        return instance;
    }

    /**
     * 第三步：1.读取本地数据
     *         2.添加到集合当中：
     */
    private void listToSparse() {
        List<goodsBean> goodsBeanList=getAllData();
        Log.i("zzzz",goodsBeanList.toString());
        //2.把list数据转换成SparseArray；
        for (int i = 0; i < goodsBeanList.size(); i++) {
            goodsBean goodsBean=goodsBeanList.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }
    }

    //1.读取本地数据:
    public List<goodsBean> getAllData() {
        List<goodsBean> goodsBeanList=new ArrayList<>();
        //1.存本地获取：
        String json=CacheUtils.getString(context,JSON_CART);
        //2.使用gson转成列表：(判断不为空是就执行)
        if (!TextUtils.isEmpty(json)){
            //直接把String转换成list
            goodsBeanList=new Gson().fromJson(json,new TypeToken< List<goodsBean>>(){}.getType());
            Log.i("zzzzz",goodsBeanList.toString());
        }else{
            Log.i("zzzzz","nonononononono");
        }
        return goodsBeanList;
    }

    /**
     * 添加数据：
     * @param goodsBean
     */
    public void addDate(goodsBean goodsBean){
        //1.添加到内存中——如果商品已存在，就修改数据numer递增：
        goodsBean tempData=sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempData !=null){
            tempData.setNumber(tempData.getNumber()+1);
        }else{
            tempData=goodsBean;
            tempData.setNumber(1);
        }
        //同步到内存中：
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()),tempData);
        Log.i("aaaa",sparseArray.toString());
        //2.同步到本地
        saveLocal();
    }
    public void deleteDate(goodsBean goodsBean){
        //1.内存中删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        //2.同步到本地
        saveLocal();
    }
    public void updateDate(goodsBean goodsBean){
        //1.内存中更新
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        //2.同步到本地
        saveLocal();
    }

    /**
     * 同步到本地的方法：
     */
    private void saveLocal() {
        //sparseArray转成list
            List<goodsBean> goodsBeanList=sparseToList();
            //把列表转换成————String类型
            String json=new Gson().toJson(goodsBeanList);
            //把数据储存：
            CacheUtils.saveString(context,JSON_CART,json);
        }

    private List<goodsBean> sparseToList() {
        List<goodsBean> goodsBeanList=new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            goodsBean goodsBeans=sparseArray.valueAt(i);
            Log.i("zzzz",sparseArray.toString());
            goodsBeanList.add(goodsBeans);
        }
        return goodsBeanList;
    }
}
