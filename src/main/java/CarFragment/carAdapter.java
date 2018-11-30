package CarFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cn.android_testtwo.CarStorage;
import com.cn.android_testtwo.R;
import com.cn.android_testtwo.goodsBean;
import java.util.ArrayList;
import java.util.List;

public class carAdapter extends RecyclerView.Adapter<carViewHolder>{
    private Context context;
    private List<goodsBean> goodsBeanList;
    private goodsinterface goodsinterface;

    public carAdapter(Context context, List<goodsBean> goodsBeanList, CarFragment.goodsinterface goodsinterface) {
        this.context = context;
        this.goodsBeanList = goodsBeanList;
        this.goodsinterface = goodsinterface;
    }

    @NonNull
    @Override
    public carViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_car,null);
        carViewHolder holder=new carViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final carViewHolder holder, final int position) {
        holder.name2.setText(goodsBeanList.get(position).getName());
        Glide.with(context.getApplicationContext())
                .load(goodsBeanList.get(position).getFigure())
                .into(holder.image2);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < goodsBeanList.size(); i++) {
                    goodsBeanList.get(position).setNumber(goodsBeanList.get(position).getNumber()+1);
                    goodsinterface.SetonClickListerer(position);
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodsBeanList.get(position).getNumber()>1){
                    for (int i = 0; i < goodsBeanList.size(); i++) {
                        goodsBeanList.get(position).setNumber(goodsBeanList.get(position).getNumber()-1);
                        goodsinterface.SetonClickListerer(position);
                    }
                }else{
                    Toast.makeText(context, "已经没有商品可以减少了！", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
                goodsinterface.SetonClickRemover(position);
            }
        });


        holder.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.根据位置找到对应的Bean对象：
                goodsBean goodsBean=goodsBeanList.get(position);
                //2.设置取反状态：
                goodsBean.setChecked(!goodsBean.isChecked());
                //3.刷新状态：
                notifyItemChanged(position);
                //4.刷新数据库：
                goodsinterface.SetonClickListerer(position);
            }
        });

        //检查是否有没有选中的,如果有就将activity里的checkbox取消选中：
        isAllchecks();
        //检查编辑时，是否有没有选中的,如果有就将activity里的checkbox取消选中：
        isAllUpdatechecks();

        holder.check.setChecked(goodsBeanList.get(position).isChecked());
        holder.count.setText(goodsBeanList.get(position).getNumber()+"");
        holder.price.setText(goodsBeanList.get(position).getCover_price());
        holder.sum.setText(String.valueOf((int)Double.parseDouble(goodsBeanList.get(position).getCover_price())*goodsBeanList.get(position).getNumber()));
    }

    public void isAllchecks(){
        int flag = 0;
        for (int i = 0; i < goodsBeanList.size(); i++) {
            goodsBean goodsBean = goodsBeanList.get(i);
            if (goodsBean.isChecked() != true) {
                //显示不全选：
                goodsinterface.SetonClickchecked(false);
            } else {
                flag++;
            }
        }
        if (flag == goodsBeanList.size()) {
            //显示全选:
            goodsinterface.SetonClickchecked(true);
        }
    }

    public void isAllUpdatechecks(){
        int flag = 0;
        for (int i = 0; i < goodsBeanList.size(); i++) {
            goodsBean goodsBean = goodsBeanList.get(i);
            if (goodsBean.isChecked() != true) {
                //显示不全选：
                goodsinterface.SetonClickchecked(false);
            } else {
                flag++;
            }
        }
        if (flag == goodsBeanList.size()) {
            //显示全选:
            goodsinterface.SetonClickchecked(true);
        }
    }
    @Override
    public int getItemCount() {
        return goodsBeanList.size();
    }
}
