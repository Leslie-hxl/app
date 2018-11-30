package HomeFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.android_testtwo.R;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

public class HomeActivity extends Fragment {
    @BindView(R.id.tv_search_home)
    TextView tvSearchHome;
    @BindView(R.id.tv_message_home)
    TextView tvMessageHome;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.top)
    ImageButton top;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_activity, null);
        unbinder = ButterKnife.bind(this, view);
        /**
         * 解析数据，获取主页面轮播image资源：
         */
        OkHttpUtils
                .get()
                .url(HomeFragment.utils.Homejson)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        if (response!=null){
                            Gson gson = new Gson();
                            HomeBean homeBean = gson.fromJson(response, HomeBean.class);
                            HomeBean.ResultBean result = homeBean.getResult();

                            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),1);
                            //设置跨度监听事件：
                            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                @Override
                                public int getSpanSize(int position) {
                                    if (position<=3){
                                        //隐藏：
                                        top.setVisibility(View.GONE);
                                    }else {
                                        //显示:
                                        top.setVisibility(View.VISIBLE);
                                    }
                                    //只能返回1
                                    return 1;
                                }
                            });
                            rv.setLayoutManager(gridLayoutManager);

                            MyRecycler recycler=new MyRecycler(getActivity(),result);
                            rv.setAdapter(recycler);
                        }
                    }
                });
                //置顶监听;
                top.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rv.scrollToPosition(0);
                    }
                });

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
