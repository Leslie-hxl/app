package HomeFragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.android_testtwo.R;

import java.util.List;

public class HotHolder extends RecyclerView.ViewHolder{
    private Context context;
    public TextView hot_name,hot_price;
    public ImageView hot_image;
    public RecyclerView rv;

    public HotHolder(Context context, View itemView) {
        super(itemView);
        this.context=context;
        hot_name=itemView.findViewById(R.id.hot_name);
        hot_price=itemView.findViewById(R.id.hot_price);
        hot_image=itemView.findViewById(R.id.hot_image);
        rv=itemView.findViewById(R.id.rv);
    }

    public void setData(List<HomeBean.ResultBean.HotInfoBean> data) {
        HotViewPager pager=new HotViewPager(context,data);
        rv.setAdapter(pager);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
        rv.setLayoutManager(gridLayoutManager);
    }
}
