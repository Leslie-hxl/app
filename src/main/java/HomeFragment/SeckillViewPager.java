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

public class SeckillViewPager extends RecyclerView.Adapter<itemSeckillHolder>{
    private Context context;
    private List<HomeBean.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillViewPager(Context context, List<HomeBean.ResultBean.SeckillInfoBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public itemSeckillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_seckill,null);
        itemSeckillHolder holder=new itemSeckillHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final itemSeckillHolder holder, final int position) {

        Glide.with(context)
                .load(utils.Imageurl+list.get(position).getFigure())
                .into(holder.imageView);
        holder.c_price.setText(list.get(position).getCover_price());
        holder.o_price.setText(list.get(position).getOrigin_price());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, list.get(position).getName(), Toast.LENGTH_SHORT).show();
                //添加商品信息：
                goodsBean goods=new goodsBean();
                goods.setName(list.get(position).getName());
                goods.setFigure(utils.Imageurl+list.get(position).getFigure());
                goods.setProduct_id(list.get(position).getProduct_id());
                goods.setCover_price(list.get(position).getCover_price());
                goods.setChecked(true);

                Intent intent=new Intent(context,goodsActivity.class);
                intent.putExtra("Seckill_goods",goods);
                intent.putExtra("value",4);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class itemSeckillHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView c_price,o_price;
    public itemSeckillHolder(View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.seckill_image);
        c_price=itemView.findViewById(R.id.c_price);
        o_price=itemView.findViewById(R.id.o_price);
    }
}
