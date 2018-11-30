package HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cn.android_testtwo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;

public class SeckillHolder extends RecyclerView.ViewHolder{
    private Context context;
    public TextView more,time;
    public RecyclerView rv;
    private long dt=0;//相差的时间——毫秒
    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dt=dt-1000;
            //获取当前时间差：
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
            String format = simpleDateFormat.format(new Date(dt));
            time.setText(format);

            //线程倒计时：
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0,1000);
            if (dt<=0){
                //移除消息：
                handler.removeCallbacksAndMessages(null);
            }
        }
    };

    public SeckillHolder(Context context, View itemView) {
        super(itemView);
        this.context=context;
        more=itemView.findViewById(R.id.more);
        time=itemView.findViewById(R.id.time);
        rv=itemView.findViewById(R.id.rv);
    }

    public void setData(HomeBean.ResultBean.SeckillInfoBean data) {
        List<HomeBean.ResultBean.SeckillInfoBean.ListBean> list = data.getList();
        SeckillViewPager pager=new SeckillViewPager(context,list);
        rv.setAdapter(pager);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        rv.setLayoutManager(linearLayoutManager);

        //秒杀倒计时：
        dt=Integer.valueOf(data.getEnd_time())-Integer.valueOf(data.getStart_time());
        handler.sendEmptyMessageDelayed(0,1000);
    }
}
