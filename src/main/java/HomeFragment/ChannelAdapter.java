package HomeFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cn.android_testtwo.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

class ChannelAdapter extends BaseAdapter{

    private Context context;
    private List<HomeBean.ResultBean.ChannelInfoBean> channel_info;

    public ChannelAdapter(Context context, List<HomeBean.ResultBean.ChannelInfoBean> channel_info) {
        this.context = context;
        this.channel_info = channel_info;
    }


    @Override
    public int getCount() {
        return channel_info.size();
    }

    @Override
    public Object getItem(int position) {
        return channel_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChannelViewHolder Channe_holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.change_item,null);
            Channe_holder=new ChannelViewHolder();
            Channe_holder.image=convertView.findViewById(R.id.image);
            Channe_holder.name=convertView.findViewById(R.id.name);
            convertView.setTag(Channe_holder);
        }else {
            Channe_holder= (ChannelViewHolder) convertView.getTag();
        }
        Channe_holder.name.setText(channel_info.get(position).getChannel_name());
        String image = channel_info.get(position).getImage();
        Channe_holder.image.setImageURI(utils.Imageurl+image);
        return convertView;
    }

    static class ChannelViewHolder{
         SimpleDraweeView image;
         TextView name;
    }
}
