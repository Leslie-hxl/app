package HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cn.android_testtwo.R;
import com.cn.android_testtwo.goodsActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class bannerHolder extends RecyclerView.ViewHolder{
    public Banner banner;
    private Context context;
    private List<String> images=new ArrayList<>();

    public bannerHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        banner=itemView.findViewById(R.id.ban);
    }
    public void setData(List<HomeBean.ResultBean.BannerInfoBean> data) {
        for (int i = 0; i < data.size(); i++) {
            String image = data.get(i).getImage();
            images.add(image);
        }
        //Banner的样式:
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片的加载器：
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合：
        banner.setImages(images);
        //设置banner动画效果：
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true:
        banner.isAutoPlay(true);
        ////设置轮播时间:
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）:
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setViewPagerIsScroll(true);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(context, "页面的点击跳转详情", Toast.LENGTH_SHORT).show();
            }
        });
        banner.start();
    }
}
