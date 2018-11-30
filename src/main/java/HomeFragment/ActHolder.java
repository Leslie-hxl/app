package HomeFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cn.android_testtwo.R;
//import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
//import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.util.List;

class ActHolder extends RecyclerView.ViewHolder{
    private Context context;
    public ViewPager vp;
    public ActHolder(Context context, View itemView) {
        super(itemView);
        this.context=context;
        vp=itemView.findViewById(R.id.vp);
//        vp.setOffscreenPageLimit(3);
//        //动画
//        vp.setPageTransformer(true,
//                new ScaleInTransformer());
    }

    public void setData(final List<HomeBean.ResultBean.ActInfoBean> data) {
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView=new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(context).load(utils.Imageurl+data.get(position).getIcon_url()).into(imageView);
                container.addView(imageView);

                final String name = data.get(position).getName();
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                    }
                });
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
    }
}
