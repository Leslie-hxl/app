package CarFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.android_testtwo.CarStorage;
import com.cn.android_testtwo.R;
import com.cn.android_testtwo.goodsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CarActivity extends Fragment {
    @BindView(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.allcheck)
    CheckBox allcheck;
    @BindView(R.id.goods_count2)
    TextView goodsCount2;
    @BindView(R.id.goods_price2)
    TextView goodsPrice2;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @BindView(R.id.ll_empty_shopcart)
    LinearLayout llEmptyShopcart;
    Unbinder unbinder;
    @BindView(R.id.layout1)
    LinearLayout layout1;
    @BindView(R.id.layout2)
    LinearLayout layout2;
    @BindView(R.id.ibGoodInfoUpdates)
    TextView ibGoodInfoUpdates;
    @BindView(R.id.allcheck2)
    CheckBox allcheck2;
    @BindView(R.id.choice_save)
    Button choiceSave;
    @BindView(R.id.choice_delete)
    Button choice_delete;
    private View view1;
    private carAdapter adapter;
    private List<goodsBean> goodsBeanList=new ArrayList<>();
    //编辑状态：
    private static final int Action_edit = 1;
    //完成状态：
    private static final int Action_completf = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.see_activity, null);
        unbinder = ButterKnife.bind(this, view);
        view1 = inflater.inflate(R.layout.empty_cart, null);
        setDate();
        setupdated();
        /**
         * 设置全选和全反选：
         */
        allcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取状态：
                boolean checked = allcheck.isChecked();
                if (checked == true) {
                    setAllchecks(true);
                    setprice();
                } else if (checked == false) {
                    setAllchecks(false);
                    setprice();
                }
            }
        });
        /**
         * 设置编辑时的全选和反选：
         */
        allcheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取状态：
                boolean checked = allcheck2.isChecked();
                if (checked == true) {
                    setAllchecks(true);
                    setprice();
                } else if (checked == false) {
                    setAllchecks(false);
                    setprice();
                }
            }
        });

        /**
         * 设置编辑时的删除：
         */
        choice_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChoice_delete();
            }
        });
        return view;
    }

    /**
     * 设配资源：
     */
    public void setDate() {
        goodsBeanList = CarStorage.getInstance().getAllData();

        if (goodsBeanList.toString() != null && goodsBeanList.size() > 0) {
            //设配资源：
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(manager);
            adapter = new carAdapter(getActivity(), goodsBeanList, new goodsinterface() {
                @Override
                public void SetonClickListerer(int posation) {
                    save(posation);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void SetonClickRemover(int posation) {
                    remove(posation);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void SetonClickchecked(boolean ischeck) {
                    allcheck.setChecked(ischeck);
                    allcheck2.setChecked(ischeck);
                }
            });
            rv.setAdapter(adapter);
        } else if (goodsBeanList.toString() == null || goodsBeanList.size() == 0) {
            //没有数据则显示布局：
            rv.setVisibility(View.GONE);
            view1.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 修改商品数量：
     *
     * @param posation
     */
    private void save(int posation) {
        CarStorage.getInstance().updateDate(goodsBeanList.get(posation));
        setprice();
    }

    /**
     * 删除商品：
     *
     * @param posation
     */
    private void remove(int posation) {
        goodsBeanList.remove(posation);
        if (goodsBeanList.size() > 0) {
            CarStorage.getInstance().deleteDate(goodsBeanList.get(posation));
        }
        setprice();
    }



    //设置全选和反选：
    public void setAllchecks(boolean checked) {
        for (int i = 0; i < goodsBeanList.size(); i++) {
            goodsBean goodsBean = goodsBeanList.get(i);
            goodsBean.setChecked(checked);
            adapter.notifyDataSetChanged();
        }
    }

    //设置一键删除：
    private void setChoice_delete() {
        for (int i = 0; i < goodsBeanList.size(); i++) {
            goodsBean goodsBean = goodsBeanList.get(i);
            if (goodsBean.isChecked()==true){
                goodsBeanList.remove(i);
                if (goodsBeanList.size() > 0) {
                    CarStorage.getInstance().deleteDate(goodsBeanList.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
        setprice();
    }
    /**
     * 编辑功能：
     */
    private void setupdated() {
        //设置默认的完成状态：
        ibGoodInfoUpdates.setTag(Action_edit);
        //点击隐藏：
        ibGoodInfoUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                if (tag == Action_edit) {
                    showdelete();
                } else {
                    hidedelete();
                }
            }
        });
    }

    /**
     * 点击到编辑状态，隐藏完成状态：
     * 可全选，删除，收藏
     */
    private void showdelete() {
        //切换为编辑状态：
        ibGoodInfoUpdates.setTag(Action_completf);
        //编辑文字;
        ibGoodInfoUpdates.setText("完成");
        //变成非勾选：
        if (adapter != null) {
            setAllchecks(false);
        }
        //隐藏完成状态，显示编辑状态：
        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.VISIBLE);
    }

    /**
     * 点击到完成状态，隐藏编辑状态
     * 可全选 合计 结算
     */
    private void hidedelete() {
        //切换成完成状态：
        ibGoodInfoUpdates.setTag(Action_edit);
        //编辑文字;
        ibGoodInfoUpdates.setText("编辑");
        //变成非勾选：
        if (adapter != null) {
            setAllchecks(true);
        }
        //隐藏完成状态，显示编辑状态：
        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);
    }


    //计算商品的总价：
    public void setprice() {
        int count = 0;
        int money = 0;
        for (int i1 = 0; i1 < goodsBeanList.size(); i1++) {
            int temp = 0;
            if (goodsBeanList.get(i1).isChecked() == true) {
                temp = (int) Double.parseDouble(goodsBeanList.get(i1).getCover_price()) * goodsBeanList.get(i1).getNumber();
                money += temp;
                count += goodsBeanList.get(i1).getNumber();
                continue;
            }
        }
        goodsCount2.setText(count + "");
        goodsPrice2.setText(money + "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
