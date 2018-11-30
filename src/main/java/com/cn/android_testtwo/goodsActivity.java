package com.cn.android_testtwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import CarFragment.CarActivity;
import HomeFragment.HomeActivity;

public class goodsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView goods_image;
    private ImageButton ib_good_info_back;
    private ImageButton ib_good_info_more;
    private TextView tv_good_info_name;
    private TextView tv_good_info_desc;
    private TextView tv_good_info_price;
    private TextView tv_good_info_store;
    private TextView tv_good_info_style;
    private WebView wb_good_info_more;
    private TextView tv_good_info_callcenter;
    private TextView tv_good_info_collection;
    private TextView tv_good_info_cart;
    private Button btn_good_info_addcart;
    private LinearLayout ll_goods_root;
    private goodsBean goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        initView();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int value = (int) extras.get("value");

        if (value == 4) {
            getIntent().getSerializableExtra("Seckill_goods");
            goods= (goodsBean) extras.get("Seckill_goods");
            tv_good_info_name.setText(goods.getName());
            tv_good_info_price.setText("￥"+goods.getCover_price());
            setWebViewData(goods.getProduct_id());
            Glide.with(this).load(goods.getFigure()).into(goods_image);
        } else if (value == 5) {
            goods= (goodsBean) extras.get("Recommend_goods");
            tv_good_info_name.setText(goods.getName());
            tv_good_info_price.setText("￥"+goods.getCover_price());
            setWebViewData(goods.getProduct_id());
            Glide.with(this).load(goods.getFigure()).into(goods_image);
        } else if (value == 6) {
            goods= (goodsBean) extras.get("hot_goods");
            tv_good_info_name.setText(goods.getName());
            tv_good_info_price.setText("￥"+goods.getCover_price());
            setWebViewData(goods.getProduct_id());
            Glide.with(this).load(goods.getFigure()).into(goods_image);
        }
    }

    private void initView() {
        goods_image = (ImageView) findViewById(R.id.goods_image);

        ib_good_info_back = (ImageButton) findViewById(R.id.ib_good_info_back);
        ib_good_info_back.setOnClickListener(this);
        ib_good_info_more = (ImageButton) findViewById(R.id.ib_good_info_more);
        ib_good_info_more.setOnClickListener(this);
        tv_good_info_name = (TextView) findViewById(R.id.tv_good_info_name);
        tv_good_info_name.setOnClickListener(this);
        tv_good_info_desc = (TextView) findViewById(R.id.tv_good_info_desc);
        tv_good_info_desc.setOnClickListener(this);
        tv_good_info_price = (TextView) findViewById(R.id.tv_good_info_price);
        tv_good_info_price.setOnClickListener(this);
        tv_good_info_store = (TextView) findViewById(R.id.tv_good_info_store);
        tv_good_info_store.setOnClickListener(this);
        tv_good_info_style = (TextView) findViewById(R.id.tv_good_info_style);
        tv_good_info_style.setOnClickListener(this);
        wb_good_info_more = (WebView) findViewById(R.id.wb_good_info_more);
        wb_good_info_more.setOnClickListener(this);
        tv_good_info_callcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        tv_good_info_callcenter.setOnClickListener(this);
        tv_good_info_collection = (TextView) findViewById(R.id.tv_good_info_collection);
        tv_good_info_collection.setOnClickListener(this);
        tv_good_info_cart = (TextView) findViewById(R.id.tv_good_info_cart);
        tv_good_info_cart.setOnClickListener(this);
        btn_good_info_addcart = (Button) findViewById(R.id.btn_good_info_addcart);
        btn_good_info_addcart.setOnClickListener(this);
        ll_goods_root = (LinearLayout) findViewById(R.id.ll_goods_root);
        ll_goods_root.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_good_info_back:
                finish();
                break;
            case R.id.ib_good_info_more:
                Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_good_info_addcart:
                Toast.makeText(this, "添加到购物车", Toast.LENGTH_SHORT).show();
                Log.i("g",goods.toString());
                CarStorage.getInstance().addDate(goods);
                break;
            case R.id.btn_more:

                break;
        }
    }

    public void setWebViewData(String webViewData) {
        if (webViewData!=null){
            wb_good_info_more.loadUrl("https://blog.csdn.net/LoverLeslie");
            WebSettings settings = wb_good_info_more.getSettings();
            settings.setUseWideViewPort(true);
            settings.setJavaScriptEnabled(true);

            wb_good_info_more.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        }
    }
}
