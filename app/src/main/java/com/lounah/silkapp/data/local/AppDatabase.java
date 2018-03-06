package com.lounah.silkapp.data.local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.lounah.silkapp.data.local.comment.CommentsDao;
import com.lounah.silkapp.data.local.dialog.DialogsDao;
import com.lounah.silkapp.data.local.message.MessagesDao;
import com.lounah.silkapp.data.local.product.ProductsDao;
import com.lounah.silkapp.data.local.users.UsersDao;
import com.lounah.silkapp.data.model.Comment;
import com.lounah.silkapp.data.model.Dialog;
import com.lounah.silkapp.data.model.Message;
import com.lounah.silkapp.data.model.Product;
import com.lounah.silkapp.data.model.User;

@Database(entities = {Dialog.class, Message.class, Product.class, Comment.class, User.class}, version = 7, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DialogsDao dialogsDao();

    public abstract MessagesDao messagesDao();

    public abstract ProductsDao productsDao();

    public abstract CommentsDao commentsDao();

    public abstract UsersDao usersDao();

}
