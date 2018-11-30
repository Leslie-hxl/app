package HomeFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import com.cn.android_testtwo.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

class MyViewHolder extends RecyclerView.ViewHolder{
    private Context context;
    private GridView gridView;
    private ChannelAdapter channelAdapter;

    public MyViewHolder(Context context, View itemView) {
        super(itemView);
        this.context=context;
        gridView=itemView.findViewById(R.id.gv_channel);
    }
    public void setData(List<HomeBean.ResultBean.ChannelInfoBean> channel_info){
        channelAdapter=new ChannelAdapter(context,channel_info);
        gridView.setAdapter(channelAdapter);
    }
}
