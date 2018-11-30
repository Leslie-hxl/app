package HomeFragment;


import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //fresco加载图片：
        Glide.with(context.getApplicationContext())
                .load(utils.Imageurl+path)
                .into(imageView);
    }
    public ImageView createImageView(Context context){
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        return simpleDraweeView;
    }
}
