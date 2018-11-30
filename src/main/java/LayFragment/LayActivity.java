package LayFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cn.android_testtwo.R;
import com.handmark.pulltorefresh.library.LoadingLayoutProxy;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LayActivity extends Fragment {
    @BindView(R.id.bar)
    RelativeLayout bar;
    Unbinder unbinder;
    byte bitmaps[] = new byte[20];
    @BindView(R.id.pull)
    PullToRefreshListView pull;
    int pager = 1;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    private BaseAdapters adapters;
    private List<javaBean.DataBean> datas = new ArrayList<>();
    private LoadingLayoutProxy loadingLayoutProxy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lay_activity, null);
        unbinder = ButterKnife.bind(this, view);

        bar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                drawerlayout.openDrawer(GravityCompat.START);
                return true;
            }
        });


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.na:
                        Toast.makeText(getActivity(), "sb", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        //设置模式：
        pull.setMode(PullToRefreshBase.Mode.BOTH);
        add();

        //下拉刷新：
        pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                pager = 1;
                datas.clear();
                add();
                pull.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pull.onRefreshComplete();
                    }
                }, 1000);
            }

            //上拉加载：
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                pager++;
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                pull.onRefreshComplete();
                adapters.notifyDataSetChanged();
            }
        });

        //刷新效果：
        loadingLayoutProxy = (LoadingLayoutProxy) pull.getLoadingLayoutProxy();
        loadingLayoutProxy.setLoadingDrawable(getResources().getDrawable(R.drawable.car));
        loadingLayoutProxy.setPullLabel("上拉加载");
        loadingLayoutProxy.setRefreshingLabel("下拉刷新");
        pull.setPullToRefreshOverScrollEnabled(true);
        return view;
    }

    //添加数据的方法：第三缓存
    public void add() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.qubaobei.com/ios/cf/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyInterface myInterface = retrofit.create(MyInterface.class);
        Call<javaBean> getintent = myInterface.getintent(1, 20, pager);
        getintent.enqueue(new Callback<javaBean>() {
            @Override
            public void onResponse(Call<javaBean> call, Response<javaBean> response) {
                javaBean body = response.body();
                final List<javaBean.DataBean> data = body.getData();

                for (int i = 0; i < data.size(); i++) {
                    bitmaps[i]= Byte.parseByte(data.get(i).getPic());
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length);

                datas.addAll(data);
                adapters = new BaseAdapters(getActivity(), datas);
                pull.setAdapter(adapters);
                adapters.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<javaBean> call, Throwable t) {

            }
        });
    }
}
