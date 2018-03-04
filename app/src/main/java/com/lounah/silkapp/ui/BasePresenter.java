package com.lounah.silkapp.ui;


public abstract class BasePresenter<V> {

    public abstract void setView(V view);

    public abstract void dropView();

}
