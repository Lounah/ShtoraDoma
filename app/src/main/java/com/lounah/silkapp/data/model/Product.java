package com.lounah.silkapp.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey
    private int id;

    private String header;

    private String image_header_url;

    private int comments_quantity;

    @Ignore
    public Product() {}


    public Product(int id, String header, String image_header_url, int comments_quantity) {
        this.id = id;
        this.header = header;
        this.image_header_url = image_header_url;
        this.comments_quantity = comments_quantity;
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getImage_header_url() {
        return image_header_url;
    }

    public int getComments_quantity() {
        return comments_quantity;
    }
}
