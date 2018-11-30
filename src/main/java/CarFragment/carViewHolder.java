package CarFragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.cn.android_testtwo.R;
//import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
//import com.zhy.magicviewpager.transformer.ScaleInTransformer;

class carViewHolder extends RecyclerView.ViewHolder{
    public ViewPager vp;
    public CheckBox check;
    public Button shanchu;
    public ImageView image2,add,delete;
    public TextView name2,price,sum,count;


    public carViewHolder(View itemView) {
        super(itemView);
        vp=itemView.findViewById(R.id.vp);
        check=itemView.findViewById(R.id.check);
        shanchu=itemView.findViewById(R.id.shanchu);
        image2=itemView.findViewById(R.id.image2);
        add=itemView.findViewById(R.id.add);
        delete=itemView.findViewById(R.id.delete);
        name2=itemView.findViewById(R.id.name2);
        price=itemView.findViewById(R.id.price);
        sum=itemView.findViewById(R.id.sum);
        count=itemView.findViewById(R.id.count);
    }
}
