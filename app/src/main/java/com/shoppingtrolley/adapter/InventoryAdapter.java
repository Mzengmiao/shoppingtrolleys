package com.shoppingtrolley.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shoppingtrolley.MainActivity;
import com.shoppingtrolley.R;
import com.shoppingtrolley.bean.InventoryBean;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<InventoryBean.DataBean>mlist;

    public InventoryAdapter(Context context, ArrayList<InventoryBean.DataBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    public void setMlist(ArrayList<InventoryBean.DataBean> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_item, null, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.rlw_introduce.setText(mlist.get(i).getTitle());
        viewHolder.rlw_money.setText(mlist.get(i).getNum()+"$");
        Glide.with(context).load(mlist.get(i).getPic()).into(viewHolder.rlw_img);
        final Integer integer = new Integer(i);
        viewHolder.rlw_cb.setTag(integer);
        if (MainActivity.map.containsKey(integer)){
            viewHolder.rlw_cb.setChecked(true);
        }else {
            viewHolder.rlw_cb.setChecked(false);
        }
        viewHolder.rlw_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.rlw_cb.isChecked()){
                    MainActivity.map.put(integer,true);
                }else {
                    MainActivity.map.remove(integer);
                }
                if (a!=null){
                    a.setOnClick(view,i,mlist.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView rlw_img;
        private final TextView rlw_introduce;
        private final TextView rlw_money;
        private final CheckBox rlw_cb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlw_img = itemView.findViewById(R.id.rlw_img);
            rlw_introduce = itemView.findViewById(R.id.rlw_introduce);
            rlw_money = itemView.findViewById(R.id.rlw_money);
            rlw_cb = itemView.findViewById(R.id.rlw_cb);
        }
    }
    private  a a;

    public void setA(InventoryAdapter.a a) {
        this.a = a;
    }

    public interface a{
        void setOnClick(View view,int i,InventoryBean.DataBean bean);
    }
}
