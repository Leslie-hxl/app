package LayFragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cn.android_testtwo.R;
import com.cn.android_testtwo.UpLineActivity;

import java.util.List;

public class BaseAdapters extends BaseAdapter{
    private Context context;
    List<javaBean.DataBean> data;
    private NotificationManager notificationManager;
    public BaseAdapters(Context context, List<javaBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view1= LayoutInflater.from(context).inflate(R.layout.item_lay,null);
        TextView tv=view1.findViewById(R.id.tv);
        ImageView imageView=view1.findViewById(R.id.image);
        tv.setText(data.get(position).getTitle());
        Glide.with(context.getApplicationContext())
                .load(data.get(position).getPic())
                .into(imageView);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder nb=new NotificationCompat.Builder(context);
                nb.setAutoCancel(true);
                nb.setSmallIcon(R.drawable.car);
                nb.setContentTitle(data.get(position).getTitle());
                nb.setContentText(data.get(position).getFood_str());
                nb.setDefaults(Notification.DEFAULT_SOUND);
                PendingIntent pendingIntent=PendingIntent
                        .getActivity(context,0,new Intent(context,UpLineActivity.class)
                                ,PendingIntent.FLAG_CANCEL_CURRENT);
                nb.setContentIntent(pendingIntent);
                Notification build = nb.build();
                notificationManager.notify(1,build);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view1;
    }
}

