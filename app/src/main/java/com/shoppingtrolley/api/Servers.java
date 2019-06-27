package com.shoppingtrolley.api;

import com.shoppingtrolley.bean.InventoryBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Servers {
    String baseUrl="http://www.qubaobei.com/ios/cf/";
    @GET("dish_list.php?stage_id=1&limit=20&page=1")
    Observable<InventoryBean>getData();
}
