package HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.cn.android_testtwo.R;
import com.cn.android_testtwo.goodsActivity;
import com.cn.android_testtwo.goodsBean;

import java.util.List;

public class RecommendViewPager extends RecyclerView.Adapter<itemRecommendHolder> {
    private Context context;
    private List<HomeBean.ResultBean.RecommendInfoBean> data;

    public RecommendViewPager(Context context, List<HomeBean.ResultBean.RecommendInfoBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public itemRecommendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend, null);
        itemRecommendHolder itemRecommendHolder = new itemRecommendHolder(view);
        return itemRecommendHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull itemRecommendHolder holder, final int position) {
        Glide.with(context)
                .load(utils.Imageurl + data.get(position).getFigure())
                .into(holder.recommend_image);
        holder.recommend_price.setText(data.get(position).getCover_price());
        holder.recommend_name.setText(data.get(position).getName());

        holder.recommend_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, data.get(position).getName(), Toast.LENGTH_SHORT).show();
                //添加商品信息：
                goodsBean goods=new goodsBean();
                goods.setName(data.get(position).getName());
                goods.setCover_price(data.get(position).getCover_price());
                goods.setProduct_id(data.get(position).getProduct_id());
                goods.setFigure(utils.Imageurl+data.get(position).getFigure());
                goods.setChecked(true);

                Intent intent=new Intent(context,goodsActivity.class);
                intent.putExtra("Recommend_goods",goods);
                intent.putExtra("value",5);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}

class itemRecommendHolder extends RecyclerView.ViewHolder{
    public ImageView recommend_image;
    public TextView recommend_name,recommend_price;
    public itemRecommendHolder(View itemView) {
        super(itemView);
        recommend_image=itemView.findViewById(R.id.recommend_image);
        recommend_name=itemView.findViewById(R.id.recommend_name);
        recommend_price=itemView.findViewById(R.id.recommend_price);
    }
}
