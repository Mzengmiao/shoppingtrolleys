package com.shoppingtrolley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shoppingtrolley.adapter.InventoryAdapter;
import com.shoppingtrolley.api.Servers;
import com.shoppingtrolley.bean.InventoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rlw)
    RecyclerView rlw;
    public static Map<Integer, Boolean> map;

    @BindView(R.id.money)
    TextView money;
    private ArrayList<InventoryBean.DataBean> mlist = new ArrayList<>();
    private InventoryAdapter adapter;
    private int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Servers.baseUrl)
                .build();
        Servers servers = retrofit.create(Servers.class);
        Observable<InventoryBean> data = servers.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InventoryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(InventoryBean inventoryBean) {
                        if (inventoryBean!=null){
                            mlist.addAll(inventoryBean.getData());
                            adapter.setMlist(mlist);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        rlw.setLayoutManager(new LinearLayoutManager(this));
        map = new HashMap<>();
        adapter = new InventoryAdapter(this, mlist);
        rlw.setAdapter(adapter);
        adapter.setA(new InventoryAdapter.a() {
            @Override
            public void setOnClick(View view, int i, InventoryBean.DataBean bean) {
                int num = bean.getNum();
                CheckBox rle_cb = view.findViewById(R.id.rlw_cb);
                if (rle_cb.isChecked()) {
                    a += num;
                    money.setText("合计："+a+"$");
                }else {
                    a-=num;
                    money.setText("合计："+a+"$");
                }
            }
        });
    }
}
