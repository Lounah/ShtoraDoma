package com.lounah.silkapp.data.local.product;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.lounah.silkapp.data.local.BaseDao;
import com.lounah.silkapp.data.model.Product;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ProductsDao extends BaseDao<Product> {

    @Query("SELECT * FROM products")
    Single<List<Product>> getProducts();
}
