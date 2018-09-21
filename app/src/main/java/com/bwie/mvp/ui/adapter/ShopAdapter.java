package com.bwie.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bwie.mvp.ui.bean.Goods;
import com.bwie.mvp.ui.bean.Pic;
import com.bwie.mvp.ui.event.OnResFreshListener;
import com.bwie.mvp.ui.holder.ShopCartHolder;
import com.bwie.wang.MainActivity;
import com.bwie.wang.R;

import java.util.List;

/**
 * Created by wangbingjun on 2018/9/21.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopCartHolder> {

    private String url = "https://m.360buyimg.com/n0/jfs/t6130/97/1370670410/180682/1109582a/593276b1Nd81fe723.jpg!q70.jpg";
    Context context;
    List<Goods.CartlistBean> data;
    List<Pic.DataBean.ListBean> listBeans;

    public ShopAdapter(Context context, List<Goods.CartlistBean> data ) {
        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public ShopCartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopCartHolder(LayoutInflater.from(context).inflate(R.layout.shop_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopCartHolder holder, final int position) {
         /* 商品图片 */
        Glide.with(context).load(url).into(holder.ivShopCartClothPic);
        /* 商品的基本信息 */
        holder.tvShopCartClothColor.setText("颜色：" + data.get(position).getColor());
        holder.tvShopCartClothSize.setText("尺寸：" + data.get(position).getSize());
        holder.tvShopCartClothName.setText(data.get(position).getProductName());
        holder.tvShopCartShopName.setText(data.get(position).getShopName());
        holder.tvShopCartClothPrice.setText("¥" + data.get(position).getPrice());
        holder.etShopCartClothNum.setText(data.get(position).getCount() + "");
         /* 显示前面的选中状态 */
        if (data.get(position).getIsSelect()){
            holder.ivShopCartClothSel.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_selected));
        }else {
            holder.ivShopCartClothSel.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_unselected));
        }
        if (data.get(position).getIsShopSelect()){
            holder.ivShopCartShopSel.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_selected));
        }else {
            holder.ivShopCartShopSel.setImageDrawable(context.getResources().getDrawable(R.drawable.shopcart_unselected));
        }
         /* 判断是否显示商铺 */
        if (position>0){
             /* 判断是否是同一个商铺的商品 */
            if (data.get(position).getShopId() == data.get(position - 1).getShopId()){
                holder.llShopCartHeader.setVisibility(View.GONE);

            }else {
                holder.llShopCartHeader.setVisibility(View.VISIBLE);
            }
        }else {
            holder.llShopCartHeader.setVisibility(View.VISIBLE);
        }
          /* 判断是否全选并计算 */
        if (mOnResfreshListener != null){
            boolean isSelect = false;
            for (int i=0;i<data.size();i++){
                if (!data.get(i).getIsSelect()){
                    isSelect = false;
                    break;
                }else {
                    isSelect = true;
                }
            }
            mOnResfreshListener.onResfresh(isSelect);
        }
        setGoodsjisuanqi(holder, position);//商品的计算器

          /* 删除操作 */
        holder.ivShopCartClothDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                //重新排序，标记所有商品不同商铺第一个的商品位置
                MainActivity.isSelectFirst(data);
                notifyDataSetChanged();
            }
        });
           /* 单个商品 选中状态 */
        holder.ivShopCartClothSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setSelect(!data.get(position).getIsSelect());
                //通过循环找出不同商铺的第一个商品的位置
                for (int i = 0; i < data.size(); i++){
                    if (data.get(i).isFirst()){
                        //遍历去找出同一家商铺的所有商品的勾选情况
                        for (int j = 0; j < data.size(); j++){
                            //如果是同一家商铺的商品，并且其中一个商品是未选中，那么商铺的全选勾选取消
                            if (data.get(j).getShopId() == data.get(i).getShopId() && !data.get(j).getIsSelect()){
                                data.get(i).setShopSelect(false);
                                break;
                            }else {
                                //如果是同一家商铺的商品，并且所有商品是选中，那么商铺的选中全选勾选
                                data.get(i).setShopSelect(true);
                            }

                        }

                    }

                }
                notifyDataSetChanged();
            }
        });
           /* 商铺选中状态 */
        holder.ivShopCartShopSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).isFirst()){
                    // 商铺选中状态执反
                    data.get(position).setShopSelect(!data.get(position).getIsShopSelect());
                    // 改变商品的选中状态和商铺一样
                    for (int i = 0; i < data.size(); i++){
                        if (data.get(i).getShopId() == data.get(position).getShopId()){
                            data.get(i).setSelect(data.get(position).getIsShopSelect());
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });

    }
    //商品的计算器
    private void setGoodsjisuanqi(@NonNull ShopCartHolder holder, final int position) {
    /* 商品数量加 */
        holder.ivShopCartClothAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setCount(data.get(position).getCount()+1);
                notifyDataSetChanged();

            }
        });
          /* 商品数量减 */
        holder.ivShopCartClothMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getCount()>1){
                    data.get(position).setCount(data.get(position).getCount()-1);
                    notifyDataSetChanged();
                }
            }
        });
    }//商品的计算器

    @Override
    public int getItemCount() {//长度
        return data == null?0:data.size();
    }
    //刷新的接口
    private OnResFreshListener mOnResfreshListener;

    public void setmOnResfreshListener(OnResFreshListener mOnResfreshListener) {
        this.mOnResfreshListener = mOnResfreshListener;
    }
}