package HomeFragment;

import android.content.Context;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.android_testtwo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecommendHolder extends RecyclerView.ViewHolder{
    private Context context;
    public TextView recommend_name,recommend_price;
    public ImageView recommend_image;
    public RecyclerView rv;

    public RecommendHolder(Context context, View itemView) {
        super(itemView);
        this.context=context;
        recommend_name=itemView.findViewById(R.id.recommend_name);
        recommend_price=itemView.findViewById(R.id.recommend_price);
        recommend_image=itemView.findViewById(R.id.recommend_image);
        rv=itemView.findViewById(R.id.rv);
    }

    public void setData(List<HomeBean.ResultBean.RecommendInfoBean> data) {
        RecommendViewPager pager=new RecommendViewPager(context,data);
        rv.setAdapter(pager);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,3);
        rv.setLayoutManager(gridLayoutManager);
    }
}
