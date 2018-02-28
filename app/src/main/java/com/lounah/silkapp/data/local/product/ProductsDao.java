package com.lounah.silkapp.data.local.product;

import android.arch.persistence.room.Dao;

import com.lounah.silkapp.data.local.BaseDao;
import com.lounah.silkapp.data.model.Product;

@Dao
public interface ProductsDao extends BaseDao<Product> {
}
