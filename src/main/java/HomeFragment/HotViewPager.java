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

public class HotViewPager extends RecyclerView.Adapter<itemHotHolder> {
    private Context context;
    private List<HomeBean.ResultBean.HotInfoBean> data;

    public HotViewPager(Context context, List<HomeBean.ResultBean.HotInfoBean> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public itemHotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hot, null);
        itemHotHolder itemHotHolders = new itemHotHolder(view);
        return itemHotHolders;
    }

    @Override
    public void onBindViewHolder(@NonNull itemHotHolder holder, final int position) {
        Glide.with(context)
                .load(utils.Imageurl + data.get(position).getFigure())
                .into(holder.hot_image);
        holder.hot_price.setText(data.get(position).getCover_price()+"");
        holder.hot_name.setText(data.get(position).getName());

        holder.hot_image.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("hot_goods",goods);
                intent.putExtra("value",6);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class itemHotHolder extends RecyclerView.ViewHolder{
    public ImageView hot_image;
    public TextView hot_name,hot_price;
    public itemHotHolder(View itemView) {
        super(itemView);
        hot_image=itemView.findViewById(R.id.hot_image);
        hot_name=itemView.findViewById(R.id.hot_name);
        hot_price=itemView.findViewById(R.id.hot_price);
    }
}
