package com.bwie.wang;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.mvp.ui.adapter.ShopAdapter;
import com.bwie.mvp.ui.bean.Goods;
import com.bwie.mvp.ui.event.OnResFreshListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvShopCartSubmit, tvShopCartSelect, tvShopCartTotalNum;

    private RecyclerView rlvShopCart, rlvHotProducts;
    private ShopAdapter mShopCartAdapter;
    private LinearLayout llPay;
    private RelativeLayout rlHaveProduct;
    private List<Goods.CartlistBean> mAllOrderList = new ArrayList<>();
    private ArrayList<Goods.CartlistBean> mGoPayList = new ArrayList<>();
    private List<String> mHotProductsList = new ArrayList<>();
    private TextView tvShopCartTotalPrice;
    private int mCount, mPosition;
    private float mTotalPrice1;
    private boolean mSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShopCartSelect = findViewById(R.id.tv_shopcart_addselect);
        tvShopCartTotalPrice = findViewById(R.id.tv_shopcart_totalprice);
        tvShopCartTotalNum = findViewById(R.id.tv_shopcart_totalnum);

        rlHaveProduct = findViewById(R.id.rl_shopcart_have);
        rlvShopCart = findViewById(R.id.rlv_shopcart);
        llPay = findViewById(R.id.ll_pay);

        tvShopCartSubmit = findViewById(R.id.tv_shopcart_submit);

        rlvShopCart.setLayoutManager(new LinearLayoutManager(this));
        mShopCartAdapter = new ShopAdapter(this,mAllOrderList);
        rlvShopCart.setAdapter(mShopCartAdapter);
        //实时监控全选按钮
        mShopCartAdapter.setmOnResfreshListener(new OnResFreshListener() {
            @Override
            public void onResfresh(boolean isSelect) {
                mSelect= isSelect;
                if (isSelect){
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                }else {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                }
                // 计算总价
                float mTotalPrice = 0;
                int mTotalNum = 0;
                mTotalPrice1 = 0;
                mGoPayList.clear();
                // 遍历所有商品 计算总价
                for (int i = 0; i < mAllOrderList.size(); i++){
                    if (mAllOrderList.get(i).getIsSelect()){
                        mTotalPrice += Float.parseFloat(mAllOrderList.get(i).getPrice()) * mAllOrderList.get(i).getCount();
                        mTotalNum += 1;
                        mGoPayList.add(mAllOrderList.get(i));
                    }
                    mTotalPrice1 = mTotalPrice;
                    tvShopCartTotalPrice.setText("总价：" + mTotalPrice);
                    tvShopCartTotalNum.setText("共" + mTotalNum + "件商品");
                }
            }
        });
        //全选
        tvShopCartSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelect = !mSelect;
                if (mSelect){
                     /* 全选 */
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    for (int i = 0; i < mAllOrderList.size(); i++){
                        mAllOrderList.get(i).setSelect(true);
                        mAllOrderList.get(i).setShopSelect(true);

                    }
                }else {
                     /* 全不选 */
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    tvShopCartSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    for (int i = 0; i < mAllOrderList.size(); i++){
                        mAllOrderList.get(i).setSelect(false);
                        mAllOrderList.get(i).setShopSelect(false);
                    }
                }
                mShopCartAdapter.notifyDataSetChanged();
            }
        });
        initData();
        mShopCartAdapter.notifyDataSetChanged();

    }

    private void initData() {
        for (int i = 0; i < 3; i++){
            Goods.CartlistBean sb = new Goods.CartlistBean();
            sb.setShopId(1);
            sb.setPrice("256.0");
            sb.setSize("45");
            sb.setDefaultPic("https://m.360buyimg.com/n0/jfs/t9004/210/1160833155/647627/ad6be059/59b4f4e1N9a2b1532.jpg");
            sb.setProductName("中秋节月饼");
            sb.setShopName("商家1");
            sb.setColor("花的");
            sb.setCount(1);
            mAllOrderList.add(sb);
        }
        for (int i = 0; i < 3; i++){
            Goods.CartlistBean sb = new Goods.CartlistBean();
            sb.setShopId(2);
            sb.setPrice("12000.0");
            sb.setDefaultPic("https://m.360buyimg.com/n0/jfs/t6130/97/1370670410/180682/1109582a/593276b1Nd81fe723.jpg!q70.jpg");
            sb.setProductName("新款Apple");
            sb.setShopName("商家2");
            sb.setColor("银");
            sb.setCount(1);
            mAllOrderList.add(sb);
        }
        for (int i = 0; i < 2; i++){
            Goods.CartlistBean sb = new Goods.CartlistBean();
            sb.setShopId(3);
            sb.setPrice("5599.0");
            sb.setDefaultPic("http://img2.3lian.com/2014/c7/25/d/40.jpg");
            sb.setProductName("戴尔DELL灵越游匣15PR-6648B GTX1050 15.6英寸游戏笔记本电脑(i5-7300HQ 8G 128GSSD+1T 4G独显 IPS)黑");
            sb.setShopName("商家3");
            sb.setColor("银光银");
            sb.setCount(1);
            mAllOrderList.add(sb);
        }
        for (int i = 0; i < 2; i++){
            Goods.CartlistBean sb = new Goods.CartlistBean();
            sb.setShopId(4);
            sb.setPrice("5599.0");
            sb.setDefaultPic("http://img2.3lian.com/2014/c7/25/d/40.jpg");
            sb.setProductName("戴尔DELL灵越游匣15PR-6648B GTX1050 15.6英寸游戏笔记本电脑(i5-7300HQ 8G 128GSSD+1T 4G独显 IPS)黑");
            sb.setShopName("商家4");
            sb.setColor("银光银");
            sb.setCount(1);
            mAllOrderList.add(sb);
        }
        isSelectFirst(mAllOrderList);
    }

    public static void isSelectFirst(List<Goods.CartlistBean> list) {
        // 1. 判断是否有商品 有商品 根据商品是否是第一个显示商铺
        if (list.size() > 0){
            //头个商品一定属于它所在商铺的第一个位置，isFirst标记为1.
            list.get(0).setFirst(true);
            for (int i = 2; i < list.size(); i++){
                //每个商品跟它前一个商品比较，如果Shopid相同isFirst则标记为2，
                //如果Shopid不同，isFirst标记为1.
                if (list.get(i).getShopId() == list.get(i - 1).getShopId()){
                    list.get(i).setFirst(false);
                }else {
                    list.get(i).setFirst(true);
                }
            }
        }
    }

}
