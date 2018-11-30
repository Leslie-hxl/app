package HomeFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cn.android_testtwo.R;

import java.util.List;

public class MyRecycler extends RecyclerView.Adapter{

    private Context context;
    private HomeBean.ResultBean result;
    private LayoutInflater layoutInflater;

    public static final int banner = 0;//广告
    public static final int chanel= 1;//频道
    public static final int act=2;//活动
    public static final int seckill=3;//秒杀
    public static final int recommend=4;//推荐
    public static final int hot=5;//热卖

    private int currenType= banner;//当前显示

    public MyRecycler(Context context, HomeBean.ResultBean result) {
        this.context = context;
        this.result = result;
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==banner){
            return new bannerHolder(context,layoutInflater.inflate(R.layout.banner_item,null));
        }else if (viewType==chanel){
            return new MyViewHolder(context,layoutInflater.inflate(R.layout.channel_item,null));
        }else if (viewType==act){
            return new ActHolder(context,layoutInflater.inflate(R.layout.act_item,null));
        }else if (viewType==seckill){
            return new SeckillHolder(context,layoutInflater.inflate(R.layout.seckill_item,null));
        }else if (viewType==recommend){
            return new RecommendHolder(context,layoutInflater.inflate(R.layout.recommend_item,null));
        }else if (viewType==hot){
            return new HotHolder(context,layoutInflater.inflate(R.layout.hot_item,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==banner){
            bannerHolder bannerHolders= (bannerHolder) holder;
            List<HomeBean.ResultBean.BannerInfoBean> banner_info = result.getBanner_info();
            bannerHolders.setData(banner_info);
        }else if (getItemViewType(position)==chanel){
            MyViewHolder myViewHolder= (MyViewHolder) holder;
            List<HomeBean.ResultBean.ChannelInfoBean> channel_info= result.getChannel_info();
            myViewHolder.setData(channel_info);
        }else if (getItemViewType(position)==act){
            ActHolder actHolder= (ActHolder) holder;
            List<HomeBean.ResultBean.ActInfoBean> act_info = result.getAct_info();
            actHolder.setData(act_info);
        }else if (getItemViewType(position)==seckill){
            SeckillHolder seckillHolder= (SeckillHolder) holder;
            HomeBean.ResultBean.SeckillInfoBean seckill_info = result.getSeckill_info();
            seckillHolder.setData(seckill_info);
        }else if (getItemViewType(position)==recommend){
            RecommendHolder recommendHolder= (RecommendHolder) holder;
            List<HomeBean.ResultBean.RecommendInfoBean> recommend_info = result.getRecommend_info();
            recommendHolder.setData(recommend_info);
        }else if (getItemViewType(position)==hot){
            HotHolder hotHolder= (HotHolder) holder;
            List<HomeBean.ResultBean.HotInfoBean> hot_info = result.getHot_info();
            hotHolder.setData(hot_info);
        }
    }
    /**
     * item类型：
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case banner:
                currenType = banner;
                break;
            case chanel:
                currenType = chanel;
                break;
            case act:
                currenType = act;
                break;
            case seckill:
                currenType = seckill;
                break;
            case recommend:
                currenType = recommend;
                break;
            case hot:
                currenType = hot;
                break;
        }
        return currenType;
    }

    /**
     * item的总个数：
     * @return
     */
    @Override
    public int getItemCount() {
        return 6;
    }
}

